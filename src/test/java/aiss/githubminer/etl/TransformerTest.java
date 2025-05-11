package aiss.githubminer.etl;

import aiss.githubminer.model.Project;
import aiss.githubminer.model.Commit;
import aiss.githubminer.model.Issue;
import aiss.githubminer.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransformerTest {

    @Test
    void testTransformProjectWithValidData() {
        Project githubProject = new Project();
        githubProject.setId("1");
        githubProject.setName("Test Project");
        githubProject.setHtmlUrl("http://html.com");

        Commit commit = new Commit();
        commit.setId("c1");
        commit.setTitle("Commit title");
        githubProject.setCommits(List.of(commit));

        User user = new User();
        user.setLogin("testuser");
        user.setUsername("testuser");
        Issue issue = new Issue("i1", "ref", "Issue title", "desc", "open", "now", "now", null, List.of("bug"), user, null, 1, 0, "url");
        githubProject.setIssues(List.of(issue));

        Transformer transformer = new Transformer();
        Project result = transformer.transform(githubProject);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test Project", result.getName());
        assertEquals("http://html.com", result.getWebUrl());
        assertEquals(1, result.getIssues().size());
    }

    @Test
    void testTransformProjectWithInvalidIssues() {
        Project githubProject = new Project();
        githubProject.setId("1");
        githubProject.setName("Test Project");
        githubProject.setHtmlUrl("http://html.com");

        // Issue sin username
        User user = new User();
        user.setLogin("testuser");
        Issue issue = new Issue("i1", "ref", "Issue title", "desc", "open", "now", "now", null, List.of("bug"), user, null, 1, 0, "url");
        githubProject.setIssues(List.of(issue));

        Transformer transformer = new Transformer();
        Project result = transformer.transform(githubProject);

        // No issues válidos porque username es null y el transformer lo requiere
        assertNotNull(result);
        assertEquals(0, result.getIssues().size());
    }
}
