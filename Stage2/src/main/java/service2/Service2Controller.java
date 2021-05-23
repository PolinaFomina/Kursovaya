package service2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import main.Main;
import service1.TimeTable;
import service3.Statistic;

@Controller
@RequestMapping("/service2")
public class Service2Controller
{
    @GetMapping("/getTimetable")
    @ResponseBody
    public String getTimetable(@RequestParam int countOfShips) throws IOException
    {
        String url = "http://localhost:8080/service1/getTimetable?countOfShips=" + countOfShips;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TimeTable> responseEntity = restTemplate.getForEntity(url, TimeTable.class);
        TimeTable timeTable = responseEntity.getBody();

        InteractionWithJson.timeTableToJson(timeTable, Main.fileNameForTimetable);

        String pathToFile = System.getProperty("user.dir") + File.separator + Main.fileNameForTimetable + ".json";

        return new String(Files.readAllBytes(Paths.get(pathToFile)));
    }

    @GetMapping("/readTimetable")
    @ResponseBody
    public String readTimetable(@RequestParam String filename) throws IOException
    {
        if (!filename.equals(Main.fileNameForTimetable))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Timetable with this name is absent");
        }

        String pathToFile = System.getProperty("user.dir") + File.separator + Main.fileNameForTimetable + ".json";

        return new String(Files.readAllBytes(Paths.get(pathToFile)));
    }

    @PostMapping("/saveStatistic")
    @ResponseBody
    public void saveStatistic(@RequestBody Statistic statistic)
    {
        InteractionWithJson.statisticsToJson(statistic, Main.fileNameForStatistic);
    }
}
