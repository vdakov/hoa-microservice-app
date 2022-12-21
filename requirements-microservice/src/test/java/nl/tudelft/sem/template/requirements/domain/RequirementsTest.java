package nl.tudelft.sem.template.requirements.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RequirementsTest {

    private Requirements requirement1;

    @BeforeEach
    void setUp() {
        requirement1 = new Requirements(1, "First requirement", "You have to park");
    }

    @Test
    void testEquals() {
        Requirements requirement2 = new Requirements(1, "First requirement", "You have to park");
        Requirements requirement3 = new Requirements(1, "First requirements", "You have to park");
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
        assertEquals("You have to park", requirement1.getRequirementDescription());
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
        requirement1.setRequirementName("Third requirement");
        assertEquals("Third requirement", requirement1.getRequirementName());
    }

    @Test
    void setRequirementDescription() {
        requirement1.setRequirementDescription("Third description");
        assertEquals("Third description", requirement1.getRequirementDescription());
    }

    @Test
    void hashcode() {
        int hash1 = requirement1.hashCode();
        int hash2 = requirement1.hashCode();

        assertEquals(hash1, hash2);
        Requirements requirement2 = new Requirements(1, "Second requirement", "Details");
        hash2 = requirement2.hashCode();
        assertNotEquals(hash1, hash2);
    }
}
