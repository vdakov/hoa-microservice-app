package nl.tudelft.sem.template.authentication.integration;

import nl.tudelft.sem.template.authentication.domain.user.HashedPassword;
import nl.tudelft.sem.template.authentication.domain.user.Password;
import nl.tudelft.sem.template.authentication.domain.user.PasswordHashingService;
import nl.tudelft.sem.template.authentication.domain.user.Username;
import nl.tudelft.sem.template.authentication.framework.integration.utils.JsonUtil;
import nl.tudelft.sem.template.authentication.models.AuthenticationResponseModel;
import nl.tudelft.sem.template.authentication.models.RegistrationRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test", "mockPasswordEncoder", "mockTokenGenerator", "mockAuthenticationManager"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class GatewayTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private transient AuthenticationManager mockAuthenticationManager;

    @Autowired
    private transient PasswordHashingService mockPasswordEncoder;

    private String token;

    @BeforeEach
    public void authenticate() throws Exception {
        Username username = new Username("testUser");
        Password password = new Password("testPass");
        HashedPassword hashedPass = new HashedPassword("testPass");
        when(mockPasswordEncoder.hash(password)).thenReturn(hashedPass);

        RegistrationRequestModel request = new RegistrationRequestModel();
        request.setUsername(username.toString());
        request.setPassword(password.toString());

        //Register user.
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.serialize(request)));

        //Authenticate user and retrieve token.
        ResultActions resultActions = mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.serialize(request)));

        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andReturn();

        //Retrieve token from result
        token = JsonUtil.deserialize(result.getResponse().getContentAsString(), AuthenticationResponseModel.class).getToken();
    }

    @Test
    void testToken(){
        
    }
}
