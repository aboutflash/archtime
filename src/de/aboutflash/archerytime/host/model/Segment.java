package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.model.ScreenState;
import de.aboutflash.archerytime.model.SettingsModel;
import javafx.util.Builder;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Class
 *
 * @author falk@aboutflash.de on 06.12.2017.
 */
public class Segment {
  private final ScreenState.Screen screen;
  private final ScreenState.Sequence sequence;

  private final double offsetMillis;
  private final double durationMillis;
  private final int startWhistleCount;
  private String message;

  public Segment(final ScreenState.Sequence sequence, final ScreenState.Screen screen,
                 final double durationMillis, final double offsetMillis,
                 final int startWhistleCount, final String message) {

    checkArgument(offsetMillis >= 0, "Negative time offset is not allowed");

    this.screen = screen;
    this.sequence = sequence;
    this.durationMillis = durationMillis;
    this.offsetMillis = offsetMillis;
    this.startWhistleCount = startWhistleCount;
    this.message = message;
  }

  public Segment(final ScreenState.Sequence sequence, final ScreenState.Screen screen,
                 final double durationMillis, final double offsetMillis,
                 final int startWhistleCount) {

    this(sequence, screen, durationMillis, offsetMillis, startWhistleCount, "");
  }

  public Segment(final ScreenState.Sequence sequence, final ScreenState.Screen screen,
                 final double durationMillis, final int startWhistleCount) {
    this(sequence, screen, durationMillis, 0, startWhistleCount);
  }

  public ScreenState.Sequence getSequence() {
    return sequence;
  }

  public ScreenState.Screen getScreen() {
    return screen;
  }

  public double getDurationMillis() {
    return durationMillis;
  }

  public double getOffsetMillis() {
    return offsetMillis;
  }

  public int getStartWhistleCount() {
    return startWhistleCount;
  }

  public String getMessage() {
    return message;
  }


  /**
   * Builder class for easy segment generation.
   */
  @SuppressWarnings("ReturnOfThis")
  public static class SegmentBuilder implements Builder<Segment> {
    private static final double NONE = 0.0;
    private static final double INDEFINITE = 0.0;

    private ScreenState.Screen screen;
    private ScreenState.Sequence sequence;
    private String message;
    private double duration = INDEFINITE;
    private double offset = INDEFINITE;
    private int startWhistleCount = 0;

    @Override
    public Segment build() {
      return new Segment(sequence, screen, duration, offset, startWhistleCount, message);
    }

    public SegmentBuilder screen(ScreenState.Screen screen) {
      final SettingsModel settings = SettingsModel.getInstance();

      this.screen = screen;
      switch (screen) {
        case STOP:
          duration = durationOrDefault(INDEFINITE);
          offset = NONE;
          startWhistleCount = 3;
          break;

        case SHOOT:
          duration = durationOrDefault(settings.getTotalShootingTimeMillis());
          offset = settings.getShootingUp30WarningTimeMillis();
          startWhistleCount = 2;
          break;

        case SHOOT_UP30:
          duration = durationOrDefault(settings.getShootingUp30WarningTimeMillis());
          offset = NONE;
          startWhistleCount = 0;
          break;

        case STEADY:
          duration = durationOrDefault(settings.getSteadyTimeMillis());
          offset = NONE;
          startWhistleCount = 1;
          break;

        case MESSAGE:
        case INVALID:
        default:
          duration = durationOrDefault(INDEFINITE);
          offset = NONE;
          startWhistleCount = 0;
          break;
      }
      return this;
    }

    public SegmentBuilder sequence(ScreenState.Sequence sequence) {
      this.sequence = sequence;
      return this;
    }

    public SegmentBuilder whistle(int value) {
      startWhistleCount = value;
      return this;
    }

    public SegmentBuilder message(String message) {
      this.message = message;
      return this;
    }

    public SegmentBuilder duration(int value) {
      this.duration = (double) value * 1_000.0;
      return this;
    }

    private double durationOrDefault(double defaultValue) {
      return duration > INDEFINITE ? duration : defaultValue;
    }
  }
}
