package com.navneet.event_ticket_platform.Services;

import com.navneet.event_ticket_platform.domain.Entities.Ticket;

import java.util.UUID;


public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}


