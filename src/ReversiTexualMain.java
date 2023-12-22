import model.Board;
import model.ReadOnlyReversiModel;
import model.SquareBoard;
import model.SquareReversiGame;
import view.IView;
import view.SquareGridView;
import view.SquareTexualView;
import view.TexualView;

/**
 * Class to run the Reversi game.
 */

public class ReversiTexualMain {

  /**
   * Main method for the reversi game.
   * @param args takes in view.
   */
  public static void main(String[] args) {
    Board board = new SquareBoard(8);
    TexualView view = new SquareTexualView(board);
    System.out.println(view);

    ReadOnlyReversiModel model = new SquareReversiGame();
    IView viewGraphic = new SquareGridView(model);
    viewGraphic.makeVisible();

  }
}
