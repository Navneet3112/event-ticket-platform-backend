package com.navneet.event_ticket_platform.domain.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEventTicketTypeResponseDTO {
    private UUID id;
    private String name;
    private Double price;
    private Integer totalAvailable;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
