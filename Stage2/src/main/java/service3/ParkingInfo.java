package service3;

import service1.Date;
import service1.Time;

public class ParkingInfo
{
    private Date arrivalDate;
    private Time waitingTime;
    private Date beginUnloadingDate;
    private Time unloadingTime;
    private Date endUnloadingDate;

    public ParkingInfo()
    {
        this.arrivalDate = new Date();
        this.waitingTime = new Time();
        this.beginUnloadingDate = new Date();
        this.unloadingTime = new Time();
        this.endUnloadingDate = new Date();
    }

    public ParkingInfo(Date arrivalDate)
    {
        this.arrivalDate = new Date(arrivalDate);
        this.waitingTime = new Time();
        this.beginUnloadingDate = new Date();
        this.unloadingTime = new Time();
        this.endUnloadingDate = new Date();
    }

    public ParkingInfo(ParkingInfo parkingInfo)
    {
        this.arrivalDate = new Date(parkingInfo.arrivalDate);
        this.waitingTime = new Time(parkingInfo.waitingTime);
        this.beginUnloadingDate = new Date(parkingInfo.beginUnloadingDate);
        this.unloadingTime = new Time(parkingInfo.unloadingTime);
        this.endUnloadingDate = new Date(parkingInfo.endUnloadingDate);
    }

    public Date getArrivalDate()
    {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate)
    {
        this.arrivalDate = new Date (arrivalDate);
    }

    public Time getWaitingTime()
    {
        return waitingTime;
    }

    public void setWaitingTime(Time waitingTime)
    {
        this.waitingTime = new Time(waitingTime);
    }

    public Date getBeginUnloadingDate()
    {
        return beginUnloadingDate;
    }

    public void setBeginUnloadingDate(Date beginUnloadingDate)
    {
        this.beginUnloadingDate = new Date(beginUnloadingDate);
    }

    public Time getUnloadingTime()
    {
        return unloadingTime;
    }

    public void setUnloadingTime(Time unloadingTime)
    {
        this.unloadingTime = new Time(unloadingTime);
    }

    public Date getEndUnloadingDate()
    {
        return endUnloadingDate;
    }

    public void setEndUnloadingDate(Date endUnloadingDate)
    {
        this.endUnloadingDate = new Date(endUnloadingDate);
    }

    @Override
    public String toString()
    {
        return "\narrivalDate: " + arrivalDate +
                "\nwaitingTime: " + waitingTime +
                "\nbeginningUnloadingDate: " + beginUnloadingDate +
                "\nunloadingTime: " + unloadingTime +
                "\nendUnloadingDate: " + endUnloadingDate;
    }

}