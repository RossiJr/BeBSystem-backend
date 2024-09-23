package org.rossijr.bebsystem;

public class Utils {
    public static Boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }
    public static String cleanPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll(" ", "").replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "");
    }
}
