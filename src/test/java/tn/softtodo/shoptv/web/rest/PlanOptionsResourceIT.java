package tn.softtodo.shoptv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.IntegrationTest;
import tn.softtodo.shoptv.domain.PlanOptions;
import tn.softtodo.shoptv.repository.PlanOptionsRepository;

/**
 * Integration tests for the {@link PlanOptionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanOptionsResourceIT {

    private static final String DEFAULT_AE_TEMPLATE_LIMIT = "AAAAAAAAAA";
    private static final String UPDATED_AE_TEMPLATE_LIMIT = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_SUBMITTION_LIMIT = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_SUBMITTION_LIMIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EMOJIS = false;
    private static final Boolean UPDATED_EMOJIS = true;

    private static final String DEFAULT_STORAGE = "AAAAAAAAAA";
    private static final String UPDATED_STORAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plan-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanOptionsRepository planOptionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanOptionsMockMvc;

    private PlanOptions planOptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanOptions createEntity(EntityManager em) {
        PlanOptions planOptions = new PlanOptions()
            .aeTemplateLimit(DEFAULT_AE_TEMPLATE_LIMIT)
            .videoSubmittionLimit(DEFAULT_VIDEO_SUBMITTION_LIMIT)
            .emojis(DEFAULT_EMOJIS)
            .storage(DEFAULT_STORAGE);
        return planOptions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanOptions createUpdatedEntity(EntityManager em) {
        PlanOptions planOptions = new PlanOptions()
            .aeTemplateLimit(UPDATED_AE_TEMPLATE_LIMIT)
            .videoSubmittionLimit(UPDATED_VIDEO_SUBMITTION_LIMIT)
            .emojis(UPDATED_EMOJIS)
            .storage(UPDATED_STORAGE);
        return planOptions;
    }

    @BeforeEach
    public void initTest() {
        planOptions = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanOptions() throws Exception {
        int databaseSizeBeforeCreate = planOptionsRepository.findAll().size();
        // Create the PlanOptions
        restPlanOptionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planOptions)))
            .andExpect(status().isCreated());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        PlanOptions testPlanOptions = planOptionsList.get(planOptionsList.size() - 1);
        assertThat(testPlanOptions.getAeTemplateLimit()).isEqualTo(DEFAULT_AE_TEMPLATE_LIMIT);
        assertThat(testPlanOptions.getVideoSubmittionLimit()).isEqualTo(DEFAULT_VIDEO_SUBMITTION_LIMIT);
        assertThat(testPlanOptions.getEmojis()).isEqualTo(DEFAULT_EMOJIS);
        assertThat(testPlanOptions.getStorage()).isEqualTo(DEFAULT_STORAGE);
    }

    @Test
    @Transactional
    void createPlanOptionsWithExistingId() throws Exception {
        // Create the PlanOptions with an existing ID
        planOptions.setId(1L);

        int databaseSizeBeforeCreate = planOptionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanOptionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planOptions)))
            .andExpect(status().isBadRequest());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlanOptions() throws Exception {
        // Initialize the database
        planOptionsRepository.saveAndFlush(planOptions);

        // Get all the planOptionsList
        restPlanOptionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].aeTemplateLimit").value(hasItem(DEFAULT_AE_TEMPLATE_LIMIT)))
            .andExpect(jsonPath("$.[*].videoSubmittionLimit").value(hasItem(DEFAULT_VIDEO_SUBMITTION_LIMIT)))
            .andExpect(jsonPath("$.[*].emojis").value(hasItem(DEFAULT_EMOJIS.booleanValue())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE)));
    }

    @Test
    @Transactional
    void getPlanOptions() throws Exception {
        // Initialize the database
        planOptionsRepository.saveAndFlush(planOptions);

        // Get the planOptions
        restPlanOptionsMockMvc
            .perform(get(ENTITY_API_URL_ID, planOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planOptions.getId().intValue()))
            .andExpect(jsonPath("$.aeTemplateLimit").value(DEFAULT_AE_TEMPLATE_LIMIT))
            .andExpect(jsonPath("$.videoSubmittionLimit").value(DEFAULT_VIDEO_SUBMITTION_LIMIT))
            .andExpect(jsonPath("$.emojis").value(DEFAULT_EMOJIS.booleanValue()))
            .andExpect(jsonPath("$.storage").value(DEFAULT_STORAGE));
    }

    @Test
    @Transactional
    void getNonExistingPlanOptions() throws Exception {
        // Get the planOptions
        restPlanOptionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanOptions() throws Exception {
        // Initialize the database
        planOptionsRepository.saveAndFlush(planOptions);

        int databaseSizeBeforeUpdate = planOptionsRepository.findAll().size();

        // Update the planOptions
        PlanOptions updatedPlanOptions = planOptionsRepository.findById(planOptions.getId()).get();
        // Disconnect from session so that the updates on updatedPlanOptions are not directly saved in db
        em.detach(updatedPlanOptions);
        updatedPlanOptions
            .aeTemplateLimit(UPDATED_AE_TEMPLATE_LIMIT)
            .videoSubmittionLimit(UPDATED_VIDEO_SUBMITTION_LIMIT)
            .emojis(UPDATED_EMOJIS)
            .storage(UPDATED_STORAGE);

        restPlanOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanOptions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlanOptions))
            )
            .andExpect(status().isOk());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeUpdate);
        PlanOptions testPlanOptions = planOptionsList.get(planOptionsList.size() - 1);
        assertThat(testPlanOptions.getAeTemplateLimit()).isEqualTo(UPDATED_AE_TEMPLATE_LIMIT);
        assertThat(testPlanOptions.getVideoSubmittionLimit()).isEqualTo(UPDATED_VIDEO_SUBMITTION_LIMIT);
        assertThat(testPlanOptions.getEmojis()).isEqualTo(UPDATED_EMOJIS);
        assertThat(testPlanOptions.getStorage()).isEqualTo(UPDATED_STORAGE);
    }

    @Test
    @Transactional
    void putNonExistingPlanOptions() throws Exception {
        int databaseSizeBeforeUpdate = planOptionsRepository.findAll().size();
        planOptions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planOptions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planOptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanOptions() throws Exception {
        int databaseSizeBeforeUpdate = planOptionsRepository.findAll().size();
        planOptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanOptionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planOptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanOptions() throws Exception {
        int databaseSizeBeforeUpdate = planOptionsRepository.findAll().size();
        planOptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanOptionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planOptions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanOptionsWithPatch() throws Exception {
        // Initialize the database
        planOptionsRepository.saveAndFlush(planOptions);

        int databaseSizeBeforeUpdate = planOptionsRepository.findAll().size();

        // Update the planOptions using partial update
        PlanOptions partialUpdatedPlanOptions = new PlanOptions();
        partialUpdatedPlanOptions.setId(planOptions.getId());

        partialUpdatedPlanOptions
            .aeTemplateLimit(UPDATED_AE_TEMPLATE_LIMIT)
            .videoSubmittionLimit(UPDATED_VIDEO_SUBMITTION_LIMIT)
            .emojis(UPDATED_EMOJIS)
            .storage(UPDATED_STORAGE);

        restPlanOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanOptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanOptions))
            )
            .andExpect(status().isOk());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeUpdate);
        PlanOptions testPlanOptions = planOptionsList.get(planOptionsList.size() - 1);
        assertThat(testPlanOptions.getAeTemplateLimit()).isEqualTo(UPDATED_AE_TEMPLATE_LIMIT);
        assertThat(testPlanOptions.getVideoSubmittionLimit()).isEqualTo(UPDATED_VIDEO_SUBMITTION_LIMIT);
        assertThat(testPlanOptions.getEmojis()).isEqualTo(UPDATED_EMOJIS);
        assertThat(testPlanOptions.getStorage()).isEqualTo(UPDATED_STORAGE);
    }

    @Test
    @Transactional
    void fullUpdatePlanOptionsWithPatch() throws Exception {
        // Initialize the database
        planOptionsRepository.saveAndFlush(planOptions);

        int databaseSizeBeforeUpdate = planOptionsRepository.findAll().size();

        // Update the planOptions using partial update
        PlanOptions partialUpdatedPlanOptions = new PlanOptions();
        partialUpdatedPlanOptions.setId(planOptions.getId());

        partialUpdatedPlanOptions
            .aeTemplateLimit(UPDATED_AE_TEMPLATE_LIMIT)
            .videoSubmittionLimit(UPDATED_VIDEO_SUBMITTION_LIMIT)
            .emojis(UPDATED_EMOJIS)
            .storage(UPDATED_STORAGE);

        restPlanOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanOptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanOptions))
            )
            .andExpect(status().isOk());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeUpdate);
        PlanOptions testPlanOptions = planOptionsList.get(planOptionsList.size() - 1);
        assertThat(testPlanOptions.getAeTemplateLimit()).isEqualTo(UPDATED_AE_TEMPLATE_LIMIT);
        assertThat(testPlanOptions.getVideoSubmittionLimit()).isEqualTo(UPDATED_VIDEO_SUBMITTION_LIMIT);
        assertThat(testPlanOptions.getEmojis()).isEqualTo(UPDATED_EMOJIS);
        assertThat(testPlanOptions.getStorage()).isEqualTo(UPDATED_STORAGE);
    }

    @Test
    @Transactional
    void patchNonExistingPlanOptions() throws Exception {
        int databaseSizeBeforeUpdate = planOptionsRepository.findAll().size();
        planOptions.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planOptions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planOptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanOptions() throws Exception {
        int databaseSizeBeforeUpdate = planOptionsRepository.findAll().size();
        planOptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planOptions))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanOptions() throws Exception {
        int databaseSizeBeforeUpdate = planOptionsRepository.findAll().size();
        planOptions.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanOptionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(planOptions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanOptions in the database
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanOptions() throws Exception {
        // Initialize the database
        planOptionsRepository.saveAndFlush(planOptions);

        int databaseSizeBeforeDelete = planOptionsRepository.findAll().size();

        // Delete the planOptions
        restPlanOptionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, planOptions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanOptions> planOptionsList = planOptionsRepository.findAll();
        assertThat(planOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
