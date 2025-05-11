package aiss.githubminer.model;

import aiss.githubminer.model.Issue;
import aiss.githubminer.model.User;
import aiss.githubminer.model.IssueData.Label;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class IssueTest {

    @Test
    void testGettersAndSetters() {
        Issue issue = new Issue("id", "ref", "title", "desc", "open", "now", "now", null, List.of("bug"), new User(), new User(), 1, 0, "url");
        issue.setId("id2");
        issue.setTitle("title2");
        assertEquals("id2", issue.getId());
        assertEquals("title2", issue.getTitle());
    }

    @Test
    void testLabels() {
        Issue issue = new Issue("id", "ref", "title", "desc", "open", "now", "now", null, null, null, null, 0, 0, "url");
        List<Label> labels = new ArrayList<>();
        Label l1 = new Label();
        l1.setName("enhancement");
        Label l2 = new Label();
        l2.setName("bug");
        labels.add(l1);
        labels.add(l2);
        issue.setLabels(labels);
        assertEquals(2, issue.getLabels().size());
        assertTrue(issue.getLabels().contains("enhancement"));
        assertTrue(issue.getLabels().contains("bug"));
    }

    @Test
    void testToString() {
        Issue issue = new Issue("id", "ref", "title", "desc", "open", "now", "now", null, null, null, null, 0, 0, "url");
        String str = issue.toString();
        assertTrue(str.contains("id"));
    }
}