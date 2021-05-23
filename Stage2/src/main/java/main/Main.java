package main;

import service1.TimeTable;
import service2.InteractionWithJson;
import service3.Port;
import service3.Statistic;

public class Main
{
    public static final String fileNameForTimetable = "Timetable";
    public static final String fileNameForStatistic = "Statistics";

    public static void main(String[] args)
    {
        TimeTable timeTable = new TimeTable(100);
        timeTable.addShipFromConsole();
        System.out.println("\nTimetable:\n");
        System.out.println(timeTable);
        InteractionWithJson.timeTableToJson(timeTable, fileNameForTimetable);

        Port port = new Port();
        port.calculateOptionalNumberOfCranes();

        System.out.println("Total statistic:");
        Statistic statistic = new Statistic(port);
        System.out.println(statistic);
        InteractionWithJson.statisticsToJson(statistic, fileNameForStatistic);
    }
}