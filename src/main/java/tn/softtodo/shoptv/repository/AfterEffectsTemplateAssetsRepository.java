package tn.softtodo.shoptv.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.softtodo.shoptv.domain.AfterEffectsTemplateAssets;

/**
 * Spring Data JPA repository for the AfterEffectsTemplateAssets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AfterEffectsTemplateAssetsRepository extends JpaRepository<AfterEffectsTemplateAssets, Long> {}
