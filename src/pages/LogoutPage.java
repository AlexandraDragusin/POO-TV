package pages;

import main.Output;
import main.Start;

public final class LogoutPage implements Page {
    /**
     * Execute the logout action: set the current user to null
     *                             set the current page to Homepage unauthenticated
     */
    @Override
    public void doAction() {
        Start.setCurrentUser(null);
        Start.setCurrentPage("Homepage unauthenticated");
        Output.setAddOutput(false);
    }

    @Override
    public void changePage() {
        Start.getCurrentUser().setAccessedPages(null); // pusa acum

        Start.setCurrentUser(null);
        Start.setCurrentPage("Homepage unauthenticated");
        Output.setAddOutput(false);
    }
}
