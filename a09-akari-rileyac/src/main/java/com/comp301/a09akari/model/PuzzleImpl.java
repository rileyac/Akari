package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private int[][] board;

  public PuzzleImpl(int[][] board) { // [r], [c]
    boardCheck(board);
    this.board = board;
  }

  @Override
  public int getWidth() { // columns
    return board[0].length;
  }

  @Override
  public int getHeight() { // rows
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    cellCheck(r, c);
    CellType type = null;
    int value = board[r][c];
    switch (value) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
        type = CellType.CLUE;
        break;
      case 5:
        type = CellType.WALL;
        break;
      case 6:
        type = CellType.CORRIDOR;
        break;
      default:
        throw new IllegalArgumentException("Cell type not defined for value " + board[r][c]);
    }
    return type;
  }

  @Override
  public int getClue(int r, int c) {
    cellCheck(r, c);
    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Cell requested is not a clue cell!");
    }
    return board[r][c];
  }

  private void cellCheck(int r, int c) {
    if (r >= getHeight() || c >= getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("That was not a valid cell!");
    }
  }

  private void boardCheck(int[][] board) {
    // null board, len 0 rows, unequal rows, undefined cell values
    if (board == null) { // null board
      throw new IllegalArgumentException("Board cannot be null!");
    }

    if (board.length == 0) { // 0 rows
      throw new IllegalArgumentException("Board cannot have zero rows!");
    }

    int rl = -1; // variable for row length
    for (int[] row : board) {
      for (int value : row) { // check for valid cell values
        if (value > 6 || value < 0) {
          throw new IllegalArgumentException(
              "Value [" + value + "] not defined. Values range from 0 to 6.");
        }
      }
      if (rl == -1) {
        rl = row.length; // initialize only on first iteration
      } else if (rl != row.length) { // compare all row lengths
        throw new IllegalArgumentException("Rows cannot be different lengths!");
      }
    }
  }
}
