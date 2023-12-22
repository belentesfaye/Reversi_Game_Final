package strategies;

import model.CellState;
import model.Game;
import model.ReversiGame;
import player.GamePlayers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Reversi strategy that prioritizes moves based on capturing
 * the maximum number of opponent pieces.
 */
public class CaptureStrategy implements ReversiStrategy {

  /**
   * choose a move for the strategy.
   * @param game the game to choose a move from
   * @return the move.
   */
  @Override
  public Move chooseMove(Game game) {
    List<Move> validMoves = getValidMoves(game);
    if (validMoves.isEmpty()) {
      return null;
    }

    int maxCaptures = -1;
    Move selectedMove = null;

    for (Move move : validMoves) {
      ReversiGame copyGame = new ReversiGame();
      copyGame.play(move.getQ(), move.getR());
      int captures = countCaptures(copyGame, game.getCurrentPlayer());

      if (captures > maxCaptures || (captures == maxCaptures &&
              move.compareTo(Objects.requireNonNull(selectedMove)) < 0)) {
        maxCaptures = captures;
        selectedMove = move;
      }
    }

    return selectedMove;
  }

  /**
   * Counts the number of captures for a given game configuration and player.
   * @param copyGame      The ReversiGame copy to analyze.
   * @param currentPlayer The player for whom to count captures.
   * @return The number of captures for the specified player.
   **/
  private int countCaptures(ReversiGame copyGame, GamePlayers currentPlayer) {
    int captures = 0;
    for (int q = 0; q < copyGame.getBoard().getSize(); q++) {
      for (int r = 0; r < copyGame.getBoard().getSize(); r++) {
        if (copyGame.getCellState(q, r) == CellState.EMPTY) {
          if (isCaptured(copyGame, q, r, currentPlayer)) {
            captures++;
          }
        }
      }
    }
    return captures;
  }

  /**
   * Checks if a specific cell in the ReversiGame is captured by the given player.
   * @param copyGame      The ReversiGame copy to analyze.
   * @param q             The q-coordinate of the cell.
   * @param r             The r-coordinate of the cell.
   * @param currentPlayer The player for whom to check if the cell is captured.
   * @return True if the cell is captured by the player, false otherwise.
   */
  private boolean isCaptured(ReversiGame copyGame, int q, int r, GamePlayers currentPlayer) {
    for (int dq = -1; dq <= 1; dq++) {
      for (int dr = -1; dr <= 1; dr++) {
        if (dq != 0 || dr != 0) {
          if (isCapturedInDirection(copyGame, q, r, dq, dr, currentPlayer)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Checks if a cell in a given direction from a specified starting point is captured by
   * the player.
   * @param copyGame      The ReversiGame copy to analyze.
   * @param q             The q-coordinate of the starting point.
   * @param r             The r-coordinate of the starting point.
   * @param dq            The change in q-coordinate indicating the direction.
   * @param dr            The change in r-coordinate indicating the direction.
   * @param currentPlayer The player for whom to check if the cell is captured.
   * @return True if the cell in the specified direction is captured, false otherwise.
   */
  private boolean isCapturedInDirection(ReversiGame copyGame, int q, int r, int dq, int dr,
                                        GamePlayers currentPlayer) {
    int nextQ = q + dq;
    int nextR = r + dr;

    if (!copyGame.isValidCell(nextQ, nextR)) {
      return false;
    }
    return  copyGame.getCellState(nextQ, nextR) == CellState.X && currentPlayer
            == GamePlayers.Black
            || copyGame.getCellState(nextQ, nextR) == CellState.O && currentPlayer
            == GamePlayers.White
            || isCapturedInDirection(copyGame, nextQ, nextR, dq, dr, currentPlayer);
  }

  /**
   * Generates a list of valid moves for the current player in the given game.
   * @param game The ReversiGame in which to find valid moves.
   * @return A list of valid moves for the current player.
   */
  private List<Move> getValidMoves(Game game) {
    List<Move> validMoves = new ArrayList<>();
    for (int q = 0; q < game.getBoard().getSize(); q++) {
      for (int r = 0; r < game.getBoard().getSize(); r++) {
        if (game.isValidMove(q, r, game.getCurrentPlayer())) {
          validMoves.add(new Move(q, r));
        }
      }
    }
    return validMoves;
  }
}
