package com.mynewadventure.services;

import com.mynewadventure.models.Event;
import com.mynewadventure.models.Tag;
import com.mynewadventure.models.UserActivity;
import com.mynewadventure.repositories.EventRepository;
import com.mynewadventure.repositories.TagRepository;
import com.mynewadventure.repositories.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private TagRepository tagRepository;

    public Event createEvent(Event event, Long userId) {
        Optional<UserActivity> userActivityOptional = userActivityRepository.findById(userId);
        if (userActivityOptional.isPresent()) {
            UserActivity userActivity = userActivityOptional.get();
            event.setPublisher(userActivity);
            userActivity.addPublishedEvent(event);
            eventRepository.save(event);
            userActivityRepository.save(userActivity);
            return event;
        } else {
            throw new RuntimeException("UserActivity not found with id: " + userId);
        }
    }

    public Event updateEvent(Long eventId, Event eventDetails) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setTitle(eventDetails.getTitle());
            event.setType(eventDetails.getType());
            event.setDate(eventDetails.getDate());
            event.setStartTime(eventDetails.getStartTime());
            event.setEndTime(eventDetails.getEndTime());
            event.setLocation(eventDetails.getLocation());
            event.setDescription(eventDetails.getDescription());
            event.setLink(eventDetails.getLink());
            Set<Tag> newTags = eventDetails.getTags();
            for (Tag tag : newTags) {
                event.addTag(tag);
            }
            eventRepository.save(event);
            return event;
        } else {
            throw new RuntimeException("Event not found with id: " + eventId);
        }
    }

    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
    }

    public void deleteEvent(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            UserActivity publisher = event.getPublisher();
            if (publisher != null) {
                publisher.getPublishedEvents().remove(event);
                userActivityRepository.save(publisher);
            }
            Set<Tag> tags = event.getTags();
            for (Tag tag : tags) {
                tag.removeEventThatDoesntUseThisTag(event);
                tagRepository.save(tag);
            }
            eventRepository.delete(event);
        } else {
            throw new RuntimeException("Event not found with id: " + eventId);
        }
    }

    public void saveEvent(Long eventId, Long userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Optional<UserActivity> userActivityOptional = userActivityRepository.findById(userId);
            if (userActivityOptional.isPresent()) {
                UserActivity userActivity = userActivityOptional.get();
                userActivity.getSavedEvents().add(event);
                userActivityRepository.save(userActivity);
            } else {
                throw new RuntimeException("UserActivity not found with id: " + userId);
            }
        } else {
            throw new RuntimeException("Event not found with id: " + eventId);
        }
    }
}
