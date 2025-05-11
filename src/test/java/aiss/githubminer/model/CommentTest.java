package aiss.githubminer.model;

import aiss.githubminer.model.Comment;
import aiss.githubminer.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @Test
    void testGettersAndSetters() {
        Comment comment = new Comment();
        User user = new User();
        user.setId("u1");
        user.setUsername("testuser");
        user.setLogin("testlogin");
        user.setName("Test User");
        user.setAvatarUrl("http://avatar.com");
        user.setHtmlUrl("http://html.com");
        user.setWebUrl("http://web.com");
        comment.setId("c1");
        comment.setBody("Test body");
        comment.setCreatedAt("2024-01-01T00:00:00Z");
        comment.setUpdatedAt("2024-01-02T00:00:00Z");
        comment.setAuthor(user);
        comment.setUser(user);
        assertEquals("c1", comment.getId());
        assertEquals("Test body", comment.getBody());
        assertEquals("2024-01-01T00:00:00Z", comment.getCreatedAt());
        assertEquals("2024-01-02T00:00:00Z", comment.getUpdatedAt());
        assertEquals(user, comment.getAuthor());
        assertEquals(user, comment.getUser());
    }

    @Test
    void testToString() {
        Comment comment = new Comment();
        User user = new User();
        user.setUsername("testuser");
        comment.setAuthor(user);
        String str = comment.toString();
        assertTrue(str.contains("testuser"));
    }
}