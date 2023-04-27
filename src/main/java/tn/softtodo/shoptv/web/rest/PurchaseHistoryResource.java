package tn.softtodo.shoptv.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import tn.softtodo.shoptv.domain.PurchaseHistory;
import tn.softtodo.shoptv.repository.PurchaseHistoryRepository;
import tn.softtodo.shoptv.service.PurchaseHistoryService;
import tn.softtodo.shoptv.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.softtodo.shoptv.domain.PurchaseHistory}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseHistoryResource.class);

    private static final String ENTITY_NAME = "purchaseHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseHistoryService purchaseHistoryService;

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    public PurchaseHistoryResource(PurchaseHistoryService purchaseHistoryService, PurchaseHistoryRepository purchaseHistoryRepository) {
        this.purchaseHistoryService = purchaseHistoryService;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
    }

    /**
     * {@code POST  /purchase-histories} : Create a new purchaseHistory.
     *
     * @param purchaseHistory the purchaseHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseHistory, or with status {@code 400 (Bad Request)} if the purchaseHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-histories")
    public ResponseEntity<PurchaseHistory> createPurchaseHistory(@RequestBody PurchaseHistory purchaseHistory) throws URISyntaxException {
        log.debug("REST request to save PurchaseHistory : {}", purchaseHistory);
        if (purchaseHistory.getId() != null) {
            throw new BadRequestAlertException("A new purchaseHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseHistory result = purchaseHistoryService.save(purchaseHistory);
        return ResponseEntity
            .created(new URI("/api/purchase-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-histories/:id} : Updates an existing purchaseHistory.
     *
     * @param id the id of the purchaseHistory to save.
     * @param purchaseHistory the purchaseHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseHistory,
     * or with status {@code 400 (Bad Request)} if the purchaseHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-histories/{id}")
    public ResponseEntity<PurchaseHistory> updatePurchaseHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PurchaseHistory purchaseHistory
    ) throws URISyntaxException {
        log.debug("REST request to update PurchaseHistory : {}, {}", id, purchaseHistory);
        if (purchaseHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchaseHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purchaseHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PurchaseHistory result = purchaseHistoryService.update(purchaseHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchaseHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /purchase-histories/:id} : Partial updates given fields of an existing purchaseHistory, field will ignore if it is null
     *
     * @param id the id of the purchaseHistory to save.
     * @param purchaseHistory the purchaseHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseHistory,
     * or with status {@code 400 (Bad Request)} if the purchaseHistory is not valid,
     * or with status {@code 404 (Not Found)} if the purchaseHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the purchaseHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/purchase-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PurchaseHistory> partialUpdatePurchaseHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PurchaseHistory purchaseHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update PurchaseHistory partially : {}, {}", id, purchaseHistory);
        if (purchaseHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchaseHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!purchaseHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PurchaseHistory> result = purchaseHistoryService.partialUpdate(purchaseHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchaseHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /purchase-histories} : get all the purchaseHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseHistories in body.
     */
    @GetMapping("/purchase-histories")
    public ResponseEntity<List<PurchaseHistory>> getAllPurchaseHistories(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PurchaseHistories");
        Page<PurchaseHistory> page = purchaseHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /purchase-histories/:id} : get the "id" purchaseHistory.
     *
     * @param id the id of the purchaseHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-histories/{id}")
    public ResponseEntity<PurchaseHistory> getPurchaseHistory(@PathVariable Long id) {
        log.debug("REST request to get PurchaseHistory : {}", id);
        Optional<PurchaseHistory> purchaseHistory = purchaseHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseHistory);
    }

    /**
     * {@code DELETE  /purchase-histories/:id} : delete the "id" purchaseHistory.
     *
     * @param id the id of the purchaseHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-histories/{id}")
    public ResponseEntity<Void> deletePurchaseHistory(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseHistory : {}", id);
        purchaseHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
