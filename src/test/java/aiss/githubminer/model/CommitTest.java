package aiss.githubminer.model;

import aiss.githubminer.model.Commit;
import aiss.githubminer.model.CommitData.CommitProperty;
import aiss.githubminer.model.CommitData.Author;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommitTest {

    @Test
    void testGettersAndSetters() {
        Commit commit = new Commit();
        commit.setId("c1");
        commit.setTitle("Initial commit");
        commit.setMessage("This is a commit");
        commit.setSha("sha123");
        commit.setWebUrl("http://test.com");
        assertEquals("c1", commit.getId());
        assertEquals("Initial commit", commit.getTitle());
        assertEquals("This is a commit", commit.getMessage());
        assertEquals("sha123", commit.getSha());
        assertEquals("http://test.com", commit.getWebUrl());
    }

    @Test
    void testCommitPropertyIntegration() {
        Commit commit = new Commit();
        CommitProperty prop = new CommitProperty();
        Author author = new Author();
        author.setName("Test User");
        author.setEmail("testuser@test.com");
        author.setDate("2024-01-01");
        prop.setAuthor(author);
        prop.setMessage("Commit message");
        commit.setCommit(prop);
        assertEquals("Test User", commit.getAuthorName());
        assertEquals("Commit message", commit.getCommit().getMessage());
    }

    @Test
    void testToString() {
        Commit commit = new Commit();
        commit.setId("c1");
        String str = commit.toString();
        assertTrue(str.contains("c1"));
    }
}