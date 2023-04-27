package tn.softtodo.shoptv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AfterEffectsTemplateAssets.
 */
@Entity
@Table(name = "after_effects_template_assets")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AfterEffectsTemplateAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_name")
    private String assetName;

    @Column(name = "asset_type")
    private String assetType;

    @OneToMany(mappedBy = "afterEffectsTemplateAssets")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "videos", "company", "category", "afterEffectsTemplateAssets" }, allowSetters = true)
    private Set<AfterEffectsTemplate> afterEffectsTemplates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AfterEffectsTemplateAssets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return this.assetName;
    }

    public AfterEffectsTemplateAssets assetName(String assetName) {
        this.setAssetName(assetName);
        return this;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType() {
        return this.assetType;
    }

    public AfterEffectsTemplateAssets assetType(String assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public Set<AfterEffectsTemplate> getAfterEffectsTemplates() {
        return this.afterEffectsTemplates;
    }

    public void setAfterEffectsTemplates(Set<AfterEffectsTemplate> afterEffectsTemplates) {
        if (this.afterEffectsTemplates != null) {
            this.afterEffectsTemplates.forEach(i -> i.setAfterEffectsTemplateAssets(null));
        }
        if (afterEffectsTemplates != null) {
            afterEffectsTemplates.forEach(i -> i.setAfterEffectsTemplateAssets(this));
        }
        this.afterEffectsTemplates = afterEffectsTemplates;
    }

    public AfterEffectsTemplateAssets afterEffectsTemplates(Set<AfterEffectsTemplate> afterEffectsTemplates) {
        this.setAfterEffectsTemplates(afterEffectsTemplates);
        return this;
    }

    public AfterEffectsTemplateAssets addAfterEffectsTemplate(AfterEffectsTemplate afterEffectsTemplate) {
        this.afterEffectsTemplates.add(afterEffectsTemplate);
        afterEffectsTemplate.setAfterEffectsTemplateAssets(this);
        return this;
    }

    public AfterEffectsTemplateAssets removeAfterEffectsTemplate(AfterEffectsTemplate afterEffectsTemplate) {
        this.afterEffectsTemplates.remove(afterEffectsTemplate);
        afterEffectsTemplate.setAfterEffectsTemplateAssets(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AfterEffectsTemplateAssets)) {
            return false;
        }
        return id != null && id.equals(((AfterEffectsTemplateAssets) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AfterEffectsTemplateAssets{" +
            "id=" + getId() +
            ", assetName='" + getAssetName() + "'" +
            ", assetType='" + getAssetType() + "'" +
            "}";
    }
}
