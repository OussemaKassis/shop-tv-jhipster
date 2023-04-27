package tn.softtodo.shoptv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tn.softtodo.shoptv.domain.enumeration.Job;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "current_plan_offer")
    private String currentPlanOffer;

    @Enumerated(EnumType.STRING)
    @Column(name = "job")
    private Job job;

    @JsonIgnoreProperties(value = { "planOptions" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Plan plan;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private Set<PurchaseHistory> purchaseHistories = new HashSet<>();

    @JsonIgnoreProperties(value = { "videos", "company", "user" }, allowSetters = true)
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Client phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return this.gender;
    }

    public Client gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Client dateOfBirth(Instant dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCurrentPlanOffer() {
        return this.currentPlanOffer;
    }

    public Client currentPlanOffer(String currentPlanOffer) {
        this.setCurrentPlanOffer(currentPlanOffer);
        return this;
    }

    public void setCurrentPlanOffer(String currentPlanOffer) {
        this.currentPlanOffer = currentPlanOffer;
    }

    public Job getJob() {
        return this.job;
    }

    public Client job(Job job) {
        this.setJob(job);
        return this;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Plan getPlan() {
        return this.plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Client plan(Plan plan) {
        this.setPlan(plan);
        return this;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Client address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<PurchaseHistory> getPurchaseHistories() {
        return this.purchaseHistories;
    }

    public void setPurchaseHistories(Set<PurchaseHistory> purchaseHistories) {
        if (this.purchaseHistories != null) {
            this.purchaseHistories.forEach(i -> i.setClient(null));
        }
        if (purchaseHistories != null) {
            purchaseHistories.forEach(i -> i.setClient(this));
        }
        this.purchaseHistories = purchaseHistories;
    }

    public Client purchaseHistories(Set<PurchaseHistory> purchaseHistories) {
        this.setPurchaseHistories(purchaseHistories);
        return this;
    }

    public Client addPurchaseHistory(PurchaseHistory purchaseHistory) {
        this.purchaseHistories.add(purchaseHistory);
        purchaseHistory.setClient(this);
        return this;
    }

    public Client removePurchaseHistory(PurchaseHistory purchaseHistory) {
        this.purchaseHistories.remove(purchaseHistory);
        purchaseHistory.setClient(null);
        return this;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Client appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", currentPlanOffer='" + getCurrentPlanOffer() + "'" +
            ", job='" + getJob() + "'" +
            "}";
    }
}
