package aiss.githubminer.controller;
import aiss.githubminer.model.Project;
import aiss.githubminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
@RestController
@RequestMapping("/github")

public class GitHubController {
    @Autowired
    ProjectService service;
    @Autowired
    RestTemplate restTemplate;
    final String gitMinerUri =
            "http://localhost:8080/gitminer/projects";
    @GetMapping("/{owner}/{repo}")
    public Project getData(@PathVariable String owner, @PathVariable
                           String repo, @RequestParam(defaultValue = "5", required = false) Integer sinceCommits,
                           @RequestParam(defaultValue = "20", required = false) Integer
                                   sinceIssues, @RequestParam(defaultValue = "2", required = false) Integer maxPages){
        return service.allData(owner,repo, sinceCommits, sinceIssues,
                maxPages);
    }
    @PostMapping("/{owner}/{repo}")
    public Project sendData(@PathVariable String owner,@PathVariable
                            String repo, @RequestParam(defaultValue = "5", required = false) Integer sinceCommits,
                            @RequestParam(defaultValue = "20", required = false) Integer
                                    sinceIssues, @RequestParam(defaultValue = "2", required = false) Integer maxPages){
        Project project= service.allData(owner,repo, sinceCommits,
                sinceIssues, maxPages);
        System.out.println("JSON enviado a GitMiner:\n" + project.toString());

        HttpEntity<Project> request = new HttpEntity<>(project);
        ResponseEntity<Project> response =
                restTemplate.exchange(gitMinerUri, HttpMethod.POST,request,
                        Project.class);
        return response.getBody();
    }
}
