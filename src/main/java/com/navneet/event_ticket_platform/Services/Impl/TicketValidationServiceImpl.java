package com.navneet.event_ticket_platform.Services.Impl;

import com.navneet.event_ticket_platform.Exceptions.QRCodeNotFoundException;
import com.navneet.event_ticket_platform.Exceptions.TicketNotFoundException;
import com.navneet.event_ticket_platform.Repo.QRCodeRepository;
import com.navneet.event_ticket_platform.Repo.TicketRepository;
import com.navneet.event_ticket_platform.Repo.TicketValidationRepository;
import com.navneet.event_ticket_platform.Services.TicketValidationService;
import com.navneet.event_ticket_platform.domain.Entities.Enums.QRCodeStatus;
import com.navneet.event_ticket_platform.domain.Entities.Enums.TicketValidationMethod;
import com.navneet.event_ticket_platform.domain.Entities.Enums.TicketValidationStatus;
import com.navneet.event_ticket_platform.domain.Entities.QRCode;
import com.navneet.event_ticket_platform.domain.Entities.Ticket;
import com.navneet.event_ticket_platform.domain.Entities.TicketValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;
    private final QRCodeRepository qrCodeRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QRCode qrCode=qrCodeRepository.findByIdAndStatus(qrCodeId, QRCodeStatus.ACTIVE)
                .orElseThrow(()->new QRCodeNotFoundException(
                        String.format("QR code with id %s not found", qrCodeId)
                ));

        Ticket ticket=qrCode.getTicket();

        return validateTicket(ticket, TicketValidationMethod.QR_SCAN);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket=ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        return validateTicket(ticket, TicketValidationMethod.MANUAL);

    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethod ticketValidationMethod) {
        TicketValidation ticketValidation=new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(ticketValidationMethod);

        TicketValidationStatus ticketValidationStatus=ticket.getValidations()
                .stream()
                .filter(v-> TicketValidationStatus.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v->TicketValidationStatus.INVALID)
                .orElse(TicketValidationStatus.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }
}
