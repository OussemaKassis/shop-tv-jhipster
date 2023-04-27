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
import tn.softtodo.shoptv.domain.PurchaseHistory;
import tn.softtodo.shoptv.repository.PurchaseHistoryRepository;

/**
 * Integration tests for the {@link PurchaseHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PurchaseHistoryResourceIT {

    private static final String DEFAULT_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_PLAN = "BBBBBBBBBB";

    private static final Instant DEFAULT_PURCHASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PURCHASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/purchase-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseHistoryMockMvc;

    private PurchaseHistory purchaseHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseHistory createEntity(EntityManager em) {
        PurchaseHistory purchaseHistory = new PurchaseHistory().plan(DEFAULT_PLAN).purchaseDate(DEFAULT_PURCHASE_DATE);
        return purchaseHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseHistory createUpdatedEntity(EntityManager em) {
        PurchaseHistory purchaseHistory = new PurchaseHistory().plan(UPDATED_PLAN).purchaseDate(UPDATED_PURCHASE_DATE);
        return purchaseHistory;
    }

    @BeforeEach
    public void initTest() {
        purchaseHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createPurchaseHistory() throws Exception {
        int databaseSizeBeforeCreate = purchaseHistoryRepository.findAll().size();
        // Create the PurchaseHistory
        restPurchaseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseHistory))
            )
            .andExpect(status().isCreated());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseHistory testPurchaseHistory = purchaseHistoryList.get(purchaseHistoryList.size() - 1);
        assertThat(testPurchaseHistory.getPlan()).isEqualTo(DEFAULT_PLAN);
        assertThat(testPurchaseHistory.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
    }

    @Test
    @Transactional
    void createPurchaseHistoryWithExistingId() throws Exception {
        // Create the PurchaseHistory with an existing ID
        purchaseHistory.setId(1L);

        int databaseSizeBeforeCreate = purchaseHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPurchaseHistories() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        // Get all the purchaseHistoryList
        restPurchaseHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].plan").value(hasItem(DEFAULT_PLAN)))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())));
    }

    @Test
    @Transactional
    void getPurchaseHistory() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        // Get the purchaseHistory
        restPurchaseHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, purchaseHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseHistory.getId().intValue()))
            .andExpect(jsonPath("$.plan").value(DEFAULT_PLAN))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPurchaseHistory() throws Exception {
        // Get the purchaseHistory
        restPurchaseHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPurchaseHistory() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();

        // Update the purchaseHistory
        PurchaseHistory updatedPurchaseHistory = purchaseHistoryRepository.findById(purchaseHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseHistory are not directly saved in db
        em.detach(updatedPurchaseHistory);
        updatedPurchaseHistory.plan(UPDATED_PLAN).purchaseDate(UPDATED_PURCHASE_DATE);

        restPurchaseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPurchaseHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseHistory))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
        PurchaseHistory testPurchaseHistory = purchaseHistoryList.get(purchaseHistoryList.size() - 1);
        assertThat(testPurchaseHistory.getPlan()).isEqualTo(UPDATED_PLAN);
        assertThat(testPurchaseHistory.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPurchaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();
        purchaseHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchaseHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPurchaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();
        purchaseHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPurchaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();
        purchaseHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePurchaseHistoryWithPatch() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();

        // Update the purchaseHistory using partial update
        PurchaseHistory partialUpdatedPurchaseHistory = new PurchaseHistory();
        partialUpdatedPurchaseHistory.setId(purchaseHistory.getId());

        restPurchaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseHistory))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
        PurchaseHistory testPurchaseHistory = purchaseHistoryList.get(purchaseHistoryList.size() - 1);
        assertThat(testPurchaseHistory.getPlan()).isEqualTo(DEFAULT_PLAN);
        assertThat(testPurchaseHistory.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePurchaseHistoryWithPatch() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();

        // Update the purchaseHistory using partial update
        PurchaseHistory partialUpdatedPurchaseHistory = new PurchaseHistory();
        partialUpdatedPurchaseHistory.setId(purchaseHistory.getId());

        partialUpdatedPurchaseHistory.plan(UPDATED_PLAN).purchaseDate(UPDATED_PURCHASE_DATE);

        restPurchaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseHistory))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
        PurchaseHistory testPurchaseHistory = purchaseHistoryList.get(purchaseHistoryList.size() - 1);
        assertThat(testPurchaseHistory.getPlan()).isEqualTo(UPDATED_PLAN);
        assertThat(testPurchaseHistory.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPurchaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();
        purchaseHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, purchaseHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPurchaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();
        purchaseHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPurchaseHistory() throws Exception {
        int databaseSizeBeforeUpdate = purchaseHistoryRepository.findAll().size();
        purchaseHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseHistory in the database
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePurchaseHistory() throws Exception {
        // Initialize the database
        purchaseHistoryRepository.saveAndFlush(purchaseHistory);

        int databaseSizeBeforeDelete = purchaseHistoryRepository.findAll().size();

        // Delete the purchaseHistory
        restPurchaseHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, purchaseHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findAll();
        assertThat(purchaseHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
