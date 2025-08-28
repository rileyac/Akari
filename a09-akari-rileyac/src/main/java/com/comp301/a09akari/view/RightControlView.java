package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class RightControlView implements FXComponent, ModelObserver {
  private Model model;
  private ClassicMvcController controller;

  public RightControlView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException("Model and controller cannot be null!");
    }
    this.model = model;
    this.controller = controller;
  }

  @Override
  public void update(Model model) {}

  @Override
  public Parent render() {
    // Right Control Panel
    VBox rightControlPane = new VBox();
    rightControlPane.setAlignment(Pos.CENTER);
    // Spacing between the buttons
    rightControlPane.setSpacing(50);
    // Padding around the HBox for spacing
    rightControlPane.setStyle("-fx-padding: 50;");

    Button randPuzzle = new Button("Random Puzzle");
    randPuzzle.setOnAction((ActionEvent event) -> controller.clickRandPuzzle());
    randPuzzle.getStyleClass().add("puzzle-button");

    Button resetPuzzle = new Button("Reset Puzzle");
    resetPuzzle.setOnAction((ActionEvent event) -> controller.clickResetPuzzle());
    resetPuzzle.getStyleClass().add("puzzle-button");

    rightControlPane.getChildren().addAll(randPuzzle, resetPuzzle);

    return rightControlPane;
  }
}
