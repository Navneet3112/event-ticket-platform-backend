package com.navneet.event_ticket_platform.domain;

import com.navneet.event_ticket_platform.domain.Entities.Enums.EventStatus;
import com.navneet.event_ticket_platform.domain.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequest {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String venue;
    private LocalDateTime salesStartTime;
    private LocalDateTime salesEndTime;
    private EventStatus status;
    private List<CreateTicketTypeRequest> ticketTypes=new ArrayList<>();
}
