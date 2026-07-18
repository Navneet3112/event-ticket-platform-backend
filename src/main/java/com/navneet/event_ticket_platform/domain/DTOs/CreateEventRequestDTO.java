package com.navneet.event_ticket_platform.domain.DTOs;

import com.navneet.event_ticket_platform.domain.Entities.Enums.EventStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateEventRequestDTO {
    @NotBlank(message = "Event name is required")
    private String name;

    @NotNull @Future
    private LocalDateTime startTime;

    @NotNull @Future
    private LocalDateTime endTime;

    @NotBlank(message = "Venue Details are required")
    private String venue;

    private LocalDateTime salesStartTime;

    private LocalDateTime salesEndTime;

    @NotNull(message = "Event status must be provided")
    private EventStatus status;

    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    private List<CreateTicketTypeRequestDTO> ticketTypes;

}
