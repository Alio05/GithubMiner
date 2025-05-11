package aiss.githubminer;

import aiss.githubminer.model.Commit;
import aiss.githubminer.model.CommitData.Author;
import aiss.githubminer.model.CommitData.CommitProperty;
import aiss.githubminer.model.CommitData.Committer;
import aiss.githubminer.service.CommitService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CommitServiceTest {

    @Test
    void testMapCommitValues() {
        CommitService service = new CommitService();

        CommitProperty prop = new CommitProperty();
        Author author = new Author();
        author.setName("Test User");
        author.setEmail("testuser@test.com");
        author.setDate("2024-01-01");
        prop.setAuthor(author);
        prop.setMessage("Mensaje de commit de prueba");
        Committer committer = new Committer();
        committer.setName("Committer");
        committer.setEmail("committer@test.com");
        committer.setDate("2024-01-02");
        prop.setCommitter(committer);

        Commit commit = new Commit();
        commit.setSha("sha123");
        commit.setCommit(prop);

        service.mapCommitValues(List.of(commit));

        assertEquals("sha123", commit.getId());
        assertEquals("Test User", commit.getAuthorName());
        assertEquals("Mensaje de commit de prueba", commit.getMessage());
        assertEquals("Committer", commit.getCommitterName());
    }
}