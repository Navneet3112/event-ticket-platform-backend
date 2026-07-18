package com.navneet.event_ticket_platform.Controllers;

import com.navneet.event_ticket_platform.Services.EventService;
import com.navneet.event_ticket_platform.domain.DTOs.GetPublishedEventDetailsResponseDTO;
import com.navneet.event_ticket_platform.domain.DTOs.ListPublishedEventResponseDTO;
import com.navneet.event_ticket_platform.domain.Entities.Event;
import com.navneet.event_ticket_platform.mappers.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDTO>> getAllPublishedEvents(
            @RequestParam(required = false) String searchTerm,
            Pageable pageable) {
        Page<Event> events;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            events=eventService.searchPublishedEvents(searchTerm, pageable);
        }else{
            events=eventService.listPublishedEvents(pageable);
        }
        return ResponseEntity.ok(events
                .map(eventMapper::toListPublishedEventResponseDTO));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDTO> getPublishedEventDetails(
            @PathVariable UUID eventId) {
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
