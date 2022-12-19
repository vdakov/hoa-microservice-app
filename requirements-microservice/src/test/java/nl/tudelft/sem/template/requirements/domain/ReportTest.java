package nl.tudelft.sem.template.requirements.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    private Report report1;
    private Requirements requirement1;


    @BeforeEach
    void setup() {
        requirement1 = new Requirements("First requirement", "Some details idk");
        report1 = new Report("sem", "sem2", requirement1);
    }

    @Test
    void testEquals() {
        Report report2 = new Report("sem", "sem3", requirement1);
        Report report3 = new Report("sem", "sem2", requirement1);
        assertEquals(report1, report1);
        assertEquals(report1, report3);
        assertNotEquals(report1, report2);
        assertNotEquals(report1, null);
    }

    @Test
    void getId() {
        assertEquals(0, report1.getId());
    }

    @Test
    void getReportBy() {
        assertEquals("sem", report1.getReportBy());
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
    void setReportBy() {
        report1.setReportBy("bruh");
        assertEquals("bruh", report1.getReportBy());
    }

    @Test
    void setReportedUser() {
        report1.setReportedUser("test");
        assertEquals("test", report1.getReportedUser());
    }

    @Test
    void setRequirement() {
        Requirements requirement2 = new Requirements("Second requirement", "Some useless details");
        report1.setRequirement(requirement2);
        assertEquals(requirement2, report1.getRequirement());
    }
}
