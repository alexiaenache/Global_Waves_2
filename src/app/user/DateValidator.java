package app.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateValidator {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final int MIN_YEAR = 1900;
    public static final int MAX_YEAR = 2023;
    public static final int MIN_MONTH = 1;
    public static final int MAX_MONTH = 12;
    public static final int DAYS_IN_MONTH = 31;
    public static final int DAYS_IN_FEBRUARY = 28;
    public static final int DAYS_IN_LEAP_FEBRUARY = 29;
    public static final int ZERO = 0;
    public static final int FOUR = 4;
    public static final int HUNDRED = 100;
    public static final int FOUR_HUNDRED = 400;
    public static final int FEBRUARY = 2;
    public static final int APRIL = 4;
    public static final int JUNE = 6;
    public static final int SEPTEMBER = 9;
    public static final int NOVEMBER = 11;


    /**
     * Validates a given date string against the specified date format and ranges.
     *
     * @param dateString The date string to be validated.
     * @return {@code true} if the date is valid, {@code false} otherwise.
     */
    public static boolean isValidDate(final String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateString);

            // Check year range (MIN_YEAR - MAX_YEAR)
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            if (year < MIN_YEAR || year > MAX_YEAR) {
                return false;
            }

            // Check month range (MIN_MONTH - MAX_MONTH)
            int month = cal.get(Calendar.MONTH) + MIN_MONTH;
            if (month < MIN_MONTH || month > MAX_MONTH) {
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

    /**
     * Calculates the maximum number of days in a given month and year, considering leap years.
     *
     * @param month The month for which to calculate the maximum days.
     * @param year  The year for which to calculate the maximum days.
     * @return The maximum number of days in the specified month and year.
     */
    private static int getMaxDaysInMonth(final int month, final int year) {
        switch (month) {
            case FEBRUARY:
                return isLeapYear(year) ? DAYS_IN_LEAP_FEBRUARY : DAYS_IN_FEBRUARY;
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                return DAYS_IN_MONTH - MIN_MONTH;
            default:
                return DAYS_IN_MONTH;
        }
    }

    /**
     * Checks if a given year is a leap year.
     *
     * @param year The year to check for leap year status.
     * @return {@code true} if the year is a leap year, {@code false} otherwise.
     */
    private static boolean isLeapYear(final int year) {
        return (year % FOUR == ZERO && year % HUNDRED != ZERO) || (year % FOUR_HUNDRED == ZERO);
    }
}
