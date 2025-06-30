package listeners;

/**
 * The HitNotifier interface should be implemented by any class
 * that can register and notify HitListener objects of hit events.
 */
public interface HitNotifier {

    /**
     * Adds a HitListener to the list of listeners that will be notified
     * when a hit event occurs.
     *
     * @param hl the HitListener to add
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a HitListener from the list of listeners
     * so it will no longer receive hit event notifications.
     *
     * @param hl the HitListener to remove
     */
    void removeHitListener(HitListener hl);
}
