package aiss.githubminer;

import aiss.githubminer.model.Comment;
import aiss.githubminer.model.User;
import aiss.githubminer.service.CommentService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    @Test
    void testMapAuthorValues() {
        CommentService service = new CommentService();

        User user = new User();
        user.setLogin("testuser");
        user.setHtmlUrl("http://user.com");

        Comment comment = new Comment();
        comment.setUser(user);

        service.mapAuthorValues(List.of(comment));

        assertEquals("testuser", comment.getAuthor().getLogin());
        assertEquals("testuser", comment.getAuthor().getUsername());
        assertEquals("testuser", comment.getAuthor().getName());
        assertEquals("http://user.com", comment.getAuthor().getWebUrl());
    }
}