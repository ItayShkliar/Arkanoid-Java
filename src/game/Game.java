package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import geometry.Point;
import geometry.Rectangle;
import listeners.Counter;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import listeners.HitListener;
import sprites.Ball;
import sprites.Block;
import sprites.Sprite;
import sprites.ScoreIndicator;
import sprites.SpriteCollection;
import sprites.Paddle;
import physics.Collidable;


import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 * The Game class manages the overall game environment, including all sprites,
 * collidables, and the game loop. It initializes the game objects and runs
 * the game with a consistent frame rate, drawing sprites and handling
 * collisions between them.
 */
public class Game {
    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private GUI gui;
    private final Counter remainingBlocks;
    private final Counter remainingBalls;
    private final Counter score;

    /**
     * Constructs a new Game with an empty sprite collection and collision environment.
     */
    public Game() {
        sprites = new SpriteCollection();
        environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = new Counter();
    }

    /**
     * Adds a collidable object to the game environment so it can be involved in collisions.
     *
     * @param c the Collidable to add
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the game so it will be drawn and notified of time updates.
     *
     * @param s the Sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Initializes the game by creating all game objects: balls, blocks, paddle, and borders.
     * Adds them to the game so they will be drawn and take part in collisions.
     *
     * <p>The setup includes:
     * <ul>
     *   <li>Two black balls with velocity (5, 5)</li>
     *   <li>Four border blocks around the screen edges</li>
     *   <li>A player-controlled paddle</li>
     *   <li>Multiple rows of colored blocks arranged in decreasing count per row</li>
     * </ul>
     */
    public void initialize() {
        this.gui = new GUI("Ass5Game", 800, 600);

        //2 balls
        Ball ball1 = new Ball(400, 500, 5, Color.black, this.environment);
        ball1.setVelocity(3, 3);
        ball1.addToGame(this);
        this.remainingBalls.increase(1);
        Ball ball2 = new Ball(350, 500, 5, Color.black, this.environment);
        ball2.setVelocity(3, 3);
        this.remainingBalls.increase(1);
        ball2.addToGame(this);
        Ball ball3 = new Ball(450, 500, 5, Color.black, this.environment);
        ball3.setVelocity(3, 3);
        this.remainingBalls.increase(1);
        ball3.addToGame(this);

        //borders
        Color brown = new Color(40, 46, 60);
        Block b1 = new Block(new Rectangle(new Point(0, 0), 25, 600), brown, 2);
        Block b2 = new Block(new Rectangle(new Point(0, 0), 800, 25), brown, 1);
        Block b3 = new Block(new Rectangle(new Point(775, 0), 25, 600), brown, 2);
        b1.addToGame(this);
        b2.addToGame(this);
        b3.addToGame(this);

        //death zone
        Block doom = new Block(new Rectangle(new Point(0, 575), 800, 25), Color.lightGray, 1);
        doom.addHitListener(new BallRemover(this, remainingBalls));
        doom.addToGame(this);

        //score
        ScoreTrackingListener scoreTracking = new ScoreTrackingListener(this.score);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);

        //paddle
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        Paddle paddle = new Paddle(keyboard, new Rectangle(new Point(370, 560),
                125, 30), new Color(152, 136, 41), 7);
        paddle.addToGame(this);

        HitListener blockRemover = new BlockRemover(this, remainingBlocks);
        //blocks
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(new Color(57, 53, 88));
        colors.add(new Color(100, 102, 159));
        colors.add(new Color(255, 204, 138));
        colors.add(new Color(255, 159, 128));
        colors.add(new Color(230, 135, 124));
        colors.add(new Color(211, 118, 132));

        /*int heightB = 50;
        for (int i = 12; i > 6; i--) {
            //Color c = new Color(255 - 15 * i, 255 - 19 * i, 3 * i);
            Color c = colors.get(12 - i);
            for (int j = 0; j < 10; j++) {
                Block b = new Block(new Rectangle(new Point(715 - j * 75, heightB),
                        50, 25), c);
                b.addHitListener(blockRemover);
                b.addHitListener(scoreTracking);
                b.addToGame(this);
                this.remainingBlocks.increase(1);
            }
            heightB += 40;
        }*/

        int blockWidth = 50;
        int blockHeight = 25;
        int spacingX = 75;
        int spacingY = 35;
        int startX = 25; // (10 blocks × 75 = 750; (800 - 750)/2 = 25)
        int startY = 50;

        boolean[][] spiral = {
                {true, true, true, true, true, true, true, true, true, true},   // Row 0
                {true, false, false, false, false, false, false, false, false, true},
                {true, false, true, true, true, true, true, false, false, true},
                {true, false, true, false, false, false, true, false, false, true},
                {true, false, true, false, true, true, true, false, false, true},
                {true, false, true, false, true, false, false, false, false, true},
                {true, false, true, true, true, true, true, true, false, true},
                {true, true, true, true, true, true, true, true, true, true}
        };

        for (int i = 0; i < spiral.length; i++) {
            Color c = colors.get(i % colors.size()); // cycle through colors
            for (int j = 0; j < spiral[i].length; j++) {
                if (!spiral[i][j]) {
                    continue;
                }

                int x = startX + j * spacingX;
                int y = startY + i * spacingY;

                Block b = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight), c);
                b.addHitListener(blockRemover);
                b.addHitListener(scoreTracking);
                b.addToGame(this);
                this.remainingBlocks.increase(1);
            }
        }
    }

    /**
     * Runs the game loop: repeatedly draws all sprites and updates their state.
     *
     * <p>The loop runs at a fixed frame rate of 60 frames per second. In each frame:
     * <ul>
     *   <li>The background is drawn</li>
     *   <li>All sprites are drawn onto the surface</li>
     *   <li>The surface is shown on the GUI</li>
     *   <li>All sprites are notified that time has passed</li>
     * </ul>
     * The method uses {@code Sleeper} to ensure consistent frame timing.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();

        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            //d.setColor(new Color(80, 92, 124));
            //d.fillRectangle(0, 0, 800, 600);
            Image img = Toolkit.getDefaultToolkit().getImage("assets/air_temple.jpg");
            d.drawImage(-200, -20, img);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }

            if (this.remainingBlocks.getValue() == 0 || this.remainingBalls.getValue() == 0) {
                if (this.remainingBlocks.getValue() == 0) {
                    this.score.increase(100);
                    System.out.println("You Win!\nYour score is: " + score.getValue());
                } else {
                    System.out.println("Game Over.\nYour score is: " + score.getValue());
                }
                gui.close();
                return;
            }
        }
    }

    /**
     * Removes a {@link Collidable} object from the game environment.
     *
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.getCollidables().remove(c);
    }

    /**
     * Removes a {@link Sprite} object from the game.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSprites().remove(s);
    }
}