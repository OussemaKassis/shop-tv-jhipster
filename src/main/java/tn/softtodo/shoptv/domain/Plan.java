package tn.softtodo.shoptv.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Plan.
 */
@Entity
@Table(name = "plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "price")
    private String price;

    @OneToOne
    @JoinColumn(unique = true)
    private PlanOptions planOptions;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Plan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return this.planName;
    }

    public Plan planName(String planName) {
        this.setPlanName(planName);
        return this;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPrice() {
        return this.price;
    }

    public Plan price(String price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public PlanOptions getPlanOptions() {
        return this.planOptions;
    }

    public void setPlanOptions(PlanOptions planOptions) {
        this.planOptions = planOptions;
    }

    public Plan planOptions(PlanOptions planOptions) {
        this.setPlanOptions(planOptions);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plan)) {
            return false;
        }
        return id != null && id.equals(((Plan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plan{" +
            "id=" + getId() +
            ", planName='" + getPlanName() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
