package player;


import model.Game;
import model.ReadOnlyReversiModel;
import strategies.Move;
import strategies.ReversiStrategy;


/**
 * Represents a human player in the game.
 */

public class HumanPlayer implements Player {
  private ReversiStrategy strategy;
  private  ReadOnlyReversiModel model;



  /**
   * Constructs a new human player with the specified player type.
   *
   * @param model The type of player (e.g., Black or White).
   */

  public HumanPlayer(ReadOnlyReversiModel model) {
    this.model = model;
  }

  /**
   * Constructs a new human player with the specified player type and strategy.
   *
   * @param model    The type of player (e.g., Black or White).
   * @param strategy The strategy to use for the human player.
   */
  public HumanPlayer(ReadOnlyReversiModel model, ReversiStrategy strategy) {
    this.strategy = strategy;
    this.model = model;
    makeStrategyMove((Game) model);
  }

  /**
   * Makes a move on the game board as the human player.
   *
   * @param board The game board on which the move is to be made.
   */

  public void makeStrategyMove(Game board) {
    Move humanMove = strategy.chooseMove(board);
    if (humanMove != null) {
      model.play(humanMove.getQ(), humanMove.getR());
    }
  }

  /**
   * Makes a move on the game board as the human player.
   *
   * @param model The game board on which the move is to be made.
   */

  @Override
  public void makeMove(Game model) {
    // do nothing
    // human player makes a move by clicking on the board
  }

}

