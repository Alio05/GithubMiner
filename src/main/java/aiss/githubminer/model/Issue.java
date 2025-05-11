package aiss.githubminer.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.annotation.Generated;
import aiss.githubminer.model.Comment;
import aiss.githubminer.model.IssueData.Label;
import aiss.githubminer.model.IssueData.Reactions;
import aiss.githubminer.model.User;
import com.fasterxml.jackson.annotation.*;
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
        "id",
        "ref_id",
        "title",
        "description",
        "state",
        "created_at",
        "updated_at",
        "closed_At",
        "labels",
        "author",
        "assignee",
        "upvotes",
        "downvotes",
        "web_url",
        "comments"
})
@Generated("jsonschema2pojo")
public class Issue {
    @JsonProperty("id")
    private String id;
    @JsonProperty("ref_id")
    private String refId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("state")
    private String state;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("closed_at")
    private String closedAt;
    @JsonProperty("author")
    private User author;
    @JsonProperty("assignee")
    private User assignee;
    @JsonProperty("upvotes")
    private Integer upvotes;
    @JsonProperty("downvotes")
    private Integer downvotes;
    @JsonProperty("html_url")
    private String webUrl;
    @JsonProperty("comments")
    private List<Comment> comments;
    @JsonProperty("labels")
    private List<String> labels;
    public Issue(String id, String refId, String title, String
                         description,
                 String state, String createdAt, String
                         updatedAt, String closedAt,
                 List<String> labels, User author, User
                         assignee, Integer upvotes,
                 Integer downvotes, String webUrl) {
        this.id = id;
        this.refId = refId;
        this.title = title;
        this.description = description;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.labels = labels;
        this.author = author;
        this.assignee = assignee;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.webUrl = webUrl;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRefId() {
        return refId;
    }
    public void setRefId(String refId) {
        this.refId = refId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getClosedAt() {
        return closedAt;
    }
    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }
    public Integer getUpvotes() {
        return upvotes;
    }
    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }
    public Integer getDownvotes() {
        return downvotes;
    }
    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public User getAssignee() {
        return assignee;
    }
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }
    public String getWebUrl() {
        return webUrl;
    }
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public List<String> getLabels() {
        return labels;
    }
    public void setLabels(List<Label> labels) {
        this.labels = (labels != null)
                ? labels.stream().map(Label::getName).filter(Objects::nonNull).toList()
                : new ArrayList<>();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(",\n");
        sb.append("refId");
        sb.append('=');
        sb.append(((this.refId == null)?"<null>":this.refId));
        sb.append(",\n");
        sb.append("title");
        sb.append('=');
        sb.append(",\n");
        sb.append("description");
        sb.append('=');
        sb.append(",\n");
        sb.append("state");
        sb.append('=');
        sb.append(((this.state == null)?"<null>":this.state));
        sb.append(",\n");
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(",\n");
        sb.append("updatedAt");
        sb.append('=');
        sb.append(((this.updatedAt == null)?"<null>":this.updatedAt));
        sb.append(",\n");
        sb.append("closedAt");
        sb.append('=');
        sb.append(((this.closedAt == null)?"<null>":this.closedAt));
        sb.append(",\n");
        sb.append("labels");
        sb.append('=');
        sb.append(((this.labels == null)?"<null>":this.labels));
        sb.append(",\n");
        sb.append("upvotes");
        sb.append('=');
        sb.append(((this.upvotes == null)?"<null>":this.upvotes));
        sb.append(",\n");
        sb.append("downvotes");
        sb.append('=');
        sb.append(((this.downvotes == null)?"<null>":this.downvotes));
        sb.append(",\n");
        sb.append("author");
        sb.append('=');
        sb.append(((this.author == null)?"<null>":this.author));
        sb.append(",\n");
        sb.append("assignee");
        sb.append('=');
        sb.append(((this.assignee == null)?"<null>":this.assignee));
        sb.append(",\n");
        sb.append("web_url");
        sb.append('=');
        sb.append(((this.webUrl == null)?"<null>":this.webUrl));
        sb.append(",\n");
        sb.append("comments");
        sb.append('=');
        sb.append(((this.comments == null)?"<null>":this.comments));
        sb.append(",\n");
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
