package de.aboutflash.archerytime.host.net;

import de.aboutflash.archerytime.host.model.FITACycleModel;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Class
 *
 * @author falk@aboutflash.de on 24.11.2017.
 */
public class Announcer {

  private final ExecutorService executorService;
  private Supplier<FITACycleModel> model;
  private Future<?> submission;
  private AnnounceThread thread;

  public Announcer() {
    executorService = newSingleThreadExecutor();
  }

  public void stop() {
    submission.cancel(true);
    executorService.shutdownNow();
  }

  public synchronized void setModel(Supplier<FITACycleModel> model) {
    this.model = model;

    if (thread == null) {
      runForever();
    } else {
      thread.setModel(this.model);
    }
  }

  private void runForever() {
    try {
      thread = new AnnounceThread(model);
      thread.setDaemon(true);
      thread.setUncaughtExceptionHandler((t, e) -> {
        System.out.println(t.getName() + " - " + e);
      });
      submission = executorService.submit(thread);
    } catch (UnknownHostException | SocketException ignored) {
    }
  }
}
