/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mahmoud
 */
public class IPValidation {

    private static String validIp;

    public static boolean isValidIPAddress(String ip) {
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";

        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        Pattern p = Pattern.compile(regex);
        if (ip == null) {
            return false;
        }
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    public static String getIp() {
        return validIp;
    }

}
