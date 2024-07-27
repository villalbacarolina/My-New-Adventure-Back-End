package com.mynewadventure.services;

import com.mynewadventure.models.Event;
import com.mynewadventure.models.Tag;
import com.mynewadventure.models.UserActivity;
import com.mynewadventure.repositories.EventRepository;
import com.mynewadventure.repositories.TagRepository;
import com.mynewadventure.repositories.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private TagRepository tagRepository;

    public boolean userIsTheOwner(UserActivity user, Event event){
        return user.getPublishedEvents().contains(event);
    }

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

    public Event updateEvent(Long eventId, Event newEventInformation, Long userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Optional<UserActivity> userActivityOptional = userActivityRepository.findById(userId);
            if (userActivityOptional.isPresent()) {
                UserActivity userActivity = userActivityOptional.get();
                if (userIsTheOwner(userActivity, event) ) {
                    event.setTitle(newEventInformation.getTitle());
                    event.setType(newEventInformation.getType());
                    event.setDate(newEventInformation.getDate());
                    event.setStartTime(newEventInformation.getStartTime());
                    event.setEndTime(newEventInformation.getEndTime());
                    event.setLocation(newEventInformation.getLocation());
                    event.setDescription(newEventInformation.getDescription());
                    event.setLink(newEventInformation.getLink());
                    eventRepository.save(event);
                    return event;
                } else {
                    throw new RuntimeException("User is not the owner of the event");
                }
            } else {
                    throw new RuntimeException("UserActivity not found with id: " + eventId);
            }
        } else {
            throw new RuntimeException("Event not found with id: " + eventId);
        }
    }

    public Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public void deleteEvent(Long eventId, Long userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Optional<UserActivity> userActivityOptional = userActivityRepository.findById(userId);
            if (userActivityOptional.isPresent()) {
                UserActivity userActivity = userActivityOptional.get();
                if (userIsTheOwner(userActivity, event) ) {
                    userActivity.getPublishedEvents().remove(event);
                    userActivityRepository.save(userActivity);
                    for (Tag tag : event.getTags()) {
                        tag.removeEventThatDoesntUseThisTag(event);
                        tagRepository.save(tag);
                    }
                    eventRepository.delete(event);
                }else{
                    throw new RuntimeException("User is not the owner of the event");
                }
            }else{
                throw new RuntimeException("UserActivity not found with id: " + eventId);
            }
        }else{
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

    public void deleteSavedEvent(Long eventId, Long userId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Optional<UserActivity> userActivityOptional = userActivityRepository.findById(userId);
            if (userActivityOptional.isPresent()) {
                UserActivity userActivity = userActivityOptional.get();
                userActivity.getSavedEvents().remove(event);
                userActivityRepository.save(userActivity);
            } else {
                throw new RuntimeException("UserActivity not found with id: " + userId);
            }
        } else {
            throw new RuntimeException("Event not found with id: " + eventId);
        }
    }
}