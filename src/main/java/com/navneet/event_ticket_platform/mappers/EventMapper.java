package com.navneet.event_ticket_platform.mappers;

import com.navneet.event_ticket_platform.domain.CreateEventRequest;
import com.navneet.event_ticket_platform.domain.CreateTicketTypeRequest;
import com.navneet.event_ticket_platform.domain.DTOs.*;
import com.navneet.event_ticket_platform.domain.Entities.Event;
import com.navneet.event_ticket_platform.domain.Entities.TicketType;
import com.navneet.event_ticket_platform.domain.UpdateEventRequest;
import com.navneet.event_ticket_platform.domain.UpdateTicketTypeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest toCreateTicketTypeRequest(CreateTicketTypeRequestDTO createTicketTypeRequestDTO);

    CreateEventRequest toCreateEventRequest(CreateEventRequestDTO createEventRequestDTO);

    CreateEventResponseDTO toCreateEventResponseDTO(Event event);

    ListEventTicketTypeResponseDTO toCreateListEventTicketTypeResponseDTO(TicketType ticketType);

    ListEventResponseDTO toCreateListEventResponseDTO(Event event);

    GetEventTicketTypeResponseDTO toGetEventTicketTypeResponseDTO(TicketType ticketType);

    GetEventDetailsResponseDTO toGetEventDetailsResponseDTO(Event event);

    UpdateTicketTypeRequest toUpdateTicketTypeRequest(UpdateTicketTypeRequestDTO updateTicketTypeRequestDTO);

    UpdateEventRequest toUpdateEventRequest(UpdateEventRequestDTO updateEventRequestDTO);

    UpdateTicketTypeResponseDTO toUpdateTicketTypeResponseDTO(TicketType ticketType);

    UpdateEventResponseDTO toUpdateEventResponseDTO(Event event);

    ListPublishedEventResponseDTO toListPublishedEventResponseDTO(Event event);

    GetPublishedEventTicketTypeResponseDTO toGetPublishedEventTicketTypeResponseDTO(TicketType ticketType);

    GetPublishedEventDetailsResponseDTO toGetPublishedEventDetailsResponseDTO(Event event);

}
