package aiss.githubminer.service;
import aiss.githubminer.model.Commit;
import aiss.githubminer.model.CommitData.Author;
import aiss.githubminer.model.CommitData.Committer;
import aiss.githubminer.utils.RESTUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://api.github.com";
    public List<Commit> sinceCommits(String owner, String repo, Integer
            days, Integer pages){
        LocalDate date = LocalDate.now().minusDays(days);
        String uri = baseUri + "/repos/" + owner + "/" + repo +
                "/commits?page=1&since=" + date;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " );
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
    public void mapCommitValues(List<Commit> commits){
        for(Commit commit: commits){
            Author author = commit.getCommit().getAuthor();
            Committer committer = commit.getCommit().getCommitter();
            String id = commit.getSha();
            String title = commit.getCommit().getMessage();
            String authorName = author.getName();
            String authorEmail = author.getEmail();
            String authoredDate = author.getDate();
            String committerName = committer.getName();
            String committerEmail = committer.getEmail();
            String committedDate = committer.getDate();
            String webUrl = commit.getCommit().getUrl();
            commit.setId(id);
            if(title.length() < 20) {
                commit.setTitle(title);
            }else {
                commit.setTitle(title.substring(0,20));
            }
            commit.setMessage(title);
            commit.setAuthorName(authorName);
            commit.setAuthorEmail(authorEmail);
            commit.setAuthoredDate(authoredDate);
            commit.setCommitterName(committerName);
            commit.setCommitterEmail(committerEmail);
            commit.setCommittedDate(committedDate);
            commit.setWebUrl(webUrl);
        }
    }
}

