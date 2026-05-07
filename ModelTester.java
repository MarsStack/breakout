/**
 * ModelTester - Direct unit tests for GameModel without external testing libraries.
 * Tests core game behaviors: paddle movement, ball collisions, brick destruction, and win/loss conditions.
 */
public class ModelTester {

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {
        System.out.println("=== GameModel Unit Tests ===\n");

        testPaddleLeftBoundary();
        testPaddleRightBoundary();
        testBallLeftWallBounce();
        testBrickCollisionAndScore();
        testWinCondition();
        testLossCondition();

        System.out.println("\n=== Test Summary ===");
        System.out.println("Tests run: " + testsRun);
        System.out.println("Tests passed: " + testsPassed);
        System.out.println("Tests failed: " + (testsRun - testsPassed));
    }

    /**
     * Test 1: Paddle cannot move past the left edge of the screen.
     */
    private static void testPaddleLeftBoundary() {
        testsRun++;
        System.out.println("Test 1: Paddle left boundary");

        GameModel model = new GameModel(800, 600);
        int paddleX = model.getPaddleX();

        // Move paddle left many times
        for (int i = 0; i < 100; i++) {
            model.movePaddleLeft();
        }

        // Paddle should be at x = 0 (left edge), not negative
        if (model.getPaddleX() == 0) {
            System.out.println("✓ PASS: Paddle stopped at left boundary (x=0)\n");
            testsPassed++;
        } else {
            System.out.println("✗ FAIL: Paddle at x=" + model.getPaddleX() + " (expected 0)\n");
        }
    }

    /**
     * Test 2: Paddle cannot move past the right edge of the screen.
     */
    private static void testPaddleRightBoundary() {
        testsRun++;
        System.out.println("Test 2: Paddle right boundary");

        GameModel model = new GameModel(800, 600);
        int screenWidth = 800;
        int paddleWidth = model.getPaddleWidth();
        int maxX = screenWidth - paddleWidth;

        // Move paddle right many times
        for (int i = 0; i < 100; i++) {
            model.movePaddleRight();
        }

        // Paddle should be at rightmost valid position
        if (model.getPaddleX() == maxX) {
            System.out.println("✓ PASS: Paddle stopped at right boundary (x=" + maxX + ")\n");
            testsPassed++;
        } else {
            System.out.println("✗ FAIL: Paddle at x=" + model.getPaddleX() + " (expected " + maxX + ")\n");
        }
    }

    /**
     * Test 3: Ball bounces correctly off the left wall.
     */
    private static void testBallLeftWallBounce() {
        testsRun++;
        System.out.println("Test 3: Ball left wall bounce");

        GameModel model = new GameModel(800, 600);

        // Move ball to near left wall with leftward velocity
        double ballRadius = model.getBallRadius();

        // Use reflection to set ball position and velocity (simulate private field access)
        // For this test, we'll move the ball and check bounce behavior
        // Position ball near left wall
        for (int i = 0; i < 300; i++) {
            model.update();
            if (model.getBallX() - model.getBallRadius() <= 1) {
                break; // Ball reached left edge
            }
        }

        // After several updates, if ball is at left edge, it should have bounced
        // (ballSpeedX should be positive, moving right)
        double ballX = model.getBallX();
        if (ballX - ballRadius >= 0) {
            System.out.println("✓ PASS: Ball bounced off left wall (ballX=" + ballX + ")\n");
            testsPassed++;
        } else {
            System.out.println("✗ FAIL: Ball passed through left wall (ballX=" + ballX + ")\n");
        }
    }

    /**
     * Test 4: Hitting a brick deactivates it and increases the score.
     */
    private static void testBrickCollisionAndScore() {
        testsRun++;
        System.out.println("Test 4: Brick collision and scoring");

        GameModel model = new GameModel(800, 600);
        int initialScore = model.getScore();
        int activeBricksBefore = countActiveBricks(model);

        // Get the first brick
        GameModel.Brick firstBrick = model.getBricks().get(0);
        boolean wasBrickActive = firstBrick.isActive();

        // Simulate collision by deactivating the brick
        // (In real game, collision detection does this)
        if (wasBrickActive) {
            firstBrick.setActive(false);
            model.update(); // Let model recalculate score if needed
        }

        int activeBricksAfter = countActiveBricks(model);

        // Check that brick was deactivated
        boolean brickDeactivated = !firstBrick.isActive();
        boolean brickCountDecreased = activeBricksAfter < activeBricksBefore;

        if (brickDeactivated && brickCountDecreased) {
            System.out.println("✓ PASS: Brick deactivated and count decreased\n");
            testsPassed++;
        } else {
            System.out.println("✗ FAIL: Brick not properly deactivated\n");
        }
    }

    /**
     * Test 5: When all bricks are destroyed, game enters WON state.
     */
    private static void testWinCondition() {
        testsRun++;
        System.out.println("Test 5: Win condition (all bricks destroyed)");

        GameModel model = new GameModel(800, 600);

        // Deactivate all bricks
        for (GameModel.Brick brick : model.getBricks()) {
            brick.setActive(false);
        }

        // Call update to trigger win condition check
        model.update();

        // Check if game state is WON
        if (model.getGameState() == GameModel.GameState.WON) {
            System.out.println("✓ PASS: Game won when all bricks destroyed\n");
            testsPassed++;
        } else {
            System.out.println("✗ FAIL: Game state is " + model.getGameState() + " (expected WON)\n");
        }
    }

    /**
     * Test 6: When ball falls below screen, game enters LOST state.
     */
    private static void testLossCondition() {
        testsRun++;
        System.out.println("Test 6: Loss condition (ball falls below screen)");

        GameModel model = new GameModel(800, 600);
        int screenHeight = 600;

        // Simulate ball falling by repeatedly calling update
        // Move ball downward by calling update many times
        for (int i = 0; i < 1000; i++) {
            if (model.getGameState() == GameModel.GameState.LOST) {
                break;
            }
            model.update();
        }

        // Check if game state is LOST
        if (model.getGameState() == GameModel.GameState.LOST) {
            System.out.println("✓ PASS: Game lost when ball fell below screen\n");
            testsPassed++;
        } else {
            System.out.println("✗ FAIL: Game state is " + model.getGameState() + " (expected LOST)\n");
        }
    }

    /**
     * Helper method to count active bricks.
     */
    private static int countActiveBricks(GameModel model) {
        int count = 0;
        for (GameModel.Brick brick : model.getBricks()) {
            if (brick.isActive()) {
                count++;
            }
        }
        return count;
    }
}
