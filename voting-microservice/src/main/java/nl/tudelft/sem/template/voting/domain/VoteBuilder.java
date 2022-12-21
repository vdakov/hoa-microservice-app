package nl.tudelft.sem.template.voting.domain;

import java.time.temporal.TemporalAmount;
import java.util.List;

public interface VoteBuilder {
    public VoteBuilder forHoaWithId(int hoaId);
    public VoteBuilder withOptions(List<String> options);
    public VoteBuilder withTimeKeeper(TimeKeeper timeKeeper);
    public VoteBuilder startInstantlyWithDuration(TemporalAmount temporalAmount);
    public Vote build();

}
