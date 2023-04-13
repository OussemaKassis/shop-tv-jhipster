package tn.softtodo.shoptv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.softtodo.shoptv.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
