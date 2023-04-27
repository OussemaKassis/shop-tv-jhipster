package tn.softtodo.shoptv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AfterEffectsTemplate.
 */
@Entity
@Table(name = "after_effects_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AfterEffectsTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "template_duration")
    private String templateDuration;

    @Column(name = "template_description")
    private String templateDescription;

    @Column(name = "template_rating")
    private Instant templateRating;

    @Column(name = "template_active")
    private Boolean templateActive;

    @Column(name = "template_type")
    private String templateType;

    @Column(name = "template_expected_size")
    private String templateExpectedSize;

    @Column(name = "template_count")
    private String templateCount;

    @Column(name = "template_visible_for")
    private String templateVisibleFor;

    @Column(name = "ratio")
    private String ratio;

    @Column(name = "preview_url")
    private String previewUrl;

    @OneToMany(mappedBy = "afterEffectsTemplate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "afterEffectsTemplate", "user" }, allowSetters = true)
    private Set<Video> videos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "appUsers", "afterEffectsTemplates" }, allowSetters = true)
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties(value = { "afterEffectsTemplates" }, allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "afterEffectsTemplates" }, allowSetters = true)
    private AfterEffectsTemplateAssets afterEffectsTemplateAssets;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AfterEffectsTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public AfterEffectsTemplate templateName(String templateName) {
        this.setTemplateName(templateName);
        return this;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDuration() {
        return this.templateDuration;
    }

    public AfterEffectsTemplate templateDuration(String templateDuration) {
        this.setTemplateDuration(templateDuration);
        return this;
    }

    public void setTemplateDuration(String templateDuration) {
        this.templateDuration = templateDuration;
    }

    public String getTemplateDescription() {
        return this.templateDescription;
    }

    public AfterEffectsTemplate templateDescription(String templateDescription) {
        this.setTemplateDescription(templateDescription);
        return this;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public Instant getTemplateRating() {
        return this.templateRating;
    }

    public AfterEffectsTemplate templateRating(Instant templateRating) {
        this.setTemplateRating(templateRating);
        return this;
    }

    public void setTemplateRating(Instant templateRating) {
        this.templateRating = templateRating;
    }

    public Boolean getTemplateActive() {
        return this.templateActive;
    }

    public AfterEffectsTemplate templateActive(Boolean templateActive) {
        this.setTemplateActive(templateActive);
        return this;
    }

    public void setTemplateActive(Boolean templateActive) {
        this.templateActive = templateActive;
    }

    public String getTemplateType() {
        return this.templateType;
    }

    public AfterEffectsTemplate templateType(String templateType) {
        this.setTemplateType(templateType);
        return this;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateExpectedSize() {
        return this.templateExpectedSize;
    }

    public AfterEffectsTemplate templateExpectedSize(String templateExpectedSize) {
        this.setTemplateExpectedSize(templateExpectedSize);
        return this;
    }

    public void setTemplateExpectedSize(String templateExpectedSize) {
        this.templateExpectedSize = templateExpectedSize;
    }

    public String getTemplateCount() {
        return this.templateCount;
    }

    public AfterEffectsTemplate templateCount(String templateCount) {
        this.setTemplateCount(templateCount);
        return this;
    }

    public void setTemplateCount(String templateCount) {
        this.templateCount = templateCount;
    }

    public String getTemplateVisibleFor() {
        return this.templateVisibleFor;
    }

    public AfterEffectsTemplate templateVisibleFor(String templateVisibleFor) {
        this.setTemplateVisibleFor(templateVisibleFor);
        return this;
    }

    public void setTemplateVisibleFor(String templateVisibleFor) {
        this.templateVisibleFor = templateVisibleFor;
    }

    public String getRatio() {
        return this.ratio;
    }

    public AfterEffectsTemplate ratio(String ratio) {
        this.setRatio(ratio);
        return this;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public AfterEffectsTemplate previewUrl(String previewUrl) {
        this.setPreviewUrl(previewUrl);
        return this;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public Set<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<Video> videos) {
        if (this.videos != null) {
            this.videos.forEach(i -> i.setAfterEffectsTemplate(null));
        }
        if (videos != null) {
            videos.forEach(i -> i.setAfterEffectsTemplate(this));
        }
        this.videos = videos;
    }

    public AfterEffectsTemplate videos(Set<Video> videos) {
        this.setVideos(videos);
        return this;
    }

    public AfterEffectsTemplate addVideo(Video video) {
        this.videos.add(video);
        video.setAfterEffectsTemplate(this);
        return this;
    }

    public AfterEffectsTemplate removeVideo(Video video) {
        this.videos.remove(video);
        video.setAfterEffectsTemplate(null);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public AfterEffectsTemplate company(Company company) {
        this.setCompany(company);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AfterEffectsTemplate category(Category category) {
        this.setCategory(category);
        return this;
    }

    public AfterEffectsTemplateAssets getAfterEffectsTemplateAssets() {
        return this.afterEffectsTemplateAssets;
    }

    public void setAfterEffectsTemplateAssets(AfterEffectsTemplateAssets afterEffectsTemplateAssets) {
        this.afterEffectsTemplateAssets = afterEffectsTemplateAssets;
    }

    public AfterEffectsTemplate afterEffectsTemplateAssets(AfterEffectsTemplateAssets afterEffectsTemplateAssets) {
        this.setAfterEffectsTemplateAssets(afterEffectsTemplateAssets);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AfterEffectsTemplate)) {
            return false;
        }
        return id != null && id.equals(((AfterEffectsTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AfterEffectsTemplate{" +
            "id=" + getId() +
            ", templateName='" + getTemplateName() + "'" +
            ", templateDuration='" + getTemplateDuration() + "'" +
            ", templateDescription='" + getTemplateDescription() + "'" +
            ", templateRating='" + getTemplateRating() + "'" +
            ", templateActive='" + getTemplateActive() + "'" +
            ", templateType='" + getTemplateType() + "'" +
            ", templateExpectedSize='" + getTemplateExpectedSize() + "'" +
            ", templateCount='" + getTemplateCount() + "'" +
            ", templateVisibleFor='" + getTemplateVisibleFor() + "'" +
            ", ratio='" + getRatio() + "'" +
            ", previewUrl='" + getPreviewUrl() + "'" +
            "}";
    }
}
