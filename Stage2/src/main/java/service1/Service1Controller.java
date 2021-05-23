package service1;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/service1")
public class Service1Controller
{
    @GetMapping("/getTimetable")
    @ResponseBody
    public TimeTable getTimeTable(@RequestParam int countOfShips)
    {
        if (countOfShips <= 0)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The number of ships can't be negative or equal to zero");
        }

        TimeTable timeTable = new TimeTable(countOfShips);
        timeTable.addShipFromConsole();
        System.out.println(timeTable);

        return timeTable;
    }
}