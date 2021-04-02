package servis3;

import servis1.Ship;

import java.util.ArrayList;
import java.util.List;

public class CraneThread extends Thread
{
    public List<Ship> queueOfShips = new ArrayList<Ship>();
    public double loadCapacityOfCrane;
    public double time;

    CraneThread(List<Ship> queueOfShips, double loadCapacityOfCrane)
    {
        this.queueOfShips = queueOfShips;
        this.loadCapacityOfCrane = loadCapacityOfCrane;
        time = 0;
    }

    @Override
    public void run() {
    }
}