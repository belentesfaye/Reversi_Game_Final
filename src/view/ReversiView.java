package view;

import model.Board;
import model.Cell;
import model.CellState;

/**
 * A textual view implementation for rendering a Reversi game board.
 */

public class ReversiView implements TexualView {

  private final Board board;

  /**
   * Constructs a ReversiView with the specified game board.
   *
   * @param board The Reversi game board to render.
   */

  public ReversiView(Board board) {
    this.board = board;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    int size = board.getSize(); //size of the board
    int totalRows = 2 * size - 1; //total rows -- so it needs to bigger than how I have now

    for (int r = 0; r < totalRows; r++) {
      //offset is the number of spaces before the first cell in the row
      int offset = Math.abs(size - 1 - r);

      //add offset number of spaces before the first cell in the row
      for (int i = 0; i < offset; i++) {
        builder.append(" ");
      }

      //add the cells in the row
      for (int q = 0; q < 2 * size - 1; q++) {
        Cell cell = board.getCell(q, r);

        if (cell == null) { //if the cell is null, skip it and continue to the next cell
          continue;
        }

        //add the cell's state to the string representation
        if (cell.getState() == CellState.EMPTY) {
          builder.append("_");
        } else {
          builder.append(cell.getState() == CellState.X ? "X" : "O");
        }

        //add a space between cells in the row
        if (q != 2 * size - 2) {
          builder.append(" ");
        }
      }

      //add a new line after each row
      builder.append("\n");
    }
    return builder.toString();
  }

}
