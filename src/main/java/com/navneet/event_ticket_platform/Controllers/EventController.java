package com.navneet.event_ticket_platform.Controllers;


import com.navneet.event_ticket_platform.Services.EventService;
import com.navneet.event_ticket_platform.domain.CreateEventRequest;
import com.navneet.event_ticket_platform.domain.DTOs.*;
import com.navneet.event_ticket_platform.domain.Entities.Event;
import com.navneet.event_ticket_platform.domain.UpdateEventRequest;
import com.navneet.event_ticket_platform.mappers.EventMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

import static com.navneet.event_ticket_platform.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path="/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDTO createEventRequestDTO) {
        CreateEventRequest createEventRequest=eventMapper.toCreateEventRequest(createEventRequestDTO);
        UUID userId=parseUserId(jwt);

        Event createdEvent=eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDTO createdEventResponseDTO=eventMapper.toCreateEventResponseDTO(createdEvent);

        return new ResponseEntity<>(createdEventResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping(path="/{eventId}")
    public ResponseEntity<UpdateEventResponseDTO> UpdateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDTO updateEventRequestDTO) {
        UpdateEventRequest updateEventRequest=eventMapper.toUpdateEventRequest(updateEventRequestDTO);
        UUID userId=parseUserId(jwt);

        Event updatedEvent=eventService.updateEventForOrganizer(userId,eventId,updateEventRequest);
        UpdateEventResponseDTO updatedEventResponseDTO=eventMapper.toUpdateEventResponseDTO(updatedEvent);

        return ResponseEntity.ok(updatedEventResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDTO>> listEvents(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ){
        UUID userId=parseUserId(jwt);
        Page<Event> events=eventService.listEventsForOrganizer(userId, pageable);
        return ResponseEntity.ok(events.map(
                event->eventMapper.toCreateListEventResponseDTO(event)
                )
        );
    }

    @GetMapping(path="/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDTO> getEventDetails(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ){
        UUID userId=parseUserId(jwt);
        return eventService.getEventForOrganizer(userId, eventId)
                .map(event->eventMapper.toGetEventDetailsResponseDTO(event))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId){
        UUID userId=parseUserId(jwt);
        eventService.deleteEventForOrganizer(userId, eventId);
        return ResponseEntity.noContent().build();
    }
}
