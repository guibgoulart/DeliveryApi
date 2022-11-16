package com.deliveryapi.domain.model;

import com.deliveryapi.domain.exception.DomainException;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Delivery {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @Embedded
    private Recipient recipient;

    private BigDecimal cost;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private OffsetDateTime requestDate;

    private OffsetDateTime deliveredDate;

    public Event addEvent(String description) {
        Event event = Event.builder()
                .description(description)
                .eventDate(OffsetDateTime.now())
                .delivery(this)
                .build();

        this.getEvents().add(event);

        return event;
    }

    public void finish() {
        if(!canBeFinished()) {
            throw new DomainException("Delivery can't be finished");
        }

        setStatus(DeliveryStatus.FINISHED);
        setDeliveredDate(OffsetDateTime.now());
    }

    public boolean canBeFinished() {
        return DeliveryStatus.PENDING.equals(getStatus());
    }
}
