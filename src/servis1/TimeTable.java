package servis1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeTable
{
    public List<Ship> ships = new ArrayList<Ship>();
    private int countOfShips = 150;

    public TimeTable()
    {
        for (int i = 0; i < countOfShips; i++)
        {
            Ship newShip = new Ship(i);
            ships.add(newShip);
        }

        Collections.sort(ships);
    }

    public void print()
    {
        for (int i = 0; i < countOfShips; i++)
        {
            ships.get(i).print();
        }
    }

    public int getCountOfShips()
    {
        return countOfShips;
    }
}
