package model;


import player.GamePlayers;


/**
 * Represents a generic game with hexagonal grid-based rules.
 *
 * <p>This interface defines the common functionalities for a game with hexagonal grid-based rules.
 * It includes methods for checking the validity of a move, retrieving valid moves,
 * and getting information about the opponent.</p>
 */
public interface Game extends ReadOnlyReversiModel {

  /**
   * Checks if a move to the specified position (q, r) is valid in the game.
   *
   * @param q The q-coordinate of the move.
   * @param r The r-coordinate of the move.
   * @return `true` if the move is valid; otherwise, `false`.
   */
  boolean isValidMove(int q, int r, GamePlayers player);



  /**
   * Gets the opponent player in the game.
   *
   * @return The opponent player.
   */
  GamePlayers getOpponent();

}
