package com.navneet.event_ticket_platform.domain.DTOs;

import com.navneet.event_ticket_platform.domain.Entities.Enums.TicketValidationMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDTO {
    private UUID id;
    private TicketValidationMethod method;
}
