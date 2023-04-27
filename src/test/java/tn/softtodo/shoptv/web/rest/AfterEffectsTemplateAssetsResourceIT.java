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
import tn.softtodo.shoptv.domain.AfterEffectsTemplateAssets;
import tn.softtodo.shoptv.repository.AfterEffectsTemplateAssetsRepository;

/**
 * Integration tests for the {@link AfterEffectsTemplateAssetsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AfterEffectsTemplateAssetsResourceIT {

    private static final String DEFAULT_ASSET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/after-effects-template-assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AfterEffectsTemplateAssetsRepository afterEffectsTemplateAssetsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAfterEffectsTemplateAssetsMockMvc;

    private AfterEffectsTemplateAssets afterEffectsTemplateAssets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AfterEffectsTemplateAssets createEntity(EntityManager em) {
        AfterEffectsTemplateAssets afterEffectsTemplateAssets = new AfterEffectsTemplateAssets()
            .assetName(DEFAULT_ASSET_NAME)
            .assetType(DEFAULT_ASSET_TYPE);
        return afterEffectsTemplateAssets;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AfterEffectsTemplateAssets createUpdatedEntity(EntityManager em) {
        AfterEffectsTemplateAssets afterEffectsTemplateAssets = new AfterEffectsTemplateAssets()
            .assetName(UPDATED_ASSET_NAME)
            .assetType(UPDATED_ASSET_TYPE);
        return afterEffectsTemplateAssets;
    }

    @BeforeEach
    public void initTest() {
        afterEffectsTemplateAssets = createEntity(em);
    }

    @Test
    @Transactional
    void createAfterEffectsTemplateAssets() throws Exception {
        int databaseSizeBeforeCreate = afterEffectsTemplateAssetsRepository.findAll().size();
        // Create the AfterEffectsTemplateAssets
        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplateAssets))
            )
            .andExpect(status().isCreated());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeCreate + 1);
        AfterEffectsTemplateAssets testAfterEffectsTemplateAssets = afterEffectsTemplateAssetsList.get(
            afterEffectsTemplateAssetsList.size() - 1
        );
        assertThat(testAfterEffectsTemplateAssets.getAssetName()).isEqualTo(DEFAULT_ASSET_NAME);
        assertThat(testAfterEffectsTemplateAssets.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
    }

    @Test
    @Transactional
    void createAfterEffectsTemplateAssetsWithExistingId() throws Exception {
        // Create the AfterEffectsTemplateAssets with an existing ID
        afterEffectsTemplateAssets.setId(1L);

        int databaseSizeBeforeCreate = afterEffectsTemplateAssetsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplateAssets))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAfterEffectsTemplateAssets() throws Exception {
        // Initialize the database
        afterEffectsTemplateAssetsRepository.saveAndFlush(afterEffectsTemplateAssets);

        // Get all the afterEffectsTemplateAssetsList
        restAfterEffectsTemplateAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(afterEffectsTemplateAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetName").value(hasItem(DEFAULT_ASSET_NAME)))
            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE)));
    }

    @Test
    @Transactional
    void getAfterEffectsTemplateAssets() throws Exception {
        // Initialize the database
        afterEffectsTemplateAssetsRepository.saveAndFlush(afterEffectsTemplateAssets);

        // Get the afterEffectsTemplateAssets
        restAfterEffectsTemplateAssetsMockMvc
            .perform(get(ENTITY_API_URL_ID, afterEffectsTemplateAssets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(afterEffectsTemplateAssets.getId().intValue()))
            .andExpect(jsonPath("$.assetName").value(DEFAULT_ASSET_NAME))
            .andExpect(jsonPath("$.assetType").value(DEFAULT_ASSET_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingAfterEffectsTemplateAssets() throws Exception {
        // Get the afterEffectsTemplateAssets
        restAfterEffectsTemplateAssetsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAfterEffectsTemplateAssets() throws Exception {
        // Initialize the database
        afterEffectsTemplateAssetsRepository.saveAndFlush(afterEffectsTemplateAssets);

        int databaseSizeBeforeUpdate = afterEffectsTemplateAssetsRepository.findAll().size();

        // Update the afterEffectsTemplateAssets
        AfterEffectsTemplateAssets updatedAfterEffectsTemplateAssets = afterEffectsTemplateAssetsRepository
            .findById(afterEffectsTemplateAssets.getId())
            .get();
        // Disconnect from session so that the updates on updatedAfterEffectsTemplateAssets are not directly saved in db
        em.detach(updatedAfterEffectsTemplateAssets);
        updatedAfterEffectsTemplateAssets.assetName(UPDATED_ASSET_NAME).assetType(UPDATED_ASSET_TYPE);

        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAfterEffectsTemplateAssets.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAfterEffectsTemplateAssets))
            )
            .andExpect(status().isOk());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeUpdate);
        AfterEffectsTemplateAssets testAfterEffectsTemplateAssets = afterEffectsTemplateAssetsList.get(
            afterEffectsTemplateAssetsList.size() - 1
        );
        assertThat(testAfterEffectsTemplateAssets.getAssetName()).isEqualTo(UPDATED_ASSET_NAME);
        assertThat(testAfterEffectsTemplateAssets.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingAfterEffectsTemplateAssets() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateAssetsRepository.findAll().size();
        afterEffectsTemplateAssets.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, afterEffectsTemplateAssets.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplateAssets))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAfterEffectsTemplateAssets() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateAssetsRepository.findAll().size();
        afterEffectsTemplateAssets.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplateAssets))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAfterEffectsTemplateAssets() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateAssetsRepository.findAll().size();
        afterEffectsTemplateAssets.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplateAssets))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAfterEffectsTemplateAssetsWithPatch() throws Exception {
        // Initialize the database
        afterEffectsTemplateAssetsRepository.saveAndFlush(afterEffectsTemplateAssets);

        int databaseSizeBeforeUpdate = afterEffectsTemplateAssetsRepository.findAll().size();

        // Update the afterEffectsTemplateAssets using partial update
        AfterEffectsTemplateAssets partialUpdatedAfterEffectsTemplateAssets = new AfterEffectsTemplateAssets();
        partialUpdatedAfterEffectsTemplateAssets.setId(afterEffectsTemplateAssets.getId());

        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfterEffectsTemplateAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAfterEffectsTemplateAssets))
            )
            .andExpect(status().isOk());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeUpdate);
        AfterEffectsTemplateAssets testAfterEffectsTemplateAssets = afterEffectsTemplateAssetsList.get(
            afterEffectsTemplateAssetsList.size() - 1
        );
        assertThat(testAfterEffectsTemplateAssets.getAssetName()).isEqualTo(DEFAULT_ASSET_NAME);
        assertThat(testAfterEffectsTemplateAssets.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateAfterEffectsTemplateAssetsWithPatch() throws Exception {
        // Initialize the database
        afterEffectsTemplateAssetsRepository.saveAndFlush(afterEffectsTemplateAssets);

        int databaseSizeBeforeUpdate = afterEffectsTemplateAssetsRepository.findAll().size();

        // Update the afterEffectsTemplateAssets using partial update
        AfterEffectsTemplateAssets partialUpdatedAfterEffectsTemplateAssets = new AfterEffectsTemplateAssets();
        partialUpdatedAfterEffectsTemplateAssets.setId(afterEffectsTemplateAssets.getId());

        partialUpdatedAfterEffectsTemplateAssets.assetName(UPDATED_ASSET_NAME).assetType(UPDATED_ASSET_TYPE);

        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAfterEffectsTemplateAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAfterEffectsTemplateAssets))
            )
            .andExpect(status().isOk());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeUpdate);
        AfterEffectsTemplateAssets testAfterEffectsTemplateAssets = afterEffectsTemplateAssetsList.get(
            afterEffectsTemplateAssetsList.size() - 1
        );
        assertThat(testAfterEffectsTemplateAssets.getAssetName()).isEqualTo(UPDATED_ASSET_NAME);
        assertThat(testAfterEffectsTemplateAssets.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingAfterEffectsTemplateAssets() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateAssetsRepository.findAll().size();
        afterEffectsTemplateAssets.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, afterEffectsTemplateAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplateAssets))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAfterEffectsTemplateAssets() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateAssetsRepository.findAll().size();
        afterEffectsTemplateAssets.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplateAssets))
            )
            .andExpect(status().isBadRequest());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAfterEffectsTemplateAssets() throws Exception {
        int databaseSizeBeforeUpdate = afterEffectsTemplateAssetsRepository.findAll().size();
        afterEffectsTemplateAssets.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAfterEffectsTemplateAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(afterEffectsTemplateAssets))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AfterEffectsTemplateAssets in the database
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAfterEffectsTemplateAssets() throws Exception {
        // Initialize the database
        afterEffectsTemplateAssetsRepository.saveAndFlush(afterEffectsTemplateAssets);

        int databaseSizeBeforeDelete = afterEffectsTemplateAssetsRepository.findAll().size();

        // Delete the afterEffectsTemplateAssets
        restAfterEffectsTemplateAssetsMockMvc
            .perform(delete(ENTITY_API_URL_ID, afterEffectsTemplateAssets.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AfterEffectsTemplateAssets> afterEffectsTemplateAssetsList = afterEffectsTemplateAssetsRepository.findAll();
        assertThat(afterEffectsTemplateAssetsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
