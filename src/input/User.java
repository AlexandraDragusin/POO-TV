package input;

import main.Output;
import main.Start;
import notifications.Notification;
import notifications.Observer;
import pages.MoviesPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.Comparator;

public final class User implements Observer {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = FREE_PREMIUM_MOVIES;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();
    private final ArrayList<Integer> ratingsOffered = new ArrayList<>();
    private ArrayList<String> accessedPages = null;
    private ArrayList<Notification> notifications = new ArrayList<>();
    private final ArrayList<String> subscribedGenres = new ArrayList<>();

    public User(final Credentials credentials) {
        this.credentials = credentials;
    }

    public User() {

    }

    @Override
    public void updateAdd(final String movieName) {
        Notification notification = new Notification();
        notification.setMessage("ADD");
        notification.setMovieName(movieName);

        notifications.add(notification);
    }

    @Override
    public void updateDelete(final String movieName) {
        Notification notification = new Notification();
        notification.setMessage("DELETE");
        notification.setMovieName(movieName);

        notifications.add(notification);

        if (Start.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
            int currentFreeMovies = Start.getCurrentUser().getNumFreePremiumMovies();
            Start.getCurrentUser().setNumFreePremiumMovies(currentFreeMovies + 1);
        } else {
            int currentTokens = Start.getCurrentUser().getTokensCount();
            Start.getCurrentUser().setTokensCount(currentTokens + 2);
        }

        Start.getCurrentUser().getPurchasedMovies().removeIf(movie -> movie.getName()
                .equals(Start.getCurrentAction().getDeletedMovie()));

        Start.getCurrentUser().getWatchedMovies().removeIf(movie -> movie.getName()
                .equals(Start.getCurrentAction().getDeletedMovie()));

        Start.getCurrentUser().getLikedMovies().removeIf(movie -> movie.getName()
                .equals(Start.getCurrentAction().getDeletedMovie()));

        for (Movie movie : Start.getCurrentUser().getRatedMovies()) {
            if (movie.getName().equals(Start.getCurrentAction().getDeletedMovie())) {
                int movieIndex = Start.getCurrentUser().getRatedMovies().indexOf(movie);
                Start.getCurrentUser().getRatingsOffered().remove(movieIndex);

                Start.getCurrentUser().getRatedMovies().remove(movie);
            }
        }

        Start.getCurrentUser().getRatedMovies().removeIf(movie -> movie.getName()
                .equals(Start.getCurrentAction().getDeletedMovie()));

    }

    /**
     * Create a movie recommendation for the user if he has a premium account
     * otherwise, display a message
     * Add the recommendation to the user's notification list
     * @param output the output class that writes information to nodes
     */
    public void doRecommendation(final Output output) {
        Boolean lastOutput = true;

        if (credentials.getAccountType().equals("premium")) {
            List<Map.Entry<String, Integer>> topGenresList = topGenres();

            ArrayList<Movie> topLikedMovies = topLikedMovies();

            for (Map.Entry<String, Integer> entry : topGenresList) {
                String genre = entry.getKey();

                for (Movie movie : topLikedMovies) {
                    if (movie.getGenres().contains(genre)) {
                        Notification notification = new Notification();
                        notification.setMessage("Recommendation");
                        notification.setMovieName(movie.getName());

                        Start.getCurrentUser().getNotifications().add(notification);

                        Output.setAddOutput(true);
                        Output.setError(null);

                        output.addOutputToNode(lastOutput);
                        return;
                    }
                }
            }
            Notification notification = new Notification();
            notification.setMessage("Recommendation");
            notification.setMovieName("No recommendation");

            Start.getCurrentUser().getNotifications().add(notification);

            Output.setAddOutput(true);
            Output.setError(null);

            output.addOutputToNode(lastOutput);
        }
    }

    /**
     * Create a top with the most appreciated genres of the user
     * @return a list of the top liked genres
     */
    public List<Map.Entry<String, Integer>> topGenres() {
        HashMap<String, Integer> genresMap = new HashMap<String, Integer>();

        for (Movie movie : likedMovies) {
            for (String genre : movie.getGenres()) {
                if (!genresMap.containsKey(genre)) {
                    genresMap.put(genre, 1);
                } else {
                    Integer previousValue = genresMap.get(genre);
                    genresMap.remove(genre);
                    genresMap.put(genre, previousValue + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> topGenresList =
                new LinkedList<Map.Entry<String, Integer>>(genresMap.entrySet());

        topGenresList.sort(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(final Map.Entry<String, Integer> entry1,
                               final Map.Entry<String, Integer> entry2) {

                if (entry2.getValue().equals(entry1.getValue())) {
                    return entry1.getKey().compareTo(entry2.getKey());
                }

                return entry2.getValue().compareTo(entry1.getValue());
            }
        });

        return topGenresList;
    }

    /**
     * Create a top with the most appreciated movies that exists on the movies platform
     * and are visible for the user
     * @return a list that contains the most liked movies
     */
    public ArrayList<Movie> topLikedMovies() {
        ArrayList<Movie> topVisibleLikedMovies = new ArrayList<>();

        for (Movie movie : MoviesPage.getVisibleMovies()) {
            if (!Start.getCurrentUser().getWatchedMovies().contains(movie)) {
                topVisibleLikedMovies.add(movie);
            }
        }

        topVisibleLikedMovies.sort(new Comparator<Movie>() {
            public int compare(final Movie movie1, final Movie movie2) {
                return movie2.getNumLikes() - movie1.getNumLikes();
            }
        });

        return topVisibleLikedMovies;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public ArrayList<String> getAccessedPages() {
        return accessedPages;
    }

    public void setAccessedPages(final ArrayList<String> accessedPages) {
        this.accessedPages = accessedPages;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }

    public ArrayList<Integer> getRatingsOffered() {
        return ratingsOffered;
    }

    static final int FREE_PREMIUM_MOVIES = 15;
}
