package tn.softtodo.shoptv.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.softtodo.shoptv.domain.Plan;

/**
 * Spring Data JPA repository for the Plan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {}
