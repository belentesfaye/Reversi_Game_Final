package strategies;

import model.Board;
import model.BoardImpl;
import model.CellState;
import model.MockSqaure;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the CaptureSquare strategy.
 */

public class AvoidSquareTest {
  @Test
  public void testChooseMove() {
    List<Move> validMoves = createValidMoves();
    MockSqaure game = new MockSqaure(createBoardWithState(), validMoves);
    AvoidCornersStrategy avoidCornersStrategy = new AvoidCornersStrategy();

    Move selectedMove = avoidCornersStrategy.chooseMove(game);

    Assert.assertNotNull(selectedMove);
    Assert.assertFalse(avoidCornersStrategy.isNextToCorner(selectedMove, game));
  }

  private Board createBoardWithState() {
    Board board = new BoardImpl(8);
    board.setState(3, 2, CellState.X);
    board.setState(4, 3, CellState.X);
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
