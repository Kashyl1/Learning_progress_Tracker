package tracker;

public enum ERROR {
    INVALID_NAME("Incorrect first name."),
    INVALID_SURNAME("Incorrect last name."),
    INVALID_MAIL("Incorrect email."),
    TAKEN_MAIL("This email is already taken."),
    INVALID_POINTS("Incorrect points format.");
    private final String message;

    ERROR(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
