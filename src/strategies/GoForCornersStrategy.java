package strategies;

import model.Game;

import java.util.List;

/**
 * A Reversi strategy that prioritizes moves based on capturing.
 */
public class GoForCornersStrategy implements ReversiStrategy {
  @Override
  public Move chooseMove(Game game) {
    List<Move> validMoves = game.getValidMoves();
    if (!validMoves.isEmpty()) {
      for (Move move : validMoves) {
        if (isCorner(move, game.getBoardSize())) {
          return move;
        }
      }
    }
    return null;
  }

  /**
   * Checks if a given move is in one of the corners of the game board.
   * @param move      The Move object representing the move to be checked.
   * @param boardSize The size of the game board.
   * @return True if the move is in one of the corners, false otherwise.
   */
  private boolean isCorner(Move move, int boardSize) {
    int q = move.getQ();
    int r = move.getR();

    return (q == 0 || q == boardSize - 1) && (r == 0 || r == boardSize - 1);
  }
}
