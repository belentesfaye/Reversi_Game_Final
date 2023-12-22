package view;

import controller.IController;
import model.Board;
import player.GamePlayers;

/**
 * The view interface for the game.
 */
public interface IView {
  /**
   * Make the view visible.
   * This is usually called after the view is constructed.
   */
  void makeVisible();

  /**
   * Transmit an error message to the view,
   * in case the command could not be processed correctly.
   *
   * @param error error.
   */
  void showErrorMessage(String error);

  /**
   * Show the message for the player turn.
   *
   * @param currentPlayer the current player.
   */
  void showPlayerTurnMessage(GamePlayers currentPlayer);

  /**
   * Repaint the view.
   */
  void repaint();

  /**
   * Add a controller to the view.
   *
   * @param iController the controller.
   */
  void addPlayerActionListener(IController iController);

  /**
   * Update the board.
   *
   * @param board the board.
   */
  void updateBoard(Board board);

  /**
   * Show a message dialog.
   *
   * @param s the message.
   */
  void showMessageDialog(String s);
}

