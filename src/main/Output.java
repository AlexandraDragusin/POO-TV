package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Movie;
import input.User;
import notifications.Notification;

import java.util.ArrayList;

public final class Output {
    private static boolean addOutput;
    private static String error;
    private final ArrayNode outputArrayNode;

    public Output(final ArrayNode outputArrayNode) {
        this.outputArrayNode = outputArrayNode;
    }

    /**
     * Add the output information of the current action to the node
     * @param lastOutput boolean that say if is the last output added or not
     */
    public void addOutputToNode(final Boolean lastOutput) {
        if (addOutput) {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode node = objectMapper.createObjectNode();

                node.put("error", error);

                ObjectMapper newObjectMapper = new ObjectMapper();

                if (error == null) {
                    if (lastOutput) {
                        node.put("currentMoviesList", (JsonNode) null);
                    } else {
                        ArrayNode movieArrayNode = createMovieArrayNode(newObjectMapper,
                                Start.getCurrentMovieList());
                        node.put("currentMoviesList", movieArrayNode);
                    }

                    ObjectNode currentUser = createUserNode(newObjectMapper,
                            Start.getCurrentUser());
                    node.put("currentUser", currentUser);
                } else {
                    ArrayNode movieArrayNode = createMovieArrayNode(newObjectMapper, null);
                    node.put("currentMoviesList", movieArrayNode);
                    node.put("currentUser", (com.fasterxml.jackson.databind.JsonNode) null);
                }

                outputArrayNode.add(node);
        }
    }

    /**
     * Create an object node that contains information about a specific user
     * @param objectMapper object mapper
     * @param user the user that want to be added to the object node
     * @return an object node
     */
    public ObjectNode createUserNode(final ObjectMapper objectMapper, final User user) {
        if (user != null) {
            ObjectNode userNode = objectMapper.createObjectNode();

            ObjectNode credentialsNode = objectMapper.createObjectNode();
            credentialsNode.put("name", user.getCredentials().getName());
            credentialsNode.put("password", user.getCredentials().getPassword());
            credentialsNode.put("accountType", user.getCredentials().getAccountType());
            credentialsNode.put("country", user.getCredentials().getCountry());
            credentialsNode.put("balance", user.getCredentials().getBalance());

            userNode.put("credentials", credentialsNode);
            userNode.put("tokensCount", user.getTokensCount());
            userNode.put("numFreePremiumMovies", user.getNumFreePremiumMovies());

            ObjectMapper newObjectMapper = new ObjectMapper();
            ArrayNode purchasedMovies = createMovieArrayNode(newObjectMapper,
                    user.getPurchasedMovies());
            userNode.put("purchasedMovies", purchasedMovies);

            ArrayNode watchedMovies = createMovieArrayNode(newObjectMapper,
                    user.getWatchedMovies());
            userNode.put("watchedMovies", watchedMovies);

            ArrayNode likedMovies = createMovieArrayNode(newObjectMapper, user.getLikedMovies());
            userNode.put("likedMovies", likedMovies);

            ArrayNode ratedMovies = createMovieArrayNode(newObjectMapper, user.getRatedMovies());
            userNode.put("ratedMovies", ratedMovies);

            ArrayNode notifications = createNotificationsArrayNode(newObjectMapper,
                    user.getNotifications());
            userNode.put("notifications", notifications);

            return userNode;
        }

        return null;
    }

    /**
     * Create an array node that contains notifications
     * @param objectMapper object mapper
     * @param notificationList the list of notifications that want to be added to the array node
     * @return an array node
     */
    public ArrayNode createNotificationsArrayNode(final ObjectMapper objectMapper,
                                                   final ArrayList<Notification> notificationList) {
        ArrayNode arrayNode = objectMapper.createArrayNode();

        if (notificationList != null) {
            for (Notification notification : notificationList) {
                ObjectNode notificationNode = objectMapper.createObjectNode();

                notificationNode.put("movieName", notification.getMovieName());
                notificationNode.put("message", notification.getMessage());

                arrayNode.add(notificationNode);
            }
        }

        return arrayNode;
    }

    /**
     * Create an object node that contains information about a specific movie
     * @param objectMapper object mapper
     * @param movie the movie that want to be added to the object node
     * @return an object node
     */
    public ObjectNode createMovieNode(final ObjectMapper objectMapper, final Movie movie) {
        ObjectNode movieNode = objectMapper.createObjectNode();

        movieNode.put("name", movie.getName());
        movieNode.put("year", movie.getYear());
        movieNode.put("duration", movie.getDuration());

        ArrayNode genres = movieNode.putArray("genres");
        for (String genre : movie.getGenres()) {
            genres.add(genre);
        }

        ArrayNode actors = movieNode.putArray("actors");
        for (String actor : movie.getActors()) {
            actors.add(actor);
        }

        ArrayNode countries = movieNode.putArray("countriesBanned");
        for (String country : movie.getCountriesBanned()) {
            countries.add(country);
        }

        movieNode.put("numLikes", movie.getNumLikes());
        movieNode.put("rating", movie.getRating());
        movieNode.put("numRatings", movie.getNumRatings());

        return movieNode;
    }

    /**
     * Create an array node that contains movies
     * @param objectMapper object mapper
     * @param moviesList the list of movies that want to be added to the array node
     * @return an array node
     */
    public ArrayNode createMovieArrayNode(final ObjectMapper objectMapper,
                                          final ArrayList<Movie> moviesList) {
        ArrayNode arrayNode = objectMapper.createArrayNode();

        if (moviesList != null) {
            for (Movie movie : moviesList) {
                ObjectNode movieNode = createMovieNode(objectMapper, movie);
                arrayNode.add(movieNode);
            }
        }

        return arrayNode;
    }

    public static void setAddOutput(final boolean addOutput) {
        Output.addOutput = addOutput;
    }

    public static String getError() {
        return error;
    }

    public static void setError(final String error) {
        Output.error = error;
    }
}
