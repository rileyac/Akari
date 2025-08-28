package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private PuzzleLibrary library;
  private int puzzleIndex = 0;
  private boolean[][] lamps;
  private List<ModelObserver> observers = new ArrayList<>();

  public ModelImpl(PuzzleLibrary library) {
    this.library = library;
    Puzzle p = library.getPuzzle(getActivePuzzleIndex());
    this.lamps = new boolean[p.getHeight()][p.getWidth()];
    ;
  }

  @Override
  public void addLamp(int r, int c) {
    boundsCheck(r, c);
    corridorCheck(r, c);
    lamps[r][c] = true;
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    boundsCheck(r, c);
    corridorCheck(r, c);
    lamps[r][c] = false;
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    boundsCheck(r, c);
    corridorCheck(r, c);
    if (isLamp(r, c)) {
      return true;
    } else {
      return (isLampUp(r, c) || isLampRight(r, c) || isLampDown(r, c) || isLampLeft(r, c));
    }
  }

  @Override
  public boolean isLamp(int r, int c) {
    boundsCheck(r, c);
    corridorCheck(r, c);
    return lamps[r][c]; // returns boolean value
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    boundsCheck(r, c);
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException("Cell requested does not contain a lamp!");
    }
    return (isLampUp(r, c) || isLampRight(r, c) || isLampDown(r, c) || isLampLeft(r, c));
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(puzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return puzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= getPuzzleLibrarySize()) {
      throw new IndexOutOfBoundsException("Puzzle index out of bounds!");
    }
    this.puzzleIndex = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    Puzzle p = library.getPuzzle(getActivePuzzleIndex());
    this.lamps = new boolean[p.getHeight()][p.getWidth()];
    notifyObservers();
  }

  @Override
  public boolean isSolved() {

    for (int r = 0; r < getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < getActivePuzzle().getWidth(); c++) {
        if (cellTypeOf(r, c) == CellType.CORRIDOR) {
          if (!isLit(r, c)) {
            return false;
          }
        }
        if (cellTypeOf(r, c) == CellType.CLUE) {
          if (!isClueSatisfied(r, c)) {
            return false;
          }
        }
        if (lamps[r][c]) { // can't use isLamp bc it throws exception
          if (isLampIllegal(r, c)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    boundsCheck(r, c);
    if (cellTypeOf(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Cell requested is not of type clue!");
    }

    int lamps = 0;
    if (cellBoundsAndLampCheck(r - 1, c)) { // check up
      lamps += 1;
    }
    if (cellBoundsAndLampCheck(r + 1, c)) { // check down
      lamps += 1;
    }
    if (cellBoundsAndLampCheck(r, c + 1)) { // check right
      lamps += 1;
    }
    if (cellBoundsAndLampCheck(r, c - 1)) { // check left
      lamps += 1;
    }
    return getActivePuzzle().getClue(r, c) == lamps;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void boundsCheck(int r, int c) {
    if (r < 0 || c < 0 || r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth()) {
      throw new IndexOutOfBoundsException("Cell requested is out of board bounds!");
    }
  }

  private void notifyObservers() {
    if (observers == null) {
      return;
    }
    for (ModelObserver o : observers) {
      o.update(this);
    }
  }

  private boolean isLampUp(int r, int c) {
    for (int i = r - 1; i >= 0; i--) {
      if (cellTypeOf(i, c) != CellType.CORRIDOR) { // false if we see wall
        return false;
      }
      if (isLamp(i, c)) {
        return true;
      }
    }
    return false;
  }

  private boolean isLampDown(int r, int c) {
    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
      if (cellTypeOf(i, c) != CellType.CORRIDOR) { // false if we see wall
        return false;
      }
      if (isLamp(i, c)) {
        return true;
      }
    }
    return false;
  }

  private boolean isLampRight(int r, int c) {
    for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {
      if (cellTypeOf(r, i) != CellType.CORRIDOR) { // false if we see wall
        return false;
      }
      if (isLamp(r, i)) {
        return true;
      }
    }
    return false;
  }

  private boolean isLampLeft(int r, int c) {
    for (int i = c - 1; i >= 0; i--) {
      if (cellTypeOf(r, i) != CellType.CORRIDOR) { // false if we see wall
        return false;
      }
      if (isLamp(r, i)) {
        return true;
      }
    }
    return false;
  }

  private CellType cellTypeOf(int r, int c) {
    return (getActivePuzzle().getCellType(r, c));
  }

  private void corridorCheck(int r, int c) {
    if (cellTypeOf(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell requested is not type corridor!");
    }
  }

  private boolean cellBoundsAndLampCheck(int r, int c) {
    // checks bounds and if cell is lamp without throwing exceptions
    if (r < 0
        || c < 0
        || r >= getActivePuzzle().getHeight()
        || c >= getActivePuzzle().getWidth()
        || (cellTypeOf(r, c) != CellType.CORRIDOR)) {
      return false;
    }
    return lamps[r][c];
  }
}
