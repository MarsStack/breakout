"I'm building breakout in Java using Swing and MVC. Here's my spec: # README.md
Create the three class shells — GameModel.java, GameView.java, GameController.java — with method stubs based on this design."

- Created the Game Controller, Model, and View java files.

Fill in GameModel.java for my Breakout game. The model should track: the paddle’s x/y position, width, height, and speed; the ball’s x/y position, size, x/y velocity; a grid of bricks with rows and columns; which bricks are still active; the score; and whether the game is running, won, or lost.

Add logic to: move the paddle left and right while keeping it inside the game window, update the ball position each tick, bounce the ball off the left, right, and top walls, bounce the ball off the paddle, detect collisions between the ball and active bricks, deactivate bricks when hit, increase the score when a brick is destroyed, detect when all bricks are gone and set the win state, and detect when the ball falls below the bottom of the screen and set the lose state.

Do not use Swing imports in GameModel.java. Keep this class focused only on game state and game logic. Include public getter methods so the View can read the paddle, ball, bricks, score, and game status.

- Updated my GameModel.java to include the paddle's x/y position
