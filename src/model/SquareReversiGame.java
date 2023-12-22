package model;

import controller.IControllerImpl;
import player.AIPlayer;
import player.GamePlayers;
import player.Player;
import strategies.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Represents a game of Reversi.
 * <p> This class implements the {@link Game} interface for a
 * Reversi game on a Square grid-based board.</p>
 * Invariants:
 * The game board is a Square grid.
 * The current player alternates between black and white after each move.
 * Valid moves are determined by the rules of Reversi.
 * Players take turns making moves until the game is over.
 */
public class SquareReversiGame implements Game {
  private final Board board; // The game board.
  private GamePlayers currentPlayer; // The current player.

  private final Player aiPlayer;


  /**
   * Constructs a new Reversi game with the given game board.
   */
  public SquareReversiGame() {
    this.board = new SquareBoard(8);
    this.currentPlayer = GamePlayers.Black;
    this.aiPlayer = new AIPlayer(this);
  }

  /**
   * Checks if a move to the specified position (q, r) is valid in the game.
   *
   * @param q      The q-coordinate of the move.
   * @param r      The r-coordinate of the move.
   * @param player The player making the move.
   * @return `true` if the move is valid; otherwise, `false`.
   */
  @Override
  public boolean isValidMove(int q, int r, GamePlayers player) {
    if (!board.isValidCell(q, r) && !board.getCell(q, r).isEmpty()) {
      return false;
    }
    // Check if at least one neighboring cell has a piece belonging to the current player
    Cell currentCell = board.getCell(q, r);
    for (Cell neighbor : currentCell.getNeighbors(board)) {
      if (neighbor.getState() == player.getCellState()) {
        return true;
      }
    }
    // If no neighboring cell has the player's piece, the move is invalid
    return false;
  }

