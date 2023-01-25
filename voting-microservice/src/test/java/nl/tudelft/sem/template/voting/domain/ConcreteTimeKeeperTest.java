package nl.tudelft.sem.template.voting.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAmount;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConcreteTimeKeeperTest {
    private static Instant startTime = Instant.now();
    private static TemporalAmount temporalAmount = Period.ofDays(4);
    private static Instant endTime = startTime.plus(temporalAmount);

    private static ConcreteTimeKeeper sut;

    @BeforeEach
    void setUp(){
        sut = new ConcreteTimeKeeper(startTime, temporalAmount);
    }

    @Test
    void getStartTime() {
        assertEquals(startTime, sut.getStartTime());
    }

    @Test
    void getEndTime() {
        assertEquals(endTime, sut.getEndTime());
    }
    @Test
    void getDurationInSeconds() {
        assertEquals((endTime.getLong(ChronoField.INSTANT_SECONDS)
                - startTime.getLong(ChronoField.INSTANT_SECONDS)), sut.getDurationInSeconds());
    }
}