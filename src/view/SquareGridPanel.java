package view;

import controller.IController;
import model.Board;
import model.Cell;
import model.CellState;
import model.HexCoordinate;
import model.ReadOnlyReversiModel;
import player.GamePlayers;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Represents a SquareGridPanel for the game.
 */
public class SquareGridPanel extends JPanel implements MouseListener, KeyListener, IView {
  private final ReadOnlyReversiModel model; // Change to ReadonlyReversiModel
  private HexCoordinate selectedCell;
  private final int squareSize = 80;
  private final Map<HexCoordinate, Cell> boardMap;
  private HexCoordinate selectedCoordinate;

  private HexCoordinate highlightedCoordinate;

  private boolean hint = false;


  /**
   * Constructs a SquareGridPanel with the provided ReadOnlyReversiModel.
   *
   * @param model The ReadOnlyReversiModel to display in the panel.
   */
  public SquareGridPanel(ReadOnlyReversiModel model) {
    this.model = model;
    this.boardMap = model.getBoardMap();
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
    if (model.getCurrentPlayer() == GamePlayers.Black) {
      hint = true;
      repaint();
    } else {
      hint = false;
      showMessageDialog("It's not your turn to get a hint.");
    }
  }

  private void handlePass() {
    model.pass();
  }

  private void showGameResult() {
    int blackScore = model.score(GamePlayers.Black);
    int whiteScore = model.score(GamePlayers.White);

    String message;
    if (blackScore > whiteScore) {
      message = "Black wins!";
    } else if (whiteScore > blackScore) {
      message = "White wins!";
    } else {
      message = "It's a tie!";
    }
    JOptionPane.showMessageDialog(this, message, "Game Over",
            JOptionPane.INFORMATION_MESSAGE);
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

    int offsetX = calculatePanelWidth(model.getBoardSize(), squareSize);
    int offsetY = calculatePanelHeight(model.getBoardSize());

    int midpointRow = model.getBoardSize() / 2; // Change to ReadonlyReversiModel

    for (HexCoordinate coordinate : boardMap.keySet()) {
      int r = coordinate.getR();

      if (r < midpointRow) {
        drawSquare(g2d, coordinate.getQ(), r, offsetX, offsetY,
                coordinate.equals(highlightedCoordinate));
      }
    }

    for (HexCoordinate coordinate : boardMap.keySet()) {
      int r = coordinate.getR();

      if (r >= midpointRow) {
        drawSquare(g2d, coordinate.getQ(), r, offsetX, offsetY,
                coordinate.equals(highlightedCoordinate));
      }
    }

    for (HexCoordinate coordinate : boardMap.keySet()) {
      int r = coordinate.getR();
      int q = coordinate.getQ();

      drawSquare(g2d, q, r, offsetX, offsetY,
              coordinate.equals(highlightedCoordinate));

      if (boardMap.get(coordinate).getState() == CellState.EMPTY
              && model.isValidMove(q, r, model.getCurrentPlayer()) &&
              model.getCurrentPlayer() == GamePlayers.Black && hint) {
        g2d.setColor(Color.GREEN);
        drawOval(g2d, calculateCenterX(q, r) + offsetX,
                calculateCenterY(q, r) + offsetY, Color.GREEN, squareSize);
      }

    }
    drawInitialOvalPieces(g2d, offsetX, offsetY);
    if (highlightedCoordinate != null) {
      int r = highlightedCoordinate.getR();
      int centerX = calculateCenterX(0, r) + offsetX;
      int centerY = calculateCenterY(0, r) + offsetY;

      int[] xPoints = calculateHexagonXPoints(centerX);
      int[] yPoints = calculateHexagonYPoints(centerY);

      int x = centerX - squareSize / 2;
      int y = centerY - squareSize / 2;

      g2d.setColor(Color.CYAN); // Semi-transparent blue
      g2d.fillRect(x, y, squareSize, squareSize);
    }


  }

  private int[] calculateHexagonYPoints(int centerY) {
    int[] yPoints = new int[6];
    yPoints[0] = centerY - squareSize;
    yPoints[1] = centerY - squareSize / 2;
    yPoints[2] = centerY;
    yPoints[3] = centerY + squareSize / 2;
    yPoints[4] = centerY + squareSize;
    yPoints[5] = centerY + squareSize / 2;
    return yPoints;
  }

  private int[] calculateHexagonXPoints(int centerX) {
    int[] xPoints = new int[6];
    xPoints[0] = centerX - squareSize / 2;
    xPoints[1] = centerX + squareSize / 2;
    xPoints[2] = centerX + squareSize;
    xPoints[3] = centerX + squareSize / 2;
    xPoints[4] = centerX - squareSize / 2;
    xPoints[5] = centerX - squareSize;
    return xPoints;
  }

  private void drawHint(Graphics2D g2d, int q, int r, int offsetX, int offsetY) {

    int centerX = calculateCenterX(q, r) + offsetX;
    int centerY = calculateCenterY(q, r) + offsetY;

    int[] xPoints = calculateHexagonXPoints(centerX);
    int[] yPoints = calculateHexagonYPoints(centerY);

    drawSquare(g2d, q, r, offsetX, offsetY, false); // Draw the square
    g2d.setColor(Color.GREEN); // Set the color to green

  }


  private int calculateCenterY(int i, int r) {
    return (r * squareSize) - 500;
  }

  private int calculateCenterX(int i, int r) {
    return (i * squareSize) - 500;
  }

