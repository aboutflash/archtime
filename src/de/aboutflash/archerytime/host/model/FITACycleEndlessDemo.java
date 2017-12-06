package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.host.model.Segment.SegmentBuilder;

import static de.aboutflash.archerytime.model.ScreenState.Screen.*;
import static de.aboutflash.archerytime.model.ScreenState.Sequence.*;

/**
 * Class
 *
 * @author falk@aboutflash.de on 27.11.2017.
 */
public class FITACycleEndlessDemo extends FITACycleModelBase {

  public FITACycleEndlessDemo() {
  }

  @Override
  protected void createSegments() {
    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("DEMONSTRATION").duration(8).build());

    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("AB / CD Zyklus").duration(8).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(STOP).whistle(0).duration(5).build()); // AB begins
    getSegments().add(new SegmentBuilder().sequence(AB).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(STEADY).build()); // swap -> CD
    getSegments().add(new SegmentBuilder().sequence(CD).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(STOP).duration(5).build()); // pause - pick up arrows

    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("Punkte zählen / Pfeile holen").duration(3).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(STOP).whistle(0).duration(5).build()); // pause - pick up arrows

    getSegments().add(new SegmentBuilder().sequence(CD).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(CD).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(STEADY).build()); // swap -> AB
    getSegments().add(new SegmentBuilder().sequence(AB).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(AB).screen(STOP).duration(5).build()); // finished cycle

    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("Punkte zählen / Pfeile holen").duration(3).build());
    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("ENDE AB / CD Zyklus").duration(3).build());


    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("A / B Zyklus").duration(8).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(STOP).duration(5).build()); // AB begins
    getSegments().add(new SegmentBuilder().sequence(A).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(STEADY).build()); // swap -> CD
    getSegments().add(new SegmentBuilder().sequence(B).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(STOP).duration(5).build()); // pause - pick up arrows

    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("Punkte zählen / Pfeile holen").duration(3).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(STOP).whistle(0).duration(5).build()); // pause - pick up arrows

    getSegments().add(new SegmentBuilder().sequence(B).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(B).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(STEADY).build()); // swap -> AB
    getSegments().add(new SegmentBuilder().sequence(A).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(A).screen(STOP).duration(5).build()); // finished cycle

    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("Punkte zählen / Pfeile holen").duration(3).build());
    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("ENDE A / B Zyklus").duration(3).build());

    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("Nachschießen Zyklus").duration(8).build());
    getSegments().add(new SegmentBuilder().sequence(N).screen(STOP).duration(5).whistle(0).build()); // Shooting begins
    getSegments().add(new SegmentBuilder().sequence(N).screen(STEADY).build());
    getSegments().add(new SegmentBuilder().sequence(N).screen(SHOOT).build());
    getSegments().add(new SegmentBuilder().sequence(N).screen(SHOOT_UP30).build());
    getSegments().add(new SegmentBuilder().sequence(N).screen(STOP).duration(5).build()); // finished cycle
    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("ENDE Nachschießen Zyklus").duration(3).build());

    getSegments().add(new SegmentBuilder().screen(MESSAGE).message("Wiederholung ...").duration(20).build());

    // repeat endlessly
    setRepeating(true);
  }

  @Override
  public String getName() {
    return "AB / CD";
  }

}
