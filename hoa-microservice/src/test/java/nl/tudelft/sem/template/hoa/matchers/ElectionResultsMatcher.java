package nl.tudelft.sem.template.hoa.matchers;

import nl.tudelft.sem.template.hoa.entitites.ElectionResults;
import nl.tudelft.sem.template.hoa.entitites.Hoa;
import nl.tudelft.sem.template.hoa.entitites.User;
import org.mockito.ArgumentMatcher;

public class ElectionResultsMatcher implements ArgumentMatcher<ElectionResults> {

    private Hoa hoa;
    private int totalVotes;
    private User user;

    public ElectionResultsMatcher(Hoa hoa, int totalVotes, User user) {
        this.hoa = hoa;
        this.totalVotes = totalVotes;
        this.user = user;
    }

    @Override
    public boolean matches(ElectionResults argument) {
        if (argument != null) {
            ElectionResults results = (ElectionResults) argument;
            return results.getHoa().equals(hoa)
                    && results.getNumberOfVotes() == totalVotes
                    && results.getWinner().equals(user);
        }
        return false;
    }

}

