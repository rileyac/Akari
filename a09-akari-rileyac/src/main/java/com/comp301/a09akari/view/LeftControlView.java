package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

public class LeftControlView implements FXComponent, ModelObserver {
  private Model model;
  private ClassicMvcController controller;

  public LeftControlView(Model model, ClassicMvcController controller) {
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
    // Left Control Panel
    VBox leftControlPane = new VBox();
    leftControlPane.setAlignment(Pos.CENTER);
    // Spacing between the buttons
    leftControlPane.setSpacing(50);
    // Padding around the HBox for spacing
    leftControlPane.setStyle("-fx-padding: 50;");

    // Initialize buttons
    Button previousPuzzle = new Button("Previous Puzzle");
    previousPuzzle.setOnAction((ActionEvent event) -> controller.clickPrevPuzzle());
    previousPuzzle.getStyleClass().add("puzzle-button");

    Button nextPuzzle = new Button("Next Puzzle");
    nextPuzzle.setOnAction((ActionEvent event) -> controller.clickNextPuzzle());
    nextPuzzle.getStyleClass().add("puzzle-button");

    // Add buttons to control pane
    leftControlPane.getChildren().addAll(previousPuzzle, nextPuzzle);

    return leftControlPane;
  }
}
