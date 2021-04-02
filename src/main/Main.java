package main;

import servis1.TimeTable;
import servis3.Port;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        TimeTable timeTable = new TimeTable();
        Port port = new Port(timeTable);
        timeTable.print();
        port.print();
    }
}
