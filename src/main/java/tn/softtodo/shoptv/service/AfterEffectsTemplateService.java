package tn.softtodo.shoptv.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.softtodo.shoptv.domain.AfterEffectsTemplate;

/**
 * Service Interface for managing {@link AfterEffectsTemplate}.
 */
public interface AfterEffectsTemplateService {
    /**
     * Save a afterEffectsTemplate.
     *
     * @param afterEffectsTemplate the entity to save.
     * @return the persisted entity.
     */
    AfterEffectsTemplate save(AfterEffectsTemplate afterEffectsTemplate);

    /**
     * Updates a afterEffectsTemplate.
     *
     * @param afterEffectsTemplate the entity to update.
     * @return the persisted entity.
     */
    AfterEffectsTemplate update(AfterEffectsTemplate afterEffectsTemplate);

    /**
     * Partially updates a afterEffectsTemplate.
     *
     * @param afterEffectsTemplate the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AfterEffectsTemplate> partialUpdate(AfterEffectsTemplate afterEffectsTemplate);

    /**
     * Get all the afterEffectsTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AfterEffectsTemplate> findAll(Pageable pageable);

    /**
     * Get the "id" afterEffectsTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AfterEffectsTemplate> findOne(Long id);

    /**
     * Delete the "id" afterEffectsTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
