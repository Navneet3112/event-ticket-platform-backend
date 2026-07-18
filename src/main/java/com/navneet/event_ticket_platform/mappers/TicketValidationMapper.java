package com.navneet.event_ticket_platform.mappers;

import com.navneet.event_ticket_platform.domain.DTOs.TicketValidationResponseDTO;
import com.navneet.event_ticket_platform.domain.Entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId", source = "ticket.id")
    TicketValidationResponseDTO toTicketValidationResponseDTO(TicketValidation ticketValidation);
}
