package commands;

public final class OnPageCommand extends Command {
    private final Receiver receiver;

    public OnPageCommand() {
        this.receiver = CommandFactory.getReceiver();
    }

    @Override
    public void execute() {
        receiver.onPageAction();
    }
}
