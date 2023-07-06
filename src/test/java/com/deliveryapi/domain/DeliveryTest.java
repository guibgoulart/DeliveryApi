package com.deliveryapi.domain;

import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.domain.delivery.DeliveryStatus;
import com.deliveryapi.domain.event.Event;
import com.deliveryapi.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryTest {
    @Test
    void testAddEvent() {
        Delivery delivery = new Delivery();
        Event event = delivery.addEvent("Test Event");

        assertEquals(1, delivery.getEvents().size());
        assertEquals("Test Event", event.getDescription());
        assertEquals(delivery, event.getDelivery());
    }

    @Test
    void testFinish() {
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.PENDING);

        delivery.finish();

        assertEquals(DeliveryStatus.FINISHED, delivery.getStatus());
        assertNotNull(delivery.getDeliveredDate());
    }

    @Test
    void testFinishThrowsException() {
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.FINISHED);

        Exception exception = assertThrows(DomainException.class, delivery::finish);

        assertEquals("Delivery can't be finished", exception.getMessage());
        assertNotEquals(DeliveryStatus.PENDING, delivery.getStatus());
    }

    @Test
    void testCanBeFinished() {
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.PENDING);

        assertTrue(delivery.canBeFinished());

        delivery.setStatus(DeliveryStatus.FINISHED);

        assertFalse(delivery.canBeFinished());
    }
}