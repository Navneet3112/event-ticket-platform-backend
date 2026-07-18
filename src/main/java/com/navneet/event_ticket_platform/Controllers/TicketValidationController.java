package com.navneet.event_ticket_platform.Controllers;

import com.navneet.event_ticket_platform.Services.TicketValidationService;
import com.navneet.event_ticket_platform.domain.DTOs.TicketValidationRequestDTO;
import com.navneet.event_ticket_platform.domain.DTOs.TicketValidationResponseDTO;
import com.navneet.event_ticket_platform.domain.Entities.Enums.TicketValidationMethod;
import com.navneet.event_ticket_platform.domain.Entities.TicketValidation;
import com.navneet.event_ticket_platform.mappers.TicketValidationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {
    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDTO> validateTicket(
            @Valid @RequestBody TicketValidationRequestDTO ticketValidationRequestDTO) {

        TicketValidationMethod method=ticketValidationRequestDTO.getMethod();

        TicketValidation ticketValidation;
        if (method==TicketValidationMethod.QR_SCAN) {
            ticketValidation=ticketValidationService.validateTicketByQrCode(ticketValidationRequestDTO.getId());
        }else{
            ticketValidation=ticketValidationService.validateTicketManually(ticketValidationRequestDTO.getId());
        }

        return ResponseEntity.ok(ticketValidationMapper.toTicketValidationResponseDTO(ticketValidation));
    }
}
