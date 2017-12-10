package de.aboutflash.archerytime.net;

import de.aboutflash.net.InetSubnetDetector;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Base class for sender/receiver threads.
 *
 * @author falk@aboutflash.de on 25.11.2017.
 */
@SuppressWarnings("WeakerAccess")
abstract public class TransmissionThread extends Thread {

  private final static Logger LOG = Logger.getAnonymousLogger();
  protected final static int RECEIVE_BUFFER_SIZE_BYTES = 0x400; // 1Kb
  protected final static int RESPONSE_PORT = 10101;
  protected final static String DISCOVERY_IDENTIFIER = "BROADCAST_ARCHERY_TIME_CONTROL_SERVER_V0.0.1";

  private final InetAddress subnetSendAddress;
  private final InetAddress receiveAddress;


  protected TransmissionThread() throws UnknownHostException, SocketException {
    setDaemon(true);

    subnetSendAddress = InetSubnetDetector.getSubnetBroadcastSendAddress();
    LOG.info(subnetSendAddress.getHostAddress());

    receiveAddress = InetSubnetDetector.getSubnetBroadcastReceiveAddress();
    LOG.info(receiveAddress.getHostAddress());
  }

  public InetAddress getSubnetSendAddress() {
    return subnetSendAddress;
  }

  public InetAddress getReceiveAddress() {
    return receiveAddress;
  }

}
