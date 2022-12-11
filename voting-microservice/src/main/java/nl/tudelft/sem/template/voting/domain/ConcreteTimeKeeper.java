package nl.tudelft.sem.template.voting.domain;

import java.time.Instant;
import java.time.temporal.TemporalAmount;

public class ConcreteTimeKeeper implements TimeKeeper{

    private Instant startTime;
    private TemporalAmount temporalAmount;
    private Instant endTime;

    public ConcreteTimeKeeper(Instant startTime, TemporalAmount temporalAmount) {
        this.temporalAmount = this.temporalAmount;
        this.startTime = startTime;
        this.temporalAmount = temporalAmount;
        this.endTime = startTime.plus(temporalAmount);
    }

    /**
     * Returns the time when a voting procedure starts.
     * @return an Instant denoting the timestamp of the start time
     */
    public Instant getStartTime() {
        return startTime;
    }

    /**
     * Returns the time when a voting procedure ends.
     * @return an Instant denoting the timestamp of the end time
     */
    public Instant getEndTime() {
        return endTime;
    }

    /**
     * Returns true if the vote is still ongoing
     * @return  Returns true if the vote is still ongoing
     */
    public boolean isVoteOngoing() {
        return Instant.now().compareTo(endTime) < 0;
    }
}
