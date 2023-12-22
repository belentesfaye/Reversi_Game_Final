package view;


/**
 * A textual view interface for rendering a game's state.
 */

public interface TexualView {


  /**
   * Generates a string representation of the current Reversi game board.
   * hexagonal layout
   *
   * @return A string representing the Reversi game board.
   */
  String toString();

}
