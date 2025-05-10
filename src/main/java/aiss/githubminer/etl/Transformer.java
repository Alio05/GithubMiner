package aiss.githubminer.etl;

import aiss.githubminer.model.Commit;
import aiss.githubminer.model.Issue;
import aiss.githubminer.model.Project;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Transformer {
    public Project transform(Project github){
        Project gitMinerProject = new Project();

        gitMinerProject.setId(github.getId());
        gitMinerProject.setName(github.getName());
        gitMinerProject.setWebUrl(github.getHtmlUrl());

        List<Commit> validCommits = github.getCommits().stream()
                .filter(commit ->
                        commit.getId() != null &&
                                !commit.getId().equals("no-id") &&
                                commit.getTitle() != null
                )
                .toList();
        github.getIssues().forEach(issue -> {if (issue.getAuthor() != null && issue.getAuthor().getUsername() == null) {
            issue.getAuthor().setUsername(issue.getAuthor().getLogin());
        }
        });
        List<Issue> validIssues = github.getIssues().stream()
                .filter(issue ->
                        issue.getTitle() != null &&
                                !issue.getTitle().isEmpty() &&
                                issue.getDescription() != null &&
                                issue.getAuthor() != null &&
                                issue.getAuthor().getUsername() != null
                ).toList();
        gitMinerProject.setIssues(validIssues);

        return gitMinerProject;
    }
    private Commit transformCommit(Commit github){
        Commit gitMinerCommit = new Commit();
        gitMinerCommit.setId(github.getSha());
        return gitMinerCommit;
    }
}
