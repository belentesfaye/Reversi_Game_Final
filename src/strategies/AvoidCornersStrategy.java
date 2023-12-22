package strategies;

import model.Game;


import java.util.ArrayList;
import java.util.List;

/**
 * A Reversi strategy that prioritizes moves based on capturing.
 */
public class AvoidCornersStrategy implements ReversiStrategy {

  /**
   * Choosing a move for the strategy.
   * @param game the game to choose a move from
   * @return a move.
   */
  @Override
  public Move chooseMove(Game game) {
    List<Move> validMoves = game.getValidMoves();
    List<Move> safeMoves = new ArrayList<>();

    for (Move move : validMoves) {
      if (!isNextToCorner(move, game)) {
        safeMoves.add(move);
      }
    }

    if (!safeMoves.isEmpty()) {
      safeMoves.sort(null);
      return safeMoves.get(0);
    }

    // If no safe moves, return null (pass)
    return null;
  }

  /**
   * Checking if the move is next to the corner.
   * @param move move.
   * @param game new game.
   * @return true if the move is next to the corner.
   */
  boolean isNextToCorner(Move move, Game game) {
    int q = move.getQ();
    int r = move.getR();
    int boardSize = game.getBoardSize();

    return isCorner(q - 1, r - 1, boardSize)
            || isCorner(q - 1, r + 1, boardSize)
            || isCorner(q + 1, r - 1, boardSize)
            || isCorner(q + 1, r + 1, boardSize);
  }

  /**
   * Checks if the move is in a corner.
   * @param q q coordinate of starting point.
   * @param r r coordinate of starting point.
   * @param boardSize size of board.
   * @return true if the move is in the corner.
   */
  private boolean isCorner(int q, int r, int boardSize) {
    return (q == 0 || q == boardSize - 1) && (r == 0 || r == boardSize - 1);
  }
}
