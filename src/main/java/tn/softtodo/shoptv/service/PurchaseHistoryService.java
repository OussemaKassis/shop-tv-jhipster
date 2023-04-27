package tn.softtodo.shoptv.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.softtodo.shoptv.domain.PurchaseHistory;

/**
 * Service Interface for managing {@link PurchaseHistory}.
 */
public interface PurchaseHistoryService {
    /**
     * Save a purchaseHistory.
     *
     * @param purchaseHistory the entity to save.
     * @return the persisted entity.
     */
    PurchaseHistory save(PurchaseHistory purchaseHistory);

    /**
     * Updates a purchaseHistory.
     *
     * @param purchaseHistory the entity to update.
     * @return the persisted entity.
     */
    PurchaseHistory update(PurchaseHistory purchaseHistory);

    /**
     * Partially updates a purchaseHistory.
     *
     * @param purchaseHistory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PurchaseHistory> partialUpdate(PurchaseHistory purchaseHistory);

    /**
     * Get all the purchaseHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PurchaseHistory> findAll(Pageable pageable);

    /**
     * Get the "id" purchaseHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseHistory> findOne(Long id);

    /**
     * Delete the "id" purchaseHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
