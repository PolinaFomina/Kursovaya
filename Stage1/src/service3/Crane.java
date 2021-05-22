package service3;

import service1.CargoType;
import service1.Ship;

public class Crane implements Runnable
{
    private final CargoType cargoType;
    private final int capacity;
    private final Port port;
    private Ship ship;

    public Crane(int capacity, CargoType cargoType, Port port)
    {
        this.capacity = capacity;
        this.cargoType = cargoType;
        this.port = port;
    }

    public CargoType getCargoType()
    {
        return cargoType;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public Port getPort()
    {
        return port;
    }

    public Ship getShip()
    {
        return ship;
    }

    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    public void run()
    {
        while(!port.isWorkDone())
        {
            if(ship == null)
            {
                Ship shipToUnload = port.getShipToUnload(cargoType);
                if(shipToUnload != null)
                {
                    ship = shipToUnload;
                }
                if(ship != null && ship.getParkingInfo().getBeginUnloadingDate().isMissing())
                {
                    ship.getParkingInfo().setBeginUnloadingDate(port.getCurrentTime());
                }
            }

            if(ship != null)
            {
                ship.unload(capacity);

                if(ship.isEmpty())
                {
                    if (ship.getParkingInfo().getEndUnloadingDate().isMissing())
                    {
                        ship.fillParkingInfo(port.getCurrentTime());
                    }

                    ship = null;
                }
            }

            port.passedMinute();
        }
    }

}
