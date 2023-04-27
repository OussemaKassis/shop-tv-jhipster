package tn.softtodo.shoptv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Video.
 */
@Entity
@Table(name = "video")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "video_name")
    private String videoName;

    @Column(name = "video_duration")
    private String videoDuration;

    @Column(name = "video_size")
    private String videoSize;

    @Column(name = "video_category")
    private String videoCategory;

    @Column(name = "video_rating")
    private String videoRating;

    @Column(name = "video_status")
    private String videoStatus;

    @Column(name = "video_public_url")
    private String videoPublicUrl;

    @Column(name = "video_path")
    private String videoPath;

    @Column(name = "video_visible_for")
    private String videoVisibleFor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "videos", "company", "category", "afterEffectsTemplateAssets" }, allowSetters = true)
    private AfterEffectsTemplate afterEffectsTemplate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "videos", "company", "user" }, allowSetters = true)
    private AppUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Video id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoName() {
        return this.videoName;
    }

    public Video videoName(String videoName) {
        this.setVideoName(videoName);
        return this;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoDuration() {
        return this.videoDuration;
    }

    public Video videoDuration(String videoDuration) {
        this.setVideoDuration(videoDuration);
        return this;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoSize() {
        return this.videoSize;
    }

    public Video videoSize(String videoSize) {
        this.setVideoSize(videoSize);
        return this;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }

    public String getVideoCategory() {
        return this.videoCategory;
    }

    public Video videoCategory(String videoCategory) {
        this.setVideoCategory(videoCategory);
        return this;
    }

    public void setVideoCategory(String videoCategory) {
        this.videoCategory = videoCategory;
    }

    public String getVideoRating() {
        return this.videoRating;
    }

    public Video videoRating(String videoRating) {
        this.setVideoRating(videoRating);
        return this;
    }

    public void setVideoRating(String videoRating) {
        this.videoRating = videoRating;
    }

    public String getVideoStatus() {
        return this.videoStatus;
    }

    public Video videoStatus(String videoStatus) {
        this.setVideoStatus(videoStatus);
        return this;
    }

    public void setVideoStatus(String videoStatus) {
        this.videoStatus = videoStatus;
    }

    public String getVideoPublicUrl() {
        return this.videoPublicUrl;
    }

    public Video videoPublicUrl(String videoPublicUrl) {
        this.setVideoPublicUrl(videoPublicUrl);
        return this;
    }

    public void setVideoPublicUrl(String videoPublicUrl) {
        this.videoPublicUrl = videoPublicUrl;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public Video videoPath(String videoPath) {
        this.setVideoPath(videoPath);
        return this;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoVisibleFor() {
        return this.videoVisibleFor;
    }

    public Video videoVisibleFor(String videoVisibleFor) {
        this.setVideoVisibleFor(videoVisibleFor);
        return this;
    }

    public void setVideoVisibleFor(String videoVisibleFor) {
        this.videoVisibleFor = videoVisibleFor;
    }

    public AfterEffectsTemplate getAfterEffectsTemplate() {
        return this.afterEffectsTemplate;
    }

    public void setAfterEffectsTemplate(AfterEffectsTemplate afterEffectsTemplate) {
        this.afterEffectsTemplate = afterEffectsTemplate;
    }

    public Video afterEffectsTemplate(AfterEffectsTemplate afterEffectsTemplate) {
        this.setAfterEffectsTemplate(afterEffectsTemplate);
        return this;
    }

    public AppUser getUser() {
        return this.user;
    }

    public void setUser(AppUser appUser) {
        this.user = appUser;
    }

    public Video user(AppUser appUser) {
        this.setUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Video)) {
            return false;
        }
        return id != null && id.equals(((Video) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Video{" +
            "id=" + getId() +
            ", videoName='" + getVideoName() + "'" +
            ", videoDuration='" + getVideoDuration() + "'" +
            ", videoSize='" + getVideoSize() + "'" +
            ", videoCategory='" + getVideoCategory() + "'" +
            ", videoRating='" + getVideoRating() + "'" +
            ", videoStatus='" + getVideoStatus() + "'" +
            ", videoPublicUrl='" + getVideoPublicUrl() + "'" +
            ", videoPath='" + getVideoPath() + "'" +
            ", videoVisibleFor='" + getVideoVisibleFor() + "'" +
            "}";
    }
}
