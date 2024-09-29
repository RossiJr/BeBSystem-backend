package org.rossijr.bebsystem;

import java.util.Calendar;

public class Utils {
    public static Boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }
    public static String cleanPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll(" ", "").replaceAll("-", "").replaceAll("\\(", "").replaceAll("\\)", "");
    }
    public static boolean isSameDate(Calendar date1, Calendar date2){
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
    }
}
