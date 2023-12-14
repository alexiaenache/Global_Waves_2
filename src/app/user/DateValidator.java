package app.user;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateValidator {

    public static boolean isValidDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateString);

            // Check year range (1900 - 2023)
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            if (year < 1900 || year > 2023) {
                return false;
            }

            // Check month range (1 - 12)
            int month = cal.get(Calendar.MONTH) + 1; // Months are zero-based
            if (month < 1 || month > 12) {
                return false;
            }

            // Check day range based on month and leap year
            int day = cal.get(Calendar.DAY_OF_MONTH);
            if (day < 1 || day > getMaxDaysInMonth(month, year)) {
                return false;
            }

            return true;
        } catch (ParseException e) {
            return false; // Invalid date format
        }
    }

    private static int getMaxDaysInMonth(int month, int year) {
        switch (month) {
            case 2: // February
                return isLeapYear(year) ? 29 : 28;
            case 4: // April
            case 6: // June
            case 9: // September
            case 11: // November
                return 30;
            default:
                return 31;
        }
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
