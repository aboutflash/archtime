package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.host.model.Segment.SegmentBuilder;

import static de.aboutflash.archerytime.model.ScreenState.Screen.*;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.AB;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.CD;

/**
 * Class
 *
 * @author falk@aboutflash.de on 27.11.2017.
 */
public class FITACycleABCD extends FITACycleModelBase {

  public FITACycleABCD() {
  }

  @Override
  protected void createSegments() {
    getSegments().add(new SegmentBuilder().sequence(AB).screen(STOP).whistle(0).build()); // AB begins
    getSegments().add(new SegmentBuilder().sequence(AB).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(STEADY).build()); // swap -> CD
    getSegments().add(new SegmentBuilder().sequence(CD).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(STOP).build()); // pause - pick up arrows
    getSegments().add(new SegmentBuilder().sequence(CD).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(STEADY).build()); // swap -> AB
    getSegments().add(new SegmentBuilder().sequence(AB).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(STOP).build()); // cycle finished
  }

  @Override
  public String getName() {
    return "AB / CD";
  }

}
