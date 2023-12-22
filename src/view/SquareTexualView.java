package view;

import model.Board;
import model.Cell;
import model.CellState;
import model.SquareBoard;

/**
 * Represents a textual view of the game.
 * <p> This class provides methods to render the game board as a string.</p>
 * Invariants:
 * The textual view is associated with a game board.
 * The textual view can render the game board as a string.
 */

public class SquareTexualView implements TexualView {
  private final Board board;

  /**
   * Constructs a ReversiView with the specified game board.
   *
   * @param board The Reversi game board to render.
   */

  public SquareTexualView(Board board) {

    this.board = new SquareBoard(8);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    int size = board.getSize();

    for (int r = 0; r < size; r++) {
      for (int q = 0; q < size; q++) {
        Cell cell = board.getCell(q, r);

        if (cell == null) {
          continue;
        }

        if (cell.getState() == CellState.EMPTY) {
          builder.append("_");
        } else {
          builder.append(cell.getState() == CellState.X ? "X" : "O");
        }

        if (q != size - 1) {
          builder.append(" ");
        }
      }

      builder.append("\n");
    }
    return builder.toString();
  }

}
