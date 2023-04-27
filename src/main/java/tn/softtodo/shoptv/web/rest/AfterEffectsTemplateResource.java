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
import tn.softtodo.shoptv.domain.AfterEffectsTemplate;
import tn.softtodo.shoptv.repository.AfterEffectsTemplateRepository;
import tn.softtodo.shoptv.service.AfterEffectsTemplateService;
import tn.softtodo.shoptv.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.softtodo.shoptv.domain.AfterEffectsTemplate}.
 */
@RestController
@RequestMapping("/api")
public class AfterEffectsTemplateResource {

    private final Logger log = LoggerFactory.getLogger(AfterEffectsTemplateResource.class);

    private static final String ENTITY_NAME = "afterEffectsTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AfterEffectsTemplateService afterEffectsTemplateService;

    private final AfterEffectsTemplateRepository afterEffectsTemplateRepository;

    public AfterEffectsTemplateResource(
        AfterEffectsTemplateService afterEffectsTemplateService,
        AfterEffectsTemplateRepository afterEffectsTemplateRepository
    ) {
        this.afterEffectsTemplateService = afterEffectsTemplateService;
        this.afterEffectsTemplateRepository = afterEffectsTemplateRepository;
    }

    /**
     * {@code POST  /after-effects-templates} : Create a new afterEffectsTemplate.
     *
     * @param afterEffectsTemplate the afterEffectsTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new afterEffectsTemplate, or with status {@code 400 (Bad Request)} if the afterEffectsTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/after-effects-templates")
    public ResponseEntity<AfterEffectsTemplate> createAfterEffectsTemplate(@RequestBody AfterEffectsTemplate afterEffectsTemplate)
        throws URISyntaxException {
        log.debug("REST request to save AfterEffectsTemplate : {}", afterEffectsTemplate);
        if (afterEffectsTemplate.getId() != null) {
            throw new BadRequestAlertException("A new afterEffectsTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AfterEffectsTemplate result = afterEffectsTemplateService.save(afterEffectsTemplate);
        return ResponseEntity
            .created(new URI("/api/after-effects-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /after-effects-templates/:id} : Updates an existing afterEffectsTemplate.
     *
     * @param id the id of the afterEffectsTemplate to save.
     * @param afterEffectsTemplate the afterEffectsTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afterEffectsTemplate,
     * or with status {@code 400 (Bad Request)} if the afterEffectsTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the afterEffectsTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/after-effects-templates/{id}")
    public ResponseEntity<AfterEffectsTemplate> updateAfterEffectsTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AfterEffectsTemplate afterEffectsTemplate
    ) throws URISyntaxException {
        log.debug("REST request to update AfterEffectsTemplate : {}, {}", id, afterEffectsTemplate);
        if (afterEffectsTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afterEffectsTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afterEffectsTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AfterEffectsTemplate result = afterEffectsTemplateService.update(afterEffectsTemplate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, afterEffectsTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /after-effects-templates/:id} : Partial updates given fields of an existing afterEffectsTemplate, field will ignore if it is null
     *
     * @param id the id of the afterEffectsTemplate to save.
     * @param afterEffectsTemplate the afterEffectsTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated afterEffectsTemplate,
     * or with status {@code 400 (Bad Request)} if the afterEffectsTemplate is not valid,
     * or with status {@code 404 (Not Found)} if the afterEffectsTemplate is not found,
     * or with status {@code 500 (Internal Server Error)} if the afterEffectsTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/after-effects-templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AfterEffectsTemplate> partialUpdateAfterEffectsTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AfterEffectsTemplate afterEffectsTemplate
    ) throws URISyntaxException {
        log.debug("REST request to partial update AfterEffectsTemplate partially : {}, {}", id, afterEffectsTemplate);
        if (afterEffectsTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, afterEffectsTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!afterEffectsTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AfterEffectsTemplate> result = afterEffectsTemplateService.partialUpdate(afterEffectsTemplate);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, afterEffectsTemplate.getId().toString())
        );
    }

    /**
     * {@code GET  /after-effects-templates} : get all the afterEffectsTemplates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of afterEffectsTemplates in body.
     */
    @GetMapping("/after-effects-templates")
    public ResponseEntity<List<AfterEffectsTemplate>> getAllAfterEffectsTemplates(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AfterEffectsTemplates");
        Page<AfterEffectsTemplate> page = afterEffectsTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /after-effects-templates/:id} : get the "id" afterEffectsTemplate.
     *
     * @param id the id of the afterEffectsTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the afterEffectsTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/after-effects-templates/{id}")
    public ResponseEntity<AfterEffectsTemplate> getAfterEffectsTemplate(@PathVariable Long id) {
        log.debug("REST request to get AfterEffectsTemplate : {}", id);
        Optional<AfterEffectsTemplate> afterEffectsTemplate = afterEffectsTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(afterEffectsTemplate);
    }

    /**
     * {@code DELETE  /after-effects-templates/:id} : delete the "id" afterEffectsTemplate.
     *
     * @param id the id of the afterEffectsTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/after-effects-templates/{id}")
    public ResponseEntity<Void> deleteAfterEffectsTemplate(@PathVariable Long id) {
        log.debug("REST request to delete AfterEffectsTemplate : {}", id);
        afterEffectsTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
