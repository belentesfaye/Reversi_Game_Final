package view;

import model.Cell;
import model.CellState;
import model.HexCoordinate;
import model.ReadOnlyReversiModel;
import player.GamePlayers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a hint manager for the game.
 * <p> This class provides methods to calculate the number of pieces that would be flipped
 * for a given move, and to display the valid moves with their corresponding flip counts.</p>
 * Invariants:
 * The hint manager is associated with a game.
 * The hint manager can calculate the number of pieces that would be flipped for a given move.
 * The hint manager can display the valid moves with their corresponding flip counts.
 */
public class HintManager {
  private ReadOnlyReversiModel model;

  /**
   * Constructs a new hint manager for the given game.
   *
   * @param model The game for which to create a hint manager.
   */
  public HintManager(ReadOnlyReversiModel model) {
    this.model = model;
  }

  /**
   * Calculates the number of pieces that would be flipped for a given move.
   * @param hexCoordinate The hexagonal coordinates of the move.
   * @param currentPlayer The current player.
   * @return The number of pieces that would be flipped for a given move.
   */
  public int getFlippedPiecesCount(HexCoordinate hexCoordinate, GamePlayers currentPlayer) {
    int q = hexCoordinate.getQ();
    int r = hexCoordinate.getR();
    int flippedCount = 0;

    CellState currentPlayerState = (currentPlayer == GamePlayers.Black) ? CellState.X : CellState.O;
    CellState opponentState = (currentPlayer == GamePlayers.Black) ? CellState.O : CellState.X;
    // Check if the cell is empty and a valid move for the current player
    if (model.getBoard().isValidCell(q, r)
            && model.getBoard().getCell(q, r).getState() == CellState.EMPTY) {

      List<Integer> directionQ = Arrays.asList(-1, 1, 0, 0, -1, -1, 1, 1);
      List<Integer> directionR = Arrays.asList(0, 0, -1, 1, -1, 1, -1, 1);
      // Check each direction
      for (int i = 0; i < directionQ.size(); i++) {
        flippedCount += getFlipDirection(q, r, directionQ.get(i), directionR.get(i),
                currentPlayerState, opponentState);
      }

      return flippedCount;
    } else {
      // The cell is not empty or not a valid move
      return 0;
    }
  }

  private int getFlipDirection(int q, int r, int directionQ, int directionR,
                                CellState currentPlayerState, CellState opponentState) {
    //list of neighbors
    List<Cell> neighbors = new ArrayList<>();
    int nextQ = q + directionQ; //next cell to check
    int nextR = r + directionR;
    //check if the next cell is valid
    while (isValidCell(nextQ, nextR)) {
      Cell cell = model.getBoard().getCell(nextQ, nextR);
      //if the next cell is the opponent's state, add it to the list of neighbors
      if (cell != null && cell.getState() == opponentState) {
        neighbors.add(cell); //add the cell to the list of neighbors
        nextQ += directionQ;
        nextR += directionR;
      } else {
        break;
      }
    }

    //if the next cell is the current player's state, flip the pieces
    if (isValidCell(nextQ, nextR) && model.getBoard().getCell(nextQ, nextR) != null
            && model.getBoard().getCell(nextQ, nextR).getState() == currentPlayerState) {
      //flip the pieces
      for (Cell c : neighbors) {
        c.setState(currentPlayerState);
      }
      return neighbors.size(); // Return the count of flipped pieces
    } else {
      return 0;  // The sequence is not valid
    }
  }

  private boolean isValidCell(int nextQ, int nextR) {
    int boardSize = model.getBoard().getSize();
    return (nextQ >= 0 && nextQ < boardSize && nextR >= 0 && nextR < boardSize);
  }


  private List<Cell> calculateValidMoves() {
    List<Cell> validMoves = model.getBoard().getValidMoves();

    // Filter the valid moves to only include those that would flip at least two pieces

    validMoves = validMoves.stream()
            .filter(cell -> getFlippedPiecesCount(cell.getHexCoordinate(),
                    model.getCurrentPlayer()) >= 2)
            .collect(Collectors.toList());  // Collect the filtered valid moves into a list

    return validMoves;
  }


  /**
   * Displays the valid moves with their corresponding flip counts.
   */
  public void handleHint() {
    List<Cell> validMoves = calculateValidMoves();

    if (validMoves.isEmpty()) {
      System.out.println("No valid moves.");
    } else {
      System.out.println("Valid moves with flip counts:");
      for (Cell cell : validMoves) {
        int flipCount = getFlippedPiecesCount(cell.getHexCoordinate(), model.getCurrentPlayer());
        System.out.println("Move at " + cell.getHexCoordinate() + " flips "
                + flipCount + " pieces.");
      }
    }
  }

}

