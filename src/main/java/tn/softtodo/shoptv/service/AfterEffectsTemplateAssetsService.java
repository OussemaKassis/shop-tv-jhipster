package tn.softtodo.shoptv.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.softtodo.shoptv.domain.AfterEffectsTemplateAssets;

/**
 * Service Interface for managing {@link AfterEffectsTemplateAssets}.
 */
public interface AfterEffectsTemplateAssetsService {
    /**
     * Save a afterEffectsTemplateAssets.
     *
     * @param afterEffectsTemplateAssets the entity to save.
     * @return the persisted entity.
     */
    AfterEffectsTemplateAssets save(AfterEffectsTemplateAssets afterEffectsTemplateAssets);

    /**
     * Updates a afterEffectsTemplateAssets.
     *
     * @param afterEffectsTemplateAssets the entity to update.
     * @return the persisted entity.
     */
    AfterEffectsTemplateAssets update(AfterEffectsTemplateAssets afterEffectsTemplateAssets);

    /**
     * Partially updates a afterEffectsTemplateAssets.
     *
     * @param afterEffectsTemplateAssets the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AfterEffectsTemplateAssets> partialUpdate(AfterEffectsTemplateAssets afterEffectsTemplateAssets);

    /**
     * Get all the afterEffectsTemplateAssets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AfterEffectsTemplateAssets> findAll(Pageable pageable);

    /**
     * Get the "id" afterEffectsTemplateAssets.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AfterEffectsTemplateAssets> findOne(Long id);

    /**
     * Delete the "id" afterEffectsTemplateAssets.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
