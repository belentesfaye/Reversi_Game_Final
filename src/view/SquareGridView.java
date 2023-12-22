package view;

import controller.IController;
import model.Board;
import model.ReadOnlyReversiModel;
import player.GamePlayers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Class to make the view visible.
 */
public class SquareGridView extends JFrame implements IView {
  private SquareGridPanel squarePanel;

  /**
   * Make the view visible. This is usually called after the view is constructed.
   *
   * @param model the model.
   */
  public SquareGridView(ReadOnlyReversiModel model) {
    squarePanel = new SquareGridPanel(model);
    initializeUI();
  }

  private void initializeUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Reversi Game");
    setLayout(new BorderLayout());
    JPanel gridPanel = new JPanel(new BorderLayout());
    gridPanel.add(squarePanel, BorderLayout.CENTER);
    add(gridPanel, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  /**
   * Make the view visible.
   * This is usually called after the view is constructed.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Transmit an error message to the view,
   * in case the command could not be processed correctly.
   *
   * @param error error.
   */
  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error"
            , JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Show the message for the player turn.
   *
   * @param currentPlayer the current player.
   */
  @Override
  public void showPlayerTurnMessage(GamePlayers currentPlayer) {
    JOptionPane.showMessageDialog(this, "It is "
                    + currentPlayer + "'s turn.", "Player Turn",
            JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Add a controller to the view.
   *
   * @param iController the controller.
   */
  @Override
  public void addPlayerActionListener(IController iController) {
    this.addController(iController);
  }

  /**
   * Update the board.
   *
   * @param board the board.
   */
  @Override
  public void updateBoard(Board board) {
    squarePanel.updateBoard(board);
  }

  /**
   * Show a message dialog.
   *
   * @param s the message.
   */
  @Override
  public void showMessageDialog(String s) {
    JOptionPane.showMessageDialog(this, s, "Illegal Move"
            , JOptionPane.ERROR_MESSAGE);
  }

  private void addController(IController iController) {
    if (iController instanceof MouseListener) {
      this.squarePanel.addMouseListener((MouseListener) iController);
    }
    if (iController instanceof KeyListener) {
      this.squarePanel.addKeyListener((KeyListener) iController);
    }
  }
}
