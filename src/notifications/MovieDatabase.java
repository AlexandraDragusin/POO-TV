package notifications;

import input.Movie;
import main.Output;
import main.Start;

public final class MovieDatabase {
    /**
     * Add a new movie to the list of movies.
     * If a movie with the same name already exists in the list, an error message is displayed.
     * After adding the movie, it will notify all the observers about the new movie added
     * @throws Error if a movie with the same name already exists in the list
     */
    public void addMovie() {
        for (Movie movie : Start.getMovies()) {
            if (movie.getName().equals(Start.getCurrentAction().getAddedMovie().getName())) {
                Output.setError("Error");
                return;
            }
        }

        Movie newMovie = new Movie(Start.getCurrentAction().getAddedMovie());
        Start.getMovies().add(newMovie);
        Output.setAddOutput(false);

        for (String genreName : newMovie.getGenres()) {
            for (Genre genre : Start.getGenreList()) {
                if (genre.getGenreName().equals(genreName)) {
                    genre.notifyObservers();
                }
            }
        }
    }

    /**
     * Deletes a movie from the list of movies.
     * If a movie with the specified name is found in the list,
     *      it will be removed and observers will be notified.
     * Otherwise, an error message is displayed.
     * @throws Error if a movie with the specified name is not found in the list
     */
    public void deleteMovie() {
        for (Movie movie : Start.getMovies()) {
            if (movie.getName().equals(Start.getCurrentAction().getDeletedMovie())) {

                movie.notifyObservers();

                Start.getMovies().remove(movie);
                Output.setAddOutput(false);

                return;
            }
            Output.setError("Error");
        }
    }
}
