package de.aboutflash.archerytime.server.model;

import de.aboutflash.archerytime.model.ScreenState;
import de.aboutflash.archerytime.model.SettingsModel;
import de.aboutflash.archerytime.server.model.Cycle.Segment;
import javafx.collections.ObservableList;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import static de.aboutflash.archerytime.model.ScreenState.Screen.*;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.AB;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.CD;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Class
 *
 * @author falk@aboutflash.de on 27.11.2017.
 */
public class FITACycleABCD implements FITACycleModel {

  private static final double MILLIS_TO_SECONDS = 0.001;

  private final AudioAnnounce announce;
  private final SettingsModel settingsModel = SettingsModel.getInstance();

  private TimerTask cycleTask;
  private Timer cycleTimer;

  private final LinkedList<Segment> segments = new LinkedList<>();
  private Iterator<Segment> segmentIterator;

  public FITACycleABCD() {
    announce = new AudioAnnounce(settingsModel.getSoundFileLocation().toString());

    segments.add(new Segment(AB, STOP, 0, 0));
    segments.add(new Segment(AB, STEADY, settingsModel.getSteadyTimeMillis(), 1));
    segments.add(new Segment(AB, SHOOT, settingsModel.getTotalShootingTimeMillis(), 2));
    segments.add(new Segment(AB, SHOOT_UP30, settingsModel.getShootingUp30WarningTimeMillis(), 0));
    segments.add(new Segment(CD, STEADY, settingsModel.getSwapTimeMillis(), 3));
    segments.add(new Segment(CD, SHOOT, settingsModel.getTotalShootingTimeMillis(), 2));
    segments.add(new Segment(CD, SHOOT_UP30, settingsModel.getShootingUp30WarningTimeMillis(), 0));
    segments.add(new Segment(CD, STOP, 0, 3));

    segmentIterator = segments.iterator();
  }

  @Override
  public void startNextStep() {
    runSegment(getNextSegment());
  }

  private void runSegment(Segment segment) {
    stopTask();

    remainingTimeMillis = segment.getDurationMillis();
    screen = segment.getScreen();
    sequence = segment.getSequence();

    announce.nTimes(segment.getStartWhistleCount());

    if (remainingTimeMillis <= 0) {
      return;
    }

    cycleTimer = new Timer();
    cycleTask = new TimerTask() {
      @Override
      public void run() {
        decreaseRemainingTime(100);

        System.out.print('.');

        if (remainingTimeMillis <= 0.0) {
          startNextStep();
        }
      }
    };

    cycleTimer.scheduleAtFixedRate(cycleTask, 1_000, 100);
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
    if (segmentIterator != null && segmentIterator.hasNext())
      return segmentIterator.next();
    else
      return null;
  }

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
  public int getRemainingTimeSeconds() {
    return (int) Math.floor(getRemainingTimeMillis() * MILLIS_TO_SECONDS);
  }

  private volatile ScreenState.Screen screen = null;

  private volatile ScreenState.Sequence sequence = null;


  @Override
  public synchronized ScreenState getScreenState() {
    final ScreenState screenState = new ScreenState(screen, sequence, getRemainingTimeSeconds());
    return screenState;
  }


  private ScreenState.Screen getRandomScreen() {
    final ObservableList<ScreenState.Screen> screens = observableArrayList(
        ScreenState.Screen.STOP,
        STEADY,
        SHOOT,
        ScreenState.Screen.SHOOT_UP30
    );

    final int idx = (int) Math.floor(Math.random() * (double) (screens.size()));
    return screens.get(idx);
  }

  private ScreenState.Sequence getRandomSequence() {
    final ObservableList<ScreenState.Sequence> sequences = observableArrayList(
        ScreenState.Sequence.A,
        ScreenState.Sequence.B,
        AB,
        ScreenState.Sequence.CD
    );

    final int idx = (int) Math.floor(Math.random() * (double) (sequences.size()));
    return sequences.get(idx);
  }


}
