import controller.IController;
import controller.IControllerImpl;
import model.ReadOnlyReversiModel;
import model.SquareReversiGame;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import strategies.AvoidCornersStrategy;
import strategies.CaptureStrategy;
import strategies.GoForCornersStrategy;
import view.IView;
import view.SquareGridView;

/**
 * Class to run the Reversi game.
 * Level 4 - Strategy Pattern
 */

public class SquareStrategyMain {
  /**
   * Main class to run the Reversi game.
   * @param args can take in human or AI player with different strategies
   */
  public static void main(String[] args) {

    if (args.length != 2) {
      System.out.println("Usage: MVCMain <player1_type> <player2_strategy>" + args[0] + args[1]);
      System.exit(1);
    }

    ReadOnlyReversiModel model = new SquareReversiGame();
    IView view = new SquareGridView(model);
    IView view2 = new SquareGridView(model);

    Player player1 = createPlayer(args[0], model);
    Player player2 = createPlayer(args[1], model);

    IController controller = new IControllerImpl(model, player1, view);
    IController controller2 = new IControllerImpl(model, player2, view2);

    view.addPlayerActionListener(controller);
    view2.addPlayerActionListener(controller2);

    model.startGame();
  }

  private static Player createPlayer(String arg, ReadOnlyReversiModel model) {
    switch (arg.toLowerCase()) {
      case "human":
        return new HumanPlayer(model);
      case "strategy1":
        return new AIPlayer(model, new CaptureStrategy());
      case "strategy2":
        return new AIPlayer(model, new GoForCornersStrategy());
      case "strategy3":
        return new AIPlayer(model, new AvoidCornersStrategy());
      default:
        throw new IllegalArgumentException("Invalid player type/strategy: " + arg);
    }
  }
}
