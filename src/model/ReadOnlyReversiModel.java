package model;

import controller.IControllerImpl;
import player.GamePlayers;
import strategies.Move;


import java.util.List;
import java.util.Map;

/**
 * Represents a read-only view of a generic game with hexagonal grid-based rules.
 *
 * <p>This interface provides a read-only view of a generic game,
 * exposing methods to retrieve information
 * about the game state, current player, winner, and other game-related details.</p>
 */
public interface ReadOnlyReversiModel {

  /**
   * Checks if a move to the specified position (q, r) is valid in the game.
   *
   * @param q The q-coordinate of the move.
   * @param r The r-coordinate of the move.
   * @return `true` if the move is valid; otherwise, `false`.
   */

  boolean isValidMove(int q, int r, GamePlayers player);

  /**
   * Gets the current player in the game.
   *
   * @return The current player.
   */

  GamePlayers getCurrentPlayer();

  /**
   * Gets the winner of the game.
   *
   * @return A string representing the winner or an empty string if the game is not over.
   */

  GamePlayers getWinner();

  /**
   * Calculates the score for the given player.
   *
   * @param gamePlayer The player for whom to calculate the score.
   * @return The score of the player.
   */

  int score(GamePlayers gamePlayer);

  /**
   * Counts the number of pieces on the board for the specified player.
   *
   * @param gamePlayers The player for whom to count the pieces.
   * @return The count of pieces for the player.
   */

  int countPieces(GamePlayers gamePlayers);

  /**
   * Gets the state of the cell at the specified position (q, r) on the board.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return The state of the cell.
   */

  CellState getCellState(int q, int r);

  /**
   * Checks if the game is over.
   *
   * @return `true` if the game is over; otherwise, `false`.
   */

  boolean isGameOver();

  /**
   * Gets the game board.
   *
   * @return The game board.
   */

  Board getBoard();

  /**
   * Passes the turn to the next player.
   */

  void pass();

  /**
   * Gets a map representing the board with hexagonal coordinates as keys and cells as values.
   *
   * @return A map representing the board.
   */

  Map<HexCoordinate, Cell> getBoardMap();

  /**
   * Gets the size of the game board.
   *
   * @return The size of the game board.
   */

  int getBoardSize();

  /**
   * Plays a move at the specified position (q, r) in the game.
   *
   * @param q The q-coordinate of the move.
   * @param r The r-coordinate of the move.
   */
  void play(int q, int r);

  /*
   * Adds a model status listener to the game.
   * @param iController The model status listener to add.
   */
  void addModelStatusListener(IControllerImpl iController);

  /*
   * Removes a model status listener from the game.
   */
  void switchPlayer();

  /*
   * Starts the game.
   */
  void startGame();

  /**
   * Retrieves a list of valid moves in the game.
   *
   * @return A list of valid moves.
   */

  List<Move> getValidMoves();

  /**
   * Gets the current player in the game.
   * @param player The current player.
   * @return The current player.
   *
   */
  GamePlayers setCurrentPlayer(GamePlayers player);


  /**
   * Checks if the game is over.
   * @param currentPlayer The current player.
   * @return `true` if the game is over; otherwise, `false`.
   */
  boolean hasValidMovesForPlayer(GamePlayers currentPlayer);
}

