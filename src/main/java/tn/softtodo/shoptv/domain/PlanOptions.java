package tn.softtodo.shoptv.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlanOptions.
 */
@Entity
@Table(name = "plan_options")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ae_template_limit")
    private String aeTemplateLimit;

    @Column(name = "video_submittion_limit")
    private String videoSubmittionLimit;

    @Column(name = "emojis")
    private Boolean emojis;

    @Column(name = "storage")
    private String storage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanOptions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAeTemplateLimit() {
        return this.aeTemplateLimit;
    }

    public PlanOptions aeTemplateLimit(String aeTemplateLimit) {
        this.setAeTemplateLimit(aeTemplateLimit);
        return this;
    }

    public void setAeTemplateLimit(String aeTemplateLimit) {
        this.aeTemplateLimit = aeTemplateLimit;
    }

    public String getVideoSubmittionLimit() {
        return this.videoSubmittionLimit;
    }

    public PlanOptions videoSubmittionLimit(String videoSubmittionLimit) {
        this.setVideoSubmittionLimit(videoSubmittionLimit);
        return this;
    }

    public void setVideoSubmittionLimit(String videoSubmittionLimit) {
        this.videoSubmittionLimit = videoSubmittionLimit;
    }

    public Boolean getEmojis() {
        return this.emojis;
    }

    public PlanOptions emojis(Boolean emojis) {
        this.setEmojis(emojis);
        return this;
    }

    public void setEmojis(Boolean emojis) {
        this.emojis = emojis;
    }

    public String getStorage() {
        return this.storage;
    }

    public PlanOptions storage(String storage) {
        this.setStorage(storage);
        return this;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanOptions)) {
            return false;
        }
        return id != null && id.equals(((PlanOptions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanOptions{" +
            "id=" + getId() +
            ", aeTemplateLimit='" + getAeTemplateLimit() + "'" +
            ", videoSubmittionLimit='" + getVideoSubmittionLimit() + "'" +
            ", emojis='" + getEmojis() + "'" +
            ", storage='" + getStorage() + "'" +
            "}";
    }
}
