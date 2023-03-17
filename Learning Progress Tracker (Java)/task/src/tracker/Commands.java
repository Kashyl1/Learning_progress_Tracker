package tracker;

public enum Commands {
    EXIT("exit", "Bye!"),
    CONTINUE(null, null),
    BACK("back", null),
    ADD_POINTS("add points", "Enter an id and points or 'back' to return:"),
    LIST("list", "Students:"),
    EMPTY("", "No input."),
    ADD_STUDENTS("add students", "Enter student credentials or 'back' to return:"),
    UNKNOWNCOMMAND(null, "Error: unknown command!"),
    FIND("find", "Enter an id or 'back' to return"),
    STATISTICS("statistics", "Type the name of a course to see details or 'back' to quit"),
    NOTIFY("notify" , null);
    private final String command;
    private final String message;
    Commands(String command, String message) {
        this.command = command;
        this.message = message;
    }
    public static Commands get(String input) {
        for (Commands commands : Commands.values()) {
            if (input.equalsIgnoreCase(commands.command)) {
                return commands;
            }
        }
        return input.trim().isEmpty() ? EMPTY : UNKNOWNCOMMAND;
    }

    public String getMessage() {
        return message;
    }

}
