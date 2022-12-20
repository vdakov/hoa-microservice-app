package nl.tudelft.sem.template.requirements.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RequirementsTest {

    private Requirements requirement1;

    @BeforeEach
    void setUp() {
        requirement1 = new Requirements(1, "First requirement", "Some details idk");
    }

    @Test
    void testEquals() {
        Requirements requirement2 = new Requirements(1, "First requirement", "Some details idk");
        Requirements requirement3 = new Requirements(1, "First requirements", "Some detail idk");
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
    void getHoaId() {
        assertEquals(1, requirement1.getHoaId());
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
    void setHoaId() {
        requirement1.setHoaId(2);
        assertEquals(2, requirement1.getHoaId());
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

    @Test
    void hashcode() {
        int hash1 = requirement1.hashCode();
        int hash2 = requirement1.hashCode();

        assertEquals(hash1, hash2);
        Requirements requirement2 = new Requirements(1, "Second requirement", "Some useless details");
        hash2 = requirement2.hashCode();
        assertNotEquals(hash1, hash2);
    }
}
