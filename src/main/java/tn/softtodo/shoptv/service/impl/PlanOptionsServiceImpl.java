package tn.softtodo.shoptv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.domain.PlanOptions;
import tn.softtodo.shoptv.repository.PlanOptionsRepository;
import tn.softtodo.shoptv.service.PlanOptionsService;

/**
 * Service Implementation for managing {@link PlanOptions}.
 */
@Service
@Transactional
public class PlanOptionsServiceImpl implements PlanOptionsService {

    private final Logger log = LoggerFactory.getLogger(PlanOptionsServiceImpl.class);

    private final PlanOptionsRepository planOptionsRepository;

    public PlanOptionsServiceImpl(PlanOptionsRepository planOptionsRepository) {
        this.planOptionsRepository = planOptionsRepository;
    }

    @Override
    public PlanOptions save(PlanOptions planOptions) {
        log.debug("Request to save PlanOptions : {}", planOptions);
        return planOptionsRepository.save(planOptions);
    }

    @Override
    public PlanOptions update(PlanOptions planOptions) {
        log.debug("Request to update PlanOptions : {}", planOptions);
        return planOptionsRepository.save(planOptions);
    }

    @Override
    public Optional<PlanOptions> partialUpdate(PlanOptions planOptions) {
        log.debug("Request to partially update PlanOptions : {}", planOptions);

        return planOptionsRepository
            .findById(planOptions.getId())
            .map(existingPlanOptions -> {
                if (planOptions.getAeTemplateLimit() != null) {
                    existingPlanOptions.setAeTemplateLimit(planOptions.getAeTemplateLimit());
                }
                if (planOptions.getVideoSubmittionLimit() != null) {
                    existingPlanOptions.setVideoSubmittionLimit(planOptions.getVideoSubmittionLimit());
                }
                if (planOptions.getEmojis() != null) {
                    existingPlanOptions.setEmojis(planOptions.getEmojis());
                }
                if (planOptions.getStorage() != null) {
                    existingPlanOptions.setStorage(planOptions.getStorage());
                }

                return existingPlanOptions;
            })
            .map(planOptionsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanOptions> findAll(Pageable pageable) {
        log.debug("Request to get all PlanOptions");
        return planOptionsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanOptions> findOne(Long id) {
        log.debug("Request to get PlanOptions : {}", id);
        return planOptionsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanOptions : {}", id);
        planOptionsRepository.deleteById(id);
    }
}
