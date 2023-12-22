import model.Board;
import model.BoardImpl;
import model.ReadOnlyReversiModel;
import model.ReversiGame;
import view.IView;
import view.IViewImpl;


/**
 * Main class for the Reversi game.
 */

public class Main {

  /**
   * Main method for the Reversi game.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    Board board = new BoardImpl(6);
    ReadOnlyReversiModel model = new ReversiGame();
    IView view = new IViewImpl(model);
    view.makeVisible();
  }
}
