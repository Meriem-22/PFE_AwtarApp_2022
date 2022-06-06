package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.enumeration.MaritalStatus;
import com.awtar.myapp.repository.ParentRepository;
import com.awtar.myapp.service.dto.ParentDTO;
import com.awtar.myapp.service.mapper.ParentMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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

/**
 * Integration tests for the {@link ParentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParentResourceIT {

    private static final Double DEFAULT_ANNUAL_REVENUE = 1D;
    private static final Double UPDATED_ANNUAL_REVENUE = 2D;

    private static final Long DEFAULT_CNSS = 1L;
    private static final Long UPDATED_CNSS = 2L;

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.DIVORCED;

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DECEASED = false;
    private static final Boolean UPDATED_DECEASED = true;

    private static final LocalDate DEFAULT_DATE_OF_DEATH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_DEATH = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/parents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ParentMapper parentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParentMockMvc;

    private Parent parent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parent createEntity(EntityManager em) {
        Parent parent = new Parent()
            .annualRevenue(DEFAULT_ANNUAL_REVENUE)
            .cnss(DEFAULT_CNSS)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .occupation(DEFAULT_OCCUPATION)
            .deceased(DEFAULT_DECEASED)
            .dateOfDeath(DEFAULT_DATE_OF_DEATH);
        // Add required entity
        Family family;
        if (TestUtil.findAll(em, Family.class).isEmpty()) {
            family = FamilyResourceIT.createEntity(em);
            em.persist(family);
            em.flush();
        } else {
            family = TestUtil.findAll(em, Family.class).get(0);
        }
        parent.setFamily(family);
        return parent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parent createUpdatedEntity(EntityManager em) {
        Parent parent = new Parent()
            .annualRevenue(UPDATED_ANNUAL_REVENUE)
            .cnss(UPDATED_CNSS)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .occupation(UPDATED_OCCUPATION)
            .deceased(UPDATED_DECEASED)
            .dateOfDeath(UPDATED_DATE_OF_DEATH);
        // Add required entity
        Family family;
        if (TestUtil.findAll(em, Family.class).isEmpty()) {
            family = FamilyResourceIT.createUpdatedEntity(em);
            em.persist(family);
            em.flush();
        } else {
            family = TestUtil.findAll(em, Family.class).get(0);
        }
        parent.setFamily(family);
        return parent;
    }

    @BeforeEach
    public void initTest() {
        parent = createEntity(em);
    }

    @Test
    @Transactional
    void createParent() throws Exception {
        int databaseSizeBeforeCreate = parentRepository.findAll().size();
        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);
        restParentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isCreated());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeCreate + 1);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getAnnualRevenue()).isEqualTo(DEFAULT_ANNUAL_REVENUE);
        assertThat(testParent.getCnss()).isEqualTo(DEFAULT_CNSS);
        assertThat(testParent.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testParent.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testParent.getDeceased()).isEqualTo(DEFAULT_DECEASED);
        assertThat(testParent.getDateOfDeath()).isEqualTo(DEFAULT_DATE_OF_DEATH);
    }

    @Test
    @Transactional
    void createParentWithExistingId() throws Exception {
        // Create the Parent with an existing ID
        parent.setId(1L);
        ParentDTO parentDTO = parentMapper.toDto(parent);

        int databaseSizeBeforeCreate = parentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnnualRevenueIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setAnnualRevenue(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.toDto(parent);

        restParentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaritalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setMaritalStatus(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.toDto(parent);

        restParentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOccupationIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentRepository.findAll().size();
        // set the field null
        parent.setOccupation(null);

        // Create the Parent, which fails.
        ParentDTO parentDTO = parentMapper.toDto(parent);

        restParentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isBadRequest());

        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParents() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        // Get all the parentList
        restParentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parent.getId().intValue())))
            .andExpect(jsonPath("$.[*].annualRevenue").value(hasItem(DEFAULT_ANNUAL_REVENUE.doubleValue())))
            .andExpect(jsonPath("$.[*].cnss").value(hasItem(DEFAULT_CNSS.intValue())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].deceased").value(hasItem(DEFAULT_DECEASED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateOfDeath").value(hasItem(DEFAULT_DATE_OF_DEATH.toString())));
    }

    @Test
    @Transactional
    void getParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        // Get the parent
        restParentMockMvc
            .perform(get(ENTITY_API_URL_ID, parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parent.getId().intValue()))
            .andExpect(jsonPath("$.annualRevenue").value(DEFAULT_ANNUAL_REVENUE.doubleValue()))
            .andExpect(jsonPath("$.cnss").value(DEFAULT_CNSS.intValue()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
            .andExpect(jsonPath("$.deceased").value(DEFAULT_DECEASED.booleanValue()))
            .andExpect(jsonPath("$.dateOfDeath").value(DEFAULT_DATE_OF_DEATH.toString()));
    }

    @Test
    @Transactional
    void getNonExistingParent() throws Exception {
        // Get the parent
        restParentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        int databaseSizeBeforeUpdate = parentRepository.findAll().size();

        // Update the parent
        Parent updatedParent = parentRepository.findById(parent.getId()).get();
        // Disconnect from session so that the updates on updatedParent are not directly saved in db
        em.detach(updatedParent);
        updatedParent
            .annualRevenue(UPDATED_ANNUAL_REVENUE)
            .cnss(UPDATED_CNSS)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .occupation(UPDATED_OCCUPATION)
            .deceased(UPDATED_DECEASED)
            .dateOfDeath(UPDATED_DATE_OF_DEATH);
        ParentDTO parentDTO = parentMapper.toDto(updatedParent);

        restParentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getAnnualRevenue()).isEqualTo(UPDATED_ANNUAL_REVENUE);
        assertThat(testParent.getCnss()).isEqualTo(UPDATED_CNSS);
        assertThat(testParent.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testParent.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testParent.getDeceased()).isEqualTo(UPDATED_DECEASED);
        assertThat(testParent.getDateOfDeath()).isEqualTo(UPDATED_DATE_OF_DEATH);
    }

    @Test
    @Transactional
    void putNonExistingParent() throws Exception {
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();
        parent.setId(count.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParent() throws Exception {
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();
        parent.setId(count.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParent() throws Exception {
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();
        parent.setId(count.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParentWithPatch() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        int databaseSizeBeforeUpdate = parentRepository.findAll().size();

        // Update the parent using partial update
        Parent partialUpdatedParent = new Parent();
        partialUpdatedParent.setId(parent.getId());

        partialUpdatedParent
            .annualRevenue(UPDATED_ANNUAL_REVENUE)
            .cnss(UPDATED_CNSS)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .deceased(UPDATED_DECEASED)
            .dateOfDeath(UPDATED_DATE_OF_DEATH);

        restParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParent))
            )
            .andExpect(status().isOk());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getAnnualRevenue()).isEqualTo(UPDATED_ANNUAL_REVENUE);
        assertThat(testParent.getCnss()).isEqualTo(UPDATED_CNSS);
        assertThat(testParent.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testParent.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testParent.getDeceased()).isEqualTo(UPDATED_DECEASED);
        assertThat(testParent.getDateOfDeath()).isEqualTo(UPDATED_DATE_OF_DEATH);
    }

    @Test
    @Transactional
    void fullUpdateParentWithPatch() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        int databaseSizeBeforeUpdate = parentRepository.findAll().size();

        // Update the parent using partial update
        Parent partialUpdatedParent = new Parent();
        partialUpdatedParent.setId(parent.getId());

        partialUpdatedParent
            .annualRevenue(UPDATED_ANNUAL_REVENUE)
            .cnss(UPDATED_CNSS)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .occupation(UPDATED_OCCUPATION)
            .deceased(UPDATED_DECEASED)
            .dateOfDeath(UPDATED_DATE_OF_DEATH);

        restParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParent))
            )
            .andExpect(status().isOk());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
        Parent testParent = parentList.get(parentList.size() - 1);
        assertThat(testParent.getAnnualRevenue()).isEqualTo(UPDATED_ANNUAL_REVENUE);
        assertThat(testParent.getCnss()).isEqualTo(UPDATED_CNSS);
        assertThat(testParent.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testParent.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testParent.getDeceased()).isEqualTo(UPDATED_DECEASED);
        assertThat(testParent.getDateOfDeath()).isEqualTo(UPDATED_DATE_OF_DEATH);
    }

    @Test
    @Transactional
    void patchNonExistingParent() throws Exception {
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();
        parent.setId(count.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParent() throws Exception {
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();
        parent.setId(count.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParent() throws Exception {
        int databaseSizeBeforeUpdate = parentRepository.findAll().size();
        parent.setId(count.incrementAndGet());

        // Create the Parent
        ParentDTO parentDTO = parentMapper.toDto(parent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(parentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Parent in the database
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParent() throws Exception {
        // Initialize the database
        parentRepository.saveAndFlush(parent);

        int databaseSizeBeforeDelete = parentRepository.findAll().size();

        // Delete the parent
        restParentMockMvc
            .perform(delete(ENTITY_API_URL_ID, parent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parent> parentList = parentRepository.findAll();
        assertThat(parentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
