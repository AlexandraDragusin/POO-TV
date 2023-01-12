package notifications;

public interface Subject {
    /**
     * Registers an observer object to the subject.
     * @param object observer object to be registered.
     */
    void register(Observer object);

    /**
     * Notifies all the registered observers about the change in the current object's state.
     */
    void notifyObservers();
}
