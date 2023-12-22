import controller.IController;
import controller.IControllerImpl;
import model.ReadOnlyReversiModel;
import model.ReversiGame;
import player.HumanPlayer;
import player.Player;
import view.IView;
import view.IViewImpl;

/**
 * Class to represent a demo of Reversi game.
 */
public class ReversiMCVDemo {
  /**
   * Main class for reversi game.
   * @param args takes in the view and model implementation.
   */
  public static void main(String[] args) {
    ReadOnlyReversiModel model = new ReversiGame();
    IView view = new IViewImpl(model);
    IView view2 = new IViewImpl(model);

    Player player1 = new HumanPlayer(model);
    Player player2 = new HumanPlayer(model);
    IController controller = new IControllerImpl(model, player1, view);
    IController controller2 = new IControllerImpl(model, player2, view2);
    controller.startGame();
    controller2.startGame();
  }
}

