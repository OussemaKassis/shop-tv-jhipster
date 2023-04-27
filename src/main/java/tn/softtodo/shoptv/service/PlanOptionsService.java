package tn.softtodo.shoptv.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.softtodo.shoptv.domain.PlanOptions;

/**
 * Service Interface for managing {@link PlanOptions}.
 */
public interface PlanOptionsService {
    /**
     * Save a planOptions.
     *
     * @param planOptions the entity to save.
     * @return the persisted entity.
     */
    PlanOptions save(PlanOptions planOptions);

    /**
     * Updates a planOptions.
     *
     * @param planOptions the entity to update.
     * @return the persisted entity.
     */
    PlanOptions update(PlanOptions planOptions);

    /**
     * Partially updates a planOptions.
     *
     * @param planOptions the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanOptions> partialUpdate(PlanOptions planOptions);

    /**
     * Get all the planOptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanOptions> findAll(Pageable pageable);

    /**
     * Get the "id" planOptions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanOptions> findOne(Long id);

    /**
     * Delete the "id" planOptions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
