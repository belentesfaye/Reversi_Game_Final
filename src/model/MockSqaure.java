package model;

import player.GamePlayers;
import strategies.Move;

import java.util.List;

/**
 * Mock class for the SquareReversiGame class.
 */
public class MockSqaure extends SquareReversiGame {

  /**
   * Constructor for the mock class.
   *
   * @param board      the board to be used
   * @param validMoves the valid moves to be used
   */
  public MockSqaure(Board board, List<Move> validMoves) {
    super();
    this.validMoves = validMoves;
  }

  private final List<Move> validMoves;

  @Override
  public boolean isValidMove(int q, int r, GamePlayers player) {
    return validMoves.contains(new Move(q, r));
  }
}
