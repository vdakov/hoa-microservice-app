package nl.tudelft.sem.template.requirements.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ReportTest {

    private Report report1;
    private Requirements requirement1;
    private Requirements requirement2;


    @BeforeEach
    void setup() {
        requirement1 = new Requirements(1, "First requirement", "You need to give us money");
        requirement2 = new Requirements(2, "First requirement", "You don't have to park");
        report1 = new Report("sem2", requirement1);
    }

    @Test
    void testEquals() {
        Report report2 = new Report("sem3", requirement1);
        Report report3 = new Report("sem2", requirement1);
        Report report4 = new Report("sem2", requirement2);
        assertEquals(report1, report1);
        assertEquals(report1, report3);
        assertNotEquals(report1, report2);
        assertNotEquals(report3, report4);
        assertNotEquals(report1, null);
    }

    @Test
    void getId() {
        assertEquals(0, report1.getId());
    }

    @Test
    void getReportedUser() {
        assertEquals("sem2", report1.getReportedUser());
    }

    @Test
    void getRequirement() {
        assertEquals(requirement1, report1.getRequirement());
    }

    @Test
    void setId() {
        report1.setId(2);
        assertEquals(2, report1.getId());
    }

    @Test
    void setReportedUser() {
        report1.setReportedUser("test");
        assertEquals("test", report1.getReportedUser());
    }

    @Test
    void setRequirement() {
        Requirements requirement2 = new Requirements(1, "Second requirement", "Second description");
        report1.setRequirement(requirement2);
        assertEquals(requirement2, report1.getRequirement());
    }

    @Test
    void hashcode() {
        int hash1 = report1.hashCode();
        int hash2 = report1.hashCode();

        assertEquals(hash1, hash2);
        Report report2 = new Report("sem40", requirement1);
        hash2 = report2.hashCode();
        assertNotEquals(hash1, hash2);
    }
}
