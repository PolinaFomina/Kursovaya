package service3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import service1.CargoType;
import service1.Date;
import service1.Ship;
import service1.Time;
import service1.TimeTable;
import service2.InteractionWithJson;

public class Port
{
    private ArrayList<Ship> queueOfShips;
    private ArrayList<Crane> cranes;
    private Date currentTime;

    private int cranesWorkInMinute;

    private int countOfBulkCranes;
    private int countOfLiquidCranes;
    private int countOfContainerCranes;

    public static final int capacityOfBulkCranes = 6;
    public static final int capacityOfLiquidCranes = 20;
    public static final int capacityOfContainerCranes = 2;

    public static final int priceOfCrane = 30000;

    public static final int fineInHour = 100;

    private int totalFine;

    public Port()
    {
        this.queueOfShips = new ArrayList<>();

        this.cranes = new ArrayList<>();
        this.cranes.add(new Crane(capacityOfBulkCranes, CargoType.BULK, this));
        this.cranes.add(new Crane(capacityOfLiquidCranes, CargoType.LIQUID, this));
        this.cranes.add(new Crane(capacityOfContainerCranes, CargoType.CONTAINER, this));

        this.currentTime = new Date();

        this.cranesWorkInMinute = 0;

        this.countOfBulkCranes = 1;
        this.countOfLiquidCranes = 1;
        this.countOfContainerCranes = 1;

        this.totalFine = 0;
    }

    public ArrayList<Ship> getQueueOfShips()
    {
        return queueOfShips;
    }

    public void setQueueOfShips(ArrayList<Ship> queueOfShips)
    {
        this.queueOfShips = new ArrayList<>(queueOfShips);
    }

    public ArrayList<Crane> getCranes()
    {
        return cranes;
    }

    public void setCranes(ArrayList<Crane> cranes)
    {
        this.cranes = new ArrayList<>(cranes);
    }

    public Date getCurrentTime()
    {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime)
    {
        this.currentTime = new Date(currentTime);
    }

    public int getCranesWorkInMinute()
    {
        return cranesWorkInMinute;
    }

    public void setCranesWorkInMinute(int cranesWorkInMinute)
    {
        this.cranesWorkInMinute = cranesWorkInMinute;
    }

    public int getCountOfBulkCranes()
    {
        return countOfBulkCranes;
    }

    public void setCountOfBulkCranes(int countOfBulkCranes)
    {
        this.countOfBulkCranes = countOfBulkCranes;
    }

    public int getCountOfLiquidCranes()
    {
        return countOfLiquidCranes;
    }

    public void setCountOfLiquidCranes(int countOfLiquidCranes)
    {
        this.countOfLiquidCranes = countOfLiquidCranes;
    }

    public int getCountOfContainerCranes()
    {
        return countOfContainerCranes;
    }

    public void setCountOfContainerCranes(int countOfContainerCranes)
    {
        this.countOfContainerCranes = countOfContainerCranes;
    }

    public double getTotalFine()
    {
        return totalFine;
    }

    public void setTotalFine(int totalFine)
    {
        this.totalFine = totalFine;
    }

    public synchronized void passedMinute()
    {
        cranesWorkInMinute++;

        if(cranesWorkInMinute < cranes.size() && !isWorkDone())
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return;
        }

