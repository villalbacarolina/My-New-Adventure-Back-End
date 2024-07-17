package com.mynewadventure.models;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Tag {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String name;
	
    @ManyToMany(mappedBy = "tags")
    private Set<Event> eventsThatUseThisTag;

    //--------CONSTRUCTORS--------

    public Tag() {
        eventsThatUseThisTag = new HashSet<Event>();
    }

    public Tag(String name) {
        this();
        this.name = name;
    }

    //--------GETTERS & SETTERS--------

    public Set<Event> getEventsThatUseThisTag() {
        return eventsThatUseThisTag;
    }

    public void addEventThatUseThisTag(Event event) {
        if(eventsThatUseThisTag.add(event))
            event.getTags().add(this);
    }

    public void removeEventThatDoesntUseThisTag(Event event) {
        if (eventsThatUseThisTag.remove(event))
            event.getTags().remove(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
