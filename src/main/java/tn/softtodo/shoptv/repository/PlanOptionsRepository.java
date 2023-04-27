package tn.softtodo.shoptv.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.softtodo.shoptv.domain.PlanOptions;

/**
 * Spring Data JPA repository for the PlanOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanOptionsRepository extends JpaRepository<PlanOptions, Long> {}
