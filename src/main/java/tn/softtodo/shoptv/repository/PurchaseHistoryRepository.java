package tn.softtodo.shoptv.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.softtodo.shoptv.domain.PurchaseHistory;

/**
 * Spring Data JPA repository for the PurchaseHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {}
