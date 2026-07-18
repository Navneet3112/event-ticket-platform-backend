package com.navneet.event_ticket_platform.Repo;

import com.navneet.event_ticket_platform.domain.Entities.Enums.QRCodeStatus;
import com.navneet.event_ticket_platform.domain.Entities.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode,UUID> {
    Optional<QRCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID ticketPurchaserId);
    Optional<QRCode> findByIdAndStatus(UUID id, QRCodeStatus status);
}
