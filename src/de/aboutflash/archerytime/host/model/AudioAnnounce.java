package de.aboutflash.archerytime.host.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.util.logging.Logger;

/**
 * Class
 *
 * @author falk@aboutflash.de on 03.12.2017.
 */
public class AudioAnnounce {
  private final static Logger log = Logger.getLogger("AudioAnnounce");

  private final String mediaLocation;
  private MediaPlayer mediaPlayer;
  private boolean canPlayMedia = false;

  public AudioAnnounce(final String mediaLocation) {
    this.mediaLocation = mediaLocation;
    try {
      mediaPlayer = new MediaPlayer(new Media(mediaLocation));
      canPlayMedia = true;
    } catch (MediaException ex) {
      // MediaExceptions may be thrown on non-Windows systems
      log.severe("Cannot create Media Player");
    }
  }

  public void playTimes(final int repeat) {
    if (!canPlayMedia) {
      log.severe("Cannot play media");
      return;
    }
    log.info(() -> String.format("Playing sound %s %d times", mediaLocation, repeat));
    mediaPlayer.stop();
    if (repeat > 0) {
      mediaPlayer.setCycleCount(repeat);
      mediaPlayer.play();
    }
  }


}
