package strategies;


import model.Board;
import model.BoardImpl;
import model.CellState;
import model.Mock;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Tests for the GoForCornersStrategy class.
 */
public class GoForCornersStrategyTest  {
  @Test
  public void testChooseMove() {
    Mock game = new Mock(createBoardWithState(), createValidMoves());
    MinimaxStrategy minimaxStrategy = new MinimaxStrategy();


    Move selectedMove = minimaxStrategy.chooseMove(game);

    Assert.assertNotNull(selectedMove);
    Assert.assertTrue(createValidMoves().contains(selectedMove));
  }

  private Board createBoardWithState() {
    Board board = new BoardImpl(8);

    board.setState(3, 2, CellState.X);
    board.setState(4, 3, CellState.O);
    board.setState(2, 4, CellState.X);

    return board;
  }

  private List<Move> createValidMoves() {
    List<Move> validMoves = new ArrayList<>();
    validMoves.add(new Move(3, 4));
    validMoves.add(new Move(5, 5));
    return validMoves;
  }
}