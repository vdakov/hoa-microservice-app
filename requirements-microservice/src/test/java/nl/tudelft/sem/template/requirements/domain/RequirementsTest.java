package nl.tudelft.sem.template.requirements.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementsTest {

    private Requirements requirement1;

    @BeforeEach
    void setUp() {
        requirement1 = new Requirements("First requirement", "Some details idk");
    }

    @Test
    void testEquals() {
        Requirements requirement2 = new Requirements("First requirement", "Some details idk");
        Requirements requirement3 = new Requirements("First requirements", "Some detail idk");
        assertEquals(requirement1, requirement1);
        assertEquals(requirement1, requirement2);
        assertNotEquals(requirement1, requirement3);
        assertNotEquals(requirement1, null);
    }

    @Test
    void getId() {
        assertEquals(0, requirement1.getId());
    }

    @Test
    void getRequirementName() {
        assertEquals("First requirement", requirement1.getRequirementName());
    }

    @Test
    void getRequirementDescription() {
        assertEquals("Some details idk", requirement1.getRequirementDescription());
    }

    @Test
    void setId() {
        requirement1.setId(1);
        assertEquals(1, requirement1.getId());
    }

    @Test
    void setRequirementName() {
        requirement1.setRequirementName("Another boring name");
        assertEquals("Another boring name", requirement1.getRequirementName());
    }

    @Test
    void setRequirementDescription() {
        requirement1.setRequirementDescription("Another boring description");
        assertEquals("Another boring description", requirement1.getRequirementDescription());
    }
}