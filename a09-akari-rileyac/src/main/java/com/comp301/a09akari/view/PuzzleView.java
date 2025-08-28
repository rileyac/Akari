package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import com.comp301.a09akari.model.Puzzle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

import java.awt.*;

public class PuzzleView implements FXComponent, ModelObserver {
  private Model model;
  private ClassicMvcController controller;

  // Create images
  private final Image lightBulb = new Image("light-bulb.png");
  private final Image redX = new Image("red-x.png");

  public PuzzleView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException("Model and controller cannot be null!");
    }
    this.model = model;
    this.controller = controller;
  }

  @Override
  public void update(Model model) {} // TODO

  @Override
  public Parent render() {
    // Create grid pane for the cells
    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setPadding(new Insets(20, 50, 20, 50));
    gridPane.setGridLinesVisible(true);

    Puzzle puzzle = model.getActivePuzzle();

    // Set all the cells
    CellType type = null;
    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {
        Node cell;
        switch (puzzle.getCellType(r, c)) {
          case CORRIDOR:
            cell = createCorridorCell(r, c);
            break;
          case CLUE:
            cell = createClueCell(r, c);
            break;
          case WALL:
            cell = createWallCell();
            break;
          default:
            throw new IllegalStateException("Unexpected cell type!");
        }
        gridPane.add(cell, c, r);
      }
    }
    return gridPane;
  }

  private Node createCorridorCell(int r, int c) {
    Button button = new Button();
    button.getStyleClass().add("button");
    ImageView view = null;

    if (model.isLamp(r, c)) {
      if (model.isLampIllegal(r, c)) {
        button.getStyleClass().add("lamp");
        view = new ImageView(redX);
      } else {
        button.getStyleClass().add("lamp");
        view = new ImageView(lightBulb);
      }
    } else {
      if (model.isLit(r, c)) {
        button.getStyleClass().add("lit-corridor");
      }
    }

    if (view != null) {
      view.setFitHeight(39);
      view.setFitWidth(39);
      // view.maxHeight(52);
      // view.maxWidth(52);
      // view.minHeight(52);
      // view.minWidth(52);
      view.setPreserveRatio(true);
      button.setGraphic(view);
    }

    button.setOnAction(e -> controller.clickCell(r, c));
    return button;
  }

  private Node createClueCell(int r, int c) {
    Puzzle puzzle = model.getActivePuzzle();
    String clueValue = Integer.toString(puzzle.getClue(r, c));
    Button clueCell = new Button();
    // clueCell.maxHeight(52);
    // clueCell.maxWidth(52);
    // clueCell.minHeight(52);
    // clueCell.minWidth(52);
    clueCell.getStyleClass().add("clue-cell");

    Label label = new Label(clueValue);
    label.setAlignment(Pos.CENTER);

    if (model.isClueSatisfied(r, c)) {
      // clueCell.getStyleClass().add("solved-clue-cell");
      label.getStyleClass().add("solved-clue-value");
    } else {
      // clueCell.getStyleClass().add("unsolved-clue-cell");
      label.getStyleClass().add("unsolved-clue-value");
    }

    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(clueCell, label);

    return stackPane;
  }

  private Node createWallCell() {
    Button wall = new Button();
    wall.getStyleClass().add("wall");
    return wall;
  }
}
