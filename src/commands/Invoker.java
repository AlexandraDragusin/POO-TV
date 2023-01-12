package commands;

public final class Invoker {
    private final Command command;

    public Invoker(final Command command) {
        this.command = command;
    }

    public void execute() {
        command.execute();
    }
}
