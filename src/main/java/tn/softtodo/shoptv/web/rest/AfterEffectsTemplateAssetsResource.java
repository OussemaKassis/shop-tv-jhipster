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
import tn.softtodo.shoptv.domain.AfterEffectsTemplateAssets;
import tn.softtodo.shoptv.repository.AfterEffectsTemplateAssetsRepository;
import tn.softtodo.shoptv.service.AfterEffectsTemplateAssetsService;
import tn.softtodo.shoptv.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.softtodo.shoptv.domain.AfterEffectsTemplateAssets}.
 */
@RestController
@RequestMapping("/api")
public class AfterEffectsTemplateAssetsResource {

    private final Logger log = LoggerFactory.getLogger(AfterEffectsTemplateAssetsResource.class);

    private static final String ENTITY_NAME = "afterEffectsTemplateAssets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AfterEffectsTemplateAssetsService afterEffectsTemplateAssetsService;

    private final AfterEffectsTemplateAssetsRepository afterEffectsTemplateAssetsRepository;

    public AfterEffectsTemplateAssetsResource(
        AfterEffectsTemplateAssetsService afterEffectsTemplateAssetsService,
        AfterEffectsTemplateAssetsRepository afterEffectsTemplateAssetsRepository
    ) {
        this.afterEffectsTemplateAssetsService = afterEffectsTemplateAssetsService;
        this.afterEffectsTemplateAssetsRepository = afterEffectsTemplateAssetsRepository;
    }

    /**
     * {@code POST  /after-effects-template-assets} : Create a new afterEffectsTemplateAssets.
     *
     * @param afterEffectsTemplateAssets the afterEffectsTemplateAssets to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new afterEffectsTemplateAssets, or with status {@code 400 (Bad Request)} if the afterEffectsTemplateAssets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/after-effects-template-assets")
    public ResponseEntity<AfterEffectsTemplateAssets> createAfterEffectsTemplateAssets(
        @RequestBody AfterEffectsTemplateAssets afterEffectsTemplateAssets
    ) throws URISyntaxException {
        log.debug("REST request to save AfterEffectsTemplateAssets : {}", afterEffectsTemplateAssets);
        if (afterEffectsTemplateAssets.getId() != null) {
            throw new BadRequestAlertException("A new afterEffectsTemplateAssets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AfterEffectsTemplateAssets result = afterEffectsTemplateAssetsService.save(afterEffectsTemplateAssets);
        return ResponseEntity
            .created(new URI("/api/after-effects-template-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /after-effects-template-assets/:id} : Updates an existing afterEffectsTemplateAssets.
     *
     * @param id the id of the afterEffectsTemplateAssets to save.
     * @param afterEffectsTemplateAssets the afterEffectsTemplateAssets to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afterEffectsTemplateAssets,
     * or with status {@code 400 (Bad Request)} if the afterEffectsTemplateAssets is not valid,
     * or with status {@code 500 (Internal Server Error)} if the afterEffectsTemplateAssets couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/after-effects-template-assets/{id}")
    public ResponseEntity<AfterEffectsTemplateAssets> updateAfterEffectsTemplateAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AfterEffectsTemplateAssets afterEffectsTemplateAssets
    ) throws URISyntaxException {
        log.debug("REST request to update AfterEffectsTemplateAssets : {}, {}", id, afterEffectsTemplateAssets);
        if (afterEffectsTemplateAssets.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afterEffectsTemplateAssets.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afterEffectsTemplateAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AfterEffectsTemplateAssets result = afterEffectsTemplateAssetsService.update(afterEffectsTemplateAssets);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, afterEffectsTemplateAssets.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /after-effects-template-assets/:id} : Partial updates given fields of an existing afterEffectsTemplateAssets, field will ignore if it is null
     *
     * @param id the id of the afterEffectsTemplateAssets to save.
     * @param afterEffectsTemplateAssets the afterEffectsTemplateAssets to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afterEffectsTemplateAssets,
     * or with status {@code 400 (Bad Request)} if the afterEffectsTemplateAssets is not valid,
     * or with status {@code 404 (Not Found)} if the afterEffectsTemplateAssets is not found,
     * or with status {@code 500 (Internal Server Error)} if the afterEffectsTemplateAssets couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/after-effects-template-assets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AfterEffectsTemplateAssets> partialUpdateAfterEffectsTemplateAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AfterEffectsTemplateAssets afterEffectsTemplateAssets
    ) throws URISyntaxException {
        log.debug("REST request to partial update AfterEffectsTemplateAssets partially : {}, {}", id, afterEffectsTemplateAssets);
        if (afterEffectsTemplateAssets.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afterEffectsTemplateAssets.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afterEffectsTemplateAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AfterEffectsTemplateAssets> result = afterEffectsTemplateAssetsService.partialUpdate(afterEffectsTemplateAssets);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, afterEffectsTemplateAssets.getId().toString())
        );
    }

    /**
     * {@code GET  /after-effects-template-assets} : get all the afterEffectsTemplateAssets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of afterEffectsTemplateAssets in body.
     */
    @GetMapping("/after-effects-template-assets")
    public ResponseEntity<List<AfterEffectsTemplateAssets>> getAllAfterEffectsTemplateAssets(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AfterEffectsTemplateAssets");
        Page<AfterEffectsTemplateAssets> page = afterEffectsTemplateAssetsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /after-effects-template-assets/:id} : get the "id" afterEffectsTemplateAssets.
     *
     * @param id the id of the afterEffectsTemplateAssets to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the afterEffectsTemplateAssets, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/after-effects-template-assets/{id}")
    public ResponseEntity<AfterEffectsTemplateAssets> getAfterEffectsTemplateAssets(@PathVariable Long id) {
        log.debug("REST request to get AfterEffectsTemplateAssets : {}", id);
        Optional<AfterEffectsTemplateAssets> afterEffectsTemplateAssets = afterEffectsTemplateAssetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(afterEffectsTemplateAssets);
    }

    /**
     * {@code DELETE  /after-effects-template-assets/:id} : delete the "id" afterEffectsTemplateAssets.
     *
     * @param id the id of the afterEffectsTemplateAssets to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/after-effects-template-assets/{id}")
    public ResponseEntity<Void> deleteAfterEffectsTemplateAssets(@PathVariable Long id) {
        log.debug("REST request to delete AfterEffectsTemplateAssets : {}", id);
        afterEffectsTemplateAssetsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
