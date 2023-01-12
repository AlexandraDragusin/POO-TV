package commands;

/**
 * Factory class that creates instances of Command objects based on the provided command type
 * commandType string parameter passed to the createCommand method
 */
public final class CommandFactory {
    private static Receiver receiver;

    private CommandFactory() { }

    /**
     * Creates a new Command object based on the commandType parameter.
     * @param commandType the type of Command to create
     * @return the newly created Command object
     *         or null if the type of command is not recognized
     */
    public static Command createCommand(final String commandType) {
        return switch (commandType) {
            case "on page" -> new OnPageCommand();
            case "change page" -> new ChangePageCommand();
            case "back" -> new BackCommand();
            case "database" -> new DatabaseCommand();
            default -> null;
        };
    }

    public static void setReceiver(final Receiver receiver) {
        CommandFactory.receiver = receiver;
    }

    public static Receiver getReceiver() {
        return receiver;
    }
}
