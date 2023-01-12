package pages;

/**
 * Factory class that creates instances of Page objects based on the provided page type
 * pageType string parameter passed to the createPage method
 */
public final class PageFactory {
    private PageFactory() { }

    /**
     * Creates a new Page object based on the pageType parameter.
     * @param pageType the type of Page to create
     * @return the newly created Page object
     *         or null if the type of page is not recognized
     */
    public static Page createPage(final String pageType) {
        return switch (pageType) {
            case "login" -> new LoginPage();
            case "register" -> new RegisterPage();
            case "movies" -> new MoviesPage();
            case "upgrades" -> new UpgradesPage();
            case "see details" -> new SeeDetailsPage();
            case "logout" -> new LogoutPage();
            default -> null;
        };
    }
}
