package model;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

/*
  The assignment says "Your model implementation should be general enough for multiple board sizes".
  I would say that you could and should test smaller board sizes.
 */

/**
 * Tests for the Model class.
 */
public class ModelTest {
  private Board board;

  @Before
  public void setUp() {
    board = new BoardImpl(6);
  }

  @Test
  public void testBoardInitialization() {
    Assert.assertEquals(6, board.getSize());
  }


  @Test
  public void testOverwriteCell() {
    board.move(2, 2, CellState.X);
    Assert.assertEquals(CellState.X, board.getCell(2, 2).getState());
    Assert.assertThrows(IllegalArgumentException.class, () -> board.move(2, 2, CellState.O));
    Assert.assertThrows(IllegalArgumentException.class, () -> board.move(6, 6, CellState.X));
  }


  @Test
  public void testValidMovesInitialization() {
    List<Cell> validMoves = board.getValidMoves();
    int expectedValidMoves = 3 * board.getSize() * board.getSize() - 3 * board.getSize() + 1;
    Assert.assertEquals(expectedValidMoves, validMoves.size());
    for (Cell cell : validMoves) {
      Assert.assertEquals(CellState.EMPTY, cell.getState());
    }
  }

  @Test
  public void testValidSetState() {
    board.setState(3, 3, CellState.O);
    Assert.assertEquals(CellState.O, board.getCell(3, 3).getState());
  }

  @Test
  public void testOverwritingSetState() {
    board.setState(3, 3, CellState.O);


    Assert.assertThrows(IllegalArgumentException.class,
        () -> board.setState(3, 3, CellState.X));
  }

  @Test
  public void testGetCellStateAt() {
    board.move(2, 3, CellState.X);
    Assert.assertEquals(CellState.X, board.getCellStateAt(new HexCoordinate(2, 3)));
  }

}
