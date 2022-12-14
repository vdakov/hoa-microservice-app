package nl.tudelft.sem.template.hoa.profiles;

import nl.tudelft.sem.template.hoa.domain.activity.ActivityService;
import nl.tudelft.sem.template.hoa.services.HoaService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * This is a configuration profile.
 * A configuration can be used to define beans to be used during injection.
 * When this profile is active spring dependency injection will use this class to look for bean methods.
 * It will then prioritise these beans due to their @Primary tag.
 * In this case we return a mock to allow for more testability.
 * .
 * The profile tag will give a name to this configuration.
 * With the tag applied the profile will be inactive by default unless activated.
 * When the profile is active its bean will be used when looking for Beans to auto-inject.
 *.
 * A configuration profile to allow injection of a mock TokenGenerator.
 */
@Profile("mockHoaService")
@Configuration
public class MockHoaServiceProfile {

    /**
     * Mocks the TokenGenerator.
     *
     * @return A mocked TokenGenerator.
     */
    @Bean
    @Primary  // marks this bean as the first bean to use when trying to inject a TokenGenerator
    public HoaService getMockHoaService() {
        return Mockito.mock(HoaService.class);
    }
}
