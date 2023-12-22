package model;

/**
 * Represents the possible states of a cell on the game board.
 * A cell can be occupied by Player X, Player O, or can be empty.
 */
public enum CellState {

  /**
   * Represents a cell occupied by Player X.
   */
  X("X"), //black

  /**
   * Represents a cell occupied by Player O.
   */
  O("O"),//white

  /**
   * Represents an empty cell.
   */
  EMPTY("_");


  private final String state;

  /**
   * Constructs a new CellState with the specified string representation.
   *
   * @param state the string representation of the cell state
   */
  CellState(String state) {
    this.state = state;
  }

  /**
   * Returns the string representation of the cell state.
   *
   * @return the string representation of the cell state
   */
  public String toString() {
    return state;
  }


}

