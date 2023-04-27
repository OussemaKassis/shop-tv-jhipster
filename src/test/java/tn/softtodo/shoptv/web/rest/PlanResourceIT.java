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
import tn.softtodo.shoptv.domain.Plan;
import tn.softtodo.shoptv.repository.PlanRepository;

/**
 * Integration tests for the {@link PlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanResourceIT {

    private static final String DEFAULT_PLAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanMockMvc;

    private Plan plan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plan createEntity(EntityManager em) {
        Plan plan = new Plan().planName(DEFAULT_PLAN_NAME).price(DEFAULT_PRICE);
        return plan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plan createUpdatedEntity(EntityManager em) {
        Plan plan = new Plan().planName(UPDATED_PLAN_NAME).price(UPDATED_PRICE);
        return plan;
    }

    @BeforeEach
    public void initTest() {
        plan = createEntity(em);
    }

    @Test
    @Transactional
    void createPlan() throws Exception {
        int databaseSizeBeforeCreate = planRepository.findAll().size();
        // Create the Plan
        restPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plan)))
            .andExpect(status().isCreated());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeCreate + 1);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getPlanName()).isEqualTo(DEFAULT_PLAN_NAME);
        assertThat(testPlan.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createPlanWithExistingId() throws Exception {
        // Create the Plan with an existing ID
        plan.setId(1L);

        int databaseSizeBeforeCreate = planRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plan)))
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlans() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get all the planList
        restPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plan.getId().intValue())))
            .andExpect(jsonPath("$.[*].planName").value(hasItem(DEFAULT_PLAN_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }

    @Test
    @Transactional
    void getPlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get the plan
        restPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, plan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plan.getId().intValue()))
            .andExpect(jsonPath("$.planName").value(DEFAULT_PLAN_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingPlan() throws Exception {
        // Get the plan
        restPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Update the plan
        Plan updatedPlan = planRepository.findById(plan.getId()).get();
        // Disconnect from session so that the updates on updatedPlan are not directly saved in db
        em.detach(updatedPlan);
        updatedPlan.planName(UPDATED_PLAN_NAME).price(UPDATED_PRICE);

        restPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlan))
            )
            .andExpect(status().isOk());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getPlanName()).isEqualTo(UPDATED_PLAN_NAME);
        assertThat(testPlan.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanWithPatch() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Update the plan using partial update
        Plan partialUpdatedPlan = new Plan();
        partialUpdatedPlan.setId(plan.getId());

        partialUpdatedPlan.price(UPDATED_PRICE);

        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlan))
            )
            .andExpect(status().isOk());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getPlanName()).isEqualTo(DEFAULT_PLAN_NAME);
        assertThat(testPlan.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void fullUpdatePlanWithPatch() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Update the plan using partial update
        Plan partialUpdatedPlan = new Plan();
        partialUpdatedPlan.setId(plan.getId());

        partialUpdatedPlan.planName(UPDATED_PLAN_NAME).price(UPDATED_PRICE);

        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlan))
            )
            .andExpect(status().isOk());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
        Plan testPlan = planList.get(planList.size() - 1);
        assertThat(testPlan.getPlanName()).isEqualTo(UPDATED_PLAN_NAME);
        assertThat(testPlan.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plan))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlan() throws Exception {
        int databaseSizeBeforeUpdate = planRepository.findAll().size();
        plan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plan in the database
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        int databaseSizeBeforeDelete = planRepository.findAll().size();

        // Delete the plan
        restPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, plan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plan> planList = planRepository.findAll();
        assertThat(planList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
