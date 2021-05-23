package service2;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

import service1.TimeTable;
import service3.Statistic;

public class InteractionWithJson
{
    public static void timeTableToJson(TimeTable timeTable, String fileName)
    {
        String pathToFile = System.getProperty("user.dir") + File.separator + fileName + ".json";

        ObjectMapper mapper = new ObjectMapper();
        try
        {
            mapper.writeValue(new File(pathToFile), timeTable);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void timeTableFromJson(TimeTable timeTable, String fileName)
    {
        String pathToFile = System.getProperty("user.dir") + File.separator + fileName + ".json";

        ObjectMapper mapper = new ObjectMapper();
        try
        {
            timeTable.setListOfShips(mapper.readValue(new File(pathToFile), TimeTable.class).getListOfShips());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void statisticsToJson(Statistic statistic, String fileName)
    {
        String pathToFile = System.getProperty("user.dir") + File.separator + fileName + ".json";

        ObjectMapper mapper = new ObjectMapper();
        try
        {
            mapper.writeValue(new File(pathToFile), statistic);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}