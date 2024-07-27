package com.mynewadventure.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;


@Entity
@Getter @Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;
    private String type;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    @NotBlank
    private String location;
    @ManyToMany
    @JoinTable(
            name = "event_tag",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
    @Size(min = 1, max = 3000, message = "Title must be between 3 and 3000 characters")
    private String description;
    private String link;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_activity_id")
    @JsonBackReference
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
    }

    //--------METHODS--------

    public void addTag(Tag tag) {
        if(tags.add(tag))
            tag.getEventsThatUseThisTag().add(this);
    }

    public void removeTag(Tag tag) {
        if (tags.remove(tag))
            tag.getEventsThatUseThisTag().remove(this);
    }

    public void setPublisher(@org.jetbrains.annotations.NotNull UserActivity publisher) {
        this.publisher = publisher;
        if (!publisher.getPublishedEvents().contains(this)) {
            publisher.addPublishedEvent(this);
        }
    }

}