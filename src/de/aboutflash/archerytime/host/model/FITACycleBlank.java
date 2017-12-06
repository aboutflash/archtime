package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.host.model.Segment.SegmentBuilder;

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
    getSegments().add(new SegmentBuilder().sequence(NONE).screen(MESSAGE).message("Well, I'll do my very best.").build());
  }

  @Override
  public String getName() {
    return "NONE";
  }

}
