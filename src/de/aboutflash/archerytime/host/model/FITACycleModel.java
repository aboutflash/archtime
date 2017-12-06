package de.aboutflash.archerytime.host.model;

import de.aboutflash.archerytime.model.ScreenState;

/**
 * Class
 *
 * @author falk@aboutflash.de on 01.12.2017.
 */
public interface FITACycleModel {

  String getName();

  int getLength();

  int getCurrentSegmentIdx();

  void startNextStep();

  double getRemainingTimeMillis();

  void setRemainingTimeMillis(double value);

  void decreaseRemainingTime(double milliseconds);

  int getRemainingTimeSeconds();

  ScreenState getScreenState();

  boolean isRepeating();

  void setRepeating(boolean value);

}
