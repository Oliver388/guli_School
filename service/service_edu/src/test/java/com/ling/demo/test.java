package com.ling.demo;

public class test {
    int calculateDaysBetweenDates(int year1, int month1, int day1, int year2, int month2, int day2) {
        // Write your code here
        int days = 0;
        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return 0;
        } else if (year1 == year2 && month1 == month2) {
            days = day2 - day1;
        } else if (year1 == year2) {
            days = (month2 - month1) * 30 + (day2 - day1);
        } else {
            days = (year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1);
        }
        return days;
    }
}