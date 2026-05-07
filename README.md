# breakout

This is a Breakout game built in Java using the Swing and MVC design pattern. The player moves a platform at the bottom of the screen using the left and right arrow keys. A ball bounces off the platform and around the screen and breaks bricks when it hits them. The goal is to destroy all the bricks without letting the ball fall under the platform.

The player wins when all bricks are destroyed. The player loses when the ball falls below the screen.

## Model

The Model keeps track of:

The platform
The ball
The bricks
The score
Whether the game is active, won, or lost

The Model handles the game logic, including ball movement, wall collisions, platform collisions, brick collisions, updates score, and win/loss conditions.

## View

The View displays:

The platform
The ball
The bricks
The score
Win or lose messages

The View does not contain the main game logic. It only reads the current state from the Model and draws it on the screen.

## Controller

The Controller handles player input and game timing. It waits for keyboard input to move the platform left and right. It also uses a Swing Timer to update the game repeatedly.

The Controller tells the Model when to move the paddle and update the ball, then tells the View to repaint the screen.

## Done for This Week

The project will be considered done for this week when:

The game window opens successfully
The platform can move left and right using the keyboard
The ball moves automatically
The ball bounces off walls
The ball bounces off the paddle
Bricks disappear when hit by the ball
The score increases when bricks are broken
The player wins when all bricks are destroyed
The player loses when the ball falls below the paddle
The code is organized using MVC
