package main;

import commands.Command;
import commands.CommandFactory;
import commands.Invoker;
import commands.Receiver;
import input.Input;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Movie;
import input.User;
import notifications.Genre;

import java.util.ArrayList;

public final class Start {
    private static Start instance = null;
    private Output output;
    private Receiver receiver;
    private static ArrayList<User> users;
    private static ArrayList<Movie> movies;
    private static ArrayList<Movie> currentMovieList;
    private static User currentUser;
    private static String currentPage;
    private static Action currentAction;
    private static ArrayList<Genre> genreList;
    private Command command;

    private Start() {
    }

    /**
     * Returns a singleton instance of the Invoker class.
     * If an instance of the class has not been created yet, a new one will be created.
     * @return the singleton instance of the Invoker class.
     */
    public static Start getInstance() {
        if (instance == null) {
            instance = new Start();
        }
        return instance;
    }

    /**
     * Set the list of users and the list of movies
     * @param input contains the information extracted from input
     */
    public void doStart(final Input input, final ArrayNode outputArrayNode) {
        users = input.getUsers();
        movies = input.getMovies();

        this.output = new Output(outputArrayNode);
        receiver = new Receiver();
        genreList = new ArrayList<>();
    }

    /**
     * Create a command according to the action type and execute it
     * Add the output to the array node
     * @param action the current action
     */
    public void executeCommand(final Action action) {
        reset();
        currentAction = action;

        CommandFactory.setReceiver(receiver);
        command = CommandFactory.createCommand(currentAction.getType());

        Invoker invoker = new Invoker(command);
        invoker.execute();

        output.addOutputToNode(false);
    }

    /**
     * Give recommendation if a user is connected
     */
    public void executeRecommendation() {
        if (Start.getCurrentUser() != null) {
            Start.getCurrentUser().doRecommendation(output);
        }
    }

    /**
     * Reset error, addOutput and currentMovieList params for a new action
     */
    public void reset() {
        Output.setError(null);
        Output.setAddOutput(true);
        currentMovieList = null;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
       Start.users = users;
    }

    public static ArrayList<Movie> getMovies() {
        return movies;
    }

    public static void setMovies(final ArrayList<Movie> movies) {
        Start.movies = movies;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(final User currentUser) {
        Start.currentUser = currentUser;
    }

    public static String getCurrentPage() {
        return currentPage;
    }

    public static void setCurrentPage(final String currentPage) {
        Start.currentPage = currentPage;
    }

    public static Action getCurrentAction() {
        return currentAction;
    }

    public static ArrayList<Movie> getCurrentMovieList() {
        return currentMovieList;
    }

    public static void setCurrentMovieList(final ArrayList<Movie> currentMovieList) {
        Start.currentMovieList = currentMovieList;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(final Output output) {
        this.output = output;
    }

    public static ArrayList<Genre> getGenreList() {
        return genreList;
    }
}
