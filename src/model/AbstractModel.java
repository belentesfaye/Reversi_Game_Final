package model;

import player.GamePlayers;
import strategies.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game of Reversi.
 * <p>This class implements the {@link Game} interface for a
 * Reversi game on a hexagonal grid-based board.</p>
 * Invariants:
 * The game board is a hexagonal grid.
 * The current player alternates between black and white after each move.
 * Valid moves are determined by the rules of Reversi.
 * Players take turns making moves until the game is over.
 */
public abstract class AbstractModel implements Game {

  protected final Board board; // The game board.
  protected GamePlayers currentPlayer; // The current player.

  /**
   * Constructs a new Reversi game with the given game board.
   */
  public AbstractModel() { //add player for Demo purposes
    this.board = new BoardImpl(6);
    this.currentPlayer = GamePlayers.Black;
  }

  /**
   * Gets the winner of the game.
   *
   * @return A string representing the winner or an empty string if the game is not over.
   */
  @Override
  public GamePlayers getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over yet.");
    }

    int blackCount = countPieces(GamePlayers.Black);
    int whiteCount = countPieces(GamePlayers.White);

    if (blackCount > whiteCount) {
      return GamePlayers.Black;
    } else if (whiteCount > blackCount) {
      return GamePlayers.White;
    } else {
      return null;
    }
  }


  /**
   * Calculates the score for the given player.
   *
   * @param gamePlayer The player for whom to calculate the score.
   * @return The score of the player.
   */
  @Override
  public int score(GamePlayers gamePlayer) {
    CellState playerDisc = (gamePlayer == GamePlayers.Black) ? CellState.X : CellState.O;
    int playerScore = 0;
    for (int q = 0; q < board.getSize(); q++) {
      for (int r = 0; r < board.getSize(); r++) {
        Cell cell = board.getCell(q, r);
        if (cell.getState() == playerDisc) {
          playerScore++;
        }
      }
    }

    return playerScore;
  }

  /**
   * Retrieves a list of valid moves in the game.
   *
   * @return A list of valid moves.
   */
  @Override
  public List<Move> getValidMoves() {
    List<Move> validMoves = new ArrayList<>();
    for (int q = 0; q < board.getSize(); q++) {
      for (int r = 0; r < board.getSize(); r++) {
        if (isValidMove(q, r, currentPlayer)) {
          validMoves.add(new Move(q, r));
        }
      }
    }
    return validMoves;
  }

}
