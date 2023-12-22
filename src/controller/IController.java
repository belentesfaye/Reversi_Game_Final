package controller;

/**
 * Represents the interface for the controller for the Reversi Game.
 */
public interface IController {

  /**
   * Starts the Reversi game.
   */
  void startGame();

  /**
   * Changes the current player in the Reversi game.
   */
  void changePlayer();

  /**
   * Ends the Reversi game.
   */
  void endGame();
}