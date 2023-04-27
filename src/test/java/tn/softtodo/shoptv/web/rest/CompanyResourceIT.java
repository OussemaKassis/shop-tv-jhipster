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
import tn.softtodo.shoptv.domain.Company;
import tn.softtodo.shoptv.repository.CompanyRepository;

/**
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyResourceIT {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_PICTURE = "BBBBBBBBBB";

    private static final Instant DEFAULT_COMPANY_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPANY_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMPANY_LOCATION_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_LOCATION_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_ACTIVITY_DOMAINE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ACTIVITY_DOMAINE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .companyName(DEFAULT_COMPANY_NAME)
            .companyType(DEFAULT_COMPANY_TYPE)
            .companyDescription(DEFAULT_COMPANY_DESCRIPTION)
            .companyPicture(DEFAULT_COMPANY_PICTURE)
            .companyCreationDate(DEFAULT_COMPANY_CREATION_DATE)
            .companyLocationAddress(DEFAULT_COMPANY_LOCATION_ADDRESS)
            .companyActivityDomaine(DEFAULT_COMPANY_ACTIVITY_DOMAINE);
        return company;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .companyName(UPDATED_COMPANY_NAME)
            .companyType(UPDATED_COMPANY_TYPE)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .companyPicture(UPDATED_COMPANY_PICTURE)
            .companyCreationDate(UPDATED_COMPANY_CREATION_DATE)
            .companyLocationAddress(UPDATED_COMPANY_LOCATION_ADDRESS)
            .companyActivityDomaine(UPDATED_COMPANY_ACTIVITY_DOMAINE);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();
        // Create the Company
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getCompanyType()).isEqualTo(DEFAULT_COMPANY_TYPE);
        assertThat(testCompany.getCompanyDescription()).isEqualTo(DEFAULT_COMPANY_DESCRIPTION);
        assertThat(testCompany.getCompanyPicture()).isEqualTo(DEFAULT_COMPANY_PICTURE);
        assertThat(testCompany.getCompanyCreationDate()).isEqualTo(DEFAULT_COMPANY_CREATION_DATE);
        assertThat(testCompany.getCompanyLocationAddress()).isEqualTo(DEFAULT_COMPANY_LOCATION_ADDRESS);
        assertThat(testCompany.getCompanyActivityDomaine()).isEqualTo(DEFAULT_COMPANY_ACTIVITY_DOMAINE);
    }

    @Test
    @Transactional
    void createCompanyWithExistingId() throws Exception {
        // Create the Company with an existing ID
        company.setId(1L);

        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE)))
            .andExpect(jsonPath("$.[*].companyDescription").value(hasItem(DEFAULT_COMPANY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].companyPicture").value(hasItem(DEFAULT_COMPANY_PICTURE)))
            .andExpect(jsonPath("$.[*].companyCreationDate").value(hasItem(DEFAULT_COMPANY_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].companyLocationAddress").value(hasItem(DEFAULT_COMPANY_LOCATION_ADDRESS)))
            .andExpect(jsonPath("$.[*].companyActivityDomaine").value(hasItem(DEFAULT_COMPANY_ACTIVITY_DOMAINE)));
    }

    @Test
    @Transactional
    void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.companyType").value(DEFAULT_COMPANY_TYPE))
            .andExpect(jsonPath("$.companyDescription").value(DEFAULT_COMPANY_DESCRIPTION))
            .andExpect(jsonPath("$.companyPicture").value(DEFAULT_COMPANY_PICTURE))
            .andExpect(jsonPath("$.companyCreationDate").value(DEFAULT_COMPANY_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.companyLocationAddress").value(DEFAULT_COMPANY_LOCATION_ADDRESS))
            .andExpect(jsonPath("$.companyActivityDomaine").value(DEFAULT_COMPANY_ACTIVITY_DOMAINE));
    }

    @Test
    @Transactional
    void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .companyName(UPDATED_COMPANY_NAME)
            .companyType(UPDATED_COMPANY_TYPE)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .companyPicture(UPDATED_COMPANY_PICTURE)
            .companyCreationDate(UPDATED_COMPANY_CREATION_DATE)
            .companyLocationAddress(UPDATED_COMPANY_LOCATION_ADDRESS)
            .companyActivityDomaine(UPDATED_COMPANY_ACTIVITY_DOMAINE);

        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompany.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testCompany.getCompanyDescription()).isEqualTo(UPDATED_COMPANY_DESCRIPTION);
        assertThat(testCompany.getCompanyPicture()).isEqualTo(UPDATED_COMPANY_PICTURE);
        assertThat(testCompany.getCompanyCreationDate()).isEqualTo(UPDATED_COMPANY_CREATION_DATE);
        assertThat(testCompany.getCompanyLocationAddress()).isEqualTo(UPDATED_COMPANY_LOCATION_ADDRESS);
        assertThat(testCompany.getCompanyActivityDomaine()).isEqualTo(UPDATED_COMPANY_ACTIVITY_DOMAINE);
    }

    @Test
    @Transactional
    void putNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, company.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(company))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(company))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .companyType(UPDATED_COMPANY_TYPE)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .companyCreationDate(UPDATED_COMPANY_CREATION_DATE)
            .companyActivityDomaine(UPDATED_COMPANY_ACTIVITY_DOMAINE);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testCompany.getCompanyDescription()).isEqualTo(UPDATED_COMPANY_DESCRIPTION);
        assertThat(testCompany.getCompanyPicture()).isEqualTo(DEFAULT_COMPANY_PICTURE);
        assertThat(testCompany.getCompanyCreationDate()).isEqualTo(UPDATED_COMPANY_CREATION_DATE);
        assertThat(testCompany.getCompanyLocationAddress()).isEqualTo(DEFAULT_COMPANY_LOCATION_ADDRESS);
        assertThat(testCompany.getCompanyActivityDomaine()).isEqualTo(UPDATED_COMPANY_ACTIVITY_DOMAINE);
    }

    @Test
    @Transactional
    void fullUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .companyName(UPDATED_COMPANY_NAME)
            .companyType(UPDATED_COMPANY_TYPE)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .companyPicture(UPDATED_COMPANY_PICTURE)
            .companyCreationDate(UPDATED_COMPANY_CREATION_DATE)
            .companyLocationAddress(UPDATED_COMPANY_LOCATION_ADDRESS)
            .companyActivityDomaine(UPDATED_COMPANY_ACTIVITY_DOMAINE);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testCompany.getCompanyDescription()).isEqualTo(UPDATED_COMPANY_DESCRIPTION);
        assertThat(testCompany.getCompanyPicture()).isEqualTo(UPDATED_COMPANY_PICTURE);
        assertThat(testCompany.getCompanyCreationDate()).isEqualTo(UPDATED_COMPANY_CREATION_DATE);
        assertThat(testCompany.getCompanyLocationAddress()).isEqualTo(UPDATED_COMPANY_LOCATION_ADDRESS);
        assertThat(testCompany.getCompanyActivityDomaine()).isEqualTo(UPDATED_COMPANY_ACTIVITY_DOMAINE);
    }

    @Test
    @Transactional
    void patchNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, company.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(company))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(company))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, company.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
