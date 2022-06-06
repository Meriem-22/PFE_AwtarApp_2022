package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.repository.SchoolLevelRepository;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
import com.awtar.myapp.service.mapper.SchoolLevelMapper;
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
 * Integration tests for the {@link SchoolLevelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchoolLevelResourceIT {

    private static final String DEFAULT_SCHOOL_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_LEVEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/school-levels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchoolLevelRepository schoolLevelRepository;

    @Autowired
    private SchoolLevelMapper schoolLevelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchoolLevelMockMvc;

    private SchoolLevel schoolLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchoolLevel createEntity(EntityManager em) {
        SchoolLevel schoolLevel = new SchoolLevel().schoolLevel(DEFAULT_SCHOOL_LEVEL).archivated(DEFAULT_ARCHIVATED);
        return schoolLevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchoolLevel createUpdatedEntity(EntityManager em) {
        SchoolLevel schoolLevel = new SchoolLevel().schoolLevel(UPDATED_SCHOOL_LEVEL).archivated(UPDATED_ARCHIVATED);
        return schoolLevel;
    }

    @BeforeEach
    public void initTest() {
        schoolLevel = createEntity(em);
    }

    @Test
    @Transactional
    void createSchoolLevel() throws Exception {
        int databaseSizeBeforeCreate = schoolLevelRepository.findAll().size();
        // Create the SchoolLevel
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(schoolLevel);
        restSchoolLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeCreate + 1);
        SchoolLevel testSchoolLevel = schoolLevelList.get(schoolLevelList.size() - 1);
        assertThat(testSchoolLevel.getSchoolLevel()).isEqualTo(DEFAULT_SCHOOL_LEVEL);
        assertThat(testSchoolLevel.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createSchoolLevelWithExistingId() throws Exception {
        // Create the SchoolLevel with an existing ID
        schoolLevel.setId(1L);
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(schoolLevel);

        int databaseSizeBeforeCreate = schoolLevelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSchoolLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolLevelRepository.findAll().size();
        // set the field null
        schoolLevel.setSchoolLevel(null);

        // Create the SchoolLevel, which fails.
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(schoolLevel);

        restSchoolLevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO))
            )
            .andExpect(status().isBadRequest());

        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSchoolLevels() throws Exception {
        // Initialize the database
        schoolLevelRepository.saveAndFlush(schoolLevel);

        // Get all the schoolLevelList
        restSchoolLevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolLevel").value(hasItem(DEFAULT_SCHOOL_LEVEL)))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @Test
    @Transactional
    void getSchoolLevel() throws Exception {
        // Initialize the database
        schoolLevelRepository.saveAndFlush(schoolLevel);

        // Get the schoolLevel
        restSchoolLevelMockMvc
            .perform(get(ENTITY_API_URL_ID, schoolLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schoolLevel.getId().intValue()))
            .andExpect(jsonPath("$.schoolLevel").value(DEFAULT_SCHOOL_LEVEL))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSchoolLevel() throws Exception {
        // Get the schoolLevel
        restSchoolLevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSchoolLevel() throws Exception {
        // Initialize the database
        schoolLevelRepository.saveAndFlush(schoolLevel);

        int databaseSizeBeforeUpdate = schoolLevelRepository.findAll().size();

        // Update the schoolLevel
        SchoolLevel updatedSchoolLevel = schoolLevelRepository.findById(schoolLevel.getId()).get();
        // Disconnect from session so that the updates on updatedSchoolLevel are not directly saved in db
        em.detach(updatedSchoolLevel);
        updatedSchoolLevel.schoolLevel(UPDATED_SCHOOL_LEVEL).archivated(UPDATED_ARCHIVATED);
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(updatedSchoolLevel);

        restSchoolLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schoolLevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO))
            )
            .andExpect(status().isOk());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeUpdate);
        SchoolLevel testSchoolLevel = schoolLevelList.get(schoolLevelList.size() - 1);
        assertThat(testSchoolLevel.getSchoolLevel()).isEqualTo(UPDATED_SCHOOL_LEVEL);
        assertThat(testSchoolLevel.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingSchoolLevel() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelRepository.findAll().size();
        schoolLevel.setId(count.incrementAndGet());

        // Create the SchoolLevel
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(schoolLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schoolLevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchoolLevel() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelRepository.findAll().size();
        schoolLevel.setId(count.incrementAndGet());

        // Create the SchoolLevel
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(schoolLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolLevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchoolLevel() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelRepository.findAll().size();
        schoolLevel.setId(count.incrementAndGet());

        // Create the SchoolLevel
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(schoolLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolLevelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchoolLevelWithPatch() throws Exception {
        // Initialize the database
        schoolLevelRepository.saveAndFlush(schoolLevel);

        int databaseSizeBeforeUpdate = schoolLevelRepository.findAll().size();

        // Update the schoolLevel using partial update
        SchoolLevel partialUpdatedSchoolLevel = new SchoolLevel();
        partialUpdatedSchoolLevel.setId(schoolLevel.getId());

        restSchoolLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchoolLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchoolLevel))
            )
            .andExpect(status().isOk());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeUpdate);
        SchoolLevel testSchoolLevel = schoolLevelList.get(schoolLevelList.size() - 1);
        assertThat(testSchoolLevel.getSchoolLevel()).isEqualTo(DEFAULT_SCHOOL_LEVEL);
        assertThat(testSchoolLevel.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateSchoolLevelWithPatch() throws Exception {
        // Initialize the database
        schoolLevelRepository.saveAndFlush(schoolLevel);

        int databaseSizeBeforeUpdate = schoolLevelRepository.findAll().size();

        // Update the schoolLevel using partial update
        SchoolLevel partialUpdatedSchoolLevel = new SchoolLevel();
        partialUpdatedSchoolLevel.setId(schoolLevel.getId());

        partialUpdatedSchoolLevel.schoolLevel(UPDATED_SCHOOL_LEVEL).archivated(UPDATED_ARCHIVATED);

        restSchoolLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchoolLevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchoolLevel))
            )
            .andExpect(status().isOk());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeUpdate);
        SchoolLevel testSchoolLevel = schoolLevelList.get(schoolLevelList.size() - 1);
        assertThat(testSchoolLevel.getSchoolLevel()).isEqualTo(UPDATED_SCHOOL_LEVEL);
        assertThat(testSchoolLevel.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingSchoolLevel() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelRepository.findAll().size();
        schoolLevel.setId(count.incrementAndGet());

        // Create the SchoolLevel
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(schoolLevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schoolLevelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchoolLevel() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelRepository.findAll().size();
        schoolLevel.setId(count.incrementAndGet());

        // Create the SchoolLevel
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(schoolLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolLevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchoolLevel() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelRepository.findAll().size();
        schoolLevel.setId(count.incrementAndGet());

        // Create the SchoolLevel
        SchoolLevelDTO schoolLevelDTO = schoolLevelMapper.toDto(schoolLevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolLevelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(schoolLevelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchoolLevel in the database
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchoolLevel() throws Exception {
        // Initialize the database
        schoolLevelRepository.saveAndFlush(schoolLevel);

        int databaseSizeBeforeDelete = schoolLevelRepository.findAll().size();

        // Delete the schoolLevel
        restSchoolLevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, schoolLevel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchoolLevel> schoolLevelList = schoolLevelRepository.findAll();
        assertThat(schoolLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
