package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link Board} interface for a square grid game board.
 * <p> This class represents a square game board with methods for checking cell validity,
 * making moves, and retrieving information about the current state of the board.</p>
 * <p> The square grid is defined by the size of the board,
 * and each cell is represented by its hexagonal coordinates (q, r).
 * The class includes methods to create the initial board configuration, validate cell
 * coordinates, make moves, retrieve cells, and obtain a list of valid moves.</p>
 */
public class SquareBoard implements Board {

  private final Map<HexCoordinate, Cell> boardMap = new HashMap<>();
  private final int size;

  /**
   * Constructs a new square game board with the specified size.
   *
   * @param size The size of the square game board.
   */
  public SquareBoard(int size) {
    this.size = size;
    createBoard();

    setState(3, 4, CellState.O);
    setState(4, 4, CellState.X);
    setState(4, 3, CellState.O);
    setState(3, 3, CellState.X);
  }

  private void createBoard() {
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        HexCoordinate coordinate = new HexCoordinate(col, row);
        boardMap.put(coordinate, new Cell(col, row, CellState.EMPTY, GridType.SQUARE));
      }
    }
  }

  @Override
  public boolean isValidCell(int q, int r) {
    return q >= 0 && q < size && r >= 0 && r < size;
  }

  /**
   * /**
   * Makes a move on the board by setting the cell at the given coordinates to the specified state.
   *
   * @param q     The q-coordinate of the cell.
   * @param r     The r-coordinate of the cell.
   * @param state The state to set the cell to.
   */
  @Override
  public void move(int q, int r, CellState state) {
    Cell cell = getCell(q, r);
    cell.setState(state);
  }

  /**
   * Retrieves the cell at the given coordinates.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return The cell at the specified coordinates.
   */
  @Override
  public Cell getCell(int q, int r) {
    return boardMap.get(new HexCoordinate(q, r));
  }

  /**
   * Sets the state of the cell at the given coordinates to the specified state.
   *
   * @param q     The q-coordinate of the cell.
   * @param r     The r-coordinate of the cell.
   * @param state The state to set the cell to.
   */
  @Override
  public void setState(int q, int r, CellState state) {
    Cell cell = getCell(q, r);
    if (cell.getState() != CellState.EMPTY) {
      throw new IllegalArgumentException("Cell is not empty.");
    }

    cell.setState(state);
  }

  /**
   * Retrieves a list of valid moves on the board, represented as cells with an empty state.
   *
   * @return A list of valid moves on the board.
   */
  @Override
  public List<Cell> getValidMoves() {
    List<Cell> validMoves = new ArrayList<>();

    for (Cell cell : boardMap.values()) {
      if (cell.isEmpty() && hasAdjacentOccupiedCell(cell.getHexCoordinate())) {
        validMoves.add(cell);
      }
    }

    return validMoves;
  }

  private boolean hasAdjacentOccupiedCell(HexCoordinate hexCoordinate) {
    int q = hexCoordinate.getQ();
    int r = hexCoordinate.getR();

    // Check top neighbor
    if (isValidCell(q, r - 1) && !getCell(q, r - 1).isEmpty()) {
      return true;
    }

    // Check bottom neighbor
    if (isValidCell(q, r + 1) && !getCell(q, r + 1).isEmpty()) {
      return true;
    }

    // Check left neighbor
    if (isValidCell(q - 1, r) && !getCell(q - 1, r).isEmpty()) {
      return true;
    }

    // Check right neighbor
    return isValidCell(q + 1, r) && !getCell(q + 1, r).isEmpty();
  }

  /**
   * Gets the size of the board.
   *
   * @return The size of the board.
   */
  @Override
  public int getSize() {
    return size;
  }

  /**
   * Retrieves the state of the cell at the given hexagonal coordinates.
   *
   * @param hex The hexagonal coordinates of the cell.
   * @return The state of the cell at the specified coordinates.
   */
  @Override
  public CellState getCellStateAt(HexCoordinate hex) {
    Cell cell = getCell(hex.getQ(), hex.getR());
    return (cell != null) ? cell.getState() : null;//CellState.EMPTY;
  }

  /**
   * Retrieves the mapping of hexagonal coordinates to cells on the board.
   *
   * @return The map of hexagonal coordinates to cells.
   */
  @Override
  public Map<HexCoordinate, Cell> getBoardMap() {
    return boardMap;
  }

  /**
   * Creates a copy of the board.
   *
   * @return A new instance of the board with the same state.
   */
  @Override
  public Board copy() {
    SquareBoard copy = new SquareBoard(size);
    boardMap.forEach((hexCoordinate, cell) -> copy.setState(hexCoordinate.getQ()
            , hexCoordinate.getR(), cell.getState()));
    return copy;
  }

  /**
   * Checks if the board has been modified.
   *
   * @return `true` if the board has been modified; otherwise, `false`.
   */
  @Override
  public boolean isModified() {
    return false;
  }

  @Override
  public boolean isFull() {
    for (Cell cell : boardMap.values()) {
      if (cell.isEmpty()) {
        return false; // Found an empty cell, so the board is not full
      }
    }
    return true;
  }
}
