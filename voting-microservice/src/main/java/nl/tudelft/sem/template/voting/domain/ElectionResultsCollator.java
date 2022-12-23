package nl.tudelft.sem.template.voting.domain;

import nl.tudelft.sem.template.commons.models.ElectionResultsModel;
import nl.tudelft.sem.template.commons.models.ResultsModel;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectionResultsCollator implements ResultsCollator {

    /**
     * Collates the results after board elections
     * @param votes the Map that stores users' votes
     * @param options the list of available options to choose from
     * @param numberOfEligibleVoters number of eligible voters
     * @return a ResultModel ready to be used in an HTTP request
     */
    @SuppressWarnings("PMD")
    public ResultsModel collateResults(Map<String, Integer> votes, List<String> options, int numberOfEligibleVoters) {
        // PMD was throwing warnings because values in the map are first zero-initialized, and then
        // incremented; rule DataflowAnomalyAnalysis
        Map<Integer, Integer> aggregatedResults = new HashMap<>();
        for (int option = 0; option < options.size(); option++) { // initialize the map containing aggregated results
            aggregatedResults.put(option, 0);
        }
        int winnerIndex = 0;
        for (Integer vote : votes.values()) {
            int currentNumber = aggregatedResults.get(vote);
            currentNumber++;
            aggregatedResults.replace(vote, currentNumber);
            if (currentNumber > aggregatedResults.get(winnerIndex)) winnerIndex = vote;
        }
        for (Integer optionIndex : aggregatedResults.keySet()) {
            if (aggregatedResults.get(optionIndex) == aggregatedResults.get(winnerIndex)
                    && optionIndex != winnerIndex) { //i.e., if there is a tie with some other option
                winnerIndex = -1;
                break;
            }
        }

        ElectionResultsModel ret = new ElectionResultsModel(numberOfEligibleVoters,
                votes.size(),
                aggregatedResults,
                winnerIndex);
        return ret;
    }
}
