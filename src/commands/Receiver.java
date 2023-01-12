package commands;

import main.Output;
import main.Start;
import notifications.MovieDatabase;
import pages.MoviesPage;
import pages.Page;
import pages.PageFactory;
import pages.SeeDetailsPage;

import java.util.ArrayList;

public final class Receiver {
    /**
     * Implementation for the change page action for the current page
     */
    public void changePageAction() {
        switch (Start.getCurrentPage()) {
            case "Homepage unauthenticated":
                if (Start.getCurrentAction().getPage().equals("login")
                        || Start.getCurrentAction().getPage().equals("register")) {

                    Page page = PageFactory.createPage(Start.getCurrentAction().getPage());
                    page.changePage();

                } else {
                    Output.setError("Error");
                }
                break;
            case "see details":
            case "Homepage authenticated":
                if (Start.getCurrentAction().getPage().equals("movies")
                        || Start.getCurrentAction().getPage().equals("upgrades")
                        || Start.getCurrentAction().getPage().equals("logout")) {

                    Page page = PageFactory.createPage(Start.getCurrentAction().getPage());
                    page.changePage();
                } else {
                    Output.setError("Error");
                }
                break;
            case "upgrades":
            case "movies":
                if (Start.getCurrentAction().getPage().equals("movies")
                        || Start.getCurrentAction().getPage().equals("see details")
                        || Start.getCurrentAction().getPage().equals("logout")) {

                    Page page = PageFactory.createPage(Start.getCurrentAction().getPage());
                    page.changePage();
                } else {
                    Output.setError("Error");
                }
                break;
            default:
        }
    }

    /**
     * Implementation for the on page action for the current page
     */
    public void onPageAction() {
        if (Start.getCurrentPage().equals("login")
                && Start.getCurrentAction().getFeature().equals("login")) {

            Page loginPage = PageFactory.createPage("login");
            loginPage.doAction();

        } else if (Start.getCurrentPage().equals("register")
                && Start.getCurrentAction().getFeature().equals("register")) {

            Page registerPage = PageFactory.createPage("register");
            registerPage.doAction();

        } else if (Start.getCurrentPage().equals("movies")
                && (Start.getCurrentAction().getFeature().equals("search")
                || Start.getCurrentAction().getFeature().equals("filter"))) {

            Page moviesPage = PageFactory.createPage("movies");
            moviesPage.doAction();
            Start.setCurrentMovieList(MoviesPage.getFilteredMovies());

        } else if (Start.getCurrentPage().equals("upgrades")
                && (Start.getCurrentAction().getFeature().equals("buy premium account")
                || Start.getCurrentAction().getFeature().equals("buy tokens"))) {

            Page upgradesPage = PageFactory.createPage("upgrades");
            upgradesPage.doAction();

        } else if (Start.getCurrentPage().equals("see details")) {

            Page seeDetailsPage = PageFactory.createPage("see details");
            seeDetailsPage.doAction();

            Start.setCurrentMovieList(new ArrayList<>());
            Start.getCurrentMovieList().add(SeeDetailsPage.getCurrentMovie());

        } else {
            Output.setError("Error");
        }
    }

    /**
     * Implementation for the back action
     */
    public void backAction() {
        if (Start.getCurrentUser() != null && Start.getCurrentUser().getAccessedPages() != null
                && Start.getCurrentUser().getAccessedPages().size() >= 2) {

            int delIndex = Start.getCurrentUser().getAccessedPages().size() - 1;
            Start.getCurrentUser().getAccessedPages().remove(delIndex);

            int pageIndex = Start.getCurrentUser().getAccessedPages().size() - 1;
            String pageName = Start.getCurrentUser().getAccessedPages().get(pageIndex);

            if (pageName.equals("Homepage authenticated")) {
                Start.setCurrentPage(pageName);
                Output.setAddOutput(false);
            } else {
                Page page = PageFactory.createPage(pageName);
                page.changePage();
            }

            Start.getCurrentUser().getAccessedPages().remove(pageIndex);
        } else {
            Output.setError("Error");
        }
    }

    /**
     * Implementation for database action
     */
    public void databaseAction() {
        MovieDatabase database = new MovieDatabase();

        if (Start.getCurrentAction().getFeature().equals("add")) {
            database.addMovie();
        } else if (Start.getCurrentAction().getFeature().equals("delete")) {
            database.deleteMovie();
        }
    }
}
