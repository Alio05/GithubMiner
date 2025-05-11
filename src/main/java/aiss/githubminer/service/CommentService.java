package aiss.githubminer.service;
import aiss.githubminer.model.Comment;
import aiss.githubminer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
@Service
public class CommentService {
    @Value( "${github.token}")
    private String token;
    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://api.github.com/";

    public List<Comment> getNotes(String owner, String repo, String
            iid) {
        String uri = baseUri + "/repos/" + owner + "/" + repo +
                "/issues/" + iid + "/comments";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri,
                HttpMethod.GET, request, Comment[].class);
        List<Comment> comments =
                Arrays.stream(response.getBody()).toList();
        mapAuthorValues(comments);
        return comments;
    }

    public void mapAuthorValues(List<Comment> comments) {
        for (Comment comment : comments) {
            if (comment != null) {
                User commentAuthor = comment.getUser();
                String commentAuthorUserName = commentAuthor.getLogin();
                String commentAuthorName = commentAuthor.getName();
                String commentAuthorWebUrl = commentAuthor.getHtmlUrl();
                comment.setAuthor(commentAuthor);
                commentAuthor.setUsername(commentAuthorUserName);
                commentAuthor.setName(commentAuthorName);
                commentAuthor.setWebUrl(commentAuthorWebUrl);
            }
        }
    }
}