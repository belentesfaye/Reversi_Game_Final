package strategies;

import model.CellState;
import model.Game;
import player.GamePlayers;

import java.util.List;

/**
 * A Reversi strategy that prioritizes moves based on capturing.
 */
public class MinimaxStrategy implements ReversiStrategy {

  /**
   * Chooses the best move for the AI player using the Minimax algorithm.
   * @param game the game to choose from.
   * @return The best move for the AI player.
   */
  @Override
  public Move chooseMove(Game game) {
    List<Move> validMoves = game.getValidMoves();
    Move bestMove = null;
    int bestScore = Integer.MIN_VALUE;

    for (Move move : validMoves) {
      int q = move.getQ();
      int r = move.getR();

      CellState currentPlayerState = game.getCurrentPlayer()
              == GamePlayers.Black ? CellState.X : CellState.O;
      game.getBoard().move(q, r, currentPlayerState);

      int score = evaluateOpponentMove(game, 3);
      game.getBoard().move(q, r, CellState.EMPTY);
      if (score > bestScore) {
        bestScore = score;
        bestMove = move;
      }
    }

    return bestMove;
  }

  /**
   * Evaluates the opponent's move in the Minimax algorithm.
   * @param game  The current state of the game.
   * @param depth The depth of the recursive evaluation.
   * @return The score of the opponent's move.
   */
  private int evaluateOpponentMove(Game game, int depth) {
    if (depth == 0 || game.isGameOver()) {
      return game.score(game.getCurrentPlayer()) - game.score(game.getOpponent());
    }

    List<Move> opponentMoves = game.getValidMoves();
    int minScore = Integer.MAX_VALUE;

    for (Move opponentMove : opponentMoves) {
      int q = opponentMove.getQ();
      int r = opponentMove.getR();

      CellState opponentState = game.getCurrentPlayer()
              == GamePlayers.Black ? CellState.X : CellState.O;
      game.getBoard().move(q, r, opponentState);

      int score = evaluateOpponentMove(game, depth - 1);
      minScore = Math.min(minScore, score);

      game.getBoard().move(q, r, CellState.EMPTY);

    }

    return minScore;
  }
}

