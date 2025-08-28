package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class View implements FXComponent, ModelObserver {
  private FXComponent puzzleView;
  private FXComponent leftControlView;
  private FXComponent rightControlView;
  private FXComponent messageView;
  private FXComponent titleView;
  private Scene scene;

  public View(Model model, ClassicMvcController controller) {
    this.puzzleView = new PuzzleView(model, controller);
    this.leftControlView = new LeftControlView(model, controller);
    this.rightControlView = new RightControlView(model, controller);
    this.messageView = new MessageView(model, controller);
    this.titleView = new TitleView();
    this.scene = new Scene(render());
    scene.getStylesheets().add("main.css");
    model.addObserver(this);
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }

  @Override
  public Parent render() {
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(titleView.render());
    borderPane.setBottom(messageView.render());
    borderPane.setCenter(puzzleView.render());
    borderPane.setRight(rightControlView.render());
    borderPane.setLeft(leftControlView.render());
    return borderPane;
  }
}
