package aiss.githubminer.service;
import aiss.githubminer.model.Issue;
import aiss.githubminer.model.IssueData.IssueData;
import aiss.githubminer.model.IssueData.Label;
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    public List<Issue> sinceIssues(String owner, String repo, Integer days, Integer pages) {
        LocalDate date = LocalDate.now().minusDays(days);
        String uri = baseUri + "/repos/" + owner + "/" + repo + "/issues?state=all&since=" + date;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<IssueData[]> request = new HttpEntity<>(null, headers);
        List<IssueData> issues = new ArrayList<>();
        int pageCount = 0;

        while (uri != null && pageCount < pages) {
            ResponseEntity<IssueData[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, IssueData[].class);
            List<IssueData> issuePage = Arrays.stream(response.getBody()).collect(Collectors.toList());
            issues.addAll(issuePage);
            uri = extractNextPageUrl(response.getHeaders());
            pageCount++;
        }

        List<Issue> data = mapIssuesValues(issues);
        mapAuthorValues(data);
        data.forEach(x -> x.setComments(commentService.getNotes(owner, repo, x.getRefId())));
        return data;
    }
    private String extractNextPageUrl(HttpHeaders headers) {
        if (!headers.containsKey("Link")) return null;
        String linksHeader = headers.getFirst("Link");
        if (linksHeader == null) return null;

        for (String link : linksHeader.split(", ")) {
            if (link.contains("rel=\"next\"")) {
                String url = link.split(";")[0].replaceAll("[<>]", "");
                return URLDecoder.decode(url, StandardCharsets.UTF_8);
            }
        }
        return null;
    }
    public List<Issue> mapIssuesValues(List<IssueData> issues) {
        List<Issue> data = new ArrayList<>();
        for (IssueData issue : issues) {
            List<String> labels = issue.getLabels().stream()
                    .map(Label::getName)
                    .filter(name -> name != null && !name.isEmpty())
                    .toList();

            String title = issue.getTitle() != null ? issue.getTitle() : "No title";
            String description = issue.getBody() != null ? issue.getBody() : "No description";
            Integer upvotes = issue.getReactions().getPlus1() != null ? issue.getReactions().getPlus1() : 0;
            Integer downvotes = issue.getReactions().getMinous1() != null ? issue.getReactions().getMinous1() : 0;
            Issue issue1 = new Issue(
                    issue.getId(),
                    issue.getNumber(),
                    title,
                    description,
                    issue.getState(),
                    issue.getCreatedAt(),
                    issue.getUpdatedAt(),
                    issue.getClosedAt(),
                    labels,
                    issue.getUser(),
                    issue.getAssignee(),
                    upvotes,
                    downvotes,
                    issue.getHtmlUrl()
            );
            data.add(issue1);
        }
        return data;
    }
    public void mapAuthorValues(List<Issue> issues) {
        for (Issue issue: issues) {
        User commentAuthor = issue.getAuthor();
        issue.setAuthor(commentAuthor);
        commentAuthor.setUsername(commentAuthor.getLogin());
        commentAuthor.setName(commentAuthor.getName());
        commentAuthor.setWebUrl(commentAuthor.getHtmlUrl());
        if (issue.getAssignee() != null) {
            User assignee = issue.getAssignee();
            issue.setAssignee(assignee);
            assignee.setUsername(assignee.getLogin());
            assignee.setName(assignee.getName());
            assignee.setWebUrl(assignee.getHtmlUrl());
        }
        }
    }
}
