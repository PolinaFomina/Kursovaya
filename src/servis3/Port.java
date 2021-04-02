package servis3;

import servis1.Ship;
import servis1.TimeTable;

import java.util.ArrayList;
import java.util.List;

public class Port
{
    public List<Ship> queueOfBulkShips = new ArrayList<Ship>();
    public List<Ship> queueOfLiquidShips = new ArrayList<Ship>();
    public List<Ship> queueOfContainerShips = new ArrayList<Ship>();

    private int countOfBulkCranes;
    private int countOfLiquidCranes;
    private int countOfContainerCranes;

    public static double loadCapacityOfBulkCranes = 80;
    public static double loadCapacityOfLiquidCranes = 5000;
    public static double loadCapacityOfContainerCranes = 5;

    private int priceOfCran = 30000;

    private int fine;

    private double result;

    public Port(TimeTable timeTable)
    {
        for (int i = 0; i < timeTable.getCountOfShips(); i++)
        {
            if (timeTable.ships.get(i).getCargo() == "сыпучий")
            {
                queueOfBulkShips.add(timeTable.ships.get(i));
            }
            else if (timeTable.ships.get(i).getCargo() == "жидкий")
            {
                queueOfLiquidShips.add(timeTable.ships.get(i));
            }
            else
            {
                queueOfContainerShips.add(timeTable.ships.get(i));
            }
        }

        countOfBulkCranes = 1;
        countOfLiquidCranes = 1;
        countOfContainerCranes = 1;

        fine = 0;
        result  = 0;
    }

    public void print()
    {
        System.out.println("_______First queue_______");
        for (int i = 0; i < queueOfBulkShips.size(); i++)
        {
            queueOfBulkShips.get(i).print();
        }
        System.out.println("_______Second queue_______");
        for (int i = 0; i < queueOfLiquidShips.size(); i++)
        {
            queueOfLiquidShips.get(i).print();
        }
        System.out.println("_______Third queue_______");
        for (int i = 0; i < queueOfContainerShips.size(); i++)
        {
            queueOfContainerShips.get(i).print();
        }
    }
}
