package de.aboutflash.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * A utility class to detect the most likely main subnet the current machine communicates
 * with the outside world.
 * <p>
 * Returns only correct values for a /24 subnet.
 *
 * @author falk@aboutflash.de on 25.11.2017.
 */
public final class InetSubnetDetector {

  @SuppressWarnings("ImplicitNumericConversion")
  private static final byte[] UNUSED_ADDRESS = new byte[]{1, 1, 1, 1};
  @SuppressWarnings("ImplicitNumericConversion")
  private static final byte[] BROADCAST_ADDRESS = new byte[]{0, 0, 0, 0};
  private static final int FULL_SEGMENT = 0xff;

  private InetSubnetDetector() {}

  /**
   * Gets the local IPv4 address.
   */
  public static InetAddress getLocalAddress() throws UnknownHostException, SocketException {
    return InetAddress.getByAddress(discoverBroadcastAddress().getAddress());
  }

  /**
   * Gets the IPv4 broadcast address of the local subnet.
   * This address will be used as target address for a sending host.
   */
  public static InetAddress getSubnetBroadcastSendAddress() throws UnknownHostException, SocketException {
    return InetAddress.getByAddress(getLocalSubnetBroadcast());
  }

  /**
   * Gets the IPv4 broadcast address to bind to to listen for packages on all devices.
   * This address is used by a listening client.
   */
  public static InetAddress getSubnetBroadcastReceiveAddress() throws UnknownHostException, SocketException {
    return InetAddress.getByAddress(BROADCAST_ADDRESS);
  }

  /**
   * Takes the discovered local address and uses this to determine the broadcast address
   * in the form of xxx.xxx.xxx.255.
   */
  private static byte[] getLocalSubnetBroadcast() throws SocketException, UnknownHostException {
    final byte[] address = discoverBroadcastAddress().getAddress();

    // replace last IP segment by .255
    //noinspection NumericCastThatLosesPrecision
    address[3] = (byte) FULL_SEGMENT;

    return address;
  }

  /**
   * Send an UDP packet to Nirvana and retrieve the local IPv4 address that was used from the created DatagramSocket.
   * This is probably the machines IP address which can be accepted as the "default" network interface address.
   */
  private static InetAddress discoverBroadcastAddress() throws SocketException, UnknownHostException {
    try (DatagramSocket s = new DatagramSocket()) {
      s.connect(InetAddress.getByAddress(UNUSED_ADDRESS), 0);
      System.out.printf("**************** FOUND SOCKET ADDRESS:PORT %1s%n", s.getLocalSocketAddress());
      return s.getLocalAddress();
    }
  }

}
