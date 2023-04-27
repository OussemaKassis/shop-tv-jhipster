package tn.softtodo.shoptv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PurchaseHistory.
 */
@Entity
@Table(name = "purchase_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PurchaseHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plan")
    private String plan;

    @Column(name = "purchase_date")
    private Instant purchaseDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "plan", "address", "purchaseHistories", "appUser" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PurchaseHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlan() {
        return this.plan;
    }

    public PurchaseHistory plan(String plan) {
        this.setPlan(plan);
        return this;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Instant getPurchaseDate() {
        return this.purchaseDate;
    }

    public PurchaseHistory purchaseDate(Instant purchaseDate) {
        this.setPurchaseDate(purchaseDate);
        return this;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PurchaseHistory client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseHistory)) {
            return false;
        }
        return id != null && id.equals(((PurchaseHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseHistory{" +
            "id=" + getId() +
            ", plan='" + getPlan() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            "}";
    }
}
