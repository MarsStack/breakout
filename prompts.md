"I'm building breakout in Java using Swing and MVC. Here's my spec: # README.md
Create the three class shells — GameModel.java, GameView.java, GameController.java — with method stubs based on this design."

- Created the Game Controller, Model, and View java files.

Fill in GameModel.java for my Breakout game. The model should track: the paddle’s x/y position, width, height, and speed; the ball’s x/y position, size, x/y velocity; a grid of bricks with rows and columns; which bricks are still active; the score; and whether the game is running, won, or lost.

Add logic to: move the paddle left and right while keeping it inside the game window, update the ball position each tick, bounce the ball off the left, right, and top walls, bounce the ball off the paddle, detect collisions between the ball and active bricks, deactivate bricks when hit, increase the score when a brick is destroyed, detect when all bricks are gone and set the win state, and detect when the ball falls below the bottom of the screen and set the lose state.

Do not use Swing imports in GameModel.java. Keep this class focused only on game state and game logic. Include public getter methods so the View can read the paddle, ball, bricks, score, and game status.

- Updated my GameModel.java to include the paddle's x/y position

Fill in GameView.java for my Breakout game. It should take a reference to the GameModel and draw everything the player sees: the paddle, the ball, the active bricks, the score, and win/loss messages.

The view should extend JPanel and override paintComponent(Graphics g). Set a preferred size based on the game width and height from the model. Draw the background, then draw only the bricks that are still active. Draw the paddle and ball using the model’s getter methods. Draw the score near the top of the screen.

If the player wins, show a centered “You Win!” message. If the player loses, show a centered “Game Over” message.

The View should only read from the Model. It must never move the paddle, move the ball, change the score, destroy bricks, or change game state. Keep all game logic inside GameModel.

-Updated my GameView.java using in class prompt as guideline

Fill in GameController.java for my Breakout game. Add keyboard controls so the player can move the paddle left and right using the arrow keys. Use keyPressed and keyReleased so movement feels smooth while keys are held down.

Add a Swing Timer to create the game loop. Each timer tick should update the model, including paddle movement, ball movement, collision checks, score updates, and win/loss detection. After updating the model, repaint the view.

The controller should connect the GameModel and GameView together. The Controller should handle input and timing, but it should not directly draw graphics or store the main game state.

Stop the timer when the player wins or loses.

- Updated GameController.java using in class prompt as guideline

Create a separate file called ModelTester.java with a main method. It should create a GameModel, call its methods directly, and print PASS or FAIL for each check. Do not use any testing libraries — use plain Java only.

Write tests for at least five behaviors:

The paddle cannot move past the left edge of the screen.
The paddle cannot move past the right edge of the screen.
The ball bounces correctly off the left wall.
Hitting a brick deactivates the brick and increases the score.
When all bricks are destroyed, the game enters the win state.
When the ball falls below the bottom of the screen, the game enters the lose state.
Each test should clearly print whether it passed or failed.

- Created a ModelTester.java to make sure we've tested at least 5 behaviors.
