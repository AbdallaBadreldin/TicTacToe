package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Abdo
 */
public class IPValidation {

    private static final String IPV4_PATTERN
            = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private static final Pattern pattern = Pattern.compile(IPV4_PATTERN);

    public static boolean isIpValid(String ip) {
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}
