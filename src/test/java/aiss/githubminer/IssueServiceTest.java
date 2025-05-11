package aiss.githubminer;

import aiss.githubminer.model.Issue;
import aiss.githubminer.model.IssueData.IssueData;
import aiss.githubminer.model.IssueData.Label;
import aiss.githubminer.model.IssueData.Reactions;
import aiss.githubminer.model.User;
import aiss.githubminer.service.IssueService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class IssueServiceTest {

    @Test
    void testMapIssuesValues() {
        IssueService service = new IssueService();

        IssueData issueData = new IssueData();
        issueData.setId("i1");
        issueData.setNumber("42");
        issueData.setTitle("Bug");
        issueData.setBody("Hay un bug");
        issueData.setState("open");
        issueData.setCreatedAt("2024-01-01");
        issueData.setUpdatedAt("2024-01-02");
        issueData.setClosedAt(null);
        Label label = new Label();
        label.setName("bug");
        issueData.setLabels(List.of(label));
        User user = new User();
        user.setLogin("testuser");
        user.setHtmlUrl("http://user.com");
        issueData.setUser(user);
        issueData.setAssignee(user);
        Reactions reactions = new Reactions();
        reactions.setPlus1(5);
        reactions.setBruh(0);
        issueData.setReactions(reactions);
        issueData.setHtmlUrl("http://issue.com");

        List<Issue> issues = service.mapIssuesValues(List.of(issueData));
        assertEquals(1, issues.size());
        Issue issue = issues.get(0);
        assertEquals("i1", issue.getId());
        assertEquals("Bug", issue.getTitle());
        assertEquals("bug", issue.getLabels().get(0));
        assertEquals("testuser", issue.getAuthor().getLogin());
        assertEquals(5, issue.getUpvotes());
    }
}