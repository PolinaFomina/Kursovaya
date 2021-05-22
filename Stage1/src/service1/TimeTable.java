package service1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TimeTable
{
    private ArrayList<Ship> listOfShips;

    public TimeTable()
    {
        this.listOfShips = new ArrayList<>();
    }

    public TimeTable(int countOfShips)
    {
        this.listOfShips = new ArrayList<>();
        for (int i = 0; i < countOfShips; i++)
        {
            Ship newShip = new Ship(i);
            this.listOfShips.add(newShip);
        }

        Collections.sort(this.listOfShips);
    }

    public TimeTable(TimeTable timeTable)
    {
        this.listOfShips = new ArrayList<>(timeTable.listOfShips);
    }

    public ArrayList<Ship> getListOfShips()
    {
        return listOfShips;
    }

    public void setListOfShips(ArrayList<Ship> listOfShips)
    {
        this.listOfShips = new ArrayList<>(listOfShips);
    }

    @Override
    public String toString()
    {
        String timetable = new String();

        for (Ship ship : listOfShips)
        {
            timetable += ship.toString();
        }

        return timetable;
    }

    public void addShipFromConsole()
    {
        String answer = "";
        Scanner in = new Scanner(System.in);

        while (!answer.equals("да") && !answer.equals("нет"))
        {
            System.out.println("Хотите добавить корабль в расписание? (да/нет)");
            answer = in.nextLine();
        }

        if (answer.equals("да"))
        {
            String name = "";

            while (name.equals(""))
            {
                System.out.println("Введите имя корабля: ");
                name = in.nextLine();
            }

            int index = -1;

            while (index != 0 && index != 1 && index != 2)
            {
                System.out.println("Введите тип груза (0 - сыпучий, 1 - жидкий, 2 - контейнеры): ");
                index = in.nextInt();
            }

            double countOfCargo = 0;
            switch (index)
            {
                case 0:
                    while (countOfCargo < 500 || countOfCargo > 12000)
                    {
                        System.out.println("Введите вес груза (число от 500 до 12000): ");
                        countOfCargo = in.nextDouble();
                    }
                    break;
                case 1:
                    while (countOfCargo < 5000 || countOfCargo > 400000)
                    {
                        System.out.println("Введите вес груза (число от 5000 до 400000): ");
                        countOfCargo = in.nextDouble();
                    }
                    break;
                case 2:
                    while (countOfCargo < 250 || countOfCargo > 18000)
                    {
                        System.out.println("Введите количество контейнеров (число от 250 до 18000): ");
                        countOfCargo = in.nextDouble();
                    }
                    break;
            }

            int day = 0;

            int MAX_DAYS = 30;
            int MIN_DAYS = 1;

            while (day < MIN_DAYS || day > MAX_DAYS)
            {
                System.out.println("Введите дату прибытия (число от 1 до 30): ");
                day = in.nextInt();
            }

            int hour = -1;

            while (hour < Time.MIN_HOURS || hour > Time.MAX_HOURS)
            {
                System.out.println("Введите час (число от 0 до 23): ");
                hour = in.nextInt();
            }

            int minute = -1;

            while (minute < Time.MIN_MINUTES || minute > Time.MAX_MINUTES)
            {
                System.out.println("Введите минуты (число от 0 до 59): ");
                minute = in.nextInt();
            }

            int second = -1;

            while (second < Time.MIN_SECONDS || second > Time.MAX_SECONDS)
            {
                System.out.println("Введите секунды (число от 0 до 59): ");
                second = in.nextInt();
            }

            CargoType cargoType = CargoType.values()[index];

            Date arrivalDate = new Date(4, day, hour, minute, second);

            Ship newShip = new Ship(name, cargoType, countOfCargo, arrivalDate);

            listOfShips.add(newShip);

            Collections.sort(listOfShips);
        }
    }
}

