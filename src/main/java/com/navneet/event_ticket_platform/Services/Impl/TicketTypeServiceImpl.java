package com.navneet.event_ticket_platform.Services.Impl;


import com.navneet.event_ticket_platform.Exceptions.TicketSoldOutException;
import com.navneet.event_ticket_platform.Exceptions.TicketTypeNotFoundException;
import com.navneet.event_ticket_platform.Exceptions.UserNotFoundException;
import com.navneet.event_ticket_platform.Repo.QRCodeRepository;
import com.navneet.event_ticket_platform.Repo.TicketRepository;
import com.navneet.event_ticket_platform.Repo.TicketTypeRepository;
import com.navneet.event_ticket_platform.Repo.UserRepository;
import com.navneet.event_ticket_platform.Services.QRCodeService;
import com.navneet.event_ticket_platform.Services.TicketTypeService;
import com.navneet.event_ticket_platform.domain.Entities.Enums.TicketStatus;
import com.navneet.event_ticket_platform.domain.Entities.Ticket;
import com.navneet.event_ticket_platform.domain.Entities.TicketType;
import com.navneet.event_ticket_platform.domain.Entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QRCodeService qrCodeService;


    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(
                String.format("User with id %s not found", userId)
        ));

        TicketType ticketType=ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(()->new TicketTypeNotFoundException(
                String.format("Ticket type with id %s not found", ticketTypeId)
        ));

        int purchasedTickets=ticketRepository.countByTicketTypeId(ticketTypeId);

        Integer totalAvailable=ticketType.getTotalAvailable();

        if (purchasedTickets+1>totalAvailable) {
            throw new TicketSoldOutException();
        }


        Ticket ticket=new Ticket();
        ticket.setStatus(TicketStatus.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket=ticketRepository.save(ticket);
        qrCodeService.generateQRCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
