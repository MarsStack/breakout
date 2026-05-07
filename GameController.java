import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * GameController - Handles player input and game timing.
 * Listens for keyboard input to move the paddle and uses a Swing Timer for the game loop.
 */
public class GameController extends KeyAdapter {

    private GameModel model;
    private GameView view;
    private JFrame frame;

    private Timer gameTimer;
    private static final int TIMER_DELAY = 20; // milliseconds between updates (~50 FPS)

    // Track which keys are currently pressed
    private boolean leftPressed;
    private boolean rightPressed;

    /**
     * Constructor - Initialize the controller with model and view references.
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.leftPressed = false;
        this.rightPressed = false;
    }

    /**
     * Set up the game window frame.
     */
    public void setupFrame(JFrame frame) {
        this.frame = frame;
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Start the game - initialize the timer and game loop.
     */
    public void startGame() {
        gameTimer = new Timer(TIMER_DELAY, e -> gameUpdate());
        gameTimer.start();
        frame.setFocusable(true);
        frame.requestFocus();
    }

    /**
     * Stop the game - halt the game loop.
     */
    public void stopGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    /**
     * Game update loop - called repeatedly by the timer.
     */
    private void gameUpdate() {
        // Update paddle position based on key presses
        if (leftPressed) {
            model.movePaddleLeft();
        }
        if (rightPressed) {
            model.movePaddleRight();
        }

        // Update model state (ball movement, collisions, etc.)
        model.update();

        // Repaint the view
        view.repaintGame();

        // Check if game is over and stop timer if needed
        GameModel.GameState gameState = model.getGameState();
        if (gameState == GameModel.GameState.WON || gameState == GameModel.GameState.LOST) {
            stopGame();
        }
    }

    /**
     * Handle key pressed event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        } else if (keyCode == KeyEvent.VK_R) {
            model.reset();
        }
    }

    /**
     * Handle key released event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }
}
