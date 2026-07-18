package com.navneet.event_ticket_platform.domain.DTOs;

import com.navneet.event_ticket_platform.domain.Entities.Enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDTO {
    private UUID id;
    private TicketStatus status;
    private ListTicketTicketTypeResponseDTO ticketType;
}
