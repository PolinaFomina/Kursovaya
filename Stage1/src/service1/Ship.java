package service1;

import com.fasterxml.jackson.annotation.JsonIgnore;

import service3.ParkingInfo;
import service3.Port;

public class Ship implements Comparable <Ship>
{
    private String name;
    private CargoType cargoType;
    private double countOfCargo;
    private ParkingInfo parkingInfo;
    private Time planePeriodOfParking;
    private int arrivingDelay;
    private int unloadingDelay;
    @JsonIgnore
    private int countOfCranes;

    public Ship()
    {
    }

    public Ship(String name, CargoType cargoType, double countOfCargo, Date arrivalDate)
    {
        this.name = name;
        this.cargoType = cargoType;
        this.countOfCargo = countOfCargo;
        this.parkingInfo = new ParkingInfo(arrivalDate);

        this.planePeriodOfParking = new Time();

        switch(this.cargoType)
        {
            case BULK:
                this.planePeriodOfParking.minutesInTime((int) Math.ceil((this.countOfCargo / Port.capacityOfBulkCranes)));
                break;

            case LIQUID:
                this.planePeriodOfParking.minutesInTime((int) Math.ceil((this.countOfCargo / Port.capacityOfLiquidCranes)));
                break;

            case CONTAINER:
                this.planePeriodOfParking.minutesInTime((int) Math.ceil((this.countOfCargo / Port.capacityOfContainerCranes)));
                break;
        }

        this.arrivingDelay = (int) (Math.random() * 15 - 7);
        this.unloadingDelay = (int) (Math.random() * 1441);
    }

    public Ship(int number)
    {
        this.name = "ship";
        String numberOfShip = Integer.toString(number);
        this.name += numberOfShip;

        int countOfTypes = 3;
        this.cargoType = CargoType.values()[(int) (Math.random() * countOfTypes)];

        this.planePeriodOfParking  = new Time();

        switch(this.cargoType)
        {
            case BULK:
                this.countOfCargo = (int) (Math.random() * 11501) + 500;
                this.planePeriodOfParking.minutesInTime((int) Math.ceil((this.countOfCargo / Port.capacityOfBulkCranes)));
                break;

            case LIQUID:
                this.countOfCargo = (int) (Math.random() * 395001) + 5000;
                this.planePeriodOfParking.minutesInTime((int) Math.ceil((this.countOfCargo / Port.capacityOfLiquidCranes)));
                break;

            case CONTAINER:
                this.countOfCargo = (int) (Math.random() * 17751) + 250;
                this.planePeriodOfParking.minutesInTime((int) Math.ceil((this.countOfCargo / Port.capacityOfContainerCranes)));
                break;
        }

        Date arrivalDate = new Date(4, (int) (Math.random() * 30 + 1), (int) (Math.random() * 24),
                            (int) (Math.random() * 60), (int) (Math.random() * 60));

        this.parkingInfo = new ParkingInfo(arrivalDate);

        this.arrivingDelay = (int) (Math.random() * 15 - 7);
        this.unloadingDelay = (int) (Math.random() * 1441);
    }

