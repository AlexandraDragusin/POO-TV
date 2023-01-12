package pages;

import input.Action;
import input.Movie;
import main.Start;

import java.util.ArrayList;
import java.util.Comparator;

public final class MoviesPage implements Page {
    private ArrayList<Movie> movies;
    private Action action;
    private static ArrayList<Movie> filteredMovies;

    /**
     * Constructor for MoviesPage class
     */
    public MoviesPage() {
        this.movies = Start.getMovies();
        this.action = Start.getCurrentAction();
        filteredMovies = getVisibleMovies();
    }

    /**
     * Execute the action according to the feature
     */
    @Override
    public void doAction() {
        if (action.getFeature().equals("search")) {
            doSearch();
        } else if (action.getFeature().equals("filter")) {
            doFilter();
        }
    }

    @Override
    public void changePage() {
        Start.setCurrentPage("movies");
        Start.setCurrentMovieList(getVisibleMovies());

        Start.getCurrentUser().getAccessedPages().add("movies");
    }

    /**
     * Perform search action
     */
    public void doSearch() {
        filteredMovies = new ArrayList<>();

        for (Movie movie : getVisibleMovies()) {
            if (movie.getName().startsWith(action.getStartsWith())) {
                filteredMovies.add(movie);
            }
        }
    }

    /**
     * Perform filter action
     */
    public void doFilter() {
        filteredMovies = new ArrayList<>();

        if (action.getFilters().getSort() != null) {
            filteredMovies = sortFilter();
        }

        if (action.getFilters().getContains() != null) {
            filteredMovies = containsFilter();
        }
    }

    /**
     * Extract the movies containing the desired actors and genres
     * from the list of movies visible to the current user
     * @return a list of movies that contains the desired actors and/or genres
     */
    public ArrayList<Movie> containsFilter() {
        ArrayList<Movie> movieList = getVisibleMovies();

        if (action.getFilters().getContains().getActors() != null) {
            for (Movie movie : getVisibleMovies()) {
                for (String actor : action.getFilters().getContains().getActors()) {
                    if (!movie.getActors().contains(actor)) {
                        movieList.remove(movie);
                    }
                }
            }
        }

        if (action.getFilters().getContains().getGenre() != null) {
            for (Movie movie : getVisibleMovies()) {
                for (String genre : action.getFilters().getContains().getGenre()) {
                    if (!movie.getGenres().contains(genre)) {
                        movieList.remove(movie);
                    }
                }
            }
        }

        return movieList;
    }

    /**
     * Sort the list of movies visible to the current user by rating or/and duration
     * @return the sorted list of movies
     */
    public ArrayList<Movie> sortFilter() {
        ArrayList<Movie> visibileMovies = getVisibleMovies();

        // sort by rating
        if (action.getFilters().getSort().getRating() != null) {
            if (action.getFilters().getSort().getRating().equals("increasing")) {
                visibileMovies.sort(new Comparator<Movie>() {
                    @Override
                    public int compare(final Movie movie1, final Movie movie2) {
                        return Double.compare(movie1.getRating(), movie2.getRating());
                    }
                });
            } else if (action.getFilters().getSort().getRating().equals("decreasing")) {
                visibileMovies.sort(new Comparator<>() {
                    @Override
                    public int compare(final Movie movie1, final Movie movie2) {
                        return Double.compare(movie2.getRating(), movie1.getRating());
                    }
                });
            }
        }

        // sort by duration
        if (action.getFilters().getSort().getDuration() != null) {
            if (action.getFilters().getSort().getDuration().equals("increasing")) {
                visibileMovies.sort(new Comparator<Movie>() {
                    @Override
                    public int compare(final Movie movie1, final Movie movie2) {
                        return movie1.getDuration() - movie2.getDuration();
                    }
                });
            } else if (action.getFilters().getSort().getDuration().equals("decreasing")) {
                visibileMovies.sort(new Comparator<Movie>() {
                    @Override
                    public int compare(final Movie movie1, final Movie movie2) {
                        return movie2.getDuration() - movie1.getDuration();
                    }
                });
            }
        }

        return visibileMovies;
    }

    /**
     * Extract the movies that are visible to current user
     * @return the list of movies visible to the current user
     */
    public static ArrayList<Movie> getVisibleMovies() {
        ArrayList<Movie> visibleMovies = new ArrayList<>();

        for (Movie movie : Start.getMovies()) {
            if (!movie.getCountriesBanned().contains(Start.getCurrentUser().getCredentials()
                    .getCountry())) {
                visibleMovies.add(movie);
            }
        }

        return visibleMovies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(final Action action) {
        this.action = action;
    }

    public static ArrayList<Movie> getFilteredMovies() {
        return filteredMovies;
    }
}
