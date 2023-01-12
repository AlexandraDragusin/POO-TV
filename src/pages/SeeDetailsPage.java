package pages;

import input.Action;
import input.Movie;
import main.Output;
import main.Start;
import notifications.Genre;

import java.util.ArrayList;

public final class SeeDetailsPage implements Page {
    private Action action;
    private static Movie currentMovie;

    /**
     * Constructor for SeeDetailsPage class
     */
    public SeeDetailsPage() {
        this.action = Start.getCurrentAction();
    }

    /**
     * Execute the action according to the feature
     */
    @Override
    public void doAction() {
        switch (action.getFeature()) {
            case "purchase" -> doPurchaseMovie();
            case "watch" -> doWatchMovie();
            case "like" -> doLikeMovie();
            case "rate" -> doRateMovie();
            case "subscribe" -> doSubscribeMovie();
            default -> { }
        }
    }

    @Override
    public void changePage() {
        currentMovie = null;
        Start.setCurrentMovieList(seeMovieDetails());
    }

    /**
     * Find a specific movie in the MoviesPage
     * @return the movie for which the details are found, if the movie exists in the page
     *         an empty list, otherwise
     */
    public static ArrayList<Movie> seeMovieDetails() {
        ArrayList<Movie> movieDetails = new ArrayList<>();

        for (Movie movie : MoviesPage.getFilteredMovies()) {
            if (movie.getName().equals(Start.getCurrentAction().getMovie())) {
                movieDetails.add(movie);
                SeeDetailsPage.currentMovie = movie;
            }
        }

        if (!movieDetails.isEmpty()) {
            Start.setCurrentPage("see details");
            Start.getCurrentUser().getAccessedPages().add("see details");
        } else {
            Output.setError("Error");
        }

        return movieDetails;
    }

    /**
     * Execute the purchase action
     */
    public void doPurchaseMovie() {
        if (!Start.getCurrentUser().getPurchasedMovies().contains(currentMovie)) {
            if (Start.getCurrentUser().getCredentials().getAccountType().equals("premium")
                    && (Start.getCurrentUser().getNumFreePremiumMovies() > 0)) {
                Start.getCurrentUser().getPurchasedMovies().add(currentMovie);
                Start.getCurrentUser().setNumFreePremiumMovies(Start.getCurrentUser()
                        .getNumFreePremiumMovies() - 1);
            } else {
                Start.getCurrentUser().getPurchasedMovies().add(currentMovie);
                Start.getCurrentUser()
                        .setTokensCount(Start.getCurrentUser().getTokensCount() - 2);
            }

            currentMovie.getObservers().add(Start.getCurrentUser());
        } else {
            Output.setError("Error");
        }
    }

    /**
     * Execute the watch action:
     * if the user has already purchased the movie, add it to the user's list of purchased movies
     */
    public void doWatchMovie() {
        if (Start.getCurrentUser().getPurchasedMovies().contains(currentMovie)) {
            if (!Start.getCurrentUser().getWatchedMovies().contains(currentMovie)) {
                Start.getCurrentUser().getWatchedMovies().add(currentMovie);
            }
        } else {
            Output.setError("Error");
        }
    }

    /**
     * Execute the like action:
     * if the user has already watched the movie, add it to the user's list of liked movies
     *                                            increment the movie's number of likes
     */
    public void doLikeMovie() {
        if (Start.getCurrentUser().getWatchedMovies().contains(currentMovie)
                && !Start.getCurrentUser().getLikedMovies().contains(currentMovie)) {
            Start.getCurrentUser().getLikedMovies().add(currentMovie);

            int index = Start.getMovies().indexOf(currentMovie);
            int numLikes = Start.getMovies().get(index).getNumLikes();
            Start.getMovies().get(index).setNumLikes(numLikes + 1);
        } else {
            Output.setError("Error");
        }
    }

    /**
     * Execute the rate action:
     * if the user has already watched the movie, add it to the user's list of rated movies
     *                                            increment the movie's number of ratings
     *                                            calculate and set the new rating for the movie
     */
    public void doRateMovie() {
        if (Start.getCurrentUser().getWatchedMovies().contains(currentMovie)
                && action.getRate() <= MAXIMUM_RATE)  {

            int index = Start.getMovies().indexOf(currentMovie);
            int numRatings = Start.getMovies().get(index).getNumRatings();

            if (!Start.getCurrentUser().getRatedMovies().contains(currentMovie)) {
                Start.getCurrentUser().getRatedMovies().add(currentMovie);
                Start.getCurrentUser().getRatingsOffered().add(action.getRate());

                Start.getMovies().get(index).setNumRatings(numRatings + 1);
            } else {
                int movieIndex = Start.getCurrentUser().getRatedMovies().indexOf(currentMovie);
                Start.getCurrentUser().getRatingsOffered().remove(movieIndex);
                Start.getCurrentUser().getRatingsOffered().add(movieIndex, action.getRate());

                Start.getMovies().get(index).getRatings().remove(movieIndex);
            }

            Start.getMovies().get(index).getRatings().add(action.getRate());

            if (Start.getMovies().get(index).getRatings() != null) {
                double rating = 0;

                for (int i : Start.getMovies().get(index).getRatings()) {
                    rating += i;
                }

                int size = Start.getMovies().get(index).getRatings().size();
                Start.getMovies().get(index).setRating(rating / size);
            }

        } else {
            Output.setError("Error");
        }
    }

    /**
     * Subscribes a user to a specific genre of movies
     * If the user has not already subscribed to the genre and the movie being viewed has
     * the genre, the user will be subscribed to that genre and registered to the genre's
     * observer list
     * If there is no observer list for the genre, a new one will be created
     */
    public void doSubscribeMovie() {
        if (!Start.getCurrentUser().getSubscribedGenres().contains(action.getSubscribedGenre())) {

            if (currentMovie.getGenres().contains(action.getSubscribedGenre())) {

                Start.getCurrentUser().getSubscribedGenres().add(Start.getCurrentAction()
                        .getSubscribedGenre());

                if (!Start.getGenreList().isEmpty()) {
                    for (Genre genre : Start.getGenreList()) {
                        if (genre.getGenreName().equals(Start.getCurrentAction()
                                .getSubscribedGenre())) {
                            genre.register(Start.getCurrentUser());
                            return;
                        }
                    }

                } else {
                    Genre genre = new Genre(Start.getCurrentAction().getSubscribedGenre());
                    genre.register(Start.getCurrentUser());
                    Start.getGenreList().add(genre);
                }

                Output.setAddOutput(false);
            }

        } else {
            Output.setError("Error");
        }
    }

    public Action getAction() {
        return action;
    }

    public void setAction(final Action action) {
        this.action = action;
    }

    public static Movie getCurrentMovie() {
        return currentMovie;
    }

    static final int MAXIMUM_RATE = 5;
}
