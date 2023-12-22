package player;



import model.CellState;

/**
 * Represents the possible players in the game.
 */

public enum GamePlayers {

  Black(CellState.X),
  White(CellState.O);

  private final CellState cellState;

  GamePlayers(CellState cellState) {
    this.cellState = cellState;
  }

  public CellState getCellState() {
    return cellState;
  }


}
