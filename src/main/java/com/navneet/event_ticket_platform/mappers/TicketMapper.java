package com.navneet.event_ticket_platform.mappers;

import com.navneet.event_ticket_platform.domain.DTOs.GetTicketResponseDTO;
import com.navneet.event_ticket_platform.domain.DTOs.ListTicketResponseDTO;
import com.navneet.event_ticket_platform.domain.DTOs.ListTicketTicketTypeResponseDTO;
import com.navneet.event_ticket_platform.domain.Entities.Ticket;
import com.navneet.event_ticket_platform.domain.Entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketTicketTypeResponseDTO toListTicketTicketTypeResponseDTO(TicketType ticketType);

    ListTicketResponseDTO toListTicketResponseDTO(Ticket ticket);

    @Mapping(target = "price", source = "ticketType.price")
    @Mapping(target = "description", source = "ticketType.description")
    @Mapping(target = "eventName", source = "ticketType.event.name")
    @Mapping(target = "eventVenue", source = "ticketType.event.venue")
    @Mapping(target = "eventStart", source = "ticketType.event.startTime")
    @Mapping(target = "eventEnd", source = "ticketType.event.endTime")
    GetTicketResponseDTO toGetTicketResponseDTO(Ticket ticket);
}
