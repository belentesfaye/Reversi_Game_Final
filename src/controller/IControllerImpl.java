package controller;

import model.ReadOnlyReversiModel;
import player.Player;
import view.IView;

/**
 * Represents implementation for the controller.
 */
public class IControllerImpl implements IController {
  private final ReadOnlyReversiModel model;
  private final Player player;
  private final IView view;

  /**
   * Represents implementation for the controller.
   *
   * @param model  the Reversi model.
   * @param player a Reversi Player.
   * @param view   a view for Reversi Game.
   */
  public IControllerImpl(ReadOnlyReversiModel model, Player player, IView view) {
    this.model = model;
    this.player = player;
    this.view = view;

    view.addPlayerActionListener(this);
    model.addModelStatusListener(this);
  }

  /**
   * Starts the game with a new Board, with the model and the view.
   */
  @Override
  public void startGame() {
    view.updateBoard(model.getBoard());
    view.makeVisible();
  }


  /**
   * Changes the player type from 1 to 2 and vice versa.
   */
  @Override
  public void changePlayer() {
    model.switchPlayer();
    view.showPlayerTurnMessage(model.getCurrentPlayer());
  }

  /**
   * Filler method.
   * @return a player.
   */
  public Player playerEx() {
    return this.player;
  }


  /**
   * Ends the game once there is a winner shown.
   */

  @Override
  public void endGame() {
    view.showMessageDialog("Game over! Winner: " );

  }


}
