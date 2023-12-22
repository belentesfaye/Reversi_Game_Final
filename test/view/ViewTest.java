package view;

import model.Board;
import model.BoardImpl;
import model.CellState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the View class.
 */

public class ViewTest {
  private Board board;
  private ReversiView view;

  @Before
  public void setUp() {
    board = new BoardImpl(6);
    view = new ReversiView(board);
  }

  @Test
  public void testInitialViewState() {
    String expectedOutput =
            "     _ _ _ _ _ _ \n" +
                    "    _ _ _ _ _ _ _ \n" +
                    "   _ _ _ _ _ _ _ _ \n" +
                    "  _ _ _ _ _ _ _ _ _ \n" +
                    " _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _\n" +
                    " _ _ _ _ _ _ _ _ _ _\n" +
                    "  _ _ _ _ _ _ _ _ _\n" +
                    "   _ _ _ _ _ _ _ _\n" +
                    "    _ _ _ _ _ _ _\n" +
                    "     _ _ _ _ _ _\n";
    System.out.println(view.toString());
    Assert.assertEquals(expectedOutput, view.toString());
  }

  @Test
  public void testCreateStates() {
    board.setState(1, 2, CellState.O);
    board.setState(2, 3, CellState.X);
    board.setState(2, 4, CellState.O);
    board.setState(3, 5, CellState.X);

    String expectedOutput = "     _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ _ _ \n" +
            "   _ O _ _ _ _ _ _ \n" +
            "  _ _ X _ _ _ _ _ _ \n" +
            " _ _ O _ _ _ _ _ _ _ \n" +
            "_ _ _ X _ _ _ _ _ _ _\n" +
            " _ _ _ _ _ _ _ _ _ _\n" +
            "  _ _ _ _ _ _ _ _ _\n" +
            "   _ _ _ _ _ _ _ _\n" +
            "    _ _ _ _ _ _ _\n" +
            "     _ _ _ _ _ _\n";

    Assert.assertEquals(expectedOutput, view.toString());
    System.out.println(view.toString());
  }
}
