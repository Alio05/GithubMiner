package aiss.githubminer.controller;
import aiss.githubminer.model.Project;
import aiss.githubminer.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
@RestController
@RequestMapping("/github")
@Tag(name = "GitHub", description = "Github Management API")
public class GitHubController {
    @Autowired
    ProjectService service;
    @Autowired
    RestTemplate restTemplate;
    final String gitMinerUri =
            "http://localhost:8080/gitminer/projects";
    @GetMapping("/{owner}/{repo}")
    @Operation(
            summary = "Gets a project from GitHub",
            description = "Gets a project from an owner and a repository",
            tags ={"Projects","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200",content = {@Content(schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404",content = {@Content(schema = @Schema())})
    })
    public Project getData(@PathVariable String owner, @PathVariable
                           String repo, @RequestParam(defaultValue = "5") Integer sinceCommits,
                           @RequestParam(defaultValue = "20") Integer
                                   sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
        return service.allData(owner,repo, sinceCommits, sinceIssues,
                maxPages);
    }
    @Operation(
            summary = "Obtains and resends data to another endpoint or backend",
            description = "Creates a project from Github's data and sends it",
            tags ={"Projects","send"})
    @ApiResponses({
            @ApiResponse(responseCode = "200",content = {@Content(schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "404",content = {@Content(schema = @Schema())})
    })
    @PostMapping("/{owner}/{repo}")
    public Project sendData(@PathVariable String owner,@PathVariable
                            String repo, @RequestParam(defaultValue = "5") Integer sinceCommits,
                            @RequestParam(defaultValue = "20") Integer
                                    sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
        Project project= service.allData(owner,repo, sinceCommits,
                sinceIssues, maxPages);

        HttpEntity<Project> request = new HttpEntity<>(project);
        ResponseEntity<Project> response =
                restTemplate.exchange(gitMinerUri, HttpMethod.POST,request,
                        Project.class);
        return response.getBody();
    }
}
