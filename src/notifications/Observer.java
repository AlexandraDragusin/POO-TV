package notifications;

public interface Observer {
    /**
     * Add a new notification about a movie addition to observer
     * @param movieName the name of the movie that was added
     */
    void updateAdd(String movieName);

    /**
     * Add a new notification about a movie deletion to observer
     * @param movieName the name of the movie that was deleted
     */
    void updateDelete(String movieName);
}
