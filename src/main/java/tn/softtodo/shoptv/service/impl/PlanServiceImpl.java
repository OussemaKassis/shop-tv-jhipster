package tn.softtodo.shoptv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.domain.Plan;
import tn.softtodo.shoptv.repository.PlanRepository;
import tn.softtodo.shoptv.service.PlanService;

/**
 * Service Implementation for managing {@link Plan}.
 */
@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final Logger log = LoggerFactory.getLogger(PlanServiceImpl.class);

    private final PlanRepository planRepository;

    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public Plan save(Plan plan) {
        log.debug("Request to save Plan : {}", plan);
        return planRepository.save(plan);
    }

    @Override
    public Plan update(Plan plan) {
        log.debug("Request to update Plan : {}", plan);
        return planRepository.save(plan);
    }

    @Override
    public Optional<Plan> partialUpdate(Plan plan) {
        log.debug("Request to partially update Plan : {}", plan);

        return planRepository
            .findById(plan.getId())
            .map(existingPlan -> {
                if (plan.getPlanName() != null) {
                    existingPlan.setPlanName(plan.getPlanName());
                }
                if (plan.getPrice() != null) {
                    existingPlan.setPrice(plan.getPrice());
                }

                return existingPlan;
            })
            .map(planRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Plan> findAll(Pageable pageable) {
        log.debug("Request to get all Plans");
        return planRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plan> findOne(Long id) {
        log.debug("Request to get Plan : {}", id);
        return planRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plan : {}", id);
        planRepository.deleteById(id);
    }
}
