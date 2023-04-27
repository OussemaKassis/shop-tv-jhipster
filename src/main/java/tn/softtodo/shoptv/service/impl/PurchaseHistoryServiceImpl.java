package tn.softtodo.shoptv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.domain.PurchaseHistory;
import tn.softtodo.shoptv.repository.PurchaseHistoryRepository;
import tn.softtodo.shoptv.service.PurchaseHistoryService;

/**
 * Service Implementation for managing {@link PurchaseHistory}.
 */
@Service
@Transactional
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private final Logger log = LoggerFactory.getLogger(PurchaseHistoryServiceImpl.class);

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    public PurchaseHistoryServiceImpl(PurchaseHistoryRepository purchaseHistoryRepository) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
    }

    @Override
    public PurchaseHistory save(PurchaseHistory purchaseHistory) {
        log.debug("Request to save PurchaseHistory : {}", purchaseHistory);
        return purchaseHistoryRepository.save(purchaseHistory);
    }

    @Override
    public PurchaseHistory update(PurchaseHistory purchaseHistory) {
        log.debug("Request to update PurchaseHistory : {}", purchaseHistory);
        return purchaseHistoryRepository.save(purchaseHistory);
    }

    @Override
    public Optional<PurchaseHistory> partialUpdate(PurchaseHistory purchaseHistory) {
        log.debug("Request to partially update PurchaseHistory : {}", purchaseHistory);

        return purchaseHistoryRepository
            .findById(purchaseHistory.getId())
            .map(existingPurchaseHistory -> {
                if (purchaseHistory.getPlan() != null) {
                    existingPurchaseHistory.setPlan(purchaseHistory.getPlan());
                }
                if (purchaseHistory.getPurchaseDate() != null) {
                    existingPurchaseHistory.setPurchaseDate(purchaseHistory.getPurchaseDate());
                }

                return existingPurchaseHistory;
            })
            .map(purchaseHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseHistory> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseHistories");
        return purchaseHistoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseHistory> findOne(Long id) {
        log.debug("Request to get PurchaseHistory : {}", id);
        return purchaseHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseHistory : {}", id);
        purchaseHistoryRepository.deleteById(id);
    }
}
