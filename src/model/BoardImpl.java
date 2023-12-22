package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link Board} interface for a hexagonal grid game board.
 *
 * <p>This class represents a hexagonal game board with methods for checking cell validity,
 * making moves, and retrieving information about the current state of the board.</p>
 *
 * <p>The hexagonal grid is defined by the size of the board,
 * and each cell is represented by its hexagonal coordinates (q, r).
 * The class includes methods to create the initial board configuration, validate cell
 * coordinates, make moves, retrieve cells, and obtain a list of valid moves.</p>
 */
public class BoardImpl implements Board {
  private final Map<HexCoordinate, Cell> boardMap = new HashMap<>();
  private final int size;

  /**
   * Constructs a new hexagonal game board with the specified size.
   *
   * @param size The size of the hexagonal game board.
   */

  public BoardImpl(int size) {
    this.size = size;
    createBoard();

    setState(4, 4, CellState.X);
    setState(6, 5, CellState.X);
    setState(5, 6, CellState.X);

    setState(5, 4, CellState.O);
    setState(4, 5, CellState.O);
    setState(6, 6, CellState.O);
  }

  /**
   * Creates the initial configuration of the hexagonal game board.
   */
  private void createBoard() {
    int totalRows = 2 * size - 1; // The total number of rows in the board
    int halfSize = size - 1;  // The number of cells in the middle row

    // Create the board
    for (int r = 0; r < totalRows; r++) {
      int maxQ = Math.min(2 * size - 1, r + halfSize + 1);
      int minQ = Math.max(0, r - halfSize);

      // The number of cells in this row depends on the row number
      for (int q = minQ; q < maxQ; q++) {
        // Skip cells that are outside the hexagon
        HexCoordinate c = new HexCoordinate(q, r);
        boardMap.put(c, new Cell(q, r, CellState.EMPTY, GridType.HEXAGONAL));
      }
    }
  }


  /*
   * Returns true if the given coordinates are outside the hexagon.
   */
  private boolean skipCell(int q, int r) {
    return !(q >= -size + 1 && q <= size - 1
            && r >= -size + 1
            && r <= size - 1
            && -q - r >= -size + 1
            && -q - r <= size - 1);
  }


  /*
   * Returns true if the given coordinates are valid.
   * A cell is valid if it is not outside the hexagon.
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return True if the cell is valid, false otherwise.
   */
  @Override
  public boolean isValidCell(int q, int r) {
    // The cell is valid if it is not outside the hexagon
    return !skipCell(q, r);
  }


  /*
   * Makes a move on the board by setting the cell at the given coordinates to the specified state.
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @param state The state to set the cell to.
   */

  @Override
  public void move(int q, int r, CellState state) {
    Cell cell = getCell(q, r);
    cell.setState(state);
  }


  /*
   * Retrieves the cell at the given coordinates.
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return The cell at the specified coordinates.
   */
  @Override
  public Cell getCell(int q, int r) {
    return boardMap.get(new HexCoordinate(q, r));
  }


  /*
   * Sets the state of the cell at the given coordinates to the specified state.
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
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

  /*
   * Retrieves a list of valid moves on the board, represented as cells with an empty state.
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

    List<HexCoordinate> neighbors = Arrays.asList(
            new HexCoordinate(q - 1, r),
            new HexCoordinate(q + 1, r),
            new HexCoordinate(q, r - 1),
            new HexCoordinate(q, r + 1)
    );

    for (HexCoordinate neighbor : neighbors) {
      if (isValidCell(neighbor.getQ(), neighbor.getR())) {
        Cell neighborCell = getCell(neighbor.getQ(), neighbor.getR());
        if (neighborCell != null && !neighborCell.isEmpty()) {
          return true; // Found an adjacent occupied cell
        }
      }
    }

    return false;
  }

  /*
   * Gets the size of the board.
   * @return The size of the board.
   */
  @Override
  public int getSize() {
    return size;
  }

  /*
   * Retrieves the state of the cell at the given hexagonal coordinates.
   * @param hex The hexagonal coordinates of the cell.
   * @return The state of the cell at the specified coordinates.
   */
  @Override
  public CellState getCellStateAt(HexCoordinate hex) {
    Cell cell = getCell(hex.getQ(), hex.getR());
    return (cell != null) ? cell.getState() : null;//CellState.EMPTY;
  }

  /*
   * Retrieves the mapping of hexagonal coordinates to cells on the board.
   * @return The map of hexagonal coordinates to cells.
   */
  @Override
  public Map<HexCoordinate, Cell> getBoardMap() {
    return boardMap;
  }

  /*
   * Retrieves the mapping of hexagonal coordinates to cells on the board.
   * @return The map of hexagonal coordinates to cells.
   */

  @Override
  public Board copy() {
    BoardImpl copy = new BoardImpl(size);
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
        return false;
      }
    }
    return true;
  }


}