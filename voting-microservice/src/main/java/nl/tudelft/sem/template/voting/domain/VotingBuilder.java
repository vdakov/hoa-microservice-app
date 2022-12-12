package nl.tudelft.sem.template.voting.domain;

import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.List;

@NoArgsConstructor
public class VotingBuilder implements Builder{
    private int hoaId;
    private List<String> options;
    private TimeKeeper timeKeeper;
    public Builder forHoaWithId(int hoaId) {
        this.hoaId = hoaId;
        return this;
    }
    public Builder withOptions(List<String> options) {
        this.options = options;
        return this;
    }
    public Builder withTimeKeeper(TimeKeeper timeKeeper) {
        this.timeKeeper = timeKeeper;
        return this;
    }
    public Builder startInstantlyWithDuration(TemporalAmount temporalAmount) {
        this.timeKeeper = new ConcreteTimeKeeper(Instant.now(), temporalAmount);
        return this;
    }
    public Voting buildRequirementsVote() {
        return null; //TODO: implement RequirementsVote once different permission levels are implemented
    }

    public Voting buildBoardElections() {
        return new BoardElections(hoaId, options, timeKeeper);
    }
}
