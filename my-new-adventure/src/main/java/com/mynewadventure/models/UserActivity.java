package com.mynewadventure.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "useractivity_event",
            joinColumns = @JoinColumn(name = "useractivity_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> savedEvents;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Event> publishedEvents;

    public void addPublishedEvent(Event event) {
        if (publishedEvents.add(event)) {
            event.setPublisher(this);
        }
    }

}
