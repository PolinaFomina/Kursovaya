package service3;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import service1.TimeTable;

@Controller
@RequestMapping("/service3")
public class Service3Controller
{
    @PostMapping("/portWork")
    @ResponseBody
    public void portWork(@RequestParam int countOfShips)
    {
        String url1 = "http://localhost:8081/service2/getTimetable?countOfShips=" + countOfShips;

        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<TimeTable> responseEntity1 = restTemplate1.getForEntity(url1, TimeTable.class);
        TimeTable timeTable = responseEntity1.getBody();

        Port port = new Port(timeTable);
        port.calculateOptionalNumberOfCranes();

        System.out.println("Total statistic:");
        Statistic statistic = new Statistic(port);
        System.out.println(statistic);

        String url2 = "http://localhost:8081/service2/saveStatistic";

        RestTemplate restTemplate2 = new RestTemplate();
        restTemplate2.postForEntity(url2, statistic, Statistic.class);
    }
}