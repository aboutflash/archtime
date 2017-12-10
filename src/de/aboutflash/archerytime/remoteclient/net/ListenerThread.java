package de.aboutflash.archerytime.remoteclient.net;

import de.aboutflash.archerytime.json.JSONObjectSerializer;
import de.aboutflash.archerytime.model.ScreenState;
import de.aboutflash.archerytime.net.TransmissionThread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class
 *
 * @author falk@aboutflash.de on 24.11.2017.
 */
@SuppressWarnings({"Duplicates", "WeakerAccess"})
public class ListenerThread extends TransmissionThread {

  private final Logger log;

  private final JSONObjectSerializer serializer;
  private final Consumer<ScreenState> screenStateConsumer;
  private long datagramCount;

  public ListenerThread(final Consumer<ScreenState> screenStateConsumer) throws UnknownHostException, SocketException {
    this.screenStateConsumer = screenStateConsumer;
    serializer = new JSONObjectSerializer();
    log = Logger.getLogger("Listener Thread");
    log.setLevel(Level.FINE);
  }

  @Override
  public void run() {
    detectServer();
  }

  @SuppressWarnings("ObjectAllocationInLoop")
  private void detectServer() {

    byte[] buffer = new byte[RECEIVE_BUFFER_SIZE_BYTES];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, getReceiveAddress(), RESPONSE_PORT);

    try (DatagramSocket socket = new DatagramSocket(packet.getPort(), packet.getAddress())) {
      socket.setBroadcast(true);
      socket.setReuseAddress(true);

      do {
        try {
          socket.receive(packet);
        } catch (IOException e) {
          log.severe(e.getMessage());
        } finally {
          processData(packet);
        }

      } while (true);

    } catch (SocketException e) {
      log.severe(e.getMessage());
    }

  }

  private void processData(DatagramPacket packet) {
    final String s = new String(packet.getData()).trim();
    ScreenState screenState = new ScreenState(ScreenState.Screen.STOP);

    try {
      screenState = serializer.deserializeScreenState(s);
    } catch (Exception e) {
      log.severe("Could not decode host message: " + s);
    }

    if (!screenState.getScreen().equals(ScreenState.Screen.INVALID)) {
      log.info("RECEIVED: " + screenState);
      screenStateConsumer.accept(screenState);
      screenStateReceived();
    }
  }

  private void screenStateReceived() {
    datagramCount++;
    log.info(String.format("Announcements received: %12d", datagramCount));
  }

}
