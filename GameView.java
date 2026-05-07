import javax.swing.*;
import java.awt.*;

/**
 * GameView - Displays the game using Java Swing.
 * Draws the paddle, ball, bricks, score, and win/loss messages.
 */
public class GameView extends JPanel {

    private GameModel model;

    // Colors for drawing
    private static final Color PADDLE_COLOR = Color.BLUE;
    private static final Color BALL_COLOR = Color.RED;
    private static final Color BRICK_COLOR = Color.GREEN;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color TEXT_COLOR = Color.WHITE;

    /**
     * Constructor - Initialize the view with a reference to the model.
     */
    public GameView(GameModel model) {
        this.model = model;
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(800, 600));
        setDoubleBuffered(true);
    }

    /**
     * Paint the game state on the screen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw game elements
        drawBricks(g2d);
        drawPaddle(g2d);
        drawBall(g2d);
        drawScore(g2d);

        // Draw game state messages
        GameModel.GameState state = model.getGameState();
        if (state == GameModel.GameState.WON) {
            drawWinMessage(g2d);
        } else if (state == GameModel.GameState.LOST) {
            drawLossMessage(g2d);
        }
    }

    /**
     * Draw the paddle.
     */
    private void drawPaddle(Graphics g) {
        g.setColor(PADDLE_COLOR);
        g.fillRect(model.getPaddleX(), model.getPaddleY(),
                   model.getPaddleWidth(), model.getPaddleHeight());
    }

    /**
     * Draw the ball.
     */
    private void drawBall(Graphics g) {
        g.setColor(BALL_COLOR);
        int diameter = model.getBallRadius() * 2;
        g.fillOval((int)(model.getBallX() - model.getBallRadius()),
                   (int)(model.getBallY() - model.getBallRadius()),
                   diameter, diameter);
    }

    /**
     * Draw all active bricks.
     */
    private void drawBricks(Graphics g) {
        g.setColor(BRICK_COLOR);
        for (GameModel.Brick brick : model.getBricks()) {
            if (brick.isActive()) {
                g.fillRect(brick.getX(), brick.getY(),
                          brick.getWidth(), brick.getHeight());
            }
        }
    }

    /**
     * Draw the score on screen.
     */
    private void drawScore(Graphics g) {
        g.setColor(TEXT_COLOR);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Score: " + model.getScore(), 10, 20);
    }

    /**
     * Draw win message.
     */
    private void drawWinMessage(Graphics g) {
        g.setColor(TEXT_COLOR);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        String message = "You Win!";
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(message)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(message, x, y);
    }

    /**
     * Draw loss message.
     */
    private void drawLossMessage(Graphics g) {
        g.setColor(TEXT_COLOR);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        String message = "Game Over";
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(message)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(message, x, y);
    }

    /**
     * Repaint the view to reflect current game state.
     * Called from the controller's game loop.
     */
    public void repaintGame() {
        repaint();
    }
}
