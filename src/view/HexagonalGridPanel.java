package view;

import controller.IController;
import model.Board;
import model.Cell;
import model.CellState;
import model.HexCoordinate;
import model.ReadOnlyReversiModel;
import player.GamePlayers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Represents a graphical panel for displaying a hexagonal grid for a Reversi game.
 * Extends JPanel and implements MouseListener and KeyListener for user interactions.
 */

public class HexagonalGridPanel extends JPanel implements MouseListener, KeyListener, IView {

  private final ReadOnlyReversiModel model; // Change to ReadonlyReversiModel
  private HexCoordinate selectedCell;
  private final int hexagonSize = 30;
  private final Map<HexCoordinate, Cell> boardMap;
  private HexCoordinate selectedCoordinate;

  private HexCoordinate highlightedCoordinate;

  private final HintManager hintManager;

  private boolean hintEnabled = false;

  /**
   * Constructs a HexagonalGridPanel with the provided ReadOnlyReversiModel.
   *
   * @param model The ReadOnlyReversiModel to display in the panel.
   */

  public HexagonalGridPanel(ReadOnlyReversiModel model) {
    this.model = model;
    this.boardMap = model.getBoardMap();
    this.hintManager = new HintManager(model);
    initializeUI();
  }

  private void initializeUI() {
    setBackground(Color.darkGray);
    setPreferredSize(new Dimension(500, 500));
    addMouseListener(this);
    addKeyListener(this);
    setFocusable(true);
    this.requestFocusInWindow();

    JButton quitButton = new JButton("Quit");
    quitButton.setBounds(100, getHeight() - 80, 80, 30);
    quitButton.addActionListener(e -> handleQuit());

    JButton passButton = new JButton("Pass");
    passButton.setBounds(10, getHeight() - 40, 80, 30);
    passButton.addActionListener(e -> handlePass());

    JButton hintButton = new JButton("Hint");
    hintButton.setBounds(10, getHeight() - 20, 80, 30);
    hintButton.addActionListener(e -> {
      handleHint();
      repaint();
    });

    add(quitButton);
    add(passButton);
    add(hintButton);

  }

  private void handleHint() {
    hintEnabled = !hintEnabled;
    hintManager.handleHint();
  }



  private void handlePass() {
    model.pass();
    if (model.isGameOver()) {
      showGameResult();
    } else {
      repaint();
    }
  }

  private void handleQuit() {
    // Handle quit action
    int result = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to quit?", "Quit Game", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      System.exit(0); // Exit the application
    }
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int offsetX = calculatePanelWidth(model.getBoardSize());
    int offsetY = calculatePanelHeight(model.getBoardSize());

    int midpointRow = model.getBoardSize() / 2; // Change to ReadonlyReversiModel

    for (HexCoordinate coordinate : boardMap.keySet()) {
      int r = coordinate.getR();

      if (r < midpointRow) {
        drawHexagon(g2d, coordinate.getQ(), r, offsetX, offsetY,
                coordinate.equals(highlightedCoordinate));
      }
    }

    for (HexCoordinate coordinate : boardMap.keySet()) {
      int r = coordinate.getR();

      if (r >= midpointRow) {
        drawHexagon(g2d, coordinate.getQ(), r, offsetX, offsetY,
                coordinate.equals(highlightedCoordinate));
      }
    }

    for (HexCoordinate coordinate : boardMap.keySet()) {
      int r = coordinate.getR();
      int q = coordinate.getQ();

      drawHexagon(g2d, q, r, offsetX, offsetY,
              coordinate.equals(highlightedCoordinate));

      // Check if the cell is empty and a valid move for the current player
      if (boardMap.get(coordinate).getState() == CellState.EMPTY
              && model.isValidMove(q, r, model.getCurrentPlayer()) &&
              model.getCurrentPlayer() == GamePlayers.Black && hintEnabled) {
        drawHint(g2d, q, r, offsetX, offsetY);
      }

    }


