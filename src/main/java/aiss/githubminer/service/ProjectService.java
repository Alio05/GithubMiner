package aiss.githubminer.service;
import aiss.githubminer.model.Project;
import aiss.githubminer.etl.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
@Service
public class ProjectService {
    @Value( "${github.token}")
    private String token;
    @Autowired
    private Transformer transformer;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CommitService commitService;
    @Autowired
    IssueService issueService;
    final String baseUri = "https://api.github.com/";
    public Project getProjectByOwnerAndName(String owner, String repo){
        String uri = baseUri + "/repos/" + owner + "/" + repo;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token );
        HttpEntity<Project> request = new HttpEntity<>(null,headers);
        ResponseEntity<Project> response = restTemplate.exchange(uri,
                HttpMethod.GET,request,Project.class);
        return response.getBody();
    }
    public Project allData(String owner, String repo, @RequestParam(required = false) Integer
            sinceCommits, @RequestParam(required = false) Integer sinceIssues,@RequestParam(required = false) Integer maxPages){
        Project github = getProjectByOwnerAndName(owner,repo);
        Project correctedGithub = transformer.transform(github);
        correctedGithub.setIssues(issueService.sinceIssues(owner,repo,sinceIssues,maxPages));
        correctedGithub.setCommits(commitService.sinceCommits(owner,repo,sinceCommits,maxPages));
        return correctedGithub;
    }
}
