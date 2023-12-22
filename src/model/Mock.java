package model;

import player.GamePlayers;
import strategies.Move;

import java.util.List;

/**
 * A mock implementation of the Reversi game for testing purposes.
 *
 * <p>This class extends the {@link ReversiGame} and overrides the {@code isValidMove} method
 * to use a predefined set of valid moves. It is intended for use in unit tests and mock scenarios
 * where a controlled environment for testing specific game scenarios is needed.</p>
 */

public class Mock extends ReversiGame {

  /**
   * Constructs a new Reversi game with the given game board.
   *
   * @param board The game board to use for the Reversi game.
   */
  public Mock(Board board, List<Move> validMoves) {
    super();
    this.validMoves = validMoves;
  }

  private final List<Move> validMoves;

  @Override
  public boolean isValidMove(int q, int r, GamePlayers player) {
    return validMoves.contains(new Move(q, r));
  }

}