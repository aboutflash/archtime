package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.model.ScreenState.Sequence;

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
