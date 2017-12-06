package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.host.model.Cycle.Segment;
import de.aboutflash.archerytime.model.ScreenState;
import de.aboutflash.archerytime.model.SettingsModel;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

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
    runSegment(getNextSegment());
  }

  abstract protected void createSegments();

  protected LinkedList<Segment> getSegments() {
    return segments;
  }

  private void runSegment(Segment segment) {
    stopTask();

    if (segment == null)
      return;

    remainingTimeMillis = segment.getDurationMillis();
    endTimeMillis = Math.max(segment.getOffsetMillis(), 0.0); // no negative values please
    message = segment.getMessage();
    screen = segment.getScreen();
    sequence = segment.getSequence();

    announce.nTimes(segment.getStartWhistleCount());

    if (remainingTimeMillis <= endTimeMillis) {
      return;
    }

    cycleTimer = new Timer();
    cycleTask = new TimerTask() {
      @Override
      public void run() {
        decreaseRemainingTime(100);

        System.out.print('.');

        if (remainingTimeMillis <= endTimeMillis) {
          startNextStep();
        }
      }
    };

    // special case of sequence UP30. We want to continue counting immediately w/o delay
    final long delay = screen == ScreenState.Screen.SHOOT_UP30 ? 0L : 1_000L;
    cycleTimer.scheduleAtFixedRate(cycleTask, delay, 100);
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
    currentSegment = segmentIterator != null && segmentIterator.hasNext()
        ? segmentIterator.next() : null;

    return currentSegment;
  }

  private volatile double endTimeMillis = 0.0;

  private volatile String message = "";

  private volatile double remainingTimeMillis = settingsModel.getTotalShootingTimeMillis();

  @Override
  public synchronized double getRemainingTimeMillis() {
    return remainingTimeMillis;
  }

  @Override
  public synchronized void setRemainingTimeMillis(double value) {
    remainingTimeMillis = value;
  }

  @Override
  public synchronized void decreaseRemainingTime(double milliseconds) {
    remainingTimeMillis -= milliseconds;
  }

  @Override
  public synchronized int getRemainingTimeSeconds() {
    return (int) Math.floor(getRemainingTimeMillis() * MILLIS_TO_SECONDS);
  }

  private volatile ScreenState.Screen screen = null;

  private volatile ScreenState.Sequence sequence = null;


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

}
