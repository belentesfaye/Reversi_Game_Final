package view;

import controller.IController;
import model.Board;
import model.ReadOnlyReversiModel;
import player.GamePlayers;


import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;


/**
 * CLass to make the view visible.
 */
public class IViewImpl extends JFrame implements IView {

  private HexagonalGridPanel hexPanel;
  /**
   * Make the view visible. This is usually called after the view is constructed.
   */

  public IViewImpl(ReadOnlyReversiModel model) {
    hexPanel = new HexagonalGridPanel(model);
    initializeUI();
  }

  private void initializeUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Reversi Game");
    setLayout(new BorderLayout());
    JPanel gridPanel = new JPanel(new BorderLayout());
    gridPanel.add(hexPanel, BorderLayout.CENTER);
    add(gridPanel, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);

  }

  /**
   * Transmit an error message to the view, in case
   * the command could not be processed correctly.
   *
   * @param error error.
   */
  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error"
            , JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showPlayerTurnMessage(GamePlayers currentPlayer) {
    JOptionPane.showMessageDialog(this, "It is "
                    + currentPlayer + "'s turn.", "Player Turn",
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void repaint() {
    hexPanel.repaint();
  }

  @Override
  public void addPlayerActionListener(IController iController) {
    this.addController(iController);
  }

  @Override
  public void updateBoard(Board board) {
    hexPanel.updateBoard(board);
  }

  @Override
  public void showMessageDialog(String s) {
    JOptionPane.showMessageDialog(this, s, "Illegal Move"
            , JOptionPane.ERROR_MESSAGE);
  }



  private void addController(IController iController) {
    if (iController instanceof MouseListener) {
      this.hexPanel.addMouseListener((MouseListener) iController);
    }
    if (iController instanceof KeyListener) {
      this.hexPanel.addKeyListener((KeyListener) iController);
    }
  }
}
