package de.aboutflash.archerytime.remoteclient.ui;

import de.aboutflash.archerytime.remoteclient.model.StartupViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Class
 *
 * @author falk@aboutflash.de on 19.11.2017.
 */
public class MessageScreen extends StackPane {
  @SuppressWarnings("WeakerAccess")
  public static final String DEFAULT_STYLE_CLASS = "message-screen";
  private final StartupViewModel model;

  public MessageScreen(StartupViewModel viewModel) {
    model = viewModel;

    getStyleClass().add(DEFAULT_STYLE_CLASS);
    drawUi();
  }

  private void drawUi() {
    final Label message = new Label();
    message.textProperty().bind(model.messageProperty());

    final VBox vBox = new VBox(message);
    vBox.setAlignment(Pos.CENTER);
    StackPane.setAlignment(vBox, Pos.CENTER);

    getChildren().add(vBox);
  }


}
