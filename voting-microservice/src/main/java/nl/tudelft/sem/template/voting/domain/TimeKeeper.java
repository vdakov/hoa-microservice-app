package nl.tudelft.sem.template.voting.domain;

import java.time.Instant;

/**
 * An interface that provides the start and end time of elections
 * for the purpose of making the services testable.
 */
public interface TimeKeeper {
    /**
     * Returns the time when a voting procedure starts.
     * @return an Instant denoting the timestamp of the start time
     */
    public Instant getStartTime();

    /**
     * Returns the time when a voting procedure ends.
     * @return an Instant denoting the timestamp of the end time
     */
    public Instant getEndTime();

    /**
     * Returns true if the vote is still ongoing
     * @return  Returns true if the vote is still ongoing
     */
    public boolean isVoteOngoing();

    /**
     * Returns the duration of the vote in seconds
     * @return -
     */
    public long getDurationInSeconds();
}
