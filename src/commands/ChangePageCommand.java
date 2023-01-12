package commands;

public final class ChangePageCommand extends Command {
    private final Receiver receiver;

    public ChangePageCommand() {
        this.receiver = CommandFactory.getReceiver();
    }

    @Override
    public void execute() {
        receiver.changePageAction();
    }
}
