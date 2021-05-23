package service1;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Time
{
    public final static int MAX_DAYS = 30;
    public final static int MAX_HOURS = 24;
    public final static int MAX_MINUTES = 60;
    public final static int MAX_SECONDS = 60;

    public final static int MIN_MONTHS = 0;
    public final static int MIN_DAYS = 0;
    public final static int MIN_HOURS = 0;
    public final static int MIN_MINUTES = 0;
    public final static int MIN_SECONDS = 0;

    private int months;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;

    public Time()
    {
        this.months = 0;
        this.days = 0;
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
    }

    public Time(int months, int days, int hours, int minutes, int seconds)
    {
        if (months >= MIN_MONTHS)
        {
            this.months = months;
        }
        if (days <= MAX_DAYS && days >= MIN_DAYS )
        {
            this.days = days;
        }
        else
        {
            System.out.println("Incorrect time: days can't be < 1 and > 30");
        }
        if (hours < MAX_HOURS && hours >= MIN_HOURS)
        {
            this.hours = hours;
        }
        else
        {
            System.out.println("Incorrect time: hours can't be < 0 and > 23");
        }
        if (minutes < MAX_MINUTES && minutes >= MIN_MINUTES)
        {
            this.minutes = minutes;
        }
        else
        {
            System.out.println("Incorrect time: minutes can't be < 0 and > 59");
        }
        if (seconds < MAX_SECONDS && seconds >= MIN_SECONDS)
        {
            this.seconds = seconds;
        }
        else
        {
            System.out.println("Incorrect time: seconds can't be < 0 and > 59");
        }
    }

    public Time(Time time)
    {
        this.months = time.months;
        this.days = time.days;
        this.hours = time.hours;
        this.minutes = time.minutes;
        this.seconds = time.seconds;
    }

    public int getMonths()
    {
        return months;
    }

    public void setMonths(int months)
    {
        this.months = months;
    }

    public int getDays()
    {
        return days;
    }

    public void setDays(int days)
    {
        this.days = days;
    }

    public int getHours()
    {
        return hours;
    }

    public void setHours(int hours)
    {
        this.hours = hours;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public void setMinutes(int minutes)
    {
        this.minutes = minutes;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }

    @Override
    public String toString()
    {
        if (months < 1)
        {
            return days + ":" + hours + ":" + minutes + ":" + seconds;
        }
        else
        {
            return months + ":" + days + ":" + hours + ":" + minutes + ":" + seconds;
        }
    }

    public void addTime(Time time)
    {
        months += time.months;
        days += time.days;
        hours += time.hours;
        minutes += time.minutes;
        seconds += time.seconds;

        while (seconds > Date.MAX_SECOND)
        {
            seconds -= MAX_SECONDS;
            minutes++;
        }
        while (minutes > Date.MAX_MINUTE)
        {
            minutes -= MAX_MINUTES;
            hours++;
        }
        while (hours > Date.MAX_HOUR)
        {
            hours -= MAX_HOURS;
            days++;
        }
        while (hours > MAX_DAYS)
        {
            days -= MAX_DAYS;
            months++;
        }
    }

    public void subtractTime(Time time)
    {
        months -= time.months;
        days -= time.days;
        hours -= time.hours;
        minutes -= time.minutes;
        seconds -= time.seconds;

        while (seconds < MIN_SECONDS)
        {
            seconds += MAX_SECONDS;
            minutes--;
        }
        while (minutes < MIN_MINUTES)
        {
            minutes += MAX_MINUTES;
            hours--;
        }
        while (hours < MIN_HOURS)
        {
            hours += MAX_HOURS;
            days--;
        }
        while (days < MIN_DAYS)
        {
            days += MAX_DAYS;
            months--;
        }
    }

    public double timeToMinutes()
    {
        double result = 0;
        result += months * MAX_DAYS * MAX_HOURS * MAX_MINUTES;
        result += days * MAX_HOURS * MAX_MINUTES;
        result += hours * MAX_MINUTES;
        result += minutes;
        result += (double) seconds / MAX_SECONDS;

        return result;
    }

    public void minutesInTime(double minutes)
    {
        if (minutes >= MIN_MINUTES)
        {
            this.months = (int) (minutes / MAX_MINUTES / MAX_HOURS / MAX_DAYS);
            minutes -= this.months * MAX_DAYS * MAX_HOURS * MAX_MINUTES;
            this.days = (int) (minutes / MAX_MINUTES / MAX_HOURS);
            minutes -= this.days * MAX_HOURS * MAX_MINUTES;
            this.hours = (int) (minutes / MAX_MINUTES);
            minutes -= this.hours * MAX_MINUTES;
            this.minutes = (int) minutes;
            minutes -= this.minutes;
            this.seconds = (int) (minutes * MAX_SECONDS);
        }
    }

    @JsonIgnore
    public boolean isMissing()
    {
        return months == 0 && days == 0 && hours == 0 && minutes == 0 && seconds == 0;
    }
}