package com.navneet.event_ticket_platform.domain.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListTicketTicketTypeResponseDTO {
    private UUID id;
    private String name;
    private Double price;
}
