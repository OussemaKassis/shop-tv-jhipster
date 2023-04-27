package tn.softtodo.shoptv.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import tn.softtodo.shoptv.domain.AfterEffectsTemplate;
import tn.softtodo.shoptv.repository.AfterEffectsTemplateRepository;

/**
 * Integration tests for the {@link AfterEffectsTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AfterEffectsTemplateResourceIT {

    private static final String DEFAULT_TEMPLATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_TEMPLATE_RATING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TEMPLATE_RATING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_TEMPLATE_ACTIVE = false;
    private static final Boolean UPDATED_TEMPLATE_ACTIVE = true;

    private static final String DEFAULT_TEMPLATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_EXPECTED_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_EXPECTED_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_COUNT = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_COUNT = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_VISIBLE_FOR = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_VISIBLE_FOR = "BBBBBBBBBB";

    private static final String DEFAULT_RATIO = "AAAAAAAAAA";
    private static final String UPDATED_RATIO = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIEW_URL = "AAAAAAAAAA";
    private static final String UPDATED_PREVIEW_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/after-effects-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AfterEffectsTemplateRepository afterEffectsTemplateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAfterEffectsTemplateMockMvc;

    private AfterEffectsTemplate afterEffectsTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AfterEffectsTemplate createEntity(EntityManager em) {
        AfterEffectsTemplate afterEffectsTemplate = new AfterEffectsTemplate()
            .templateName(DEFAULT_TEMPLATE_NAME)
            .templateDuration(DEFAULT_TEMPLATE_DURATION)
            .templateDescription(DEFAULT_TEMPLATE_DESCRIPTION)
            .templateRating(DEFAULT_TEMPLATE_RATING)
            .templateActive(DEFAULT_TEMPLATE_ACTIVE)
            .templateType(DEFAULT_TEMPLATE_TYPE)
            .templateExpectedSize(DEFAULT_TEMPLATE_EXPECTED_SIZE)
            .templateCount(DEFAULT_TEMPLATE_COUNT)
            .templateVisibleFor(DEFAULT_TEMPLATE_VISIBLE_FOR)
            .ratio(DEFAULT_RATIO)
            .previewUrl(DEFAULT_PREVIEW_URL);
        return afterEffectsTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AfterEffectsTemplate createUpdatedEntity(EntityManager em) {
        AfterEffectsTemplate afterEffectsTemplate = new AfterEffectsTemplate()
            .templateName(UPDATED_TEMPLATE_NAME)
            .templateDuration(UPDATED_TEMPLATE_DURATION)
            .templateDescription(UPDATED_TEMPLATE_DESCRIPTION)
            .templateRating(UPDATED_TEMPLATE_RATING)
            .templateActive(UPDATED_TEMPLATE_ACTIVE)
            .templateType(UPDATED_TEMPLATE_TYPE)
            .templateExpectedSize(UPDATED_TEMPLATE_EXPECTED_SIZE)
            .templateCount(UPDATED_TEMPLATE_COUNT)
            .templateVisibleFor(UPDATED_TEMPLATE_VISIBLE_FOR)
            .ratio(UPDATED_RATIO)
            .previewUrl(UPDATED_PREVIEW_URL);
        return afterEffectsTemplate;
    }

    @BeforeEach
    public void initTest() {
        afterEffectsTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createAfterEffectsTemplate() throws Exception {
        int databaseSizeBeforeCreate = afterEffectsTemplateRepository.findAll().size();
        // Create the AfterEffectsTemplate
        restAfterEffectsTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplate))
            )
            .andExpect(status().isCreated());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        AfterEffectsTemplate testAfterEffectsTemplate = afterEffectsTemplateList.get(afterEffectsTemplateList.size() - 1);
        assertThat(testAfterEffectsTemplate.getTemplateName()).isEqualTo(DEFAULT_TEMPLATE_NAME);
        assertThat(testAfterEffectsTemplate.getTemplateDuration()).isEqualTo(DEFAULT_TEMPLATE_DURATION);
        assertThat(testAfterEffectsTemplate.getTemplateDescription()).isEqualTo(DEFAULT_TEMPLATE_DESCRIPTION);
        assertThat(testAfterEffectsTemplate.getTemplateRating()).isEqualTo(DEFAULT_TEMPLATE_RATING);
        assertThat(testAfterEffectsTemplate.getTemplateActive()).isEqualTo(DEFAULT_TEMPLATE_ACTIVE);
        assertThat(testAfterEffectsTemplate.getTemplateType()).isEqualTo(DEFAULT_TEMPLATE_TYPE);
        assertThat(testAfterEffectsTemplate.getTemplateExpectedSize()).isEqualTo(DEFAULT_TEMPLATE_EXPECTED_SIZE);
        assertThat(testAfterEffectsTemplate.getTemplateCount()).isEqualTo(DEFAULT_TEMPLATE_COUNT);
        assertThat(testAfterEffectsTemplate.getTemplateVisibleFor()).isEqualTo(DEFAULT_TEMPLATE_VISIBLE_FOR);
        assertThat(testAfterEffectsTemplate.getRatio()).isEqualTo(DEFAULT_RATIO);
        assertThat(testAfterEffectsTemplate.getPreviewUrl()).isEqualTo(DEFAULT_PREVIEW_URL);
    }

    @Test
    @Transactional
    void createAfterEffectsTemplateWithExistingId() throws Exception {
        // Create the AfterEffectsTemplate with an existing ID
        afterEffectsTemplate.setId(1L);

        int databaseSizeBeforeCreate = afterEffectsTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAfterEffectsTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAfterEffectsTemplates() throws Exception {
        // Initialize the database
        afterEffectsTemplateRepository.saveAndFlush(afterEffectsTemplate);

        // Get all the afterEffectsTemplateList
        restAfterEffectsTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(afterEffectsTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateName").value(hasItem(DEFAULT_TEMPLATE_NAME)))
            .andExpect(jsonPath("$.[*].templateDuration").value(hasItem(DEFAULT_TEMPLATE_DURATION)))
            .andExpect(jsonPath("$.[*].templateDescription").value(hasItem(DEFAULT_TEMPLATE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].templateRating").value(hasItem(DEFAULT_TEMPLATE_RATING.toString())))
            .andExpect(jsonPath("$.[*].templateActive").value(hasItem(DEFAULT_TEMPLATE_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].templateType").value(hasItem(DEFAULT_TEMPLATE_TYPE)))
            .andExpect(jsonPath("$.[*].templateExpectedSize").value(hasItem(DEFAULT_TEMPLATE_EXPECTED_SIZE)))
            .andExpect(jsonPath("$.[*].templateCount").value(hasItem(DEFAULT_TEMPLATE_COUNT)))
            .andExpect(jsonPath("$.[*].templateVisibleFor").value(hasItem(DEFAULT_TEMPLATE_VISIBLE_FOR)))
            .andExpect(jsonPath("$.[*].ratio").value(hasItem(DEFAULT_RATIO)))
            .andExpect(jsonPath("$.[*].previewUrl").value(hasItem(DEFAULT_PREVIEW_URL)));
    }

    @Test
    @Transactional
    void getAfterEffectsTemplate() throws Exception {
        // Initialize the database
        afterEffectsTemplateRepository.saveAndFlush(afterEffectsTemplate);

        // Get the afterEffectsTemplate
        restAfterEffectsTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, afterEffectsTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(afterEffectsTemplate.getId().intValue()))
            .andExpect(jsonPath("$.templateName").value(DEFAULT_TEMPLATE_NAME))
            .andExpect(jsonPath("$.templateDuration").value(DEFAULT_TEMPLATE_DURATION))
            .andExpect(jsonPath("$.templateDescription").value(DEFAULT_TEMPLATE_DESCRIPTION))
            .andExpect(jsonPath("$.templateRating").value(DEFAULT_TEMPLATE_RATING.toString()))
            .andExpect(jsonPath("$.templateActive").value(DEFAULT_TEMPLATE_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.templateType").value(DEFAULT_TEMPLATE_TYPE))
            .andExpect(jsonPath("$.templateExpectedSize").value(DEFAULT_TEMPLATE_EXPECTED_SIZE))
            .andExpect(jsonPath("$.templateCount").value(DEFAULT_TEMPLATE_COUNT))
            .andExpect(jsonPath("$.templateVisibleFor").value(DEFAULT_TEMPLATE_VISIBLE_FOR))
            .andExpect(jsonPath("$.ratio").value(DEFAULT_RATIO))
            .andExpect(jsonPath("$.previewUrl").value(DEFAULT_PREVIEW_URL));
    }

    @Test
    @Transactional
    void getNonExistingAfterEffectsTemplate() throws Exception {
        // Get the afterEffectsTemplate
        restAfterEffectsTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAfterEffectsTemplate() throws Exception {
        // Initialize the database
        afterEffectsTemplateRepository.saveAndFlush(afterEffectsTemplate);

        int databaseSizeBeforeUpdate = afterEffectsTemplateRepository.findAll().size();

        // Update the afterEffectsTemplate
        AfterEffectsTemplate updatedAfterEffectsTemplate = afterEffectsTemplateRepository.findById(afterEffectsTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedAfterEffectsTemplate are not directly saved in db
        em.detach(updatedAfterEffectsTemplate);
        updatedAfterEffectsTemplate
            .templateName(UPDATED_TEMPLATE_NAME)
            .templateDuration(UPDATED_TEMPLATE_DURATION)
            .templateDescription(UPDATED_TEMPLATE_DESCRIPTION)
            .templateRating(UPDATED_TEMPLATE_RATING)
            .templateActive(UPDATED_TEMPLATE_ACTIVE)
            .templateType(UPDATED_TEMPLATE_TYPE)
            .templateExpectedSize(UPDATED_TEMPLATE_EXPECTED_SIZE)
            .templateCount(UPDATED_TEMPLATE_COUNT)
            .templateVisibleFor(UPDATED_TEMPLATE_VISIBLE_FOR)
            .ratio(UPDATED_RATIO)
            .previewUrl(UPDATED_PREVIEW_URL);

        restAfterEffectsTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAfterEffectsTemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAfterEffectsTemplate))
            )
            .andExpect(status().isOk());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeUpdate);
        AfterEffectsTemplate testAfterEffectsTemplate = afterEffectsTemplateList.get(afterEffectsTemplateList.size() - 1);
        assertThat(testAfterEffectsTemplate.getTemplateName()).isEqualTo(UPDATED_TEMPLATE_NAME);
        assertThat(testAfterEffectsTemplate.getTemplateDuration()).isEqualTo(UPDATED_TEMPLATE_DURATION);
        assertThat(testAfterEffectsTemplate.getTemplateDescription()).isEqualTo(UPDATED_TEMPLATE_DESCRIPTION);
        assertThat(testAfterEffectsTemplate.getTemplateRating()).isEqualTo(UPDATED_TEMPLATE_RATING);
        assertThat(testAfterEffectsTemplate.getTemplateActive()).isEqualTo(UPDATED_TEMPLATE_ACTIVE);
        assertThat(testAfterEffectsTemplate.getTemplateType()).isEqualTo(UPDATED_TEMPLATE_TYPE);
        assertThat(testAfterEffectsTemplate.getTemplateExpectedSize()).isEqualTo(UPDATED_TEMPLATE_EXPECTED_SIZE);
        assertThat(testAfterEffectsTemplate.getTemplateCount()).isEqualTo(UPDATED_TEMPLATE_COUNT);
        assertThat(testAfterEffectsTemplate.getTemplateVisibleFor()).isEqualTo(UPDATED_TEMPLATE_VISIBLE_FOR);
        assertThat(testAfterEffectsTemplate.getRatio()).isEqualTo(UPDATED_RATIO);
        assertThat(testAfterEffectsTemplate.getPreviewUrl()).isEqualTo(UPDATED_PREVIEW_URL);
    }

    @Test
    @Transactional
    void putNonExistingAfterEffectsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateRepository.findAll().size();
        afterEffectsTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, afterEffectsTemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAfterEffectsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateRepository.findAll().size();
        afterEffectsTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAfterEffectsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateRepository.findAll().size();
        afterEffectsTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAfterEffectsTemplateWithPatch() throws Exception {
        // Initialize the database
        afterEffectsTemplateRepository.saveAndFlush(afterEffectsTemplate);

        int databaseSizeBeforeUpdate = afterEffectsTemplateRepository.findAll().size();

        // Update the afterEffectsTemplate using partial update
        AfterEffectsTemplate partialUpdatedAfterEffectsTemplate = new AfterEffectsTemplate();
        partialUpdatedAfterEffectsTemplate.setId(afterEffectsTemplate.getId());

        partialUpdatedAfterEffectsTemplate
            .templateActive(UPDATED_TEMPLATE_ACTIVE)
            .templateExpectedSize(UPDATED_TEMPLATE_EXPECTED_SIZE)
            .templateCount(UPDATED_TEMPLATE_COUNT);

        restAfterEffectsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfterEffectsTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAfterEffectsTemplate))
            )
            .andExpect(status().isOk());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeUpdate);
        AfterEffectsTemplate testAfterEffectsTemplate = afterEffectsTemplateList.get(afterEffectsTemplateList.size() - 1);
        assertThat(testAfterEffectsTemplate.getTemplateName()).isEqualTo(DEFAULT_TEMPLATE_NAME);
        assertThat(testAfterEffectsTemplate.getTemplateDuration()).isEqualTo(DEFAULT_TEMPLATE_DURATION);
        assertThat(testAfterEffectsTemplate.getTemplateDescription()).isEqualTo(DEFAULT_TEMPLATE_DESCRIPTION);
        assertThat(testAfterEffectsTemplate.getTemplateRating()).isEqualTo(DEFAULT_TEMPLATE_RATING);
        assertThat(testAfterEffectsTemplate.getTemplateActive()).isEqualTo(UPDATED_TEMPLATE_ACTIVE);
        assertThat(testAfterEffectsTemplate.getTemplateType()).isEqualTo(DEFAULT_TEMPLATE_TYPE);
        assertThat(testAfterEffectsTemplate.getTemplateExpectedSize()).isEqualTo(UPDATED_TEMPLATE_EXPECTED_SIZE);
        assertThat(testAfterEffectsTemplate.getTemplateCount()).isEqualTo(UPDATED_TEMPLATE_COUNT);
        assertThat(testAfterEffectsTemplate.getTemplateVisibleFor()).isEqualTo(DEFAULT_TEMPLATE_VISIBLE_FOR);
        assertThat(testAfterEffectsTemplate.getRatio()).isEqualTo(DEFAULT_RATIO);
        assertThat(testAfterEffectsTemplate.getPreviewUrl()).isEqualTo(DEFAULT_PREVIEW_URL);
    }

    @Test
    @Transactional
    void fullUpdateAfterEffectsTemplateWithPatch() throws Exception {
        // Initialize the database
        afterEffectsTemplateRepository.saveAndFlush(afterEffectsTemplate);

        int databaseSizeBeforeUpdate = afterEffectsTemplateRepository.findAll().size();

        // Update the afterEffectsTemplate using partial update
        AfterEffectsTemplate partialUpdatedAfterEffectsTemplate = new AfterEffectsTemplate();
        partialUpdatedAfterEffectsTemplate.setId(afterEffectsTemplate.getId());

        partialUpdatedAfterEffectsTemplate
            .templateName(UPDATED_TEMPLATE_NAME)
            .templateDuration(UPDATED_TEMPLATE_DURATION)
            .templateDescription(UPDATED_TEMPLATE_DESCRIPTION)
            .templateRating(UPDATED_TEMPLATE_RATING)
            .templateActive(UPDATED_TEMPLATE_ACTIVE)
            .templateType(UPDATED_TEMPLATE_TYPE)
            .templateExpectedSize(UPDATED_TEMPLATE_EXPECTED_SIZE)
            .templateCount(UPDATED_TEMPLATE_COUNT)
            .templateVisibleFor(UPDATED_TEMPLATE_VISIBLE_FOR)
            .ratio(UPDATED_RATIO)
            .previewUrl(UPDATED_PREVIEW_URL);

        restAfterEffectsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfterEffectsTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAfterEffectsTemplate))
            )
            .andExpect(status().isOk());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeUpdate);
        AfterEffectsTemplate testAfterEffectsTemplate = afterEffectsTemplateList.get(afterEffectsTemplateList.size() - 1);
        assertThat(testAfterEffectsTemplate.getTemplateName()).isEqualTo(UPDATED_TEMPLATE_NAME);
        assertThat(testAfterEffectsTemplate.getTemplateDuration()).isEqualTo(UPDATED_TEMPLATE_DURATION);
        assertThat(testAfterEffectsTemplate.getTemplateDescription()).isEqualTo(UPDATED_TEMPLATE_DESCRIPTION);
        assertThat(testAfterEffectsTemplate.getTemplateRating()).isEqualTo(UPDATED_TEMPLATE_RATING);
        assertThat(testAfterEffectsTemplate.getTemplateActive()).isEqualTo(UPDATED_TEMPLATE_ACTIVE);
        assertThat(testAfterEffectsTemplate.getTemplateType()).isEqualTo(UPDATED_TEMPLATE_TYPE);
        assertThat(testAfterEffectsTemplate.getTemplateExpectedSize()).isEqualTo(UPDATED_TEMPLATE_EXPECTED_SIZE);
        assertThat(testAfterEffectsTemplate.getTemplateCount()).isEqualTo(UPDATED_TEMPLATE_COUNT);
        assertThat(testAfterEffectsTemplate.getTemplateVisibleFor()).isEqualTo(UPDATED_TEMPLATE_VISIBLE_FOR);
        assertThat(testAfterEffectsTemplate.getRatio()).isEqualTo(UPDATED_RATIO);
        assertThat(testAfterEffectsTemplate.getPreviewUrl()).isEqualTo(UPDATED_PREVIEW_URL);
    }

    @Test
    @Transactional
    void patchNonExistingAfterEffectsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateRepository.findAll().size();
        afterEffectsTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, afterEffectsTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAfterEffectsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateRepository.findAll().size();
        afterEffectsTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAfterEffectsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateRepository.findAll().size();
        afterEffectsTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AfterEffectsTemplate in the database
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAfterEffectsTemplate() throws Exception {
        // Initialize the database
        afterEffectsTemplateRepository.saveAndFlush(afterEffectsTemplate);

        int databaseSizeBeforeDelete = afterEffectsTemplateRepository.findAll().size();

        // Delete the afterEffectsTemplate
        restAfterEffectsTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, afterEffectsTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AfterEffectsTemplate> afterEffectsTemplateList = afterEffectsTemplateRepository.findAll();
        assertThat(afterEffectsTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
