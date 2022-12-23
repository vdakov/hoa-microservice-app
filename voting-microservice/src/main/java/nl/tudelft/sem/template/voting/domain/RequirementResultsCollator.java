package nl.tudelft.sem.template.voting.domain;

import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.commons.models.RequirementResultsModel;
import nl.tudelft.sem.template.commons.models.ResultsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class RequirementResultsCollator implements ResultsCollator {

    /**
     * Collates the results after a requirement vote. It assumes the fixed options provided by RequirementVoteBuilder
     * @param votes the Map that stores users' votes
     * @param options the list of available options to choose from
     * @param numberOfEligibleVoters number of eligible voters
     * @return a ResultModel ready to be used in an HTTP request
     */
    @SuppressWarnings("PMD")
    public ResultsModel collateResults(Map<String, Integer> votes, List<String> options, int numberOfEligibleVoters) {
        // PMD was throwing warnings because values in the map are first zero-initialized, and then
        // incremented; rule DataflowAnomalyAnalysis

        // index 0 is against, index 1 is for, index 2 is abstain
        Map<Integer, Integer> aggregatedResults = new HashMap<>();
        for (int option = 0; option < options.size(); option++) { // initialize the map containing aggregated results
            aggregatedResults.put(option, 0);
        }
        for (Integer vote : votes.values()) {
            int currentNumber = aggregatedResults.get(vote);
            currentNumber++;
            aggregatedResults.replace(vote, currentNumber);
        }

        boolean passed = aggregatedResults.get(1) > aggregatedResults.get(0);
        RequirementResultsModel ret = new RequirementResultsModel(numberOfEligibleVoters,
                votes.size(), aggregatedResults.get(1), passed);
        return ret;
    }
}
