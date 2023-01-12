package pages;

import input.Action;
import main.Output;
import main.Start;

public final class UpgradesPage implements Page {
    private Action action;

    /**
     * Constructor for UpgradesPage class
     */
    public UpgradesPage() {
        this.action = Start.getCurrentAction();
    }

    /**
     * Execute the wanted action according to the feature
     */
    @Override
    public void doAction() {
        if (action.getFeature().equals("buy tokens")) {
            doBuyTokens();
        } else if (action.getFeature().equals("buy premium account")) {
            doBuyPremiumAccount();
        }
    }

    @Override
    public void changePage() {
        Start.setCurrentPage("upgrades");
        Start.getCurrentUser().getAccessedPages().add("upgrades");
        Output.setAddOutput(false);
    }

    /**
     * Perform buy tokens action: set the new balance and number of tokens for the current user
     */
    public void doBuyTokens() {
        if (Integer.parseInt(Start.getCurrentUser().getCredentials().getBalance())
                >= Integer.parseInt(action.getCount())) {
            int newBalance = Integer.parseInt(Start.getCurrentUser().getCredentials()
                    .getBalance())
                            - Integer.parseInt(action.getCount());
            int newTokens = Start.getCurrentUser().getTokensCount()
                            + Integer.parseInt(action.getCount());

            Start.getCurrentUser().getCredentials().setBalance(Integer.toString(newBalance));
            Start.getCurrentUser().setTokensCount(newTokens);

            Output.setAddOutput(false);
        } else {
            Output.setError("Error");
        }
    }

    /**
     * Perform buy premium account action: decrease the number of tokens for current user
     *                                     set the current user's type account to premium
     */
    public void doBuyPremiumAccount() {
        if (Start.getCurrentUser().getTokensCount() >= PREMIUM_ACCOUNT_PRICE) {
            Start.getCurrentUser().setTokensCount(Start.getCurrentUser().getTokensCount()
                - PREMIUM_ACCOUNT_PRICE);
            Start.getCurrentUser().getCredentials().setAccountType("premium");

            Output.setAddOutput(false);
        } else {
            Output.setError("Error");
        }
    }

    public Action getAction() {
        return action;
    }

    public void setAction(final Action action) {
        this.action = action;
    }

    static final int PREMIUM_ACCOUNT_PRICE = 10;
}
