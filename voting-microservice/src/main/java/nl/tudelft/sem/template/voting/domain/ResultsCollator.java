package nl.tudelft.sem.template.voting.domain;

import nl.tudelft.sem.template.commons.models.ResultsModel;

import java.util.List;
import java.util.Map;

public interface ResultsCollator {
    public ResultsModel collateResults(Map<String, Integer> votes, List<String> options, int numberOfEligibleVoters);
}
