package tn.softtodo.shoptv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.domain.AfterEffectsTemplate;
import tn.softtodo.shoptv.repository.AfterEffectsTemplateRepository;
import tn.softtodo.shoptv.service.AfterEffectsTemplateService;

/**
 * Service Implementation for managing {@link AfterEffectsTemplate}.
 */
@Service
@Transactional
public class AfterEffectsTemplateServiceImpl implements AfterEffectsTemplateService {

    private final Logger log = LoggerFactory.getLogger(AfterEffectsTemplateServiceImpl.class);

    private final AfterEffectsTemplateRepository afterEffectsTemplateRepository;

    public AfterEffectsTemplateServiceImpl(AfterEffectsTemplateRepository afterEffectsTemplateRepository) {
        this.afterEffectsTemplateRepository = afterEffectsTemplateRepository;
    }

    @Override
    public AfterEffectsTemplate save(AfterEffectsTemplate afterEffectsTemplate) {
        log.debug("Request to save AfterEffectsTemplate : {}", afterEffectsTemplate);
        return afterEffectsTemplateRepository.save(afterEffectsTemplate);
    }

    @Override
    public AfterEffectsTemplate update(AfterEffectsTemplate afterEffectsTemplate) {
        log.debug("Request to update AfterEffectsTemplate : {}", afterEffectsTemplate);
        return afterEffectsTemplateRepository.save(afterEffectsTemplate);
    }

    @Override
    public Optional<AfterEffectsTemplate> partialUpdate(AfterEffectsTemplate afterEffectsTemplate) {
        log.debug("Request to partially update AfterEffectsTemplate : {}", afterEffectsTemplate);

        return afterEffectsTemplateRepository
            .findById(afterEffectsTemplate.getId())
            .map(existingAfterEffectsTemplate -> {
                if (afterEffectsTemplate.getTemplateName() != null) {
                    existingAfterEffectsTemplate.setTemplateName(afterEffectsTemplate.getTemplateName());
                }
                if (afterEffectsTemplate.getTemplateDuration() != null) {
                    existingAfterEffectsTemplate.setTemplateDuration(afterEffectsTemplate.getTemplateDuration());
                }
                if (afterEffectsTemplate.getTemplateDescription() != null) {
                    existingAfterEffectsTemplate.setTemplateDescription(afterEffectsTemplate.getTemplateDescription());
                }
                if (afterEffectsTemplate.getTemplateRating() != null) {
                    existingAfterEffectsTemplate.setTemplateRating(afterEffectsTemplate.getTemplateRating());
                }
                if (afterEffectsTemplate.getTemplateActive() != null) {
                    existingAfterEffectsTemplate.setTemplateActive(afterEffectsTemplate.getTemplateActive());
                }
                if (afterEffectsTemplate.getTemplateType() != null) {
                    existingAfterEffectsTemplate.setTemplateType(afterEffectsTemplate.getTemplateType());
                }
                if (afterEffectsTemplate.getTemplateExpectedSize() != null) {
                    existingAfterEffectsTemplate.setTemplateExpectedSize(afterEffectsTemplate.getTemplateExpectedSize());
                }
                if (afterEffectsTemplate.getTemplateCount() != null) {
                    existingAfterEffectsTemplate.setTemplateCount(afterEffectsTemplate.getTemplateCount());
                }
                if (afterEffectsTemplate.getTemplateVisibleFor() != null) {
                    existingAfterEffectsTemplate.setTemplateVisibleFor(afterEffectsTemplate.getTemplateVisibleFor());
                }
                if (afterEffectsTemplate.getRatio() != null) {
                    existingAfterEffectsTemplate.setRatio(afterEffectsTemplate.getRatio());
                }
                if (afterEffectsTemplate.getPreviewUrl() != null) {
                    existingAfterEffectsTemplate.setPreviewUrl(afterEffectsTemplate.getPreviewUrl());
                }

                return existingAfterEffectsTemplate;
            })
            .map(afterEffectsTemplateRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AfterEffectsTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all AfterEffectsTemplates");
        return afterEffectsTemplateRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AfterEffectsTemplate> findOne(Long id) {
        log.debug("Request to get AfterEffectsTemplate : {}", id);
        return afterEffectsTemplateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AfterEffectsTemplate : {}", id);
        afterEffectsTemplateRepository.deleteById(id);
    }
}
