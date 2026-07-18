package com.navneet.event_ticket_platform.domain.DTOs;

import com.navneet.event_ticket_platform.domain.Entities.Enums.TicketValidationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDTO {

    @NotNull(message = "Ticket id is required")
    private UUID ticketId;

    @NotNull(message = "Validation method is required")
    private TicketValidationStatus status;
}
