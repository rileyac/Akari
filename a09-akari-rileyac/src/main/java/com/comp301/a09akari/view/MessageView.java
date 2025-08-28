package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MessageView implements FXComponent, ModelObserver {
  private ClassicMvcController controller;
  private Model model;

  public MessageView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException("Model and controller cannot be null!");
    }
    this.controller = controller;
    this.model = model;
  }

  @Override
  public void update(Model model) {}

  @Override
  public Parent render() {
    HBox box = new HBox();
    box.setSpacing(60); // status and puzzle number
    box.setAlignment(Pos.CENTER);
    box.getStyleClass().add("status-box");

    String puzzleIndex = Integer.toString(model.getActivePuzzleIndex() + 1);
    Label puzzleNumberLabel = new Label("Puzzle Number: " + puzzleIndex);
    puzzleNumberLabel.getStyleClass().add("puzzle-number-label");

    Label puzzleStatusLabel = new Label();
    if (model.isSolved()) {
      puzzleStatusLabel.setText("Status: Solved");
      puzzleStatusLabel.getStyleClass().add("status");
    } else {
      puzzleStatusLabel.setText("Status: Unsolved");
      puzzleStatusLabel.getStyleClass().add("status");
    }

    box.getChildren().addAll(puzzleNumberLabel, puzzleStatusLabel);

    return box;
  }
}
