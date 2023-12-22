package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a cell on a hexagonal game board with a specified position (q, r) and a state.
 *
 * <p>This class encapsulates the information and behavior of a cell in a hexagonal grid.
 * Each cell has a unique position identified by its q and r coordinates
 * and a state that can be modified during the game.</p>
 */
public class Cell {
  private final int q;
  private final int r;
  private final GridType gridType;


  private CellState state;

  /**
   * Constructs a new cell with the specified position (q, r),
   * state, and grid type.
   *
   * @param q        The q-coordinate of the cell.
   * @param r        The r-coordinate of the cell.
   * @param state    The state to set for the cell.
   * @param gridType The type of grid (HEXAGONAL or SQUARE).
   */
  public Cell(int q, int r, CellState state, GridType gridType) {
    this.q = q;
    this.r = r;
    this.state = state;
    this.gridType = gridType;
  }

  /**
   * Gets the state of the cell.
   *
   * @return The state of the cell.
   */
  public CellState getState() {
    return state;
  }

  /**
   * Sets the state of the cell to the specified new state.
   *
   * @param newState The new state to set for the cell.
   */
  public void setState(CellState newState) {
    state = newState;
  }

  /**
   * Gets the q-coordinate of the cell.
   *
   * @return The q-coordinate of the cell.
   */
  List<HexCoordinate> getCoordinateNeighboring() {
    List<HexCoordinate> neighbors = new ArrayList<>();
    neighbors.add(new HexCoordinate(q + 1, r)); //NORTH
    neighbors.add(new HexCoordinate(q - 1, r)); //SOUTH
    neighbors.add(new HexCoordinate(q, r + 1)); //NORTHEAST
    neighbors.add(new HexCoordinate(q, r - 1)); //SOUTHWEST
    neighbors.add(new HexCoordinate(q + 1, r - 1)); //NORTHWEST
    neighbors.add(new HexCoordinate(q - 1, r + 1)); //SOUTHEAST
    return neighbors;
  }

  List<HexCoordinate> getSquareCoordinates() {
    List<HexCoordinate> neighbors = new ArrayList<>();

    // For a square grid
    neighbors.add(new HexCoordinate(q, r - 1)); // NORTH
    neighbors.add(new HexCoordinate(q, r + 1)); // SOUTH
    neighbors.add(new HexCoordinate(q - 1, r)); // WEST
    neighbors.add(new HexCoordinate(q + 1, r)); // EAST

    return neighbors;
  }

  /**
   * Gets the neighboring cells of the current cell.
   *
   * @return A list of neighboring cells.
   */
  public List<Cell> getNeighbors(Board board) {
    if (gridType == GridType.HEXAGONAL) {
      return getCoordinateNeighboring().stream()
              .map(coordinate -> board.getCell(coordinate.getQ(), coordinate.getR()))
              .filter(Objects::nonNull)
              .collect(Collectors.toList());
    }
    else {
      return getSquareCoordinates().stream()
              .map(coordinate -> board.getCell(coordinate.getQ(), coordinate.getR()))
              .filter(Objects::nonNull)
              .collect(Collectors.toList());
    }
  }

  /*
   * Gets the r-coordinate of the cell.
   * @return The r-coordinate of the cell.
   */
  public String getValue() {
    return state.toString();
  }

  /*
   * Gets the q-coordinate of the cell.
   * @return The q-coordinate of the cell.
   */
  public int getQ() {
    return q;
  }

  /*
   * Gets the r-coordinate of the cell.
   * @return The r-coordinate of the cell.
   */
  public int getR() {
    return r;
  }

  /*
   * Checks if the cell is empty.
   * @return True if the cell is empty, false otherwise.
   */
  public boolean isEmpty() {
    return state == CellState.EMPTY;
  }

  /*
   * Checks if the cell is occupied.
   * @return True if the cell is occupied, false otherwise.
   */
  public HexCoordinate getHexCoordinate() {
    return new HexCoordinate(q, r);
  }
}
