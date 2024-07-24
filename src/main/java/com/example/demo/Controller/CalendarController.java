package com.example.demo.Controller;

import com.example.demo.Request.CreateEventRequest;
import com.example.demo.Service.CalendarService;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private Calendar calendar;
    @Autowired
    private CalendarService calendarService;

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvent() {

    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest createEventRequest) throws Exception {
        Event event = new Event()
                .setSummary(createEventRequest.getSummary())
                .setLocation(createEventRequest.getLocation())
                .setDescription(createEventRequest.getDescription());

        DateTime startDateTime = new DateTime(createEventRequest.getStartDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(TimeZone.getDefault().getID());
        event.setStart(start);

        DateTime endDateTime = new DateTime(createEventRequest.getEndDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(TimeZone.getDefault().getID());
        event.setEnd(end);

       calendar.events().insert("primary", event).execute();
        return new ResponseEntity<>(event, HttpStatus.CREATED);

    }


}
