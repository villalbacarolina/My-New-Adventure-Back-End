package com.mynewadventure.repositories;

import com.mynewadventure.models.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Event e WHERE e.publisher.id = :userId")
    void deleteEventsByPublisher(Long userId);
}

