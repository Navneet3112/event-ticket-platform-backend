package com.navneet.event_ticket_platform.Services;

import com.google.zxing.WriterException;
import com.navneet.event_ticket_platform.domain.Entities.QRCode;
import com.navneet.event_ticket_platform.domain.Entities.Ticket;

import java.io.IOException;
import java.util.UUID;

public interface QRCodeService {

    QRCode generateQRCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
