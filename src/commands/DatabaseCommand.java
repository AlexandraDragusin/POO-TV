package commands;

public final class DatabaseCommand extends Command {
    private final Receiver receiver;

    public DatabaseCommand() {
        this.receiver = CommandFactory.getReceiver();
    }

    @Override
    public void execute() {
        receiver.databaseAction();
    }
}
