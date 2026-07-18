package com.navneet.event_ticket_platform.domain.Entities;

import com.navneet.event_ticket_platform.domain.Entities.Enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false,updatable = false)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ticket_type_id")
    private TicketType ticketType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="purchaser_id")
    private User purchaser;

    @OneToMany(mappedBy="ticket",cascade = CascadeType.ALL)
    private List<TicketValidation> validations=new ArrayList<>();

    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL)
    private List<QRCode>  qrCodes=new ArrayList<>();

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket other)) return false;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
