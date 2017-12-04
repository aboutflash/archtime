package de.aboutflash.archerytime.server.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.logging.Logger;

/**
 * Class
 *
 * @author falk@aboutflash.de on 03.12.2017.
 */
public class AudioAnnounce {
  private final static Logger log = Logger.getLogger("AudioAnnounce");

  private String mediaLocation;
  private MediaPlayer mediaPlayer;

  public AudioAnnounce(String mediaLocation) {
    this.mediaLocation = mediaLocation;
    mediaPlayer = new MediaPlayer(new Media(mediaLocation));
  }

  public void nTimes(int repeat) {
    log.info(() -> String.format("Playing sound %s %d times", mediaLocation, repeat));
    mediaPlayer.stop();
    if (repeat > 0) {
      mediaPlayer.setCycleCount(repeat);
      mediaPlayer.play();
    }
  }


}
