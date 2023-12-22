import controller.IController;
import controller.IControllerImpl;
import model.ReadOnlyReversiModel;
import model.ReversiGame;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import strategies.AvoidCornersStrategy;
import strategies.CaptureStrategy;
import strategies.GoForCornersStrategy;
import view.IView;
import view.IViewImpl;

/**
 * Class to run the Reversi game.
 */
public class MVCMain {
  /**
   * Main class to run the Reversi game.
   * @param args can take in human or AI player with different strategies
   */
  public static void main(String[] args) {

    if (args.length != 2) {
      System.out.println("Usage: MVCMain <player1_type> <player2_strategy>" + args[0] + args[1]);
      System.exit(1);
    }

    ReadOnlyReversiModel model = new ReversiGame();
    IView view = new IViewImpl(model);
    IView view2 = new IViewImpl(model);

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
