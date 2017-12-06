package de.aboutflash.archerytime.host.ui;

import de.aboutflash.archerytime.host.model.ControlViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Class
 *
 * @author falk@aboutflash.de on 28.11.2017.
 */
public class ControlScreen extends StackPane {
  public static final String DEFAULT_STYLE_CLASS = "control-screen";
  private final ControlViewModel model;
  private final Label status = new Label();

  private final Button startDemo = new Button("Start Demo (Ctrl+SPACE)");
  private final Button stopDemo = new Button("Stop Demo");

  public ControlScreen(ControlViewModel viewModel) {
    model = viewModel;

    getStyleClass().add(DEFAULT_STYLE_CLASS);
    drawUi();
  }

  private void drawUi() {
    status.textProperty().bind(model.statusProperty());

    HBox buttonsBox = new HBox(startDemo, stopDemo);
    buttonsBox.setSpacing(20);
    Label explain = new Label("Skip: Ctrl+SPACE  -  Toggle Fullscreen: F  -  Exit: Ctrl+C");
    VBox box = new VBox(status, buttonsBox, explain);
    box.setPadding(new Insets(20));
    box.setSpacing(10);

    getChildren().add(box);
  }

  public void setOnStart(EventHandler<ActionEvent> eventHandler) {
    startDemo.setOnAction(eventHandler);
  }

  public void setOnStop(EventHandler<ActionEvent> eventHandler) {
    stopDemo.setOnAction(eventHandler);
  }

}
