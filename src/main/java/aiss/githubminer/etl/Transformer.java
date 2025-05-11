package aiss.githubminer.etl;

import aiss.githubminer.model.Commit;
import aiss.githubminer.model.Issue;
import aiss.githubminer.model.Project;
import aiss.githubminer.model.User;
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
        List<Issue> validIssues = github.getIssues().stream()
                .map(issue -> {
                    issue.setAuthor(transformUser(issue.getAuthor()));
                    issue.setAssignee(transformUser(issue.getAssignee()));
                    return issue;
                })
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
    private User transformUser(User githubUser) {
        if (githubUser == null) return null;

        User gitMinerUser = new User();
        gitMinerUser.setUsername(
                githubUser.getUsername() != null
                        ? githubUser.getUsername()
                        : "default"
        );
        gitMinerUser.setAvatarUrl(githubUser.getAvatarUrl());
        gitMinerUser.setWebUrl(githubUser.getHtmlUrl());
        return gitMinerUser;
    }
}
