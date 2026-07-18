package com.navneet.event_ticket_platform.Controllers;


import com.navneet.event_ticket_platform.Services.QRCodeService;
import com.navneet.event_ticket_platform.Services.TicketService;
import com.navneet.event_ticket_platform.domain.DTOs.GetTicketResponseDTO;
import com.navneet.event_ticket_platform.domain.DTOs.ListTicketResponseDTO;
import com.navneet.event_ticket_platform.mappers.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.navneet.event_ticket_platform.util.JwtUtil.parseUserId;

@RestController
@RequestMapping("api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final QRCodeService qRCodeService;

    @GetMapping
    public Page<ListTicketResponseDTO> listTickets(@AuthenticationPrincipal Jwt jwt,
                                                    Pageable pageable){
        return ticketService.listTicketsForUser(parseUserId(jwt), pageable)
                .map(ticketMapper::toListTicketResponseDTO);
    }

    @GetMapping("/{tickedId}")
    public ResponseEntity<GetTicketResponseDTO> getTicket(@PathVariable UUID tickedId,
                                                          @AuthenticationPrincipal Jwt jwt){
        return ticketService.getTicketForUser(parseUserId(jwt), tickedId)
                .map(ticketMapper::toGetTicketResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
            @PathVariable UUID ticketId,
            @AuthenticationPrincipal Jwt jwt
    ){
        byte[] qrCodeImage=qRCodeService.getQrCodeImageForUserAndTicket(parseUserId(jwt), ticketId);

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeImage.length);

        return ResponseEntity.ok().headers(headers).body(qrCodeImage);
    }

}
