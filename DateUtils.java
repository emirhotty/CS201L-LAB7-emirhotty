
import java.util.Calendar;

public class DateUtils {

    //PRE: accepts a date and time string from the CSV
    //POST: returns month, day, and year as an integer array
    public static int[] parseDateTime(String date) {
        String[] parts = date.trim().split(" ");
        return parseDate(parts[0]);
    }

    //PRE: accepts a date in mm/dd/yyyy format
    //POST: returns month, day, and year
    public static int[] parseDate(String date) {
        String[] parts = date.trim().split("/");

        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        return new int[]{month, day, year};
    }

    //PRE: none
    //POST: returns the current month, day, and year
    public static int[] getCurrentDate() {
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return new int[]{month, day, year};
    }

    //PRE: accepts month, day, and year
    //POST: returns the Julian date
    public static int dateToJulian(int month, int day, int year) {
        if (month <= 2) {
            year--;
            month += 12;
        }

        int A = year / 100;
        int B = 2 - A + (A / 4);

        return (int)((365.25 * (year + 4716))
                + (30.6001 * (month + 1))
                + day + B - 1524.5);
    }
}
