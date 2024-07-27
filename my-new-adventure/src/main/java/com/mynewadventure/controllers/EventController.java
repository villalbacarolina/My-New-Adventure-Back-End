package com.mynewadventure.controllers;

import com.mynewadventure.models.Event;
import com.mynewadventure.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @PostMapping("/create/")
    public ResponseEntity<Event> createEvent(@RequestBody Event event, @RequestParam Long userId) {
        return ResponseEntity.ok(eventService.createEvent(event, userId));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id,
                                             @RequestBody Event newEventInformationRequest,
                                             @RequestParam Long userId) {
        return ResponseEntity.ok(eventService.updateEvent( id, newEventInformationRequest, userId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, @RequestParam Long userId) {
        eventService.deleteEvent(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/save/{eventId}/")
    public ResponseEntity<Void> saveEvent(@PathVariable Long eventId, @RequestParam Long userId) {
        eventService.saveEvent(eventId, userId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/delete-saved-event/{eventId}/")
    public ResponseEntity<Void> deleteSavedEvent(@PathVariable Long eventId, @RequestParam Long userId) {
        eventService.deleteSavedEvent(eventId, userId);
        return ResponseEntity.ok().build();
    }
}
