package pages;

public interface Page {
    /**
     * Execute the specific action for the page
     */
    void doAction();

    /**
     * Execute change page action to the current page
     */
    void changePage();
}
