package com.navneet.event_ticket_platform.Services;

import com.navneet.event_ticket_platform.domain.Entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {
    TicketValidation validateTicketByQrCode(UUID qrCodeId);
    TicketValidation validateTicketManually(UUID ticketId);
}
