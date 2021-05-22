package service3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.LinkedHashMap;

import service1.Ship;
import service1.Time;

public class Statistic
{
    @JsonIgnore
    private Port port;

    private LinkedHashMap<String, ParkingInfo> statistic;

    private int countOfUnloadedShips;
    private double averageQueueLength;
    private Time averageWaitingTime;
    private int maxUnloadingDelay;
    private double averageUnloadingDelay;
    private double totalFine;
    private int countOfBulkCranes;
    private int countOfLiquidCranes;
    private int countOfContainerCranes;

    public Statistic()
    {
        this.port = new Port();
        this.statistic = new LinkedHashMap<>();

        this.countOfUnloadedShips = 0;
        this.averageQueueLength = 0;
        this.averageWaitingTime = new Time();
        this.maxUnloadingDelay = 0;
        this.averageUnloadingDelay = 0;
        this.totalFine = 0;
        this.countOfBulkCranes = 0;
        this.countOfLiquidCranes = 0;
        this.countOfContainerCranes = 0;
    }

    public Statistic(Port port)
    {
        this.port = port;

        this.statistic = new LinkedHashMap<>();
        for(Ship ship : port.getQueueOfShips())
        {
            this.statistic.put(ship.getName(), ship.getParkingInfo());
        }

        int maxBulkQueueLength = 0, maxLiquidQueueLength = 0, maxContainerQueueLength = 0;
        int bulkQueueLength = 0, liquidQueueLength = 0, containerQueueLength = 0;

        double waitingTimeInMinutes = 0;

        for (int i = 0; i < statistic.size(); i++)
        {
            Ship ship = port.getQueueOfShips().get(i);

            this.countOfUnloadedShips++;

            if(!ship.getParkingInfo().getWaitingTime().isMissing())
            {
                switch (ship.getCargoType())
                {
                    case BULK:
                        bulkQueueLength++;
                        break;

                    case LIQUID:
                        liquidQueueLength++;
                        break;

                    case CONTAINER:
                        containerQueueLength++;
                        break;
                }
            }
            else
            {
                if (bulkQueueLength > maxBulkQueueLength)
                {
                    maxBulkQueueLength = bulkQueueLength;
                }
                bulkQueueLength = 0;

                if (liquidQueueLength > maxLiquidQueueLength)
                {
                    maxLiquidQueueLength = liquidQueueLength;
                }
                liquidQueueLength = 0;

                if (containerQueueLength > maxContainerQueueLength)
                {
                    maxContainerQueueLength = containerQueueLength;
                }
                containerQueueLength = 0;
            }

            waitingTimeInMinutes += ship.getParkingInfo().getWaitingTime().timeToMinutes();

            if(ship.getUnloadingDelay() > this.maxUnloadingDelay)
                this.maxUnloadingDelay = ship.getUnloadingDelay();

            this.averageUnloadingDelay += ship.getUnloadingDelay();
        }

        this.averageQueueLength = Math.round((double) (maxBulkQueueLength + maxLiquidQueueLength + maxContainerQueueLength) / 3);
        this.averageWaitingTime = new Time();
        this.averageWaitingTime.minutesInTime(waitingTimeInMinutes / statistic.size());
        this.averageUnloadingDelay /= statistic.size();

        this.totalFine = port.getTotalFine();
        this.countOfBulkCranes = port.getCountOfBulkCranes();
        this.countOfLiquidCranes = port.getCountOfLiquidCranes();
        this.countOfContainerCranes = port.getCountOfContainerCranes();
    }

    public Port getPort()
    {
        return port;
    }

    public HashMap<String, ParkingInfo> getStatistic()
    {
        return statistic;
    }

    public int getCountOfUnloadedShips()

    {
        return countOfUnloadedShips;
    }

    public double getAverageQueueLength()
    {
        return averageQueueLength;
    }

    public Time getAverageWaitingTime()
    {
        return averageWaitingTime;
    }

    public int getMaxUnloadingDelay()
    {
        return maxUnloadingDelay;
    }

    public double getAverageUnloadingDelay()
    {
        return averageUnloadingDelay;
    }

    public double getTotalFine()
    {
        return totalFine;
    }

    public int getCountOfBulkCranes()
    {
        return countOfBulkCranes;
    }

    public int getCountOfLiquidCranes()
    {
        return countOfLiquidCranes;
    }

    public int getCountOfContainerCranes()
    {
        return countOfContainerCranes;
    }

    @Override
    public String toString()
    {
        return  "\ncountOfUnloadedShips: " + countOfUnloadedShips +
                "\naverageQueueLength: " + averageQueueLength +
                "\naverageWaitingTime: " + averageWaitingTime +
                "\nmaxUnloadingDelay: " + maxUnloadingDelay +
                "\naverageUnloadingDelay: " + averageUnloadingDelay +
                "\ntotalFine: " + totalFine +
                "\ncountOfBulkCranes: " + countOfBulkCranes +
                "\ncountOfLiquidCranes: " + countOfLiquidCranes +
                "\ncountOfContainerCranes: " + countOfContainerCranes;
    }
}
