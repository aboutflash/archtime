package de.aboutflash.archerytime.host.net;

import de.aboutflash.archerytime.host.model.FITACycleModel;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Class
 *
 * @author falk@aboutflash.de on 24.11.2017.
 */
public class Announcer {

  private final ExecutorService executorService;
  private final FITACycleModel model;
  private Future<?> submission;

  public Announcer(final FITACycleModel model) {
    this.model = model;

    executorService = newSingleThreadExecutor();
    runForever();
  }

  public void stop() {
    submission.cancel(true);
    executorService.shutdownNow();
  }

  private void runForever() {
    try {
      submission = executorService.submit(new AnnounceThread(model));
    } catch (UnknownHostException | SocketException ignored) {
    }
  }
}
