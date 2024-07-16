package com.mynewadventure.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Set;

import jakarta.persistence.*;


@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private image;
    private String title;
    private String type;
    private LocalDate date;
    private LocalTime time;
    private String location;

    private String description;
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    private User publisher;

    @ManyToMany
    @JoinTable(
        name = "event_tag",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
    
}
