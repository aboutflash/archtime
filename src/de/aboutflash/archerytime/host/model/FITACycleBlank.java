package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.host.model.Cycle.Segment.SegmentBuilder;

import static de.aboutflash.archerytime.model.ScreenState.Screen.MESSAGE;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.NONE;

/**
 * Class
 *
 * @author falk@aboutflash.de on 27.11.2017.
 */
public class FITACycleBlank extends FITACycleModelBase {

  public FITACycleBlank() {
  }

  @Override
  protected void createSegments() {
    getSegments().add(new SegmentBuilder().sequence(NONE).screen(MESSAGE).message("Hello").duration(3).build());
    getSegments().add(new SegmentBuilder().sequence(NONE).screen(MESSAGE).message("world!").duration(3).build());
    getSegments().add(new SegmentBuilder().sequence(NONE).screen(MESSAGE).message("Hello").duration(3).build());
    getSegments().add(new SegmentBuilder().sequence(NONE).screen(MESSAGE).message("world!").duration(3).build());
    getSegments().add(new SegmentBuilder().sequence(NONE).screen(MESSAGE).message("Hello").duration(3).build());
    getSegments().add(new SegmentBuilder().sequence(NONE).screen(MESSAGE).message("world!").duration(3).build());
    getSegments().add(new SegmentBuilder().sequence(NONE).screen(MESSAGE).message("---").duration(3).build());
  }

  @Override
  public String getName() {
    return "NONE";
  }

}
