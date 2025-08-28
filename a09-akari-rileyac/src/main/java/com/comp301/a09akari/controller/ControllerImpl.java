package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelImpl;

import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private Model model;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("Provided model cannot be null!");
    }
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int index = model.getActivePuzzleIndex();
    if (index + 1 >= model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(index); // stay at same index if we are at the last puzzle
    } else {
      model.setActivePuzzleIndex(index + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int index = model.getActivePuzzleIndex();
    if (index - 1 < 0) {
      model.setActivePuzzleIndex(index); // stay at same index if we are at the first puzzle
    } else {
      model.setActivePuzzleIndex(index - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    Random rand = new Random();
    int randInt = rand.nextInt(model.getPuzzleLibrarySize());
    while (randInt == model.getActivePuzzleIndex()) {
      randInt = rand.nextInt(model.getPuzzleLibrarySize());
    }
    model.setActivePuzzleIndex(randInt);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else {
      model.addLamp(r, c);
    }
  }
}
