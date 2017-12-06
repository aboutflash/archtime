package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.host.model.Cycle.Segment.SegmentBuilder;

import static de.aboutflash.archerytime.model.ScreenState.Screen.*;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.N;

/**
 * Class
 *
 * @author falk@aboutflash.de on 27.11.2017.
 */
public class FITACycleRepeat extends FITACycleModelBase {

  public FITACycleRepeat() {
  }

  @Override
  protected void createSegments() {
    getSegments().add(new SegmentBuilder().sequence(N).screen(STOP).whistle(0).build()); // Shooting begins
    getSegments().add(new SegmentBuilder().sequence(N).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(N).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(N).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(N).screen(STOP).build()); // finished cycle
  }

  @Override
  public String getName() {
    return "Nachschießen";
  }

}
