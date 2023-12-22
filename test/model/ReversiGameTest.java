package model;


import player.GamePlayers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for the ReversiGame class.
 */

public class ReversiGameTest {
  private Board board;
  private ReversiGame game;


  @Before
  public void setUp() {
    board = new BoardImpl(6);
    game = new ReversiGame();
  }

  @Test
  public void testValidMoves() {
    Assert.assertTrue(game.isValidMove(2, 3, GamePlayers.Black));
    game.play(2, 3);
    Assert.assertEquals(CellState.X, game.getCellState(2, 3));
  }

  @Test
  public void testInvalidMove() {
    Assert.assertThrows(IllegalArgumentException.class, () -> game.play(11, 11));
    Assert.assertThrows(IllegalArgumentException.class, () -> game.play(7, 7));
  }

  @Test
  public void testBoardInitialization() {
    Assert.assertEquals(6, board.getSize());
    Assert.assertEquals(GamePlayers.Black, game.getCurrentPlayer());
    Assert.assertEquals(CellState.EMPTY, board.getCell(2, 2).getState());
    Assert.assertEquals(CellState.EMPTY, board.getCell(3, 3).getState());
    Assert.assertTrue(game.isValidMove(2, 3, GamePlayers.Black));
    Assert.assertEquals(0, game.countPieces(GamePlayers.Black));
    Assert.assertEquals(0, game.countPieces(GamePlayers.White));
  }


  @Test
  public void testInvalidMoves() {
    Assert.assertFalse(game.isValidMove(11, 100, GamePlayers.Black));
    Assert.assertFalse(game.isValidMove(4, 2, GamePlayers.Black));
    Assert.assertFalse(game.isValidMove(7, 7, GamePlayers.Black));
  }

  @Test
  public void testPlayWhileProgress() {
    ReversiGame game = new ReversiGame();
    Assert.assertEquals(GamePlayers.Black, game.getCurrentPlayer());
    Assert.assertThrows(IllegalStateException.class, game::getWinner);
    Assert.assertFalse(game.isGameOver());


    game.play(2, 3);
    game.play(2, 1);

    Assert.assertEquals(CellState.X, game.getCellState(2, 3));
    Assert.assertEquals(CellState.O, game.getCellState(2, 1));

    Assert.assertEquals(GamePlayers.Black, game.getCurrentPlayer());
  }

  @Test
  public void testisGameOverFalse() {
    Board testBoard = new BoardImpl(6);
    ReversiGame game = new ReversiGame();

    Assert.assertFalse(game.isGameOver());
    for (int q = 0; q < testBoard.getSize(); q++) {
      for (int r = 0; r < testBoard.getSize(); r++) {
        if (testBoard.isValidCell(q, r) && game.isValidMove(q, r, GamePlayers.Black)) {
          game.play(q, r);
        }
      }
    }

    Assert.assertFalse(game.isGameOver());
  }

  @Test
  public void testScore() {
    board.getCell(3, 2).setState(CellState.X);
    board.getCell(2, 2).setState(CellState.X);
    board.getCell(3, 3).setState(CellState.O);
    board.getCell(2, 3).setState(CellState.O);

    Assert.assertEquals(2, game.score(GamePlayers.Black));
    Assert.assertEquals(2, game.score(GamePlayers.White));
  }

  @Test
  public void testPass() {
    game.pass();
    Assert.assertEquals(GamePlayers.White, game.getCurrentPlayer());
    game.pass();
    Assert.assertEquals(GamePlayers.Black, game.getCurrentPlayer());

  }


}