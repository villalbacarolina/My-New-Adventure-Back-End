package com.mynewadventure.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;


@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    @ManyToMany
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    private String description;
    private String link;
    @ManyToOne
    @JoinColumn(name = "user_activity_id")
    private UserActivity publisher;

    //--------CONSTRUCTORS--------

    public Event() {
        tags = new HashSet<Tag>();
    }

    public Event(String title, String type, LocalDate date, LocalTime startTime, LocalTime endTime, String location, String description, String link) {
        this();
        this.title = title;
        this.type = type;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
        this.link = link;
        //this.publisher = publisher;
    }

    //--------GETTERS & SETTERS--------

    public Set<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag) {
        if(tags.add(tag))
            tag.getEventsThatUseThisTag().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
