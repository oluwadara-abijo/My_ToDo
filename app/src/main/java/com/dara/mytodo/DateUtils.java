package com.dara.mytodo;

class DateUtils {

    static String formatDate(int day, int month, int year) {
        String dayString = String.valueOf(day);
        String actualMonthString = String.valueOf(month + 1);
        String monthString;
        switch (actualMonthString) {
            case "1":
                monthString = "Jan";
                break;
            case "2":
                monthString = "Feb";
                break;
            case "3":
                monthString = "Mar";
                break;
            case "4":
                monthString = "Apr";
                break;
            case "5":
                monthString = "May";
                break;
            case "6":
                monthString = "Jun";
                break;
            case "7":
                monthString = "Jul";
                break;
            case "8":
                monthString = "Aug";
                break;
            case "9":
                monthString = "Sept";
                break;
            case "10":
                monthString = "Oct";
                break;
            case "11":
                monthString = "Nov";
                break;
            default:
                monthString = "Dec";
        }
        String yearString = String.valueOf(year);
        return dayString + " " + monthString + " " + yearString;
    }

    static String formatTime(int hour, int minute) {
        String hourString;
        if (hour < 10) {
            hourString = "0" + hour;
        } else {
            hourString = String.valueOf(hour);
        }

        String minuteString;
        if (minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = String.valueOf(minute);
        }

        return hourString + ":" + minuteString;
    }
}
