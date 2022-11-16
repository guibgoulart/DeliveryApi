package com.deliveryapi.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Event {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Delivery delivery;

    private String description;
    private OffsetDateTime eventDate;
}
