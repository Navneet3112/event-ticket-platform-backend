package com.navneet.event_ticket_platform.Services.Impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.navneet.event_ticket_platform.Exceptions.QRCodeGenerationException;
import com.navneet.event_ticket_platform.Exceptions.QRCodeNotFoundException;
import com.navneet.event_ticket_platform.Repo.QRCodeRepository;
import com.navneet.event_ticket_platform.Services.QRCodeService;
import com.navneet.event_ticket_platform.domain.Entities.Enums.QRCodeStatus;
import com.navneet.event_ticket_platform.domain.Entities.QRCode;
import com.navneet.event_ticket_platform.domain.Entities.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QRCodeServiceImpl implements QRCodeService {
    private static final int QR_HEIGHT=300;
    private static final int QR_WIDTH=300;

    private final QRCodeRepository qrCodeRepository;
    private final QRCodeWriter qrCodeWriter;

    @Override
    public QRCode generateQRCode(Ticket ticket) {
        try {
            UUID uniqueId = UUID.randomUUID();
            String qrCodeImage = generateQrCodeImage(uniqueId);

            QRCode qrcode=new QRCode();
            qrcode.setId(uniqueId);
            qrcode.setStatus(QRCodeStatus.ACTIVE);
            qrcode.setValue(qrCodeImage);
            qrcode.setTicket(ticket);

            qrCodeRepository.saveAndFlush(qrcode);
            return qrcode;

        }catch (WriterException | IOException ex){
            throw new QRCodeGenerationException("QR code generation failed",ex);
        }
    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QRCode qrCode=qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(QRCodeNotFoundException::new);

        try{
            return Base64.getDecoder().decode(qrCode.getValue());
        } catch (IllegalArgumentException ex) {
            log.error("Invalid Base64 QrCode for ticket ID: {}",ticketId,ex);
            throw new QRCodeNotFoundException();
        }
    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
        BitMatrix bitMatrix=qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT
        );

        BufferedImage qrCodeImage=MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream baos=new ByteArrayOutputStream()){
            ImageIO.write(qrCodeImage,"png",baos);
            byte[] imageBytes=baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
