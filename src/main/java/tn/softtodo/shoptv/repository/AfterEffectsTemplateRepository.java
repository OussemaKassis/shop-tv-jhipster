package tn.softtodo.shoptv.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.softtodo.shoptv.domain.AfterEffectsTemplate;

/**
 * Spring Data JPA repository for the AfterEffectsTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AfterEffectsTemplateRepository extends JpaRepository<AfterEffectsTemplate, Long> {}
