package de.aboutflash.archerytime.host.main;

import de.aboutflash.archerytime.host.model.ControlViewModel;
import de.aboutflash.archerytime.host.model.FITACycleBlank;
import de.aboutflash.archerytime.host.model.FITACycleEndlessDemo;
import de.aboutflash.archerytime.host.model.FITACycleModel;
import de.aboutflash.archerytime.host.net.Announcer;
import de.aboutflash.archerytime.host.ui.ControlScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class
 *
 * @author falk@aboutflash.de on 19.11.2017.
 */
public class ArcheryTimeControlHost extends Application {

  private final static Rectangle2D DEFAULT_SIZE = new Rectangle2D(0.0, 0.0, 1920.0 * 0.5, 1080.0 * 0.5);

  private Stage primaryStage;
  private StackPane rootPane;
  private ControlViewModel controlViewModel;

  private Announcer announcer;
  private FITACycleModel model;
  private Supplier<FITACycleModel> modelSupplier = new Supplier<FITACycleModel>() {
    @Override
    public FITACycleModel get() {
      return model;
    }
  };

  public static void main(final String... args) {
    launch(args);
  }

  @Override
  public void init() throws Exception {
    announceModel(new FITACycleBlank());
    model.startNextStep();
    observeModel();
  }

  private void announceServer() {
    announcer = new Announcer();
  }

  private void announceModel(final FITACycleModel model) {
    checkNotNull(model);

    if (this.model != null)
      this.model.dispose();

    if (announcer == null) {
      this.model = model;
      announceServer();
    }
    announcer.setModel(() -> model);
    this.model = model;
  }

  @Override
  public void start(final Stage stage) throws Exception {
    primaryStage = stage;

    primaryStage.setOnCloseRequest(e -> {
      announcer.stop();
      Platform.exit();
      System.exit(0);
    });

    layout();
    registerHotKeys();
    showControlScreen();
  }

  private void registerHotKeys() {
    // fullscreen F
    primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.F) {
        if (primaryStage.isFullScreen())
          exitFullScreenMode();
        else
          enterFullScreenMode();
        event.consume();
      }
    });

    // exit Ctrl-C
    primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event ->

    {
      if (event.getCode() == KeyCode.C
          && event.isControlDown())
      {
        Platform.exit();
        System.exit(0);
      }
    });

    // exit Ctrl-C
    primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event ->

    {
      if (event.getCode() == KeyCode.SPACE
          && event.isControlDown())
      {
        model.startNextStep();
      }
    });
  }

  private void enterFullScreenMode() {
    primaryStage.setFullScreen(true);
  }

  private void exitFullScreenMode() {
    primaryStage.setFullScreen(false);
  }


  private void layout() {
    primaryStage.setWidth(DEFAULT_SIZE.getWidth());
    primaryStage.setHeight(DEFAULT_SIZE.getHeight());

    rootPane = new StackPane();
    Scene scene = new Scene(rootPane);

    scene.getStylesheets().add(getClass().getResource("host.css").toExternalForm());
    primaryStage.setScene(scene);


    primaryStage.show();
  }

  private void showControlScreen() {
    controlViewModel = new ControlViewModel();
    final ControlScreen controlScreen = new ControlScreen(controlViewModel);
    rootPane.getChildren().setAll(controlScreen);

    controlScreen.setOnStart(event -> {
      announceModel(new FITACycleEndlessDemo());
      modelSupplier.get().startNextStep();
    });

    controlScreen.setOnStop(event -> {
      announceModel(new FITACycleBlank());
      modelSupplier.get().startNextStep();
    });
  }

  // for debugging

  private void observeModel() {
    new Timer().scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> updateUi());
      }
    }, 0, 100);
  }

  private void updateUi() {
    if (controlViewModel != null)
      controlViewModel.setStatus(model.getScreenState().toString());
  }

}
