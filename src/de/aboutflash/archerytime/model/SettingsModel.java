package de.aboutflash.archerytime.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * Class
 *
 * @author falk@aboutflash.de on 04.12.2017.
 */
public class SettingsModel {

  public static final Logger log = Logger.getLogger("Settings");

  public static final double DEFAULT_TOTAL_SHOOT_TIME = 120_000.0; //millis
  public static final double DEFAULT_SHOOT_UP30_WARN_TIME = 30_000.0; //millis
  public static final double DEFAULT_STEADY_TIME = 20_000.0; //millis
  public static final double DEFAULT_SWAP_TIME = 20_000.0; //millis
  public final static String DEFAULT_AUDIO_FILE = "/chime_cropped.mp3";

  private DoubleProperty totalShootingTimeMillis = new SimpleDoubleProperty(DEFAULT_TOTAL_SHOOT_TIME);
  private DoubleProperty shootingUp30WarningTimeMillis = new SimpleDoubleProperty(DEFAULT_SHOOT_UP30_WARN_TIME);
  private DoubleProperty steadyTimeMillis = new SimpleDoubleProperty(DEFAULT_STEADY_TIME);
  private DoubleProperty swapTimeMillis = new SimpleDoubleProperty(DEFAULT_SWAP_TIME);
  private ObjectProperty<URI> soundFileLocation = new SimpleObjectProperty<>();

  private static final SettingsModel instance = new SettingsModel();

  public static final SettingsModel getInstance() {
    return instance;
  }

  private SettingsModel() {
    setSoundFileLocation(DEFAULT_AUDIO_FILE); // default
  }

  public double getTotalShootingTimeMillis() {
    return totalShootingTimeMillis.get();
  }

  public DoubleProperty totalShootingTimeMillisProperty() {
    return totalShootingTimeMillis;
  }

  public void setTotalShootingTimeMillis(double totalShootingTimeMillis) {
    this.totalShootingTimeMillis.set(totalShootingTimeMillis);
  }


  public double getSteadyTimeMillis() {
    return steadyTimeMillis.get();
  }

  public DoubleProperty steadyTimeMillisProperty() {
    return steadyTimeMillis;
  }

  public void setSteadyTimeMillis(double steadyTimeMillis) {
    this.steadyTimeMillis.set(steadyTimeMillis);
  }


  public double getSwapTimeMillis() {
    return swapTimeMillis.get();
  }

  public DoubleProperty swapTimeMillisProperty() {
    return swapTimeMillis;
  }

  public void setSwapTimeMillis(double swapTimeMillis) {
    this.swapTimeMillis.set(swapTimeMillis);
  }


  public URI getSoundFileLocation() {
    return soundFileLocation.get();
  }

  public ObjectProperty<URI> soundFileLocationProperty() {
    return soundFileLocation;
  }

  public void setSoundFileLocation(String soundFile) {
    URI uri = null;
    try {
      uri = getClass().getResource(soundFile).toURI();
    } catch (URISyntaxException e) {
      log.severe(e.getMessage());
    }

    soundFileLocation.set(uri);
  }


  public double getShootingUp30WarningTimeMillis() {
    return shootingUp30WarningTimeMillis.get();
  }

  public DoubleProperty shootingUp30WarningTimeMillisProperty() {
    return shootingUp30WarningTimeMillis;
  }

  public void setShootingUp30WarningTimeMillis(double shootingUp30WarningTimeMillis) {
    this.shootingUp30WarningTimeMillis.set(shootingUp30WarningTimeMillis);
  }
}
