package model;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;


/**
 * Tests for the Cell class.
 */

public class CellTest {

  @Test
  public void testCell() {
    Cell cell = new Cell(1, 1, CellState.EMPTY,GridType.HEXAGONAL);
    Assert.assertEquals(cell.getState(), CellState.EMPTY);

  }

  @Test
  public void testSetState() {
    Cell cell = new Cell(1, 1, CellState.EMPTY, GridType.HEXAGONAL);
    cell.setState(CellState.X);
    Assert.assertEquals(cell.getState(), CellState.X);
  }

  @Test
  public void testGetCoordinateNeighboring() {
    Cell cell = new Cell(1, 1, CellState.EMPTY, GridType.HEXAGONAL);
    Assert.assertEquals(cell.getCoordinateNeighboring().size(), 6);
  }

  @Test
  public void testGetNeighbors() {
    Board board = new BoardImpl(6);
    Cell cell = new Cell(1, 1, CellState.X, GridType.HEXAGONAL);

    board.move(0, 1, CellState.O);
    board.move(0, 2, CellState.O);
    board.move(1, 2, CellState.O);

    board.move(1, 1, CellState.X);
    List<Cell> neighbors = cell.getNeighbors(board);

    Assert.assertEquals(6, neighbors.size());
    Assert.assertEquals("O", neighbors.get(1).getValue());
    Assert.assertEquals("O", neighbors.get(2).getValue());
  }




}