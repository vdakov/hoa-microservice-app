package nl.tudelft.sem.template.voting.domain;

import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.List;

@NoArgsConstructor
public class ElectionVoteBuilder implements VoteBuilder {
    private int hoaId;
    private List<String> options;
    private TimeKeeper timeKeeper;
    public VoteBuilder forHoaWithId(int hoaId) {
        this.hoaId = hoaId;
        return this;
    }
    public VoteBuilder withOptions(List<String> options) {
        this.options = options;
        return this;
    }
    public VoteBuilder withTimeKeeper(TimeKeeper timeKeeper) {
        this.timeKeeper = timeKeeper;
        return this;
    }
    public VoteBuilder startInstantlyWithDuration(TemporalAmount temporalAmount) {
        this.timeKeeper = new ConcreteTimeKeeper(Instant.now(), temporalAmount);
        return this;
    }

    public Vote build() {
        return new ElectionVote(hoaId, options, timeKeeper);
    }
}
