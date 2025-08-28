package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    Puzzle p1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle p2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle p3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle p4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle p5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

    PuzzleLibrary library = new PuzzleLibraryImpl();
    library.addPuzzle(p1);
    library.addPuzzle(p2);
    library.addPuzzle(p3);
    library.addPuzzle(p4);
    library.addPuzzle(p5);

    Model model = new ModelImpl(library);
    ClassicMvcController controller = new ControllerImpl(model);
    View view = new View(model, controller);
    stage.setTitle("Akari");
    Scene scene = view.getScene();
    stage.setScene(scene);
    stage.setFullScreen(true);
    stage.show();
  }
}
