import game.Game;

/**
 * The main class for running the Ass5Game application.
 * This class contains the entry point (main method) that initializes the game,
 * sets up the game environment, and runs the game loop.
 */
public class Ass5Game {

    /**
     * The main method to run the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
