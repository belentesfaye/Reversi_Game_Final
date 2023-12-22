import java.util.Scanner;
import model.BoardImpl;
import model.ReversiGame;
import model.Game;
import player.GamePlayers;
import view.ReversiView;

/**
 * A demo of the Reversi game.
 */

public class ReversiGameDemo {

  /**
   * Main method for the reversi game.
   * @param args takes in view.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Initialize the game components
    BoardImpl board = new BoardImpl(8); // Adjust the board size as needed
    Game game = new ReversiGame();
    ReversiView view = new ReversiView(board);

    System.out.println(view);

    while (!game.isGameOver()) {
      GamePlayers currentPlayer = game.getCurrentPlayer();
      System.out.println("Current Player: " + currentPlayer);

      // Prompt the user for input
      System.out.print("Enter the q-coordinate (0-7) or 'quit' to exit: ");
      String input = scanner.next();

      if (input.equalsIgnoreCase("quit")) {
        System.out.println("Quitting the game.");
        break; // Exit the loop
      }

      try {
        int q = Integer.parseInt(input);

        System.out.print("Enter the r-coordinate (0-7) or 'quit' to exit: ");
        input = scanner.next();

        if (input.equalsIgnoreCase("quit")) {
          System.out.println("Quitting the game.");
          System.out.println(view);
          break; // Exit the loop
        }

        int r = Integer.parseInt(input);

        if (game.isValidMove(q, r, currentPlayer)) {
          game.play(q, r);
        } else {
          System.out.println("Invalid move. Try again.");
        }

        // Display the updated state of the board
        System.out.println(view);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter numeric coordinates or 'quit'.");
      }
    }



    // Close the scanner
    scanner.close();
  }
}