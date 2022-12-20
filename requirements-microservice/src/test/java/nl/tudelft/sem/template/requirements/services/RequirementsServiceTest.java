package nl.tudelft.sem.template.requirements.services;

import nl.tudelft.sem.template.requirements.domain.Report;
import nl.tudelft.sem.template.requirements.domain.Requirements;
import nl.tudelft.sem.template.requirements.repositories.ReportRepository;
import nl.tudelft.sem.template.requirements.repositories.RequirementsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequirementsServiceTest {

    private RequirementsRepository requirementsRepository;

    private RequirementsService requirementsService;

    @BeforeEach
    public void setup() {
        requirementsRepository = mock(RequirementsRepository.class);
        requirementsService = new RequirementsService(requirementsRepository);
    }

    @Test
    public void creatorTest() throws Exception {
        Requirements requirement1 = requirementsService.createRequirement(1, "First requirement",
                "Some description");
        verify(requirementsRepository, times(1)).save(requirement1);
    }

    @Test
    public void getAllTest() throws Exception {
        Requirements requirement1 = requirementsService.createRequirement(1, "First requirement",
                "Some description");
        Requirements requirement2 = requirementsService.createRequirement(2, "First requirement",
                "Some description");
        Requirements requirement3 = requirementsService.createRequirement(3, "First requirement",
                "Some description");
        List<Requirements> requirementsList = Arrays.asList(requirement1, requirement2, requirement3);

        when(requirementsService.getAll()).thenReturn(requirementsList);

        assertEquals(requirementsList, requirementsService.getAll());
    }

    @Test
    public void findTest() throws Exception {
        Requirements requirement1 = requirementsService.createRequirement(1, "First requirement",
                "Some description");

        when(requirementsService.findById(2)).thenReturn(requirement1);

        assertEquals(requirement1, requirementsService.findById(2));
        assertNull(requirementsService.findById(0));
    }
}
