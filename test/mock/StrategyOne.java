package mock;

import controller.IController;
import controller.IControllerImpl;
import model.ReadOnlyReversiModel;
import model.ReversiGame;

import org.junit.Test;

import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import strategies.AvoidCornersStrategy;
import strategies.CaptureStrategy;
import strategies.GoForCornersStrategy;
import view.IView;
import view.IViewImpl;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertTrue;

/**
 * Mock class for a strategy.
 */
public class StrategyOne {
  @Test
  public void testGo() throws Exception {
    ReadOnlyReversiModel model = new ReversiGame();
    IView view = new IViewImpl(model);
    IView view2 = new IViewImpl(model);

    String input = "human strategy1";
    Reader in = new StringReader(input);

    Player player1 = createPlayer(in, model);
    Player player2 = createPlayer(in, model);

    IController controller = new IControllerImpl(model, player1, view);
    IController controller2 = new IControllerImpl(model, player2, view2);

    view.addPlayerActionListener(controller);
    view2.addPlayerActionListener(controller2);
    model.startGame();
    assertTrue(model.isGameOver());

  }

  private static Player createPlayer(Readable out, ReadOnlyReversiModel model) {
    switch (out.toString()) {
      case "human":
        return new HumanPlayer(model);
      case "strategy1":
        return new AIPlayer(model, new CaptureStrategy());
      case "strategy2":
        return new AIPlayer(model, new GoForCornersStrategy());
      case "strategy3":
        return new AIPlayer(model, new AvoidCornersStrategy());
      default:
        throw new IllegalArgumentException("Invalid player type/strategy: " + out);
    }
  }
}