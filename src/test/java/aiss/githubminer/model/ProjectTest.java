package aiss.githubminer.model;

import aiss.githubminer.model.Project;
import aiss.githubminer.model.Commit;
import aiss.githubminer.model.Issue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ProjectTest {

    @Test
    void testProjectGettersAndSetters() {
        Project project = new Project();
        project.setId("1");
        project.setName("Test Project");
        project.setWebUrl("http://test.com");
        project.setHtmlUrl("http://html.com");

        assertEquals("1", project.getId());
        assertEquals("Test Project", project.getName());
        assertEquals("http://test.com", project.getWebUrl());
        assertEquals("http://html.com", project.getHtmlUrl());
    }

    @Test
    void testProjectCommitsAndIssuesInitialization() {
        Project project = new Project();
        assertNotNull(project.getCommits());
        assertNotNull(project.getIssues());
        assertTrue(project.getCommits().isEmpty());
        assertTrue(project.getIssues().isEmpty());
    }

    @Test
    void testSetCommitsAndIssues() {
        Project project = new Project();
        Commit commit = new Commit();
        Issue issue = new Issue("id", "ref", "title", "desc", "open", "now", "now", null, List.of(), null, null, 0, 0, "url");
        project.setCommits(List.of(commit));
        project.setIssues(List.of(issue));
        assertEquals(1, project.getCommits().size());
        assertEquals(1, project.getIssues().size());
    }

    @Test
    void testToString() {
        Project project = new Project();
        project.setId("1");
        project.setName("Test Project");
        String str = project.toString();
        assertTrue(str.contains("id"));
        assertTrue(str.contains("name"));
    }
}