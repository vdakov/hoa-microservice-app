package nl.tudelft.sem.template.voting.domain;

import java.time.temporal.TemporalAmount;
import java.util.List;

public interface Builder {
    public Builder forHoaWithId(int hoaId);
    public Builder withOptions(List<String> options);
    public Builder withTimeKeeper(TimeKeeper timeKeeper);
    public Builder startInstantlyWithDuration(TemporalAmount temporalAmount);
    public Voting buildRequirementsVote();
    public Voting buildBoardElections();

}
