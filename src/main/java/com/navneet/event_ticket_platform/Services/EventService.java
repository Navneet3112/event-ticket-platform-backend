package com.navneet.event_ticket_platform.Services;

import com.navneet.event_ticket_platform.domain.CreateEventRequest;
import com.navneet.event_ticket_platform.domain.CreateTicketTypeRequest;
import com.navneet.event_ticket_platform.domain.Entities.Event;
import com.navneet.event_ticket_platform.domain.UpdateEventRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);

    Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId);

    Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest event);

    void deleteEventForOrganizer(UUID organizerId, UUID eventId);

    Page<Event> listPublishedEvents(Pageable pageable);

    Page<Event> searchPublishedEvents(String Query,Pageable pageable);

    Optional<Event> getPublishedEvent(UUID eventId);
}