    public Ship(Ship ship)
    {
        this.name = ship.name;
        this.cargoType = ship.cargoType;
        this.countOfCargo = ship.countOfCargo;
        this.parkingInfo = new ParkingInfo(ship.parkingInfo);
        this.planePeriodOfParking = new Time(ship.planePeriodOfParking);
        this.arrivingDelay = ship.arrivingDelay;
        this.unloadingDelay = ship.unloadingDelay;
        this.countOfCranes = ship.countOfCranes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public CargoType getCargoType()
    {
        return cargoType;
    }

    public void setCargoType(CargoType cargoType)
    {
        this.cargoType = cargoType;
    }

    public double getCountOfCargo()
    {
        return countOfCargo;
    }

    public void setCountOfCargo(double countOfCargo)
    {
        this.countOfCargo = countOfCargo;
    }

    public synchronized ParkingInfo getParkingInfo()
    {
        return parkingInfo;
    }

    public void setParkingInfo(ParkingInfo parkingInfo)
    {
        this.parkingInfo = new ParkingInfo(parkingInfo);
    }

    public Time getPlanePeriodOfParking()
    {
        return planePeriodOfParking;
    }

    public void setPlanePeriodOfParking(Time planePeriodOfParking)
    {
        this.planePeriodOfParking = new Time(planePeriodOfParking);
    }

    public int getArrivingDelay()
    {
        return arrivingDelay;
    }

    public void setArrivingDelay(int arrivingDelay)
    {
        this.arrivingDelay = arrivingDelay;
    }

    public int getUnloadingDelay()
    {
        return unloadingDelay;
    }

    public void setUnloadingDelay(int unloadingDelay)
    {
        this.unloadingDelay = unloadingDelay;
    }

    public int getCountOfCranes()
    {
        return countOfCranes;
    }

    public void setCountOfCranes(int countOfCranes)
    {
        this.countOfCranes = countOfCranes;
    }

    @JsonIgnore
    public boolean isEmpty()
    {
        return countOfCargo == 0;
    }

    @Override
    public String toString()
    {
        String information = "";
        information += "Имя корабля: " + name + '\n' +
                "Тип груза: " + cargoType + '\n' +
                "Вес груза: " + countOfCargo + '\n' +
                "Дата прибытия: " + parkingInfo.getArrivalDate() + '\n' +
                "Ожидаемое время стоянки: " + planePeriodOfParking + '\n' +
                "Отклонение от расписания: " + arrivingDelay + "\n\n";

        return information;
    }

    @Override
    public int compareTo(Ship ship)
    {
        if (parkingInfo.getArrivalDate().getDay() > ship.parkingInfo.getArrivalDate().getDay())
        {
            return 1;
        }
        if (parkingInfo.getArrivalDate().getDay() < ship.parkingInfo.getArrivalDate().getDay())
        {
            return -1;
        }
        if (parkingInfo.getArrivalDate().getHour() > ship.parkingInfo.getArrivalDate().getHour())
        {
            return 1;
        }
        if (parkingInfo.getArrivalDate().getHour() < ship.parkingInfo.getArrivalDate().getHour())
        {
            return -1;
        }
        if (parkingInfo.getArrivalDate().getMinute() > ship.parkingInfo.getArrivalDate().getMinute())
        {
            return 1;
        }
        if (this.parkingInfo.getArrivalDate().getMinute() < ship.parkingInfo.getArrivalDate().getMinute())
        {
            return -1;
        }
        if (parkingInfo.getArrivalDate().getSecond() > ship.parkingInfo.getArrivalDate().getSecond())
        {
            return 1;
        }
        if (parkingInfo.getArrivalDate().getSecond() < ship.parkingInfo.getArrivalDate().getSecond())
        {
            return -1;
        }

        return 0;
    }

    public int compareDelay(Ship ship)
    {
        if (parkingInfo.getArrivalDate().getDay() == ship.parkingInfo.getArrivalDate().getDay())
        {
            if (arrivingDelay == 0)
            {
                return 0;
            }
            if (Math.abs(arrivingDelay) < Math.abs(ship.arrivingDelay))
            {
                return -1;
            }
            if (Math.abs(arrivingDelay) > Math.abs(ship.arrivingDelay))
            {
                return 1;
            }
            if (arrivingDelay < ship.arrivingDelay)
            {
                return -1;
            }
            if (arrivingDelay > ship.arrivingDelay)
            {
                return 1;
            }
        }

        return 0;
    }

    public synchronized void incCountOfCranes()
    {
        countOfCranes++;
    }

    public synchronized void unload(int capacityOfCrane)
    {
        if(!isEmpty())
        {
            countOfCargo -= capacityOfCrane;
            if (countOfCargo < 0)
            {
                countOfCargo = 0;
            }
        }
    }

    public Time calculatePeriodOfParking()
    {
        Time periodOfParking = new Time(parkingInfo.getUnloadingTime());
        Time waitingTime = new Time(parkingInfo.getWaitingTime());

        periodOfParking.addTime(waitingTime);
        periodOfParking.subtractTime(planePeriodOfParking);

        if (periodOfParking.getMonths() < 0 || periodOfParking.getDays() < 0 || periodOfParking.getHours() < 0 ||
                periodOfParking.getMinutes() < 0 || periodOfParking.getSeconds() < 0)
        {
            periodOfParking = new Time();
        }

        return periodOfParking;
    }

    public void fillParkingInfo(Date currentTime)
    {
        Date newDate = new Date(currentTime);

        newDate.addMinutes(unloadingDelay + 1);
        parkingInfo.setEndUnloadingDate(newDate);

        Time unloadingTime = newDate.subtractDate(parkingInfo.getBeginUnloadingDate());
        parkingInfo.setUnloadingTime(unloadingTime);

        newDate = new Date(parkingInfo.getBeginUnloadingDate());
        Time waitingTime = newDate.subtractDate(parkingInfo.getArrivalDate());
        parkingInfo.setWaitingTime(waitingTime);
    }
}
