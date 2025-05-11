package aiss.githubminer;

import aiss.githubminer.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setId("u1");
        user.setUsername("testuser");
        user.setLogin("testlogin");
        user.setName("Test User");
        user.setAvatarUrl("http://avatar.com");
        user.setHtmlUrl("http://html.com");
        user.setWebUrl("http://web.com");
        assertEquals("u1", user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("testlogin", user.getLogin());
        assertEquals("Test User", user.getName());
        assertEquals("http://avatar.com", user.getAvatarUrl());
        assertEquals("http://html.com", user.getHtmlUrl());
        assertEquals("http://web.com", user.getWebUrl());
    }

    @Test
    void testToString() {
        User user = new User();
        user.setUsername("testuser");
        String str = user.toString();
        assertTrue(str.contains("testuser"));
    }
}