  /**
   * Gets the current player in the game.
   *
   * @return The current player.
   */
  @Override
  public GamePlayers getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Gets the winner of the game.
   *
   * @return A string representing the winner or an empty string if the game is not over.
   */
  @Override
  public GamePlayers getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over yet.");
    }

    int blackCount = countPieces(GamePlayers.Black);
    int whiteCount = countPieces(GamePlayers.White);

    if (blackCount > whiteCount) {
      return GamePlayers.Black;
    } else if (whiteCount > blackCount) {
      return GamePlayers.White;
    } else {
      return null;
    }
  }

  /**
   * Calculates the score for the given player.
   *
   * @param gamePlayer The player for whom to calculate the score.
   * @return The score of the player.
   */
  @Override
  public int score(GamePlayers gamePlayer) {
    CellState playerDisc = (gamePlayer == GamePlayers.Black) ? CellState.X : CellState.O;
    int playerScore = 0;
    for (int q = 0; q < board.getSize(); q++) {
      for (int r = 0; r < board.getSize(); r++) {
        Cell cell = board.getCell(q, r);
        if (cell.getState() == playerDisc) {
          playerScore++;
        }
      }
    }

    return playerScore;
  }

  /**
   * Counts the number of pieces on the board for the specified player.
   *
   * @param gamePlayers The player for whom to count the pieces.
   * @return The count of pieces for the player.
   */
  @Override
  public int countPieces(GamePlayers gamePlayers) {
    return (int) board.getValidMoves().stream().filter(cell -> cell.getState() == (gamePlayers
            == GamePlayers.Black ? CellState.X : CellState.O)).count();
  }

  /**
   * Gets the state of the cell at the specified position (q, r) on the board.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return The state of the cell.
   */
  @Override
  public CellState getCellState(int q, int r) {
    return board.getCell(q, r).getState();
  }

  /**
   * Checks if the game is over.
   *
   * @return `true` if the game is over; otherwise, `false`.
   */
  @Override
  public boolean isGameOver() {
    return board.getValidMoves().isEmpty() || !hasValidMoves();
  }

  private boolean hasValidMoves() {
    List<Cell> validMoves = board.getValidMoves();
    for (Cell cell : validMoves) {
      if (cell.getState() == CellState.EMPTY) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the game board.
   *
   * @return The game board.
   */
  @Override
  public Board getBoard() {
    return board;
  }

  /**
   * Passes the turn to the next player.
   */
  @Override
  public void pass() {
    switchPlayer();
  }

  /**
   * Gets a map representing the board with hexagonal coordinates as keys and cells as values.
   *
   * @return A map representing the board.
   */
  @Override
  public Map<HexCoordinate, Cell> getBoardMap() {
    return board.getBoardMap();
  }

  /**
   * Gets the size of the game board.
   *
   * @return The size of the game board.
   */
  @Override
  public int getBoardSize() {
    return board.getSize();
  }

  /**
   * Plays a move at the specified position (q, r) in the game.
   *
   * @param q The q-coordinate of the move.
   * @param r The r-coordinate of the move.
   */
  @Override
  public void play(int q, int r) {
    if (!isValidMove(q, r, currentPlayer)) {
      throw new IllegalArgumentException("Invalid move");
    }
    board.move(q, r, currentPlayer.getCellState());
    flipOpponentsPieces(q, r);
    switchPlayer();

    if (currentPlayer == GamePlayers.White) {
      aiPlayer.makeMove(this);
      switchPlayer();
    }
  }

  /*
   * Flips the pieces of the opponent if the move is valid.
   */
  private void flipOpponentsPieces(int q, int r) {
    CellState currentPlayerState = board.getCell(q, r).getState(); //get the current player's state
    //get the opponent's state
    CellState opponentState = (currentPlayerState == CellState.X) ? CellState.O : CellState.X;

    List<Integer> directionQ = Arrays.asList(-1, 1, 0, 0, -1, -1, 1, 1); //directions to check
    List<Integer> directionR = Arrays.asList(0, 0, -1, 1, -1, 1, -1, 1);

    //check each direction
    for (int i = 0; i < directionQ.size(); i++) {
      flipPiecesInDirection(q, r, directionQ.get(i), directionR.get(i),
              currentPlayerState, opponentState);
    }
  }

  /*
   * Flips the pieces of the opponent in the given direction.
   */
  private void flipPiecesInDirection(int q, int r, int directionQ, int directionR,
                                     CellState currentPlayerState, CellState opponentState) {
    List<Cell> neighbors = new ArrayList<>();
    int nextQ = q + directionQ;
    int nextR = r + directionR;

    //check if the next cell is valid
    while (board.isValidCell(nextQ, nextR)) {
      Cell cell = board.getCell(nextQ, nextR);
      //if the next cell is the opponent's state, add it to the list of neighbors
      if (cell != null && cell.getState() == opponentState) {
        neighbors.add(cell);
        nextQ += directionQ;
        nextR += directionR;
      } else {
        break;
      }
    }

    //if the next cell is the current player's state, flip the pieces
    if (board.isValidCell(nextQ, nextR) && board.getCell(nextQ, nextR) != null
            && board.getCell(nextQ, nextR).getState() == currentPlayerState) {
      //flip the pieces
      for (Cell c : neighbors) {
        c.setState(currentPlayerState);
      }
    }
  }


  @Override
  public void addModelStatusListener(IControllerImpl iController) {
    // Not needed for testing
  }

  @Override
  public void switchPlayer() {
    if (currentPlayer == GamePlayers.Black) {
      currentPlayer = GamePlayers.White;
    } else {
      currentPlayer = GamePlayers.Black;
    }
  }

  @Override
  public void startGame() {
    this.currentPlayer = GamePlayers.Black;
  }

  /**
   * Retrieves a list of valid moves in the game.
   *
   * @return A list of valid moves.
   */
  @Override
  public List<Move> getValidMoves() {
    List<Move> validMoves = new ArrayList<>();
    for (int q = 0; q < board.getSize(); q++) {
      for (int r = 0; r < board.getSize(); r++) {
        if (isValidMove(q, r, currentPlayer)) {
          validMoves.add(new Move(q, r));
        }
      }
    }
    return validMoves;
  }

  /**
   * Gets the opponent player in the game.
   *
   * @return The opponent player.
   */
  @Override
  public GamePlayers getOpponent() {
    return currentPlayer == GamePlayers.Black ? GamePlayers.White : GamePlayers.Black;
  }

  @Override
  public GamePlayers setCurrentPlayer(GamePlayers player) {
    return this.currentPlayer = player;
  }

  @Override
  public boolean hasValidMovesForPlayer(GamePlayers currentPlayer) {
    for (Cell cell : board.getValidMoves()) {
      if (cell.isEmpty() && isValidMove(cell.getHexCoordinate().getQ(),
              cell.getHexCoordinate().getR(), currentPlayer)) {
        return true; // Found a valid move for the player
      }
    }
    return false;
  }

}
