package player;



import model.Game;
import model.ReadOnlyReversiModel;
import strategies.Move;
import strategies.ReversiStrategy;

import java.util.List;
import java.util.Random;

/**
 * Represents an AI player in the game.
 */

public class AIPlayer implements Player {
  private final ReadOnlyReversiModel model;
  private ReversiStrategy strategy;

  /**
   * Constructs a new AI player with the given game model.
   *
   * @param model The game model to use for the AI player.
   */

  public AIPlayer(ReadOnlyReversiModel model) {
    this.model = model;
  }

  /**
   * Constructs a new AI player with the given game model and strategy.
   *
   * @param model    The game model to use for the AI player.
   * @param strategy The strategy to use for the AI player.
   */

  public AIPlayer(ReadOnlyReversiModel model, ReversiStrategy strategy) {
    this.model = model;
    this.strategy = strategy;
  }

  /**
   * Makes a move on the game board. If a strategy is set, it makes a strategic move; otherwise,
   * it makes a random move.
   *
   * @param board The game board on which the AI player makes a move.
   */
  @Override
  public void makeMove(Game board) {
    List<Move> validMoves = model.getValidMoves();
    if (!validMoves.isEmpty()) {
      if (strategy != null) {
        makeStrategyMove(board);
      } else {
        aIGameMove(board);
      }
    }
  }

  /**
   * Makes a move on the game board using a predefined strategy.
   *
   * @param board The game board on which the AI player makes a move.
   */
  void makeStrategyMove(Game board) {
    if (strategy != null) {
      Move aiMove = strategy.chooseMove(board);
      if (aiMove != null) {
        model.play(aiMove.getQ(), aiMove.getR());
        System.out.println("AI Player made a move at: (" + aiMove.getQ() + ", " +
                aiMove.getR() + ")");
        model.switchPlayer(); // Switch the player to white
      }
    }
  }

  /**
   * Gets the strategy used by the AI player.
   *
   * @return The strategy employed by the AI player.
   */

  public ReversiStrategy getStrategy() {
    return strategy;
  }

  /**
   * Makes a move on the game board without using a strategy, choosing a random move.
   * @param board The game board on which the AI player makes a move.
   */
  public void aIGameMove(Game board) {
    List<Move> validMoves = model.getValidMoves();

    if (!validMoves.isEmpty() && model.getCurrentPlayer() == GamePlayers.White) {
      Move aiMove = validMoves.get(new Random().nextInt(validMoves.size()));
      model.play(aiMove.getQ(), aiMove.getR());
    }
  }
}

