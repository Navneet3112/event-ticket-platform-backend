package com.navneet.event_ticket_platform.Services.Impl;

import com.navneet.event_ticket_platform.Exceptions.EventNotFoundException;
import com.navneet.event_ticket_platform.Exceptions.EventUpdateException;
import com.navneet.event_ticket_platform.Exceptions.TicketTypeNotFoundException;
import com.navneet.event_ticket_platform.Exceptions.UserNotFoundException;
import com.navneet.event_ticket_platform.Repo.EventRepository;
import com.navneet.event_ticket_platform.Repo.UserRepository;
import com.navneet.event_ticket_platform.Services.EventService;
import com.navneet.event_ticket_platform.domain.CreateEventRequest;
import com.navneet.event_ticket_platform.domain.Entities.Enums.EventStatus;
import com.navneet.event_ticket_platform.domain.Entities.Event;
import com.navneet.event_ticket_platform.domain.Entities.TicketType;
import com.navneet.event_ticket_platform.domain.Entities.User;
import com.navneet.event_ticket_platform.domain.UpdateEventRequest;
import com.navneet.event_ticket_platform.domain.UpdateTicketTypeRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer=userRepository.findById(organizerId).orElseThrow(()->new UserNotFoundException(
                String.format("User with ID %s' not found",organizerId))
        );

        Event eventToCreate=new Event();

        List<TicketType> ticketTypeList=event.getTicketTypes().stream().map(
                ticketType -> {
                        TicketType ticketTypeToCreate=new TicketType();
                        ticketTypeToCreate.setName(ticketType.getName());
                        ticketTypeToCreate.setPrice(ticketType.getPrice());
                        ticketTypeToCreate.setDescription(ticketType.getDescription());
                        ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                        ticketTypeToCreate.setEvent(eventToCreate);
                        return ticketTypeToCreate;
                }
        ).toList();

        eventToCreate.setOrganizer(organizer);
        eventToCreate.setName(event.getName());
        eventToCreate.setStartTime(event.getStartTime());
        eventToCreate.setEndTime(event.getEndTime());
        eventToCreate.setSalesStartTime(event.getSalesStartTime());
        eventToCreate.setSalesEndTime(event.getSalesEndTime());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setTicketTypes(ticketTypeList);

        eventRepository.save(eventToCreate);
        return eventToCreate;
    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId,Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId,pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
        return eventRepository.findByIdAndOrganizerId(eventId,organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest event) {
        if (event.getId()==null){
            throw new EventUpdateException("Event Id cannot be null");
        }

        if (!eventId.equals(event.getId())){
            throw new EventUpdateException("Event Id does not match");
        }

        Event existingEvent=eventRepository.findByIdAndOrganizerId(eventId,organizerId)
                .orElseThrow(()-> new EventNotFoundException(
                        String.format("Event with id %s does not exist",eventId))
                );
        existingEvent.setName(event.getName());
        existingEvent.setStartTime(event.getStartTime());
        existingEvent.setEndTime(event.getEndTime());
        existingEvent.setSalesStartTime(event.getSalesStartTime());
        existingEvent.setSalesEndTime(event.getSalesEndTime());
        existingEvent.setStatus(event.getStatus());
        existingEvent.setVenue(event.getVenue());

        Set<UUID> requestTicketTypeId= event.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest->UpdateTicketTypeRequest.getId())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(existingTicketType ->
                !requestTicketTypeId.contains(existingTicketType.getId())
        );

        Map<UUID,TicketType> existingTicketTypesIndex=existingEvent.getTicketTypes()
                .stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        for (UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
            if (null==ticketType.getId()){
                //CREATE CASE
                TicketType ticketTypeToCreate=new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                existingEvent.getTicketTypes().add(ticketTypeToCreate);
                ticketTypeToCreate.setEvent(existingEvent);

            }
            else if (existingTicketTypesIndex.containsKey(ticketType.getId())){
                //UPDATE CASE
                TicketType existingTicketType=existingTicketTypesIndex.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());

            }
            else{
                throw new TicketTypeNotFoundException(String.format(
                        "Ticket type with id %s does not exist",ticketType.getId()
                ));
            }
        }

        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID eventId) {
        getEventForOrganizer(organizerId,eventId).ifPresent(eventToDelete->eventRepository.delete(eventToDelete));
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatus.PUBLISHED,pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query,pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID eventId) {
        return eventRepository.findByIdAndStatus(eventId,EventStatus.PUBLISHED);
    }
}
