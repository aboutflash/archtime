package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.model.ScreenState.Screen;
import de.aboutflash.archerytime.model.ScreenState.Sequence;
import de.aboutflash.archerytime.model.SettingsModel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Class
 *
 * @author falk@aboutflash.de on 04.12.2017.
 */
public class Cycle {
  private final String name;
  private final Sequence sequence;
  private final LinkedList<Segment> segments;
  private final Iterator<Segment> segmentIterator;

  public Cycle(String name, Sequence sequence, LinkedList<Segment> segments) {
    this.name = name;
    this.sequence = sequence;
    this.segments = segments;
    segmentIterator = segments.iterator();
  }

  public String getName() {
    return name;
  }

  public Sequence getSequence() {
    return sequence;
  }

  public List<Segment> getSegments() {
    return segments;
  }

  public Iterator<Segment> getSegmentIterator() {
    return segmentIterator;
  }


  public static class Segment {
    private final Screen screen;
    private final Sequence sequence;

    private final double offsetMillis;
    private final double durationMillis;
    private final int startWhistleCount;
    private String message;

    public Segment(final Sequence sequence, final Screen screen,
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

    public Segment(final Sequence sequence, final Screen screen,
                   final double durationMillis, final double offsetMillis,
                   final int startWhistleCount) {

      this(sequence, screen, durationMillis, offsetMillis, startWhistleCount, "");
    }

    public Segment(final Sequence sequence, final Screen screen,
                   final double durationMillis, final int startWhistleCount) {
      this(sequence, screen, durationMillis, 0, startWhistleCount);
    }

    public Sequence getSequence() {
      return sequence;
    }

    public Screen getScreen() {
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
    static class SegmentBuilder implements javafx.util.Builder<Segment> {
      private static final double NONE = 0.0;
      private static final double INDEFINITE = 0.0;

      private Screen screen;
      private Sequence sequence;
      private String message;
      private double duration = INDEFINITE;
      private double offset = INDEFINITE;
      private int startWhistleCount = 0;

      @Override
      public Segment build() {
        return new Segment(sequence, screen, duration, offset, startWhistleCount, message);
      }

      public SegmentBuilder screen(Screen screen) {
        final SettingsModel settings = SettingsModel.getInstance();

        this.screen = screen;
        switch (screen) {
          case STOP:
            duration = INDEFINITE;
            offset = NONE;
            startWhistleCount = 3;
            break;

          case SHOOT:
            duration = settings.getTotalShootingTimeMillis();
            offset = settings.getShootingUp30WarningTimeMillis();
            startWhistleCount = 2;
            break;

          case SHOOT_UP30:
            duration = settings.getShootingUp30WarningTimeMillis();
            offset = NONE;
            startWhistleCount = 0;
            break;

          case STEADY:
            duration = settings.getSteadyTimeMillis();
            offset = NONE;
            startWhistleCount = 1;
            break;

          case MESSAGE:
          case INVALID:
          default:
            duration = INDEFINITE;
            offset = NONE;
            startWhistleCount = 0;
            break;
        }
        return this;
      }

      public SegmentBuilder sequence(Sequence sequence) {
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
    }
  }


  /**
   * Builder class for easy Cycle generation.
   */
  @SuppressWarnings("ReturnOfThis")
  static class Builder implements javafx.util.Builder<Cycle> {
    private String name;
    private Sequence sequence;
    private LinkedList<Segment> segments = new LinkedList<>();

    @Override
    public Cycle build() {
      return new Cycle(name, sequence, segments);
    }

    public Builder name(String value) {
      name = value;
      return this;
    }

    public Builder A() {
      sequence = Sequence.A;
      return this;
    }

    public Builder B() {
      sequence = Sequence.B;
      return this;
    }

    public Builder N() {
      sequence = Sequence.N;
      return this;
    }

    public Builder AB() {
      sequence = Sequence.AB;
      return this;
    }

    public Builder CD() {
      sequence = Sequence.CD;
      return this;
    }

    public Builder withSegment(final Segment segment) {
      segments.add(segment);
      return this;
    }
  }
}
