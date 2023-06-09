package nl.tudelft.sem.template.voting.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import nl.tudelft.sem.template.commons.models.VotingModel;
import nl.tudelft.sem.template.commons.models.VotingType;
import nl.tudelft.sem.template.voting.application.VotingService;
import nl.tudelft.sem.template.voting.utils.JsonUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test", "mockTokenVerifier", "mockAuthenticationManager"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class VotingTest {

    @Autowired
    private MockMvc mockMvc;

    private final VotingService votingService = Mockito.mock(VotingService.class);

    @Test
    void initializeElectionVote_returnsCreated() throws Exception {
        VotingModel model = new VotingModel(1, VotingType.ELECTIONS_VOTE, 1, new ArrayList<String>());
        ResultActions resultActions = mockMvc.perform(post("/initializeVoting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.serialize(model)));

        resultActions.andExpect(status().isCreated());
    }

    @Test
    void initializeRequirementsVote_returnsCreated() throws Exception {
        VotingModel model = new VotingModel(2, VotingType.REQUIREMENTS_VOTE, 1, new ArrayList<String>());
        ResultActions resultActions = mockMvc.perform(post("/initializeVoting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.serialize(model)));

        resultActions.andExpect(status().isCreated());
    }

    @Test
    void initializeVote_alreadyOngoing() throws Exception {
        VotingModel model = new VotingModel(4, VotingType.ELECTIONS_VOTE, 1, new ArrayList<String>());
        //Start the vote
        mockMvc.perform(post("/initializeVoting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.serialize(model)));

        //Attempt to start the vote again
        ResultActions resultActions = mockMvc.perform(post("/initializeVoting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.serialize(model)));

        resultActions.andExpect(status().isSeeOther()).andReturn();
    }
}