        cranesWorkInMinute = 0;
        currentTime.addMinutes(1);
        notifyAll();
    }

    public synchronized boolean isWorkDone()
    {
        for (Ship ship : queueOfShips)
        {
            if (!ship.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    public synchronized Ship getShipToUnload(CargoType cargoType)
    {
        for (Ship ship : queueOfShips)
        {
            if ((ship.getParkingInfo().getArrivalDate().equal(currentTime) ||
                    ship.getParkingInfo().getArrivalDate().before(currentTime)) &&
                    !ship.isEmpty() && ship.getCargoType() == cargoType && ship.getCountOfCranes() < 2)
            {
                ship.incCountOfCranes();
                return ship;
            }
        }

        return null;
    }

    public void work()
    {
        TimeTable timeTable = new TimeTable();
        InteractionWithJson.timeTableFromJson(timeTable);

        this.queueOfShips = new ArrayList<>();
        for (Ship ship : timeTable.getListOfShips())
        {
            this.queueOfShips.add(ship);
        }
        Collections.sort(queueOfShips, Ship::compareDelay);

        this.currentTime = new Date(queueOfShips.get(0).getParkingInfo().getArrivalDate());

        List<Thread> threads = new ArrayList<>();

        for (Crane crane : cranes)
        {
            Thread thread = new Thread(crane);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads)
        {
            try
            {
                thread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void calculateOptionalNumberOfCranes()
    {
        int previousFine = 0;
        int bulkFine = -1, liquidFine = -1, containerFine = -1;
        int totalBulkFine = 0, totalLiquidFine = 0, totalContainerFine = 0;
        this.totalFine = Integer.MAX_VALUE;

        boolean isFirstTime = true;
        while (totalFine >= priceOfCrane)
        {
            if (previousFine != totalFine)
            {
                if (!isFirstTime)
                {
                    if(!addCrane(bulkFine, liquidFine, containerFine))
                    {
                        return;
                    }
                }

                totalBulkFine = 0;
                totalLiquidFine = 0;
                totalContainerFine = 0;
                previousFine = totalFine;
                this.totalFine = 0;

                work();

                for (Ship ship : queueOfShips)
                {
                    Time periodOfParking = ship.calculatePeriodOfParking();
                    int minutes = (int) periodOfParking.timeToMinutes();

                    double fine = Math.ceil(minutes / Time.MAX_MINUTES * fineInHour);

                    switch (ship.getCargoType())
                    {
                        case BULK:
                            totalBulkFine += fine;
                            break;

                        case LIQUID:
                            totalLiquidFine += fine;
                            break;

                        case CONTAINER:
                            totalContainerFine += fine;
                            break;
                    }
                }

                totalFine = totalBulkFine + totalLiquidFine + totalContainerFine;

                if(bulkFine != 0 || totalBulkFine==0)
                {
                    bulkFine = totalBulkFine;
                }

                if(liquidFine != 0 || totalLiquidFine == 0)
                {
                    liquidFine = totalLiquidFine;
                }

                if(containerFine != 0 || totalContainerFine == 0)
                {
                    containerFine = totalContainerFine;
                }

                isFirstTime = false;
            }
            else
            {
                switch (removeCrane(bulkFine, liquidFine, containerFine))
                {
                    case BULK:
                        bulkFine = 0;
                        previousFine = 0;
                        break;

                    case LIQUID:
                        liquidFine = 0;
                        previousFine = 0;
                        break;

                    case CONTAINER:
                        containerFine = 0;
                        previousFine = 0;
                        break;
                }
            }
        }
    }

    public boolean addCrane(int bulkFine, int liquidFine, int containerFine)
    {
        if (bulkFine == 0 && liquidFine == 0 && containerFine == 0)
        {
            return false;
        }

        int maxFine = Math.max(Math.max(bulkFine, liquidFine), containerFine);

        if (maxFine == bulkFine)
        {
            cranes.add(new Crane(capacityOfBulkCranes, CargoType.BULK, this));
            countOfBulkCranes++;
        }
        else if (maxFine == liquidFine)
        {
            cranes.add(new Crane(capacityOfLiquidCranes, CargoType.LIQUID, this));
            countOfLiquidCranes++;
        }
        else
        {
            cranes.add(new Crane(capacityOfContainerCranes, CargoType.CONTAINER, this));
            countOfContainerCranes++;
        }

        return true;
    }

    public CargoType removeCrane(int bulkFine, int liquidFine, int containerFine)
    {
        int maxFine = Math.max(Math.max(bulkFine, liquidFine), containerFine);

        if (maxFine == bulkFine)
        {
            cranes.remove((cranes.size() - 1));
            countOfBulkCranes--;
            return CargoType.BULK;
        }
        else if (maxFine == liquidFine)
        {
            cranes.remove((cranes.size() - 1));
            countOfLiquidCranes--;
            return CargoType.LIQUID;
        }
        else
        {
            cranes.remove((cranes.size() - 1));
            countOfContainerCranes--;
            return CargoType.CONTAINER;
        }
    }
}
