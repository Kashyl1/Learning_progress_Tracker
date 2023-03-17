package tracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z](?!.*[-']{2})[a-zA-Z-' ]*[a-zA-Z]");

    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9.]+@\\w+\\.\\w+");
    public static boolean isInvalidName(String name) {
        if (name == null || name.isEmpty()) {
            return true;
        }
        Matcher matcher = NAME_PATTERN.matcher(name);
        return !matcher.matches();
    }
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
