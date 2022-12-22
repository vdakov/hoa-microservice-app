package nl.tudelft.sem.template.hoa.matchers;

import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.RequirementResults;
import org.mockito.ArgumentMatcher;


public class RequirementResultsMatcher implements ArgumentMatcher<RequirementResults> {

    private Hoa hoa;
    private int totalVotes;
    private boolean passed;
    private int votedFor;

    public RequirementResultsMatcher(Hoa hoa, int numberOfVotes, int votedFor, boolean passed) {
        this.hoa = hoa;
        this.totalVotes = numberOfVotes;
        this.votedFor = votedFor;
        this.passed = passed;
    }

    @Override
    public boolean matches(RequirementResults argument) {
        if (argument != null) {
            RequirementResults results = (RequirementResults) argument;
            return results.getHoa().equals(hoa)
                    && results.getNumberOfVotes() == totalVotes
                    && results.getVotedFor() == votedFor
                    && results.isPassed() == passed;
        }
        return false;
    }
}
