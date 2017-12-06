package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.model.ScreenState;
import de.aboutflash.archerytime.model.SettingsModel;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.common.base.Preconditions.checkState;
import static de.aboutflash.archerytime.model.ScreenState.Screen.MESSAGE;

/**
 * Class
 *
 * @author falk@aboutflash.de on 27.11.2017.
 */
public abstract class FITACycleModelBase implements FITACycleModel {

  protected static final double MILLIS_TO_SECONDS = 0.001;

  private final AudioAnnounce announce;
  private final SettingsModel settingsModel = SettingsModel.getInstance();

  private TimerTask cycleTask;
  private Timer cycleTimer;

  private final LinkedList<Segment> segments = new LinkedList<>();
  private Iterator<Segment> segmentIterator;
  private Segment currentSegment;


  private volatile double endTimeMillis = 0.0;
  private volatile String message = "";
  private volatile double remainingTimeMillis = settingsModel.getTotalShootingTimeMillis();
  private volatile ScreenState.Screen screen = null;
  private volatile ScreenState.Sequence sequence = null;
  private boolean isRepeating = false;

  private boolean isDisposed = false;


  public FITACycleModelBase() {
    announce = new AudioAnnounce(settingsModel.getSoundFileLocation().toString());
    createSegments();
    segmentIterator = segments.iterator();
  }

  @Override
  public String getName() {
    return "BASE_IMPL";
  }

  @Override
  public int getLength() {
    return getSegments().size();
  }

  @Override
  public int getCurrentSegmentIdx() {
    return segments.indexOf(currentSegment);
  }

  @Override
  public void startNextStep() {
    checkState(!isDisposed);
    runSegment(getNextSegment());
  }

  abstract protected void createSegments();

  protected LinkedList<Segment> getSegments() {
    return segments;
  }

  private void runSegment(Segment segment) {
    stopTask();

    currentSegment = segment;

    if (segment == null)
      return;

    remainingTimeMillis = segment.getDurationMillis();
    endTimeMillis = Math.max(segment.getOffsetMillis(), 0.0); // no negative values please
    message = segment.getMessage();
    screen = segment.getScreen();
    sequence = segment.getSequence();

    announce.nTimes(segment.getStartWhistleCount());

    // stop on screens with no/infinite duration
    if (remainingTimeMillis <= endTimeMillis) {
      return;
    }

    cycleTimer = new Timer();


    final long UPDATE_RATE = 100L;
    final double DECREASE_AMOUNT = (double) UPDATE_RATE;

    cycleTask = new TimerTask() {
      @Override
      public void run() {
        decreaseRemainingTime(DECREASE_AMOUNT);

        System.out.print('.');

        if (remainingTimeMillis <= endTimeMillis) {
          startNextStep();
        }
      }
    };

    // special case of sequence UP30. We want to continue counting immediately w/o delay
    final long delay = screen == ScreenState.Screen.SHOOT_UP30 ? 0L : 1_000L;
    cycleTimer.scheduleAtFixedRate(cycleTask, delay, UPDATE_RATE);
  }

  private void stopTask() {
    if (cycleTask != null) {
      cycleTask.cancel();
    }
    if (cycleTimer != null) {
      cycleTimer.purge();
      cycleTimer.cancel();
    }
  }

  @Nullable
  private Segment getNextSegment() {
    Segment segment = null;
    if (segmentIterator != null) {
      if (segmentIterator.hasNext()) {
        segment = segmentIterator.next();
      } else if (isRepeating) {
        // fetch new iterator to start over
        segmentIterator = segments.iterator();
        segment = segmentIterator.next();
      }
    }

    return segment;
  }


  @Override
  public synchronized double getRemainingTimeMillis() {
    return remainingTimeMillis;
  }

  @Override
  public synchronized void setRemainingTimeMillis(double value) {
    checkState(!isDisposed);
    remainingTimeMillis = value;
  }

  @Override
  public synchronized void decreaseRemainingTime(double milliseconds) {
    checkState(!isDisposed);
    remainingTimeMillis -= milliseconds;
  }

  @Override
  public synchronized int getRemainingTimeSeconds() {
    return (int) Math.floor(getRemainingTimeMillis() * MILLIS_TO_SECONDS);
  }

  @Override
  public boolean isRepeating() {
    return isRepeating;
  }

  @Override
  public void setRepeating(boolean value) {
    checkState(!isDisposed);
    isRepeating = value;
  }

  @Override
  public synchronized ScreenState getScreenState() {
    ScreenState screenState;

    // TODO: no null values here
    if (screen != null && screen.equals(MESSAGE)) {
      screenState = new ScreenState(screen, message);
    } else
      screenState = new ScreenState(screen, sequence, getRemainingTimeSeconds());

    return screenState;
  }

  @Override
  public void dispose() {
    isDisposed = true;

    stopTask();
    cycleTimer = null;
    cycleTask = null;
  }
}
