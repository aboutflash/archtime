package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.host.model.Cycle.Segment.SegmentBuilder;

import static de.aboutflash.archerytime.model.ScreenState.Screen.*;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.A;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.B;

/**
 * Class
 *
 * @author falk@aboutflash.de on 27.11.2017.
 */
public class FITACycleAB extends FITACycleModelBase {

  public FITACycleAB() {
  }

  @Override
  protected void createSegments() {
    getSegments().add(new SegmentBuilder().sequence(A).screen(STOP).whistle(0).build()); // A begins
    getSegments().add(new SegmentBuilder().sequence(A).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(STEADY).build()); // swap -> B
    getSegments().add(new SegmentBuilder().sequence(B).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(STOP).build()); // pause - pick up arrows
    getSegments().add(new SegmentBuilder().sequence(B).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(STEADY).build()); // swap -> A
    getSegments().add(new SegmentBuilder().sequence(A).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(STOP).build()); // cycle finished
  }

  @Override
  public String getName() {
    return "A / B";
  }

}
