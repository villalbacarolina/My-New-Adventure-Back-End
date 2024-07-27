package com.mynewadventure.models;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter @Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotBlank @Size(min = 2, max = 20)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Event> eventsThatUseThisTag;


    public Tag() {
        eventsThatUseThisTag = new HashSet<Event>();
    }

    public Tag(String name) {
        this();
        this.name = name;
    }


    public void addEventThatUseThisTag(Event event) {
        if(eventsThatUseThisTag.add(event))
            event.getTags().add(this);
    }

    public void removeEventThatDoesntUseThisTag(Event event) {
        if (eventsThatUseThisTag.remove(event))
            event.getTags().remove(this);
    }

}