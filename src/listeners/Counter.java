package listeners;


/**
 * The {@code Counter} class is a simple utility for keeping track of a numerical count.
 * It supports incrementing, decrementing, and retrieving the current count value.
 */
public class Counter {
    private int counter;

    /**
     * Constructs a new {@code Counter} with an initial value of 0.
     */
    public Counter() {
        counter = 0;
    }


    /**
     * Adds the specified number to the current count.
     *
     * @param number the number to add
     */
    public void increase(int number) {
        counter += number;
    }

    /**
     * Subtracts the specified number from the current count.
     *
     * @param number the number to subtract
     */
    public void decrease(int number) {
        counter -= number;
    }

    /**
     * Returns the current count value.
     *
     * @return the current count
     */
    public int getValue() {
        return counter;
    }
}