    drawInitialOvalPieces(g2d, offsetX, offsetY);


    if (highlightedCoordinate != null) {
      int r = highlightedCoordinate.getR();
      int centerX = calculateCenterX(0, r) + offsetX;
      int centerY = calculateCenterY(0, r) + offsetY;

      int[] xPoints = calculateHexagonXPoints(centerX);
      int[] yPoints = calculateHexagonYPoints(centerY);

      g2d.setColor(Color.CYAN); // Semi-transparent blue
      g2d.fillPolygon(xPoints, yPoints, 6);
    }


  }

  private void drawHint(Graphics2D g2d, int q, int r, int offsetX, int offsetY) {
    int centerX = calculateCenterX(q, r) + offsetX;
    int centerY = calculateCenterY(q, r) + offsetY;

    int[] xPoints = calculateHexagonXPoints(centerX);
    int[] yPoints = calculateHexagonYPoints(centerY);

    g2d.setColor(Color.GREEN); // Set the color to green for hints
    g2d.setStroke(new BasicStroke(2));
    g2d.drawPolygon(xPoints, yPoints, 6);
    g2d.setStroke(new BasicStroke(1)); // Reset the stroke

    int flippedCount = hintManager.getFlippedPiecesCount(new HexCoordinate(q, r),
            model.getCurrentPlayer());

    if (flippedCount > 1) {
      // Display the count of flipped pieces on the hexagon
      g2d.setColor(Color.BLACK); // Set the color to black for text
      Font font = new Font("Arial", Font.PLAIN, 12);
      g2d.setFont(font);

      String countText = String.valueOf(flippedCount);
      int textX = centerX - g2d.getFontMetrics().stringWidth(countText) / 2;
      int textY = centerY + g2d.getFontMetrics().getHeight() / 2;

      g2d.drawString(countText, textX, textY);
    } else if (flippedCount == 0) {
      // Display zero for invalid moves
      g2d.setColor(Color.RED); // Set the color to red for text
      Font font = new Font("Arial", Font.PLAIN, 12);
      g2d.setFont(font);

      String countText = "0";
      int textX = centerX - g2d.getFontMetrics().stringWidth(countText) / 2;
      int textY = centerY + g2d.getFontMetrics().getHeight() / 2;

      g2d.drawString(countText, textX, textY);
    }
  }


  private void drawInitialOvalPieces(Graphics2D g2d, int offsetX, int offsetY) {
    for (HexCoordinate coordinate : boardMap.keySet()) {
      int q = coordinate.getQ();
      int r = coordinate.getR();
      Cell cell = boardMap.get(coordinate);

      if (cell.getState() == CellState.X || cell.getState() == CellState.O) {
        int centerX = calculateCenterX(q, r) + offsetX;
        int centerY = calculateCenterY(q, r) + offsetY;
        drawOval(g2d, centerX, centerY, cell.getState() == CellState.X ? Color.BLACK : Color.WHITE);
      }
    }
  }

  private void drawOval(Graphics2D g2d, int centerX, int centerY, Color color) {
    int ovalSize = hexagonSize - 5; // Adjust oval size as needed
    int ovalX = centerX - ovalSize / 2;
    int ovalY = centerY - ovalSize / 2;
    g2d.setColor(color);
    g2d.fillOval(ovalX, ovalY, ovalSize, ovalSize);
  }

  private void drawHexagon(Graphics2D g2d, int q, int r, int offsetX,
                           int offsetY, boolean highlighted) {
    Cell cell = boardMap.get(new HexCoordinate(q, r));

    int centerX = calculateCenterX(q, r) + offsetX;
    int centerY = calculateCenterY(q, r) + offsetY;

    int[] xPoints = calculateHexagonXPoints(centerX);
    int[] yPoints = calculateHexagonYPoints(centerY);

    if (highlighted) {
      g2d.setColor(Color.CYAN); // Set the color to cyan when highlighted
      g2d.fillPolygon(xPoints, yPoints, 6);
    } else {
      g2d.setColor(Color.lightGray);
      g2d.fillPolygon(xPoints, yPoints, 6);
    }

    g2d.setColor(Color.BLACK);

    Stroke originalStroke = g2d.getStroke();
    g2d.setStroke(new BasicStroke(2));

    g2d.drawPolygon(xPoints, yPoints, 6);

    g2d.setStroke(originalStroke);

    if (cell.getState() == CellState.X || cell.getState() == CellState.O) {
      drawOval(g2d, centerX, centerY, cell.getState() == CellState.X ? Color.BLACK : Color.WHITE);
    }

    if (selectedCoordinate != null && selectedCoordinate.equals(new HexCoordinate(q, r))) {
      g2d.setColor(Color.CYAN); // Set the color to cyan when highlighted
      g2d.fillPolygon(xPoints, yPoints, 6);
    }
  }


  private int calculateCenterX(int q, int r) {
    int horizontalSpacing = (int) (hexagonSize * 1 * Math.sqrt(3));
    int xOffset = 0;

    if (r == 1) {
      xOffset = -2 * (horizontalSpacing / 2);
    } else if (r == 2) {
      xOffset = -2 * (horizontalSpacing / 2); // Offset for Y coordinate 2
    } else if (r == 3) {
      xOffset = -4 * (horizontalSpacing / 2); // Offset for Y coordinate 2
    } else if (r == 4) {
      xOffset = -4 * (horizontalSpacing / 2); // Offset for Y coordinate 2
    } else if (r == 5) {
      xOffset = -6 * (horizontalSpacing / 2); // Offset for Y coordinate 2
    } else if (r == 6) {
      xOffset = -6 * (horizontalSpacing / 2); // Offset for Y coordinate 2
    } else if (r == 7) {
      xOffset = -8 * (horizontalSpacing / 2); // Offset for Y coordinate 2
    } else if (r == 8) {
      xOffset = -8 * (horizontalSpacing / 2); // Offset for Y coordinate 2
    } else if (r == 9) {
      xOffset = -10 * (horizontalSpacing / 2);
    } else if (r == 10) {
      xOffset = -10 * (horizontalSpacing / 2);
    }


    return ((int) (q * horizontalSpacing + (r % 2) * (horizontalSpacing / 2.0)) + xOffset) - 300;
  }

  private int calculateCenterY(int q, int r) {
    HexUtils.OffsetCoord offsetCoord = HexUtils.axialToEvenR(new HexUtils.HexCoordinate(q, r));
    int verticalSpacing = (int) (Math.sqrt(3) * offsetCoord.getRow() * 1 * hexagonSize);

    if (r == 1) {
      verticalSpacing -= 5; // Adjust for row 1
    }

    if (r == 2) {
      verticalSpacing -= 13; // Adjust for row 2
    }

    if (r == 3) {
      verticalSpacing -= 20; // Adjust for row 3
    }

    if (r == 4) {
      verticalSpacing -= 27; // Adjust for row 4
    }

    if (r == 5) {
      verticalSpacing -= 34; // Adjust for row 5
    }

    if (r == 6) {
      verticalSpacing -= 40; // Adjust for row 6
    }

    if (r == 7) {
      verticalSpacing -= 46; // Adjust for row 7
    }

    if (r == 8) {
      verticalSpacing -= 52; // Adjust for row 8
    }
    if (r == 9) {
      verticalSpacing -= 60; // Adjust for row 9
    }
    if (r == 10) {
      verticalSpacing -= 67; // Adjust for row 8
    }

    return verticalSpacing;
  }

  private int[] calculateHexagonXPoints(int centerX) {
    int[] xPoints = new int[6];
    for (int i = 0; i < 6; i++) {
      xPoints[i] = (int) (centerX + 30 * Math.cos((i - 0.5) * Math.PI / 3));
    }
    return xPoints;
  }

  private int[] calculateHexagonYPoints(int centerY) {
    int[] yPoints = new int[6];
    for (int i = 0; i < 6; i++) {
      yPoints[i] = (int) (centerY + 30 * Math.sin((i - 0.5) * Math.PI / 3));
    }
    return yPoints;
  }


  private int calculatePanelWidth(int boardSize) {
    int horizontalSpacing = (int) (hexagonSize * 1 * Math.sqrt(3));
    Dimension panelSize = getSize();
    int panelWidth = panelSize.width;

    int offsetX = (panelWidth - horizontalSpacing * (2 * boardSize - 1) - 250) / 2;
    return horizontalSpacing * (2 * boardSize - 1) + horizontalSpacing + offsetX;
  }

  private int calculatePanelHeight(int boardSize) {
    int verticalSpacing = (int) (1.5 * 30 * boardSize * Math.sqrt(3));
    Dimension panelSize = getSize();
    int panelHeight = panelSize.height;

    int offsetY = (panelHeight - verticalSpacing + 100) / 2;
    return verticalSpacing - offsetY;
  }


  /**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    int mouseX = e.getX();
    int mouseY = e.getY();

    HexCoordinate clickedCoordinate = getClickedCoordinate(mouseX, mouseY);

    if (clickedCoordinate != null) {
      if (selectedCoordinate != null && selectedCoordinate.equals(clickedCoordinate)) {
        // Case 1: Clicked on the selected cell again, deselect it
        selectedCoordinate = null;
      } else if (isPassButtonClicked(e.getX(), e.getY())) {
        if (model.getCurrentPlayer() == GamePlayers.Black) {
          model.pass();
          repaint();
        } else {
          showMessageDialog("It's not your turn to pass.");
        }
        selectedCoordinate = null;
      } else {
        // Case 3: Clicked on another cell, select it
        selectedCoordinate = clickedCoordinate;
        handleMove(clickedCoordinate.getQ(), clickedCoordinate.getR());
      }
    } else {
      // Case 4: Clicked outside the boundary of the board, deselect the selected cell
      selectedCoordinate = null;
    }
    repaint();
  }

  private boolean isPassButtonClicked(int x, int y) {
    int passButtonX = 10; // Adjust the position as needed
    int passButtonY = getHeight() - 40; // Adjust the position as needed
    int passButtonWidth = 80;
    int passButtonHeight = 30;

    return x >= passButtonX && x <= passButtonX + passButtonWidth
            && y >= passButtonY && y <= passButtonY + passButtonHeight;
  }


  private void handleMove(int q, int r) {
    if (model.isValidMove(q, r, model.getCurrentPlayer())) {
      model.play(q, r);
      repaint();
      model.switchPlayer();
    } else if (!model.isValidMove(q, r, model.getCurrentPlayer()) && model.getCurrentPlayer()
            == GamePlayers.Black) {
      model.setCurrentPlayer(GamePlayers.Black);
      showMessageDialog("Invalid move. Please select a valid cell.");
    }
    /*
        drawHint(g2d, q, r, offsetX, offsetY);
         if (boardMap.get(coordinate).getState() == CellState.EMPTY
              && model.isValidMove(q, r, model.getCurrentPlayer()) &&
      model.getCurrentPlayer() == GamePlayers.Black)

     */
  }

  private void showGameResult() {
    int blackScore = model.score(GamePlayers.Black);
    int whiteScore = model.score(GamePlayers.White);

    String message;
    if (blackScore > whiteScore) {
      message = "Black wins! Black Score: "
              + model.score(GamePlayers.Black) + " White Score: " + model.score(GamePlayers.White);
    } else if (whiteScore > blackScore) {
      message = "White wins! White Score: "
              + model.score(GamePlayers.White) + " Black Score: " + model.score(GamePlayers.Black);
    } else {
      message = "It's a tie! Black Score: "
              + model.score(GamePlayers.Black) + " White Score: " + model.score(GamePlayers.White);
    }

    JOptionPane.showMessageDialog(this, message, "Game Over",
            JOptionPane.INFORMATION_MESSAGE);
  }


  private HexCoordinate getClickedCoordinate(int mouseX, int mouseY) {
    int offsetX = calculatePanelWidth(model.getBoardSize());
    int offsetY = calculatePanelHeight(model.getBoardSize());

    for (HexCoordinate coordinate : boardMap.keySet()) {
      int r = coordinate.getR();
      int centerX = calculateCenterX(coordinate.getQ(), r) + offsetX;
      int centerY = calculateCenterY(coordinate.getQ(), r) + offsetY;

      int[] xPoints = calculateHexagonXPoints(centerX);
      int[] yPoints = calculateHexagonYPoints(centerY);

      Polygon hexagon = new Polygon(xPoints, yPoints, 6);

      if (hexagon.contains(mouseX, mouseY)) {
        return coordinate;
      }
    }

    return null;
  }


  /**
   * Invoked when a mouse button has been pressed on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mousePressed(MouseEvent e) {
    //TODO: Implement mousePressed

  }

  /**
   * Invoked when a mouse button has been released on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    //TODO: Implement mouseReleased
  }

  /**
   * Invoked when the mouse enters a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    int mouseX = e.getX();
    int mouseY = e.getY();
    HexCoordinate clickedCoordinate = getClickedCoordinate(mouseX, mouseY);

    if (clickedCoordinate != null) {
      highlightedCoordinate = clickedCoordinate;
      repaint();
    }

  }

  /**
   * Invoked when the mouse exits a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseExited(MouseEvent e) {
    highlightedCoordinate = null;
    repaint();
  }


  /**
   * Invoked when a key has been typed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key typed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyTyped(KeyEvent e) {
    //TODO: Implement keyTyped
  }

  /**
   * Invoked when a key has been pressed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key pressed event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();

    if (keyCode == KeyEvent.VK_ENTER) {
      // Handle Enter key (indicating a move or pass)
      if (selectedCell != null) {
        model.play(selectedCell.getQ(), selectedCell.getR());
        selectedCell = null; // Clear the selection after making a move
        repaint();
      } else {
        // Handle pass action
        model.pass();
        repaint();
      }
    } else if (keyCode == KeyEvent.VK_ESCAPE) {
      // Handle Escape key (deselecting the selected cell)
      selectedCell = null;
      repaint();
    }

  }

  /**
   * Invoked when a key has been released.
   * See the class description for {@link KeyEvent} for a definition of
   * a key released event.
   *
   * @param e the event to be processed
   */
  @Override
  public void keyReleased(KeyEvent e) {
    //TODO: Implement keyReleased

  }

  /**
   * Make the view visible. This is usually called
   * after the view is constructed.
   */
  @Override
  public void makeVisible() {
    // Empty method implemented so interface does not throw error.

  }

  /**
   * Transmit an error message to the view, in case
   * the command could not be processed correctly.
   *
   * @param error error
   */
  @Override
  public void showErrorMessage(String error) {
    // implemented so interface does not throw an error.
  }

  /**
   * Implemented so interface does not throw an error.
   * @param currentPlayer current player.
   */
  @Override
  public void showPlayerTurnMessage(GamePlayers currentPlayer) {
    // Empty method implemented so interface does not throw error.

  }

  @Override
  public void addPlayerActionListener(IController iController) {
    // Empty method implemented so interface does not throw error.

  }

  @Override
  public void updateBoard(Board board) {
    // Empty method implemented so interface does not throw error.

  }

  @Override
  public void showMessageDialog(String s) {
    JOptionPane.showMessageDialog(this, "Invalid move. " +
            "Please select a valid cell.", "Illegal Move", JOptionPane.ERROR_MESSAGE);

  }
}