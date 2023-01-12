package commands;

public final class BackCommand extends Command {
    private final Receiver receiver;

    public BackCommand() {
        this.receiver = CommandFactory.getReceiver();
    }

    @Override
    public void execute() {
        receiver.backAction();
    }
}
