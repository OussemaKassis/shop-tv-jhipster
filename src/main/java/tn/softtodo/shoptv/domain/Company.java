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
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_type")
    private String companyType;

    @Column(name = "company_description")
    private String companyDescription;

    @Column(name = "company_picture")
    private String companyPicture;

    @Column(name = "company_creation_date")
    private Instant companyCreationDate;

    @Column(name = "company_location_address")
    private String companyLocationAddress;

    @Column(name = "company_activity_domaine")
    private String companyActivityDomaine;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "videos", "company", "user" }, allowSetters = true)
    private Set<AppUser> appUsers = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "videos", "company", "category", "afterEffectsTemplateAssets" }, allowSetters = true)
    private Set<AfterEffectsTemplate> afterEffectsTemplates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public Company companyName(String companyName) {
        this.setCompanyName(companyName);
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return this.companyType;
    }

    public Company companyType(String companyType) {
        this.setCompanyType(companyType);
        return this;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyDescription() {
        return this.companyDescription;
    }

    public Company companyDescription(String companyDescription) {
        this.setCompanyDescription(companyDescription);
        return this;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getCompanyPicture() {
        return this.companyPicture;
    }

    public Company companyPicture(String companyPicture) {
        this.setCompanyPicture(companyPicture);
        return this;
    }

    public void setCompanyPicture(String companyPicture) {
        this.companyPicture = companyPicture;
    }

    public Instant getCompanyCreationDate() {
        return this.companyCreationDate;
    }

    public Company companyCreationDate(Instant companyCreationDate) {
        this.setCompanyCreationDate(companyCreationDate);
        return this;
    }

    public void setCompanyCreationDate(Instant companyCreationDate) {
        this.companyCreationDate = companyCreationDate;
    }

    public String getCompanyLocationAddress() {
        return this.companyLocationAddress;
    }

    public Company companyLocationAddress(String companyLocationAddress) {
        this.setCompanyLocationAddress(companyLocationAddress);
        return this;
    }

    public void setCompanyLocationAddress(String companyLocationAddress) {
        this.companyLocationAddress = companyLocationAddress;
    }

    public String getCompanyActivityDomaine() {
        return this.companyActivityDomaine;
    }

    public Company companyActivityDomaine(String companyActivityDomaine) {
        this.setCompanyActivityDomaine(companyActivityDomaine);
        return this;
    }

    public void setCompanyActivityDomaine(String companyActivityDomaine) {
        this.companyActivityDomaine = companyActivityDomaine;
    }

    public Set<AppUser> getAppUsers() {
        return this.appUsers;
    }

    public void setAppUsers(Set<AppUser> appUsers) {
        if (this.appUsers != null) {
            this.appUsers.forEach(i -> i.setCompany(null));
        }
        if (appUsers != null) {
            appUsers.forEach(i -> i.setCompany(this));
        }
        this.appUsers = appUsers;
    }

    public Company appUsers(Set<AppUser> appUsers) {
        this.setAppUsers(appUsers);
        return this;
    }

    public Company addAppUser(AppUser appUser) {
        this.appUsers.add(appUser);
        appUser.setCompany(this);
        return this;
    }

    public Company removeAppUser(AppUser appUser) {
        this.appUsers.remove(appUser);
        appUser.setCompany(null);
        return this;
    }

    public Set<AfterEffectsTemplate> getAfterEffectsTemplates() {
        return this.afterEffectsTemplates;
    }

    public void setAfterEffectsTemplates(Set<AfterEffectsTemplate> afterEffectsTemplates) {
        if (this.afterEffectsTemplates != null) {
            this.afterEffectsTemplates.forEach(i -> i.setCompany(null));
        }
        if (afterEffectsTemplates != null) {
            afterEffectsTemplates.forEach(i -> i.setCompany(this));
        }
        this.afterEffectsTemplates = afterEffectsTemplates;
    }

    public Company afterEffectsTemplates(Set<AfterEffectsTemplate> afterEffectsTemplates) {
        this.setAfterEffectsTemplates(afterEffectsTemplates);
        return this;
    }

    public Company addAfterEffectsTemplate(AfterEffectsTemplate afterEffectsTemplate) {
        this.afterEffectsTemplates.add(afterEffectsTemplate);
        afterEffectsTemplate.setCompany(this);
        return this;
    }

    public Company removeAfterEffectsTemplate(AfterEffectsTemplate afterEffectsTemplate) {
        this.afterEffectsTemplates.remove(afterEffectsTemplate);
        afterEffectsTemplate.setCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyType='" + getCompanyType() + "'" +
            ", companyDescription='" + getCompanyDescription() + "'" +
            ", companyPicture='" + getCompanyPicture() + "'" +
            ", companyCreationDate='" + getCompanyCreationDate() + "'" +
            ", companyLocationAddress='" + getCompanyLocationAddress() + "'" +
            ", companyActivityDomaine='" + getCompanyActivityDomaine() + "'" +
            "}";
    }
}
