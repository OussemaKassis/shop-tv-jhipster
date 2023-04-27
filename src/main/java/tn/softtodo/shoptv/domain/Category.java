package tn.softtodo.shoptv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ratio")
    private String ratio;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "videos", "company", "category", "afterEffectsTemplateAssets" }, allowSetters = true)
    private Set<AfterEffectsTemplate> afterEffectsTemplates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Category id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public Category categoryName(String categoryName) {
        this.setCategoryName(categoryName);
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRatio() {
        return this.ratio;
    }

    public Category ratio(String ratio) {
        this.setRatio(ratio);
        return this;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public Set<AfterEffectsTemplate> getAfterEffectsTemplates() {
        return this.afterEffectsTemplates;
    }

    public void setAfterEffectsTemplates(Set<AfterEffectsTemplate> afterEffectsTemplates) {
        if (this.afterEffectsTemplates != null) {
            this.afterEffectsTemplates.forEach(i -> i.setCategory(null));
        }
        if (afterEffectsTemplates != null) {
            afterEffectsTemplates.forEach(i -> i.setCategory(this));
        }
        this.afterEffectsTemplates = afterEffectsTemplates;
    }

    public Category afterEffectsTemplates(Set<AfterEffectsTemplate> afterEffectsTemplates) {
        this.setAfterEffectsTemplates(afterEffectsTemplates);
        return this;
    }

    public Category addAfterEffectsTemplate(AfterEffectsTemplate afterEffectsTemplate) {
        this.afterEffectsTemplates.add(afterEffectsTemplate);
        afterEffectsTemplate.setCategory(this);
        return this;
    }

    public Category removeAfterEffectsTemplate(AfterEffectsTemplate afterEffectsTemplate) {
        this.afterEffectsTemplates.remove(afterEffectsTemplate);
        afterEffectsTemplate.setCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", ratio='" + getRatio() + "'" +
            "}";
    }
}
