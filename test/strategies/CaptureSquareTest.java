package strategies;

import model.Board;
import model.CellState;
import model.MockSqaure;
import model.SquareBoard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the CaptureSquare strategy.
 */
public class CaptureSquareTest {
  @Test
  public void testChooseMove() {
    // Create a mock board with specific configurations
    Board board = new SquareBoard(8);
    board.setState(3, 2, CellState.X);
    board.setState(4, 3, CellState.X);
    board.setState(2, 4, CellState.X);
    board.setState(5, 5, CellState.X);

    // Set up valid moves for the mock game
    List<Move> validMoves = new ArrayList<>();
    validMoves.add(new Move(2, 4));
    validMoves.add(new Move(3, 4));
    validMoves.add(new Move(4, 4));
    validMoves.add(new Move(5, 4));
    validMoves.add(new Move(2, 5));
    validMoves.add(new Move(3, 5));
    validMoves.add(new Move(4, 5));
    validMoves.add(new Move(5, 5));

    MockSqaure game = new MockSqaure(board, validMoves);

    CaptureStrategy captureStrategy = new CaptureStrategy();
    Move selectedMove = captureStrategy.chooseMove(game);

    // Assert the elected move is the uppermost-leftmost coordinate
    Assert.assertNotNull(selectedMove);
    Assert.assertEquals(2, selectedMove.getQ());
    Assert.assertEquals(4, selectedMove.getR());
  }

  @Test
  public void testTieBreaking() {
    // Create a mock board with specific configurations
    Board board = new SquareBoard(8);
    board.setState(3, 2, CellState.X);
    board.setState(4, 3, CellState.X);
    board.setState(2, 4, CellState.X);
    board.setState(3, 4, CellState.X);

    // Create a Mock game using the mock board


    List<Move> validMoves = new ArrayList<>();
    validMoves.add(new Move(2, 4));
    validMoves.add(new Move(3, 4));
    validMoves.add(new Move(4, 4));

    MockSqaure game = new MockSqaure(board, validMoves);


    CaptureStrategy captureStrategy = new CaptureStrategy();
    Move selectedMove = captureStrategy.chooseMove(game);

    // Assert the selected move is the uppermost-leftmost coordinate among tied moves
    Assert.assertNotNull(selectedMove);
    Assert.assertEquals(2, selectedMove.getQ());
    Assert.assertEquals(4, selectedMove.getR());
  }

  @Test
  public void testNoValidMoves() {
    // Create a mock board with specific configurations
    Board board = new SquareBoard(8);
    board.setState(3, 2, CellState.X);
    board.setState(4, 3, CellState.X);
    board.setState(2, 4, CellState.X);
    board.setState(5, 5, CellState.X);

    // Create a Mock game using the mock board
    MockSqaure game = new MockSqaure(board, new ArrayList<>());

    CaptureStrategy captureStrategy = new CaptureStrategy();
    Move selectedMove = captureStrategy.chooseMove(game);

    // Assert the selected move is null when there are no valid moves
    Assert.assertNull(selectedMove);
  }
}
