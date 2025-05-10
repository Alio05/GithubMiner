package aiss.githubminer.model;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "web_url",
        "commits",
        "issues"
})
public class Project {
    @Id
    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    @NotEmpty(message = "The name of the project cannot be empty")
    public String name;
    @JsonProperty("web_url")
    @NotEmpty(message = "The web url of the project cannot be empty")
    private String webUrl;
    @JsonProperty("html_url")
    @JsonIgnore
    @NotEmpty(message = "The web url of the project cannot be empty")
    private String htmlUrl;
    @JsonProperty("commits")
    private List<Commit> commits;
    @JsonProperty("issues")
    private List<Issue> issues;
    public Project() {
        commits = new ArrayList<>();
        issues = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issuesList) {
        this.issues = issuesList;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Project.class.getName()).append('@').append(Integer.toHexString
                (System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(",\n");
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(",\n");
        sb.append("webUrl");
        sb.append('=');
        sb.append(((this.webUrl == null) ? "<null>" : this.webUrl));
        sb.append(",\n");
        sb.append("commits");
        sb.append('=');
        sb.append(((this.commits == null) ? "<null>" : this.commits));
        sb.append(",\n");
        sb.append("issues");
        sb.append('=');
        sb.append(((this.issues == null) ? "<null>" : this.issues));
        sb.append(",\n");
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}

