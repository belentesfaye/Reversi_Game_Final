package model;


import java.util.List;
import java.util.Map;

/**
 * The {@code Board} interface represents the game board for a hexagonal board game.
 *
 * <p>Implementations of this interface are expected to provide methods for managing
 * the state of the board, making moves, checking the validity of moves,
 * and retrieving information about the current board configuration.</p>
 *
 * <p>The board is defined by hexagonal coordinates (q, r),
 * where 'q' represents the column and 'r' represents the row.
 * The validity of coordinates is determined by the specific rules of
 * the game being played on the board.</p>
 *
 * <p>This interface includes methods for making moves, checking if a move is valid,
 * retrieving cells at specific
 * coordinates, obtaining a list of valid moves, and copying the current board state.
 * It also provides information about the size of the board and the state of individual cells.</p>
 */

public interface Board {

  boolean isValidCell(int q, int r);

  /**
   * /**
   * Makes a move on the board by setting the cell at the given coordinates to the specified state.
   *
   * @param q     The q-coordinate of the cell.
   * @param r     The r-coordinate of the cell.
   * @param state The state to set the cell to.
   */
  void move(int q, int r, CellState state);

  /**
   * Retrieves the cell at the given coordinates.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return The cell at the specified coordinates.
   */
  Cell getCell(int q, int r);

  /**
   * Sets the state of the cell at the given coordinates to the specified state.
   *
   * @param q     The q-coordinate of the cell.
   * @param r     The r-coordinate of the cell.
   * @param state The state to set the cell to.
   */
  void setState(int q, int r, CellState state);

  /**
   * Retrieves a list of valid moves on the board, represented as cells with an empty state.
   *
   * @return A list of valid moves on the board.
   */
  List<Cell> getValidMoves();

  /**
   * Gets the size of the board.
   *
   * @return The size of the board.
   */
  int getSize();

  /**
   * Retrieves the state of the cell at the given hexagonal coordinates.
   *
   * @param hex The hexagonal coordinates of the cell.
   * @return The state of the cell at the specified coordinates.
   */
  CellState getCellStateAt(HexCoordinate hex);

  /**
   * Retrieves the mapping of hexagonal coordinates to cells on the board.
   *
   * @return The map of hexagonal coordinates to cells.
   */
  Map<HexCoordinate, Cell> getBoardMap();

  /**
   * Creates a copy of the board.
   *
   * @return A new instance of the board with the same state.
   */
  Board copy();


  /**
   * Checks if the board has been modified.
   *
   * @return `true` if the board has been modified; otherwise, `false`.
   */
  boolean isModified();

  /**
   * Checks if the board is full.
   *
   * @return
   */
  boolean isFull();
}
