package pages;

import main.Output;
import main.Start;
import input.Credentials;
import input.User;

import java.util.ArrayList;

public final class LoginPage implements Page {
    private Credentials credentials;
    private ArrayList<User> users;

    /**
     * Constructor for LoginPage class.
     */
    public LoginPage() {
        this.credentials = Start.getCurrentAction().getCredentials();
        this.users = Start.getUsers();
    }

    /**
     * Execute the login action: set the current user if it exists in the list of registered users
     *                           and the current page to Homepage authenticated
     *                           otherwise, set the current page to Homepage unauthenticated
     */
    @Override
    public void doAction() {
        for (User user : users) {
            if (user.getCredentials().getName().equals(credentials.getName())
                    && user.getCredentials().getPassword().equals(credentials.getPassword())) {
                Start.setCurrentUser(user);
                Start.setCurrentPage("Homepage authenticated");

                Start.getCurrentUser().setAccessedPages(new ArrayList<>());
                Start.getCurrentUser().getAccessedPages().add("Homepage authenticated");
                return;
            }
        }

        Output.setError("Error");
        Start.setCurrentPage("Homepage unauthenticated");
    }

    /**
     * Execute the change page action to the login page
     */
    @Override
    public void changePage() {
        Start.setCurrentPage("login");
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
