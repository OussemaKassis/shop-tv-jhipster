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
import tn.softtodo.shoptv.domain.PlanOptions;
import tn.softtodo.shoptv.repository.PlanOptionsRepository;
import tn.softtodo.shoptv.service.PlanOptionsService;
import tn.softtodo.shoptv.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.softtodo.shoptv.domain.PlanOptions}.
 */
@RestController
@RequestMapping("/api")
public class PlanOptionsResource {

    private final Logger log = LoggerFactory.getLogger(PlanOptionsResource.class);

    private static final String ENTITY_NAME = "planOptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanOptionsService planOptionsService;

    private final PlanOptionsRepository planOptionsRepository;

    public PlanOptionsResource(PlanOptionsService planOptionsService, PlanOptionsRepository planOptionsRepository) {
        this.planOptionsService = planOptionsService;
        this.planOptionsRepository = planOptionsRepository;
    }

    /**
     * {@code POST  /plan-options} : Create a new planOptions.
     *
     * @param planOptions the planOptions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planOptions, or with status {@code 400 (Bad Request)} if the planOptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plan-options")
    public ResponseEntity<PlanOptions> createPlanOptions(@RequestBody PlanOptions planOptions) throws URISyntaxException {
        log.debug("REST request to save PlanOptions : {}", planOptions);
        if (planOptions.getId() != null) {
            throw new BadRequestAlertException("A new planOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanOptions result = planOptionsService.save(planOptions);
        return ResponseEntity
            .created(new URI("/api/plan-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plan-options/:id} : Updates an existing planOptions.
     *
     * @param id the id of the planOptions to save.
     * @param planOptions the planOptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planOptions,
     * or with status {@code 400 (Bad Request)} if the planOptions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planOptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plan-options/{id}")
    public ResponseEntity<PlanOptions> updatePlanOptions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanOptions planOptions
    ) throws URISyntaxException {
        log.debug("REST request to update PlanOptions : {}, {}", id, planOptions);
        if (planOptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planOptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planOptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanOptions result = planOptionsService.update(planOptions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planOptions.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plan-options/:id} : Partial updates given fields of an existing planOptions, field will ignore if it is null
     *
     * @param id the id of the planOptions to save.
     * @param planOptions the planOptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planOptions,
     * or with status {@code 400 (Bad Request)} if the planOptions is not valid,
     * or with status {@code 404 (Not Found)} if the planOptions is not found,
     * or with status {@code 500 (Internal Server Error)} if the planOptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plan-options/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanOptions> partialUpdatePlanOptions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanOptions planOptions
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanOptions partially : {}, {}", id, planOptions);
        if (planOptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planOptions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planOptionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanOptions> result = planOptionsService.partialUpdate(planOptions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planOptions.getId().toString())
        );
    }

    /**
     * {@code GET  /plan-options} : get all the planOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planOptions in body.
     */
    @GetMapping("/plan-options")
    public ResponseEntity<List<PlanOptions>> getAllPlanOptions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PlanOptions");
        Page<PlanOptions> page = planOptionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plan-options/:id} : get the "id" planOptions.
     *
     * @param id the id of the planOptions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planOptions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plan-options/{id}")
    public ResponseEntity<PlanOptions> getPlanOptions(@PathVariable Long id) {
        log.debug("REST request to get PlanOptions : {}", id);
        Optional<PlanOptions> planOptions = planOptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planOptions);
    }

    /**
     * {@code DELETE  /plan-options/:id} : delete the "id" planOptions.
     *
     * @param id the id of the planOptions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plan-options/{id}")
    public ResponseEntity<Void> deletePlanOptions(@PathVariable Long id) {
        log.debug("REST request to delete PlanOptions : {}", id);
        planOptionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
