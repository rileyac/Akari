package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TitleView implements FXComponent, ModelObserver {

  @Override
  public void update(Model model) {}

  @Override
  public Parent render() {
    HBox title = new HBox();
    title.setSpacing(10);
    title.setAlignment(Pos.CENTER);
    title.getStyleClass().add("title-box");
    Label titleText = new Label("Play Akari!");
    titleText.getStyleClass().add("title-text");

    title.getChildren().add(titleText);

    return title;
  }
}
