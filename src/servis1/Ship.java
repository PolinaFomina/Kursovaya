package servis1;

import servis3.Port;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Ship implements Comparable <Ship>
{
    private String name;
    private String cargo;
    public int countOfCargo;
    private Calendar date = new GregorianCalendar();
    private int delay;
    private double periodOfParking;
    private int countOfCranes;

    public Ship(int number)
    {
        name = "ship";
        String numberOfShip = Integer.toString(number);
        name += numberOfShip;

        String[] typesOfCargo = { "сыпучий", "жидкий", "контейнеры" };
        //String[] typesOfCargo = { "bulk", "liquid", "container" };

        int randomNumber = (int) (Math.random() * 3);
        cargo = typesOfCargo[randomNumber];

        switch(randomNumber)
        {
            case 0:
                randomNumber = (int) (Math.random() * 11501) + 500;
                countOfCargo = randomNumber;
                periodOfParking = countOfCargo / Port.loadCapacityOfBulkCranes;
                break;
            case 1:
                randomNumber = (int) (Math.random() * 395001) + 5000;
                countOfCargo = randomNumber;
                periodOfParking = countOfCargo / Port.loadCapacityOfLiquidCranes;
                break;
            case 2:
                randomNumber = (int) (Math.random() * 17751) + 250;
                countOfCargo = randomNumber;
                periodOfParking = countOfCargo / Port.loadCapacityOfContainerCranes;
                break;
        }

        date.set(Calendar.YEAR, 2021);
        date.set(Calendar.MONTH, 4);
        date.set(Calendar.DAY_OF_MONTH, (int) (Math.random() * 30) + 1);
        date.set(Calendar.HOUR_OF_DAY, (int) (Math.random() * 25));
        date.set(Calendar.MINUTE, (int) (Math.random() * 60));

        randomNumber = (int) (Math.random() * 16) - 7;
        delay = randomNumber;

        countOfCranes = 0;
    }


    public String getCargo()
    {
        return cargo;
    }

    public int getCountOfCranes()
    {
        return countOfCranes;
    }

    public double getPeriodOfParking()
    {
        return periodOfParking;
    }

    public void setCountOfCranes(int countOfCranes)
    {
        this.countOfCranes = countOfCranes;
    }

    public void print()
    {
        if (date.get(Calendar.MINUTE) < 10)
        {
            System.out.printf("%s прибывает %d числа в %d:0%d  груз: %s  количество груза: %s  отклонение от расписания: %d  планируемый срок стоянки в часах: %.2f\n", name, date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), cargo, countOfCargo, delay, periodOfParking);
        }
        else
        {
            System.out.printf("%s прибывает %d числа в %d:%d  груз: %s  количество груза: %s  отклонение от расписания: %d  планируемый срок стоянки в часах: %.2f\n", name, date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), cargo, countOfCargo, delay, periodOfParking);
        }
    }

    @Override
    public int compareTo(Ship ship)
    {
        if (this.date.get(Calendar.DAY_OF_MONTH) > ship.date.get(Calendar.DAY_OF_MONTH))
        {
            return 1;
        }
        if (this.date.get(Calendar.DAY_OF_MONTH) < ship.date.get(Calendar.DAY_OF_MONTH))
        {
            return -1;
        }
        if (this.date.get(Calendar.HOUR_OF_DAY) > ship.date.get(Calendar.HOUR_OF_DAY))
        {
            return 1;
        }
        if (this.date.get(Calendar.HOUR_OF_DAY) < ship.date.get(Calendar.HOUR_OF_DAY))
        {
            return -1;
        }
        if (this.date.get(Calendar.MINUTE) > ship.date.get(Calendar.MINUTE))
        {
            return 1;
        }
        if (this.date.get(Calendar.MINUTE) < ship.date.get(Calendar.MINUTE))
        {
            return -1;
        }
        return 0;
    }
}

