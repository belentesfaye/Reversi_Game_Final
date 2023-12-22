package controller;

import model.Board;
import model.BoardImpl;
import model.Cell;
import model.CellState;
import model.HexCoordinate;
import model.ReadOnlyReversiModel;
import model.ReversiGame;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import player.AIPlayer;
import player.GamePlayers;
import player.Player;
import strategies.Move;
import view.IView;
import player.HumanPlayer;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the IController Implementation.
 */
public class ControllerTest {

  static class MockModel implements ReadOnlyReversiModel {
    private GamePlayers currentPlayer;
    private GamePlayers winner;

    /**
     * Checks if a move to the specified position (q, r) is valid in the game.
     *
     * @param q      The q-coordinate of the move.
     * @param r      The r-coordinate of the move.
     * @param player The player making the move.
     * @return `true` if the move is valid; otherwise, `false`.
     */
    @Override
    public boolean isValidMove(int q, int r, GamePlayers player) {
      return false;
    }

    @Override
    public GamePlayers getCurrentPlayer() {
      return currentPlayer;
    }

    @Override
    public void startGame() {
      // Simulate starting a game
      ReadOnlyReversiModel model = new ReversiGame();
      currentPlayer = model.getCurrentPlayer();
      winner = null;
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
      currentPlayer = player;
      return currentPlayer;
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


    @Override
    public GamePlayers getWinner() {
      return winner;
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

    public void setWinner(GamePlayers winner) {
      this.winner = winner;
    }

    @Override
    public Board getBoard() {
      // Return a sample board for testing
      return new BoardImpl(6);
    }

    /**
     * Passes the turn to the next player.
     */
    @Override
    public void pass() {
      currentPlayer = (currentPlayer == GamePlayers.Black) ? GamePlayers.White : GamePlayers.Black;
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
      // Not needed for testing
    }

    @Override
    public void addModelStatusListener(IControllerImpl iController) {
      // Not needed for testing
    }

    @Override
    public void switchPlayer() {
      // Not needed for testing
    }
  }


  static class MockView implements IView {
    public boolean makeVisibleCalled;
    private Board updatedBoard;
    boolean showMessageDialogCalled = false;
    boolean showPlayerTurnMessageCalled = false;

    @Override
    public void makeVisible() {
      makeVisibleCalled = true;
    }

    @Override
    public void showErrorMessage(String error) {
      // Not needed for testing
    }

    @Override
    public void showPlayerTurnMessage(GamePlayers currentPlayer) {
      showPlayerTurnMessageCalled = true;
    }

    @Override
    public void repaint() {
      // Not needed for testing
    }

    @Override
    public void addPlayerActionListener(IController controller) {
      // Not needed for testing
    }

    @Override
    public void updateBoard(Board board) {
      updatedBoard = board;
    }

    @Override
    public void showMessageDialog(String s) {
      showMessageDialogCalled = true;
    }

    public Board getUpdatedBoard() {
      return updatedBoard;
    }
  }

  private MockModel mockModel;
  private MockView mockView;
  private IControllerImpl controller;

  @Before
  public void setUp() {
    mockModel = new MockModel();
    mockView = new MockView();
    Player mockPlayer = new HumanPlayer(mockModel);
    controller = new IControllerImpl(mockModel, mockPlayer, mockView);
  }

  /**
   * Test the startGame method of IControllerImpl.
   * It should make the view visible and start the game.
   */
  @Test
  public void testStartGame() {
    assertFalse(mockView.makeVisibleCalled);
    assertNull(mockView.getUpdatedBoard());

    controller.startGame();

    Assert.assertTrue(mockView.makeVisibleCalled);
    Assert.assertNotNull(mockView.getUpdatedBoard());
  }

  /**
   * Test the changePlayer method of IController Impl.
   * It should switch the player from the current player to the next player.
   */
  @Test
  public void testChangePlayer() {
    assertEquals(HumanPlayer.class, mockModel.getCurrentPlayer().getClass());
    assertFalse(mockView.showPlayerTurnMessageCalled);

    controller.changePlayer();

    assertEquals(AIPlayer.class, mockModel.getCurrentPlayer().getClass());
    assertTrue(mockView.showPlayerTurnMessageCalled);
  }

  /**
   * Test for the endGame method of IControllerImpl when there is a winner.
   * It should verify that the controller correctly handles the end of the game,
   * sets the winner in the model, and displays the winner in the view.
   */
  @Test
  public void testEndGameWithWinner() {
    assertNull(mockModel.getWinner());
    assertFalse(mockView.showMessageDialogCalled);

    mockModel.setWinner(GamePlayers.Black);
    controller.endGame();

    assertEquals(HumanPlayer.class, mockModel.getWinner());
    assertTrue(mockView.showMessageDialogCalled);
  }

  /**
   * Test for the endGame method of IControllerImpl when there is no winner.
   * It should verify that the controller correctly handles the end of the game,
   * does not set a winner in the model, and displays the end of the game in the view.
   */
  @Test
  public void testEndGameWithoutWinner() {
    assertNull(mockModel.getWinner());
    assertFalse(mockView.showMessageDialogCalled);

    controller.endGame();

    assertNull(mockModel.getWinner());
    assertTrue(mockView.showMessageDialogCalled);
  }

}
