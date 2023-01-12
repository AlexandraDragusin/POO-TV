package pages;

import input.Credentials;
import input.User;
import main.Output;
import main.Start;

import java.util.ArrayList;

public final class RegisterPage implements Page {
    private Credentials credentials;
    private ArrayList<User> users;

    /**
     * Constructor for RegisterPage class
     */
    public RegisterPage() {
        this.credentials = Start.getCurrentAction().getCredentials();
        this.users = Start.getUsers();
    }

    /**
     * Execute the register action:
     *     if the user is already registered: set the current page to Homepage unauthenticated
     *                                        set the current user to null
     *     otherwise, set the current user to a new user with given credentials
     *                set the current page to Homepage authenticated
     */
    @Override
    public void doAction() {
        for (User user : users) {
            if (user.getCredentials().getName().equals(credentials.getName())) {
                Output.setError("Error");
                Start.setCurrentPage("Homepage unauthenticated");
                Start.setCurrentUser(null);
                return;
            }
        }

        User newUser = new User(credentials);
        users.add(newUser);
        Start.setCurrentUser(newUser);
        Start.setCurrentPage("Homepage authenticated");

        Start.getCurrentUser().setAccessedPages(new ArrayList<>());
        Start.getCurrentUser().getAccessedPages().add("Homepage authenticated");
    }

    /**
     * Execute the change page action to the register page
     */
    @Override
    public void changePage() {
        Start.setCurrentPage("register");
        Output.setAddOutput(false);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }
}