  private void drawInitialOvalPieces(Graphics2D g2d, int offsetX, int offsetY) {
    for (HexCoordinate coordinate : boardMap.keySet()) {
      int r = coordinate.getR();
      int q = coordinate.getQ();

      if (boardMap.get(coordinate).getState() == CellState.X) {
        drawOval(g2d, calculateCenterX(q, r) + offsetX,
                calculateCenterY(q, r) + offsetY, Color.BLACK, squareSize);
      } else if (boardMap.get(coordinate).getState() == CellState.O) {
        drawOval(g2d, calculateCenterX(q, r) + offsetX,
                calculateCenterY(q, r) + offsetY, Color.WHITE, squareSize);
      }
    }
  }

  private void drawOval(Graphics2D g2d, int centerX, int centerY, Color color, int squareSize) {
    int ovalSize = (int) (squareSize * 0.8);
    int ovalX = (centerX + (squareSize - ovalSize) / 2) - 45;
    int ovalY = (centerY + (squareSize - ovalSize) / 2) - 40;
    g2d.setColor(color);
    g2d.fillOval(ovalX, ovalY, ovalSize, ovalSize);
  }

  private void drawSquare(Graphics2D g2d, int q, int r, int offsetX, int offsetY, boolean equals) {
    Cell cell = boardMap.get(new HexCoordinate(q, r));
    int centerX = calculateCenterX(q, r) + offsetX;
    int centerY = calculateCenterY(q, r) + offsetY;

    int x = centerX - squareSize / 2;
    int y = centerY - squareSize / 2;

    if (equals) {
      g2d.setColor(Color.CYAN); // Semi-transparent blue
      g2d.fillRect(x, y, squareSize, squareSize);
    } else {
      g2d.setColor(Color.lightGray);
      g2d.fillRect(x, y, squareSize, squareSize);
    }
    g2d.setColor(Color.BLACK);


    Stroke originalStroke = g2d.getStroke();
    g2d.setStroke(new BasicStroke(2));
    g2d.drawRect(x, y, squareSize, squareSize);
    g2d.setStroke(originalStroke);

    if (cell.getState() == CellState.X || cell.getState() == CellState.O) {
      drawOval(g2d, centerX, centerY, cell.getState() == CellState.X ? Color.BLACK : Color.WHITE,
              squareSize);
    }
    if (selectedCoordinate != null && selectedCoordinate.equals(new HexCoordinate(q, r))) {
      g2d.setColor(Color.CYAN); // Set the color to cyan when highlighted
      g2d.fillRect(x, y, squareSize, squareSize);
    }
  }

  private int calculatePanelHeight(int boardSize) {
    return boardSize * squareSize;
  }

  private int calculatePanelWidth(int boardSize, int squareSize) {
    int horizontalSpacing = squareSize * boardSize; // Adjust spacing if needed
    Dimension panelSize = getSize();
    int panelWidth = panelSize.width;

    int offsetX = (panelWidth - horizontalSpacing) / 2;
    return horizontalSpacing + offsetX;
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
    //TODO: Implement this method
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
    //TODO: Implement this method
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
      }
      handleMove(clickedCoordinate.getQ(), clickedCoordinate.getR());
    } else {
      // Case 4: Clicked outside the boundary of the board, deselect the selected cell
      selectedCoordinate = null;
    }
    model.switchPlayer();
    repaint();

  }

  private void handleMove(int q, int r) {
    if (model.isValidMove(q, r, model.getCurrentPlayer())) {
      model.play(q, r);
      repaint();
    } else if (!model.isValidMove(q, r, model.getCurrentPlayer())
            && model.getCurrentPlayer() == GamePlayers.Black) {
      showMessageDialog("Invalid move. Please select a valid cell.");
      model.setCurrentPlayer(GamePlayers.Black);
    }
  }

  private boolean isPassButtonClicked(int x, int y) {
    int passButtonX = 10; // Adjust the position as needed
    int passButtonY = getHeight() - 40; // Adjust the position as needed
    int passButtonWidth = 80;
    int passButtonHeight = 30;

    return x >= passButtonX && x <= passButtonX + passButtonWidth
            && y >= passButtonY && y <= passButtonY + passButtonHeight;
  }

  private HexCoordinate getClickedCoordinate(int mouseX, int mouseY) {
    int offsetX = calculatePanelWidth(model.getBoardSize(), squareSize);
    int offsetY = calculatePanelHeight(model.getBoardSize());

    for (HexCoordinate coordinate : boardMap.keySet()) {
      int r = coordinate.getR();
      int centerX = calculateCenterX(coordinate.getQ(), r) + offsetX;
      int centerY = calculateCenterY(coordinate.getQ(), r) + offsetY;

      int x = centerX - squareSize / 2;
      int y = centerY - squareSize / 2;

      Rectangle boundingBox = new Rectangle(x, y, squareSize, squareSize);

      if (boundingBox.contains(mouseX, mouseY)) {
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
    //TODO: Implement this method
  }

  /**
   * Invoked when a mouse button has been released on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    //TODO: Implement this method
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
    //TODO: Implement this method
  }

  /**
   * Update the board.
   *
   * @param board the board.
   */
  @Override
  public void updateBoard(Board board) {
    //TODO: Implement this method
  }

  /**
   * Show a message dialog.
   *
   * @param s the message.
   */
  @Override
  public void showMessageDialog(String s) {
    JOptionPane.showMessageDialog(this, "Invalid move. " +
            "Please select a valid cell.", "Illegal Move", JOptionPane.ERROR_MESSAGE);
  }
}
