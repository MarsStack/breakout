import java.util.ArrayList;
import java.util.List;

/**
 * GameModel - Stores and manages the game state and logic for Breakout.
 * Handles ball movement, collisions, brick destruction, scoring, and win/loss conditions.
 */
public class GameModel {

    // Game dimensions
    private int screenWidth;
    private int screenHeight;

    // Paddle state
    private int paddleX;
    private int paddleY;
    private int paddleWidth;
    private int paddleHeight;
    private int paddleSpeed;

    // Ball state
    private double ballX;
    private double ballY;
    private int ballRadius;
    private double ballSpeedX;
    private double ballSpeedY;

    // Bricks
    private List<Brick> bricks;
    private int bricksDestroyed;

    // Game state
    private int score;
    private GameState gameState; // RUNNING, WON, LOST

    // Enum for game states
    public enum GameState {
        RUNNING, WON, LOST
    }

    /**
     * Constructor - Initialize the game model with starting state.
     */
    public GameModel(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Initialize paddle at bottom center
        this.paddleWidth = 80;
        this.paddleHeight = 12;
        this.paddleX = (screenWidth - paddleWidth) / 2;
        this.paddleY = screenHeight - 30;
        this.paddleSpeed = 6;

        // Initialize ball at center, moving downward
        this.ballRadius = 6;
        this.ballX = screenWidth / 2.0;
        this.ballY = screenHeight / 2.0;
        this.ballSpeedX = 3.0;
        this.ballSpeedY = 4.0;

        // Initialize bricks in grid pattern
        this.bricks = new ArrayList<>();
        int brickWidth = 60;
        int brickHeight = 20;
        int brickSpacing = 10;
        int startX = 20;
        int startY = 40;

        // Create 6 rows x 8 columns of bricks
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                int x = startX + col * (brickWidth + brickSpacing);
                int y = startY + row * (brickHeight + brickSpacing);
                bricks.add(new Brick(x, y, brickWidth, brickHeight));
            }
        }

        this.bricksDestroyed = 0;
        this.score = 0;
        this.gameState = GameState.RUNNING;
    }

    /**
     * Update the game state - called repeatedly from the game loop.
     */
    public void update() {
        if (gameState != GameState.RUNNING) {
            return;
        }

        // Update ball position based on velocity
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Check collisions
        checkWallCollisions();
        checkPaddleCollision();
        checkBrickCollisions();

        // Check win/loss conditions
        checkWinCondition();
        checkLossCondition();
    }

    /**
     * Move the paddle left.
     */
    public void movePaddleLeft() {
        paddleX -= paddleSpeed;
        if (paddleX < 0) {
            paddleX = 0;
        }
    }

    /**
     * Move the paddle right.
     */
    public void movePaddleRight() {
        paddleX += paddleSpeed;
        if (paddleX + paddleWidth > screenWidth) {
            paddleX = screenWidth - paddleWidth;
        }
    }

    /**
     * Check if the ball collides with the top or sides of the screen.
     */
    private void checkWallCollisions() {
        // Bounce off top wall
        if (ballY - ballRadius <= 0) {
            ballY = ballRadius;
            ballSpeedY = -ballSpeedY;
        }

        // Bounce off left wall
        if (ballX - ballRadius <= 0) {
            ballX = ballRadius;
            ballSpeedX = -ballSpeedX;
        }

        // Bounce off right wall
        if (ballX + ballRadius >= screenWidth) {
            ballX = screenWidth - ballRadius;
            ballSpeedX = -ballSpeedX;
        }
    }

    /**
     * Check if the ball collides with the paddle.
     */
    private void checkPaddleCollision() {
        // Check if ball overlaps with paddle rectangle
        if (ballY + ballRadius >= paddleY &&
            ballY - ballRadius <= paddleY + paddleHeight &&
            ballX + ballRadius >= paddleX &&
            ballX - ballRadius <= paddleX + paddleWidth) {

            // Ball hit the paddle - reverse Y direction
            ballSpeedY = -ballSpeedY;

            // Position ball above paddle to prevent multiple bounces
            ballY = paddleY - ballRadius;

            // Add horizontal spin based on where ball hit the paddle
            // Left side of paddle - angle left, right side - angle right
            double paddleCenter = paddleX + paddleWidth / 2.0;
            double hitOffset = ballX - paddleCenter; // -40 to +40
            ballSpeedX += hitOffset / 50.0; // Add some angle based on hit location
        }
    }

    /**
     * Check if the ball collides with any bricks.
     */
    private void checkBrickCollisions() {
        for (Brick brick : bricks) {
            if (!brick.isActive()) {
                continue;
            }

            // Check if ball overlaps with brick rectangle
            if (ballX + ballRadius >= brick.getX() &&
                ballX - ballRadius <= brick.getX() + brick.getWidth() &&
                ballY + ballRadius >= brick.getY() &&
                ballY - ballRadius <= brick.getY() + brick.getHeight()) {

                // Collision detected - deactivate brick and update score
                brick.setActive(false);
                bricksDestroyed++;
                score += 10;

                // Determine collision side and bounce accordingly
                double overlapLeft = (ballX + ballRadius) - brick.getX();
                double overlapRight = (brick.getX() + brick.getWidth()) - (ballX - ballRadius);
                double overlapTop = (ballY + ballRadius) - brick.getY();
                double overlapBottom = (brick.getY() + brick.getHeight()) - (ballY - ballRadius);

                // Find minimum overlap to determine collision side
                double minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                                             Math.min(overlapTop, overlapBottom));

                // Bounce off the side with minimum overlap
                if (minOverlap == overlapLeft || minOverlap == overlapRight) {
                    ballSpeedX = -ballSpeedX;
                } else {
                    ballSpeedY = -ballSpeedY;
                }

                // Only process one brick collision per frame
                return;
            }
        }
    }

    /**
     * Check win condition - all bricks destroyed.
     */
    private void checkWinCondition() {
        // Count active bricks
        int activeBrickCount = 0;
        for (Brick brick : bricks) {
            if (brick.isActive()) {
                activeBrickCount++;
            }
        }

        // If no active bricks remain, player wins
        if (activeBrickCount == 0) {
            gameState = GameState.WON;
        }
    }

    /**
     * Check loss condition - ball falls below paddle.
     */
    private void checkLossCondition() {
        if (ballY - ballRadius > screenHeight) {
            gameState = GameState.LOST;
        }
    }

    /**
     * Reset the game to initial state.
     */
    public void reset() {
        // Reset ball position and velocity
        ballX = screenWidth / 2.0;
        ballY = screenHeight / 2.0;
        ballSpeedX = 3.0;
        ballSpeedY = 4.0;

        // Reset paddle position
        paddleX = (screenWidth - paddleWidth) / 2;

        // Reset all bricks
        bricksDestroyed = 0;
        for (Brick brick : bricks) {
            brick.setActive(true);
        }

        // Reset score
        score = 0;

        // Set gameState to RUNNING
        gameState = GameState.RUNNING;
    }

    // Getters for View access

    public int getPaddleX() {
        return paddleX;
    }

    public int getPaddleY() {
        return paddleY;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public int getBallRadius() {
        return ballRadius;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public int getScore() {
        return score;
    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * Inner class to represent a brick.
     */
    public static class Brick {
        private int x;
        private int y;
        private int width;
        private int height;
        private boolean active;

        public Brick(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.active = true;
        }

        public int getX() { return x; }
        public int getY() { return y; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
    }
}
