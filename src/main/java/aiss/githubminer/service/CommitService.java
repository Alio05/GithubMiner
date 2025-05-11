package aiss.githubminer.service;
import aiss.githubminer.model.Commit;
import aiss.githubminer.model.CommitData.Author;
import aiss.githubminer.model.CommitData.CommitProperty;
import aiss.githubminer.model.CommitData.Committer;
import aiss.githubminer.utils.RESTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommitService {
    @Value( "${github.token}")
    private String token;
    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://api.github.com";
    public List<Commit> sinceCommits(String owner, String repo, Integer
            days, Integer pages){
        LocalDate date = LocalDate.now().minusDays(days);
        String uri = baseUri + "/repos/" + owner + "/" + repo +
                "/commits?page=1&since=" + date;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Commit[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Commit[]> response = restTemplate.exchange(uri,
                HttpMethod.GET, request, Commit[].class);
        List<Commit> commits = new ArrayList<>();
        int page = 1;
        while (page <= pages && uri!= null){
            System.out.println(uri);
            List<Commit> commitPage =
                    Arrays.stream(response.getBody()).toList();
            response =
                    restTemplate.exchange(uri,HttpMethod.GET,request,Commit[].class);
            mapCommitValues(commitPage);
            commits.addAll(commitPage);
            uri = RESTUtil.getNextPageUrl(response.getHeaders());
            page++;
        }
        return commits;
    }
    public void mapCommitValues(List<Commit> commits) {
        for (Commit commit : commits) {
            // Asegurar que commit y commit.getCommit() no sean nulos
            if (commit.getCommit() == null) {
                commit.setCommit(new CommitProperty());
            }

            CommitProperty commitProp = commit.getCommit();
            Author author = commitProp.getAuthor() != null ? commitProp.getAuthor() : new Author();
            Committer committer = commitProp.getCommitter() != null ? commitProp.getCommitter() : new Committer();

            // Asignar valores con defaults para evitar nulls
            commit.setId(commit.getSha() != null ? commit.getSha() : "no-id");
            commit.setTitle(commitProp.getMessage() != null
                    ? (commitProp.getMessage().length() < 20
                    ? commitProp.getMessage()
                    : commitProp.getMessage().substring(0, 20))
                    : "No title");
            commit.setMessage(commitProp.getMessage() != null ? commitProp.getMessage() : "No message");
            commit.setAuthorName(author.getName() != null ? author.getName() : "Unknown");
            commit.setAuthorEmail(author.getEmail() != null ? author.getEmail() : "No email");
            commit.setAuthoredDate(author.getDate() != null ? author.getDate() : "No date");
            commit.setCommitterName(committer.getName() != null ? committer.getName() : "Unknown");
            commit.setCommitterEmail(committer.getEmail() != null ? committer.getEmail() : "No email");
            commit.setCommittedDate(committer.getDate() != null ? committer.getDate() : "No date");
            commit.setWebUrl(commit.getWebUrl() != null ? commit.getWebUrl() : "No URL"); // ¡GitHub devuelve "html_url", no "url"!
        }
    }
}

