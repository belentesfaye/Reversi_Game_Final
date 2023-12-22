package model;


import controller.IControllerImpl;
import player.AIPlayer;
import player.Player;
import strategies.Move;
import player.GamePlayers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Represents a game of Reversi.
 *
 * <p>This class implements the {@link Game} interface for a
 * Reversi game on a hexagonal grid-based board.</p>
 * Invariants:
 * The game board is a hexagonal grid.
 * The current player alternates between black and white after each move.
 * Valid moves are determined by the rules of Reversi.
 * Players take turns making moves until the game is over.
 *
 */

public class ReversiGame implements Game {
  private final Board board; // The game board.
  private GamePlayers currentPlayer; // The current player.

  private final Player aiPlayer;


  /**
   * Constructs a new Reversi game with the given game board.
   */
  public ReversiGame() { //add player for Demo purposes
    this.board = new BoardImpl(6);
    this.currentPlayer = GamePlayers.Black;
    this.aiPlayer = new AIPlayer(this);


  }

  @Override
  public boolean isValidMove(int q, int r, GamePlayers player) {
    if (!board.isValidCell(q, r) && !board.getCell(q, r).isEmpty()) {
      return false;
    }

    // Check if at least one neighboring cell has a piece belonging to the current player
    Cell currentCell = board.getCell(q, r);
    if (currentCell.getNeighbors(board) == null) {
      return false;
    }
    for (Cell neighbor : currentCell.getNeighbors(board)) {
      if (neighbor.getState() == player.getCellState()) {
        return true;
      }
    }

    // If no neighboring cell has the player's piece, the move is invalid
    return false;
  }


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

  @Override
  public GamePlayers getOpponent() {
    return currentPlayer == GamePlayers.Black ? GamePlayers.White : GamePlayers.Black;
  }

  @Override
  public GamePlayers setCurrentPlayer(GamePlayers player) {
    return currentPlayer = player;
  }

  @Override
  public boolean hasValidMovesForPlayer(GamePlayers currentPlayer) {
    for (Cell cell : board.getValidMoves()) {
      if (cell.isEmpty() && isValidMove(cell.getHexCoordinate().getQ(),
              cell.getHexCoordinate().getR(), currentPlayer)) {
        return false; // Found a valid move for the player
      }
    }
    return true;
  }

  /*
   * Returns true if the given coordinates are outside the hexagon.
   * @param nextQ The q-coordinate of the cell.
   * @param nextR The r-coordinate of the cell.
   * @return True if the given coordinates are outside the hexagon, false otherwise.
   */
  public boolean isValidCell(int nextQ, int nextR) {
    int boardSize = board.getSize();
    return (nextQ >= 0 && nextQ < boardSize && nextR >= 0 && nextR < boardSize);
  }

  @Override
  public void play(int q, int r) {
    if (!isValidMove(q, r, currentPlayer)) {
      throw new IllegalArgumentException("Invalid move");
    }

    board.move(q, r, currentPlayer.getCellState());
    flipOppenentsPieces(q, r);
    changePlayer();

    if (currentPlayer == GamePlayers.White) {
      aiPlayer.makeMove(this);
      changePlayer();
    }
  }

  /**
   * Empty method implemented so no error is thrown in ReadOnly.
   *
   * @param iController iController implementation.
   */
  @Override
  public void addModelStatusListener(IControllerImpl iController) {
    //empty method implemented so no error is thrown.
  }


  @Override
  public void switchPlayer() {
    changePlayer();
  }


  /*
   * Changes the current player to the other player.
   */
  private void changePlayer() {
    currentPlayer = currentPlayer == GamePlayers.Black ? GamePlayers.White : GamePlayers.Black;
  }

  /*
   * Flips the pieces of the opponent if the move is valid.
   */
  private void flipOppenentsPieces(int q, int r) {
    CellState currentPlayerState = board.getCell(q, r).getState(); //get the current player's state
    //get the opponent's state
    CellState opponentState = currentPlayerState == CellState.X ? CellState.O : CellState.X;

    List<Integer> directionQ = Arrays.asList(-1, 1, 0, 0, -1, -1, 1, 1); //directions to check
    List<Integer> directionR = Arrays.asList(0, 0, -1, 1, -1, 1, -1, 1);

    //check each direction
    for (int i = 0; i < directionQ.size(); i++) {
      getFlipDirection(q, r, directionQ.get(i), directionR.get(i),
              currentPlayerState, opponentState);
    }
  }

  /*
   * Flips the pieces of the opponent in the given direction.
   */
  private void getFlipDirection(int q, int r, int directionQ, int directionR,
                                CellState currentPlayerState, CellState opponentState) {
    //list of neighbors
    List<Cell> neighbors = new ArrayList<>();
    int nextQ = q + directionQ; //next cell to check
    int nextR = r + directionR;
    //check if the next cell is valid
    while (isValidCell(nextQ, nextR)) {
      Cell cell = board.getCell(nextQ, nextR);
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
    if (isValidCell(nextQ, nextR) && board.getCell(nextQ, nextR) != null
            && board.getCell(nextQ, nextR).getState() == currentPlayerState) {
      //flip the pieces
      for (Cell c : neighbors) {
        c.setState(currentPlayerState);
      }
    }
  }


  /**
   * Calculate the score for the given player.
   *
   * @param gamePlayer The player for whom you want to calculate the score.
   * @return The score of the player.
   */
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

  @Override
  public int countPieces(GamePlayers gamePlayers) {
    return (int) board.getValidMoves().stream().filter(cell -> cell.getState() == (gamePlayers
            == GamePlayers.Black ? CellState.X : CellState.O)).count();
  }

  @Override
  public GamePlayers getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public CellState getCellState(int q, int r) {
    return board.getCell(q, r).getState();
  }

  @Override
  public boolean isGameOver() {
    boolean noValidMovesForBlack = !hasValidMoves(GamePlayers.Black);
    boolean noValidMovesForWhite = !hasValidMoves(GamePlayers.White);

    return noValidMovesForBlack && noValidMovesForWhite || board.isFull();
  }

  private boolean hasValidMoves(GamePlayers player) {
    List<Cell> validMoves = board.getValidMoves();
    for (Cell cell : validMoves) {
      if (cell.getState() == CellState.EMPTY && isValidMove(cell.getQ(), cell.getR(), player)) {
        return true;
      }
    }
    return false;
  }


  @Override
  public Board getBoard() {
    return board;
  }

  @Override
  public void pass() {
    changePlayer();
    if (currentPlayer == GamePlayers.White) {
      aiPlayer.makeMove(this);
      changePlayer();
    }
    changePlayer();
  }

  @Override
  public Map<HexCoordinate, Cell> getBoardMap() {
    return board.getBoardMap();
  }

  @Override
  public int getBoardSize() {
    return board.getSize();
  }

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

  @Override
  public void startGame() {
    this.currentPlayer = GamePlayers.Black;
  }
}