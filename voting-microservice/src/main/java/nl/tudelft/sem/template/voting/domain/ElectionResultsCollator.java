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
        Map<String, Integer> aggregatedResults = new HashMap<>();
        for (String option: options) { // initialize the map containing aggregated results
            aggregatedResults.put(option, 0);
        }
        for (Integer vote : votes.values()) {
            int currentNumber = aggregatedResults.get(options.get(vote));
            currentNumber++;
            aggregatedResults.replace(options.get(vote), currentNumber);
        }

        ElectionResultsModel ret = new ElectionResultsModel(numberOfEligibleVoters,
                votes.size(),
                aggregatedResults);
        return ret;
    }
}
