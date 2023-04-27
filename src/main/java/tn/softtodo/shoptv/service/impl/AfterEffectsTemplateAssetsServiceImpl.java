package tn.softtodo.shoptv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.domain.AfterEffectsTemplateAssets;
import tn.softtodo.shoptv.repository.AfterEffectsTemplateAssetsRepository;
import tn.softtodo.shoptv.service.AfterEffectsTemplateAssetsService;

/**
 * Service Implementation for managing {@link AfterEffectsTemplateAssets}.
 */
@Service
@Transactional
public class AfterEffectsTemplateAssetsServiceImpl implements AfterEffectsTemplateAssetsService {

    private final Logger log = LoggerFactory.getLogger(AfterEffectsTemplateAssetsServiceImpl.class);

    private final AfterEffectsTemplateAssetsRepository afterEffectsTemplateAssetsRepository;

    public AfterEffectsTemplateAssetsServiceImpl(AfterEffectsTemplateAssetsRepository afterEffectsTemplateAssetsRepository) {
        this.afterEffectsTemplateAssetsRepository = afterEffectsTemplateAssetsRepository;
    }

    @Override
    public AfterEffectsTemplateAssets save(AfterEffectsTemplateAssets afterEffectsTemplateAssets) {
        log.debug("Request to save AfterEffectsTemplateAssets : {}", afterEffectsTemplateAssets);
        return afterEffectsTemplateAssetsRepository.save(afterEffectsTemplateAssets);
    }

    @Override
    public AfterEffectsTemplateAssets update(AfterEffectsTemplateAssets afterEffectsTemplateAssets) {
        log.debug("Request to update AfterEffectsTemplateAssets : {}", afterEffectsTemplateAssets);
        return afterEffectsTemplateAssetsRepository.save(afterEffectsTemplateAssets);
    }

    @Override
    public Optional<AfterEffectsTemplateAssets> partialUpdate(AfterEffectsTemplateAssets afterEffectsTemplateAssets) {
        log.debug("Request to partially update AfterEffectsTemplateAssets : {}", afterEffectsTemplateAssets);

        return afterEffectsTemplateAssetsRepository
            .findById(afterEffectsTemplateAssets.getId())
            .map(existingAfterEffectsTemplateAssets -> {
                if (afterEffectsTemplateAssets.getAssetName() != null) {
                    existingAfterEffectsTemplateAssets.setAssetName(afterEffectsTemplateAssets.getAssetName());
                }
                if (afterEffectsTemplateAssets.getAssetType() != null) {
                    existingAfterEffectsTemplateAssets.setAssetType(afterEffectsTemplateAssets.getAssetType());
                }

                return existingAfterEffectsTemplateAssets;
            })
            .map(afterEffectsTemplateAssetsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AfterEffectsTemplateAssets> findAll(Pageable pageable) {
        log.debug("Request to get all AfterEffectsTemplateAssets");
        return afterEffectsTemplateAssetsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AfterEffectsTemplateAssets> findOne(Long id) {
        log.debug("Request to get AfterEffectsTemplateAssets : {}", id);
        return afterEffectsTemplateAssetsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AfterEffectsTemplateAssets : {}", id);
        afterEffectsTemplateAssetsRepository.deleteById(id);
    }
}
