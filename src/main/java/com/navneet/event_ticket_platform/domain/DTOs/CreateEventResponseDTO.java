package com.navneet.event_ticket_platform.domain.DTOs;

import com.navneet.event_ticket_platform.domain.CreateTicketTypeRequest;
import com.navneet.event_ticket_platform.domain.Entities.Enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventResponseDTO {

    private UUID id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String venue;
    private LocalDateTime salesStartTime;
    private LocalDateTime salesEndTime;
    private EventStatus status;
    private List<CreateTicketTypeResponseDTO> ticketTypes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
