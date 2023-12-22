import controller.IController;
import controller.IControllerImpl;
import model.ReadOnlyReversiModel;
import model.SquareReversiGame;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import view.IView;
import view.SquareGridView;

/**
 * Class to represent a demo of Reversi game.
 * Level 3 - MVC Pattern
 */
public class SquareMain {
  /**
   * Main class for reversi game.
   * @param args takes in the view and model implementation.
   */
  public static void main(String[] args) {
    ReadOnlyReversiModel model = new SquareReversiGame();
    IView view = new SquareGridView(model);
    IView view2 = new SquareGridView(model);

    Player player1 = new HumanPlayer(model);
    Player player2 = new AIPlayer(model);
    IController controller = new IControllerImpl(model, player1, view);
    IController controller2 = new IControllerImpl(model, player2, view2);
    controller.startGame();
    controller2.startGame();
  }
}
