package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.model.ScreenState;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class
 *
 * @author falk@aboutflash.de on 04.12.2017.
 */
public class Cycle {
  private final String name;
  private final ScreenState.Sequence sequence;
  private final LinkedList<Segment> segments;
  private final Iterator<Segment> segmentIterator;

  public Cycle(String name, ScreenState.Sequence sequence, LinkedList<Segment> segments) {
    this.name = name;
    this.sequence = sequence;
    this.segments = segments;
    segmentIterator = segments.iterator();
  }

  public String getName() {
    return name;
  }

  public ScreenState.Sequence getSequence() {
    return sequence;
  }

  public List<Segment> getSegments() {
    return segments;
  }

  public Iterator<Segment> getSegmentIterator() {
    return segmentIterator;
  }


  public static class Segment {
    private final ScreenState.Screen screen;
    private final ScreenState.Sequence sequence;
    private final double durationMillis;
    private final int startWhistleCount;

    public Segment(final ScreenState.Sequence sequence, final ScreenState.Screen screen, final double durationMillis, final int startWhistleCount) {
      this.screen = screen;
      this.sequence = sequence;
      this.durationMillis = durationMillis;
      this.startWhistleCount = startWhistleCount;
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

    public int getStartWhistleCount() {
      return startWhistleCount;
    }
  }


  @SuppressWarnings("ReturnOfThis")
  static class Builder implements javafx.util.Builder<Cycle> {
    private String name;
    private ScreenState.Sequence sequence;
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
      sequence = ScreenState.Sequence.A;
      return this;
    }

    public Builder B() {
      sequence = ScreenState.Sequence.B;
      return this;
    }

    public Builder N() {
      sequence = ScreenState.Sequence.N;
      return this;
    }

    public Builder AB() {
      sequence = ScreenState.Sequence.AB;
      return this;
    }

    public Builder CD() {
      sequence = ScreenState.Sequence.CD;
      return this;
    }

    public Builder withSegment(final Segment segment) {
      segments.add(segment);
      return this;
    }
  }
}
