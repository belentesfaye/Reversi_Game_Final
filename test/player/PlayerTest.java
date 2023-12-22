package player;

import controller.IControllerImpl;
import model.Board;
import model.Cell;
import model.CellState;
import model.Game;
import model.HexCoordinate;
import model.ReadOnlyReversiModel;
import org.junit.Before;
import org.junit.Test;

import strategies.Move;
import strategies.ReversiStrategy;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 *  tests for the AIPlayer and HumanPlayer classes.
 */
public class PlayerTest {

  private Game mockModel;
  private ReversiStrategy mockStrategy;

  private class MockModel implements Game, ReadOnlyReversiModel {
    @Override
    public boolean isValidMove(int q, int r, GamePlayers player) {
      return false;
    }

    /**
     * Gets the opponent player in the game.
     *
     * @return The opponent player.
     */
    @Override
    public GamePlayers getOpponent() {
      return null;
    }

    @Override
    public GamePlayers getCurrentPlayer() {
      return null;
    }

    /**
     * Gets the winner of the game.
     *
     * @return A string representing the winner or an empty string if the game is not over.
     */
    @Override
    public GamePlayers getWinner() {
      return null;
    }

    /**
     * Calculates the score for the given player.
     *
     * @param gamePlayer The player for whom to calculate the score.
     * @return The score of the player.
     */
    @Override
    public int score(GamePlayers gamePlayer) {
      return 0;
    }

    /**
     * Counts the number of pieces on the board for the specified player.
     *
     * @param gamePlayers The player for whom to count the pieces.
     * @return The count of pieces for the player.
     */
    @Override
    public int countPieces(GamePlayers gamePlayers) {
      return 0;
    }

    /**
     * Gets the state of the cell at the specified position (q, r) on the board.
     *
     * @param q The q-coordinate of the cell.
     * @param r The r-coordinate of the cell.
     * @return The state of the cell.
     */
    @Override
    public CellState getCellState(int q, int r) {
      return null;
    }

    /**
     * Checks if the game is over.
     *
     * @return `true` if the game is over; otherwise, `false`.
     */
    @Override
    public boolean isGameOver() {
      return false;
    }

    /**
     * Gets the game board.
     *
     * @return The game board.
     */
    @Override
    public Board getBoard() {
      return null;
    }

    /**
     * Passes the turn to the next player.
     */
    @Override
    public void pass() {
      //TODO: Implement this
    }

    /**
     * Gets a map representing the board with hexagonal coordinates as keys and cells as values.
     *
     * @return A map representing the board.
     */
    @Override
    public Map<HexCoordinate, Cell> getBoardMap() {
      return null;
    }

    /**
     * Gets the size of the game board.
     *
     * @return The size of the game board.
     */
    @Override
    public int getBoardSize() {
      return 0;
    }

    /**
     * Plays a move at the specified position (q, r) in the game.
     *
     * @param q The q-coordinate of the move.
     * @param r The r-coordinate of the move.
     */
    @Override
    public void play(int q, int r) {
      //TODO: Implement this
    }

    @Override
    public void addModelStatusListener(IControllerImpl iController) {
      //TODO: Implement this
    }

    @Override
    public void switchPlayer() {
      //TODO: Implement this
    }

    @Override
    public void startGame() {
      // Simulate starting a game
    }

    /**
     * Retrieves a list of valid moves in the game.
     *
     * @return A list of valid moves.
     */
    @Override
    public List<Move> getValidMoves() {
      return null;
    }

    /**
     * Gets the current player in the game.
     *
     * @param player The current player.
     * @return The current player.
     */
    @Override
    public GamePlayers setCurrentPlayer(GamePlayers player) {
      return null;
    }

    /**
     * Checks if the game is over.
     *
     * @param currentPlayer The current player.
     * @return `true` if the game is over; otherwise, `false`.
     */
    @Override
    public boolean hasValidMovesForPlayer(GamePlayers currentPlayer) {
      return false;
    }
  }

  private class MockStrategy implements ReversiStrategy {
    @Override
    public Move chooseMove(Game game) {
      return null;
    }
  }



  @Before
  public void setUp() {
    mockModel = new MockModel();
    mockStrategy = new MockStrategy();
  }

  /**
   * Test the AIPlayer's makeMove method.
   * It should make a valid move on the game board.
   */
  @Test
  public void testAIMakeMove() {
    AIPlayer aiPlayer = new AIPlayer(mockModel);

    aiPlayer.makeMove(mockModel);
    assertTrue(mockModel.getBoard().isModified());
  }

  /**
   * Test the AIPlayer's makeStrategyMove method.
   * It should make a move using the specified strategy on the game board.
   */
  @Test
  public void testAIMakeStrategyMove() {
    AIPlayer aiPlayer = new AIPlayer(mockModel, mockStrategy);

    aiPlayer.makeStrategyMove(mockModel);
    assertTrue(mockModel.getBoard().isModified());
  }

  /**
   * Test the HumanPlayer's makeMove method.
   * It should make a valid move on the game board.
   */
  @Test
  public void testHumanMakeMove() {
    HumanPlayer humanPlayer = new HumanPlayer(mockModel);

    humanPlayer.makeMove(mockModel);
    assertTrue(mockModel.getBoard().isModified());
  }

  /**
   * Test the HumanPlayer's makeStrategyMove method.
   * It should make a move using the specified strategy on the game board.
   */
  @Test
  public void testHumanMakeStrategyMove() {
    HumanPlayer humanPlayer = new HumanPlayer(mockModel, mockStrategy);

    humanPlayer.makeStrategyMove(mockModel);
    assertTrue(mockModel.getBoard().isModified());
  }
}
