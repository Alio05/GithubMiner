package aiss.githubminer.service;
import aiss.githubminer.model.Issue;
import aiss.githubminer.model.IssueData.IssueData;
import aiss.githubminer.model.User;
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
import java.util.stream.Collectors;
@Service
public class IssueService {
    @Value( "${github.token}" )
    private String token;
    @Autowired
    CommentService commentService;
    IssueData data = new IssueData();
    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://api.github.com";
    public List<Issue> sinceIssues(String owner, String repo, Integer
            days, Integer pages) {
        LocalDate date = LocalDate.now().minusDays(days);
        String uri = baseUri + "/repos/" + owner + "/" + repo +
                "/issues?state=all&page=1&since=" + date;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
                HttpEntity <IssueData[]> request = new HttpEntity<>(null, headers);
        List<IssueData> issues = new ArrayList<>();
        int page = 1;
        while (page <= pages && uri != null) {
            System.out.println(uri);
            ResponseEntity<IssueData[]> response =
                    restTemplate.exchange(uri, HttpMethod.GET, request, IssueData[].class);
            List<IssueData> issuePage =
                    Arrays.stream(response.getBody()).collect(Collectors.toList());
            issues.addAll(issuePage);
            uri = RESTUtil.getNextPageUrl(response.getHeaders());
            page++;
        }
        List<Issue> data = mapIssuesValues(issues);
        data.forEach(x -> x.setComments(commentService.getNotes(owner,
                repo, x.getRefId())));
        mapAuthorValues(data);
        return data;
    }
    public List<Issue> mapIssuesValues(List<IssueData> issues) {
        List<Issue> data= new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (IssueData issue : issues) {
            Issue issue1 = new Issue(issue.getId(),
                    issue.getNumber(),issue.getTitle(),issue.getBody(),issue.getState(),issue.getCreatedAt(),
                    issue.getUpdatedAt(),issue.getClosedAt(),
                    labels,issue.getUser(),issue.getAssignee(),issue.getReactions().getPlus1(),
                    issue.getReactions().getMinous1(),issue.getHtmlUrl());
            issue1.setLabels(issue.getLabels());
            data.add(issue1);
        }
        return data;
    }
    public void mapAuthorValues(List<Issue> issues) {
        for (Issue issue: issues) {
        User commentAuthor = issue.getAuthor();
        String commentAuthorUserName = commentAuthor.getLogin();
        String commentAuthorName = commentAuthor.getLogin();
        String commentAuthorWebUrl = commentAuthor.getHtmlUrl();
        issue.setAuthor(commentAuthor);
        commentAuthor.setUsername(commentAuthorUserName);
        commentAuthor.setName(commentAuthorName);
        commentAuthor.setWebUrl(commentAuthorWebUrl);
        }
    }
}
