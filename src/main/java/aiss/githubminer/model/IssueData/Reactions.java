package aiss.githubminer.model.IssueData;
import jakarta.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "url",
        "total_count",
        "+1",
        "-1",
        "laugh",
        "hooray",
        "confused",
        "heart",
        "rocket",
        "eyes"
})
@Generated("jsonschema2pojo")
public class Reactions {
    @JsonProperty("url")
    private String url;
    @JsonProperty("total_count")
    private Integer totalCount;
    @JsonProperty("+1")
    private Integer plus1;
    @JsonProperty("-1")
    private Integer minous1;
    @JsonProperty("laugh")
    private Integer laugh;
    @JsonProperty("hooray")
    private Integer hooray;
    @JsonProperty("confused")
    private Integer confused;
    @JsonProperty("heart")
    private Integer heart;
    @JsonProperty("rocket")
    private Integer rocket;
    @JsonProperty("eyes")
    private Integer eyes;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPlus1() {
        return plus1;
    }

    public void setPlus1(Integer plus1) {
        this.plus1 = plus1;
    }

    public Integer getMinous1() {
        return minous1;
    }

    public void setBruh(Integer minous1) {
        this.minous1 = minous1;
    }

    public Integer getLaugh() {
        return laugh;
    }

    public void setLaugh(Integer laugh) {
        this.laugh = laugh;
    }

    public Integer getHooray() {
        return hooray;
    }

    public void setHooray(Integer hooray) {
        this.hooray = hooray;
    }

    public Integer getConfused() {
        return confused;
    }

    public void setConfused(Integer confused) {
        this.confused = confused;
    }

    public Integer getHeart() {
        return heart;
    }

    public void setHeart(Integer heart) {
        this.heart = heart;
    }

    public Integer getRocket() {
        return rocket;
    }

    public void setRocket(Integer rocket) {
        this.rocket = rocket;
    }

    public Integer getEyes() {
        return eyes;
    }

    public void setEyes(Integer eyes) {
        this.eyes = eyes;
    }
}
