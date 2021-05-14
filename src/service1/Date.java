package service1;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Date
{
    public final static int MAX_MONTH = 12;
    public final static int MAX_DAY = 30;
    public final static int MAX_HOUR = 23;
    public final static int MAX_MINUTE = 59;
    public final static int MAX_SECOND = 59;

    public final static int MIN_MONTH = 1;
    public final static int MIN_DAY = 1;
    public final static int MIN_HOUR = 0;
    public final static int MIN_MINUTE = 0;
    public final static int MIN_SECOND = 0;

    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public Date()
    {
        this.month = 0;
        this.day = 0;
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }

    public Date(Date date)
    {
        this.month = date.month;
        this.day = date.day;
        this.hour = date.hour;
        this.minute = date.minute;
        this.second = date.second;
    }

    public Date(int month, int day, int hour, int minute, int second)
    {
        if (month > MAX_MONTH && month < MIN_MONTH)
        {
            System.out.println("Incorrect time: month can't be < 1 and > 12");
        }
        if (day > MAX_DAY && day < MIN_DAY)
        {
            System.out.println("Incorrect time: day can't be < 1 and > 30");
        }
        if (hour > MAX_HOUR && hour < MIN_HOUR)
        {
            System.out.println("Incorrect time: hour can't be < 0 and > 24");
        }
        if (minute > MAX_MINUTE && minute < MIN_MINUTE)
        {
            System.out.println("Incorrect time: minute can't be < 0 and > 59");
        }
        if (second > MAX_SECOND && second < MIN_SECOND)
        {
            System.out.println("Incorrect time: seconds can't be < 0 and > 59");
        }

        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public int getHour()
    {
        return hour;
    }

    public void setHour(int hour)
    {
        this.hour = hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public void setMinute(int minute)
    {
        this.minute = minute;
    }

    public int getSecond()
    {
        return second;
    }

    public void setSecond(int second)
    {
        this.second = second;
    }

    public String toString()
    {
        String stringMonth = "";
        if(month == 4)
        {
            stringMonth = " апреля";
        }
        if(month == 5)
        {
            stringMonth = " мая";
        }
        return day + stringMonth + " в " + hour + ":"
                + minute + ":" + second;
    }

    @JsonIgnore
    public boolean isMissing()
    {
        return month == 0 && day == 0 && hour == 0 && minute == 0 && second == 0;
    }

    public synchronized void addMinutes(int minutes)
    {
        minute += minutes;

        while (minute > MAX_MINUTE)
        {
            minute -= Time.MAX_MINUTES;
            hour++;
        }
        while (hour > MAX_HOUR)
        {
            hour -= Time.MAX_HOURS;
            day++;
        }
        while (day > MAX_DAY)
        {
            day -= Time.MAX_DAYS;
            month++;
        }
    }

    public synchronized Time subtractDate(Date date)
    {
        int months = month - date.month;
        int days = day - date.day;
        int hours = hour - date.hour;
        int minutes = minute - date.minute;
        int seconds = second - date.second;

        while (seconds < Time.MIN_SECONDS)
        {
            seconds += Time.MAX_SECONDS;
            minutes--;
        }
        while (minutes < Time.MIN_MINUTES)
        {
            minutes += Time.MAX_MINUTES;
            hours--;
        }
        while (hours < Time.MIN_HOURS)
        {
            hours += Time.MAX_HOURS;
            days--;
        }
        while (days < Time.MIN_DAYS)
        {
            days += Time.MAX_DAYS;
            months--;
        }

        return new Time(months, days, hours, minutes, seconds);
    }

    public boolean equal(Date date)
    {
        return month == date.month && day == date.day && hour == date.hour &&
                minute == date.minute && second == date.second;
    }

    public boolean before(Date date)
    {
        if (month != date.month)
        {
            return month < date.month;
        }
        if (day != date.day)
        {
            return day < date.day;
        }
        if (hour != date.hour)
        {
            return hour < date.hour;
        }
        if (minute != date.minute)
        {
            return minute < date.minute;
        }
        if (second != date.second)
        {
            return second < date.second;
        }

        return false;
    }
}