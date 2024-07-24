package com.example.demo.Controller;

import com.example.demo.Request.CreateEventRequest;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private Calendar calendar;

    @GetMapping("/events")
    public List<Event> getEvent() throws Exception {
        Events events = calendar.events().list("primary").execute();
        return events.getItems();
    }

    @PostMapping
    public String createEvent(@RequestBody CreateEventRequest createEventRequest) throws Exception {
        Event event = new Event()
                .setSummary(createEventRequest.getSummary())
                .setLocation(createEventRequest.getLocation())
                .setDescription(createEventRequest.getDescription());


        return calendar.events().insert("primary", event).execute().getHtmlLink();
    }


}
