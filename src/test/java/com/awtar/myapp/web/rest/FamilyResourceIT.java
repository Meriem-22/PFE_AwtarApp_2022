package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.repository.FamilyRepository;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.mapper.FamilyMapper;
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
 * Integration tests for the {@link FamilyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FamilyResourceIT {

    private static final String DEFAULT_FAMILY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DWELLING = "AAAAAAAAAA";
    private static final String UPDATED_DWELLING = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NOTEBOOK_OF_POVERTY = false;
    private static final Boolean UPDATED_NOTEBOOK_OF_POVERTY = true;

    private static final Boolean DEFAULT_NOTEBOOK_OF_HANDICAP = false;
    private static final Boolean UPDATED_NOTEBOOK_OF_HANDICAP = true;

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/families";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilyMockMvc;

    private Family family;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Family createEntity(EntityManager em) {
        Family family = new Family()
            .familyName(DEFAULT_FAMILY_NAME)
            .dwelling(DEFAULT_DWELLING)
            .area(DEFAULT_AREA)
            .notebookOfPoverty(DEFAULT_NOTEBOOK_OF_POVERTY)
            .notebookOfHandicap(DEFAULT_NOTEBOOK_OF_HANDICAP)
            .archivated(DEFAULT_ARCHIVATED);
        return family;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Family createUpdatedEntity(EntityManager em) {
        Family family = new Family()
            .familyName(UPDATED_FAMILY_NAME)
            .dwelling(UPDATED_DWELLING)
            .area(UPDATED_AREA)
            .notebookOfPoverty(UPDATED_NOTEBOOK_OF_POVERTY)
            .notebookOfHandicap(UPDATED_NOTEBOOK_OF_HANDICAP)
            .archivated(UPDATED_ARCHIVATED);
        return family;
    }

    @BeforeEach
    public void initTest() {
        family = createEntity(em);
    }

    @Test
    @Transactional
    void createFamily() throws Exception {
        int databaseSizeBeforeCreate = familyRepository.findAll().size();
        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);
        restFamilyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isCreated());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeCreate + 1);
        Family testFamily = familyList.get(familyList.size() - 1);
        assertThat(testFamily.getFamilyName()).isEqualTo(DEFAULT_FAMILY_NAME);
        assertThat(testFamily.getDwelling()).isEqualTo(DEFAULT_DWELLING);
        assertThat(testFamily.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testFamily.getNotebookOfPoverty()).isEqualTo(DEFAULT_NOTEBOOK_OF_POVERTY);
        assertThat(testFamily.getNotebookOfHandicap()).isEqualTo(DEFAULT_NOTEBOOK_OF_HANDICAP);
        assertThat(testFamily.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createFamilyWithExistingId() throws Exception {
        // Create the Family with an existing ID
        family.setId(1L);
        FamilyDTO familyDTO = familyMapper.toDto(family);

        int databaseSizeBeforeCreate = familyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFamilyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyRepository.findAll().size();
        // set the field null
        family.setFamilyName(null);

        // Create the Family, which fails.
        FamilyDTO familyDTO = familyMapper.toDto(family);

        restFamilyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isBadRequest());

        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDwellingIsRequired() throws Exception {
        int databaseSizeBeforeTest = familyRepository.findAll().size();
        // set the field null
        family.setDwelling(null);

        // Create the Family, which fails.
        FamilyDTO familyDTO = familyMapper.toDto(family);

        restFamilyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isBadRequest());

        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFamilies() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get all the familyList
        restFamilyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(family.getId().intValue())))
            .andExpect(jsonPath("$.[*].familyName").value(hasItem(DEFAULT_FAMILY_NAME)))
            .andExpect(jsonPath("$.[*].dwelling").value(hasItem(DEFAULT_DWELLING)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].notebookOfPoverty").value(hasItem(DEFAULT_NOTEBOOK_OF_POVERTY.booleanValue())))
            .andExpect(jsonPath("$.[*].notebookOfHandicap").value(hasItem(DEFAULT_NOTEBOOK_OF_HANDICAP.booleanValue())))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @Test
    @Transactional
    void getFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get the family
        restFamilyMockMvc
            .perform(get(ENTITY_API_URL_ID, family.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(family.getId().intValue()))
            .andExpect(jsonPath("$.familyName").value(DEFAULT_FAMILY_NAME))
            .andExpect(jsonPath("$.dwelling").value(DEFAULT_DWELLING))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.notebookOfPoverty").value(DEFAULT_NOTEBOOK_OF_POVERTY.booleanValue()))
            .andExpect(jsonPath("$.notebookOfHandicap").value(DEFAULT_NOTEBOOK_OF_HANDICAP.booleanValue()))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingFamily() throws Exception {
        // Get the family
        restFamilyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        int databaseSizeBeforeUpdate = familyRepository.findAll().size();

        // Update the family
        Family updatedFamily = familyRepository.findById(family.getId()).get();
        // Disconnect from session so that the updates on updatedFamily are not directly saved in db
        em.detach(updatedFamily);
        updatedFamily
            .familyName(UPDATED_FAMILY_NAME)
            .dwelling(UPDATED_DWELLING)
            .area(UPDATED_AREA)
            .notebookOfPoverty(UPDATED_NOTEBOOK_OF_POVERTY)
            .notebookOfHandicap(UPDATED_NOTEBOOK_OF_HANDICAP)
            .archivated(UPDATED_ARCHIVATED);
        FamilyDTO familyDTO = familyMapper.toDto(updatedFamily);

        restFamilyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, familyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
        Family testFamily = familyList.get(familyList.size() - 1);
        assertThat(testFamily.getFamilyName()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testFamily.getDwelling()).isEqualTo(UPDATED_DWELLING);
        assertThat(testFamily.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testFamily.getNotebookOfPoverty()).isEqualTo(UPDATED_NOTEBOOK_OF_POVERTY);
        assertThat(testFamily.getNotebookOfHandicap()).isEqualTo(UPDATED_NOTEBOOK_OF_HANDICAP);
        assertThat(testFamily.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingFamily() throws Exception {
        int databaseSizeBeforeUpdate = familyRepository.findAll().size();
        family.setId(count.incrementAndGet());

        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, familyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFamily() throws Exception {
        int databaseSizeBeforeUpdate = familyRepository.findAll().size();
        family.setId(count.incrementAndGet());

        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFamily() throws Exception {
        int databaseSizeBeforeUpdate = familyRepository.findAll().size();
        family.setId(count.incrementAndGet());

        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFamilyWithPatch() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        int databaseSizeBeforeUpdate = familyRepository.findAll().size();

        // Update the family using partial update
        Family partialUpdatedFamily = new Family();
        partialUpdatedFamily.setId(family.getId());

        partialUpdatedFamily.dwelling(UPDATED_DWELLING).archivated(UPDATED_ARCHIVATED);

        restFamilyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamily.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFamily))
            )
            .andExpect(status().isOk());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
        Family testFamily = familyList.get(familyList.size() - 1);
        assertThat(testFamily.getFamilyName()).isEqualTo(DEFAULT_FAMILY_NAME);
        assertThat(testFamily.getDwelling()).isEqualTo(UPDATED_DWELLING);
        assertThat(testFamily.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testFamily.getNotebookOfPoverty()).isEqualTo(DEFAULT_NOTEBOOK_OF_POVERTY);
        assertThat(testFamily.getNotebookOfHandicap()).isEqualTo(DEFAULT_NOTEBOOK_OF_HANDICAP);
        assertThat(testFamily.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateFamilyWithPatch() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        int databaseSizeBeforeUpdate = familyRepository.findAll().size();

        // Update the family using partial update
        Family partialUpdatedFamily = new Family();
        partialUpdatedFamily.setId(family.getId());

        partialUpdatedFamily
            .familyName(UPDATED_FAMILY_NAME)
            .dwelling(UPDATED_DWELLING)
            .area(UPDATED_AREA)
            .notebookOfPoverty(UPDATED_NOTEBOOK_OF_POVERTY)
            .notebookOfHandicap(UPDATED_NOTEBOOK_OF_HANDICAP)
            .archivated(UPDATED_ARCHIVATED);

        restFamilyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamily.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFamily))
            )
            .andExpect(status().isOk());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
        Family testFamily = familyList.get(familyList.size() - 1);
        assertThat(testFamily.getFamilyName()).isEqualTo(UPDATED_FAMILY_NAME);
        assertThat(testFamily.getDwelling()).isEqualTo(UPDATED_DWELLING);
        assertThat(testFamily.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testFamily.getNotebookOfPoverty()).isEqualTo(UPDATED_NOTEBOOK_OF_POVERTY);
        assertThat(testFamily.getNotebookOfHandicap()).isEqualTo(UPDATED_NOTEBOOK_OF_HANDICAP);
        assertThat(testFamily.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingFamily() throws Exception {
        int databaseSizeBeforeUpdate = familyRepository.findAll().size();
        family.setId(count.incrementAndGet());

        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, familyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(familyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFamily() throws Exception {
        int databaseSizeBeforeUpdate = familyRepository.findAll().size();
        family.setId(count.incrementAndGet());

        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(familyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFamily() throws Exception {
        int databaseSizeBeforeUpdate = familyRepository.findAll().size();
        family.setId(count.incrementAndGet());

        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(familyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        int databaseSizeBeforeDelete = familyRepository.findAll().size();

        // Delete the family
        restFamilyMockMvc
            .perform(delete(ENTITY_API_URL_ID, family.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
