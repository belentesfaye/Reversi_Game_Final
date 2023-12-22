package strategies;

import model.Game;

/**
 * A Reversi strategy that prioritizes moves based on capturing.
 */
public interface ReversiStrategy {
  /**
   * Returns the best move for the current player in the given game.
   *
   * @param game the game to choose a move from
   * @return the best move for the current player in the given game
   */
  Move chooseMove(Game game);
}
