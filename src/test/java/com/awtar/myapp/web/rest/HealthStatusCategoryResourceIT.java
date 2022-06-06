package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.HealthStatusCategory;
import com.awtar.myapp.repository.HealthStatusCategoryRepository;
import com.awtar.myapp.service.dto.HealthStatusCategoryDTO;
import com.awtar.myapp.service.mapper.HealthStatusCategoryMapper;
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
 * Integration tests for the {@link HealthStatusCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HealthStatusCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/health-status-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HealthStatusCategoryRepository healthStatusCategoryRepository;

    @Autowired
    private HealthStatusCategoryMapper healthStatusCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHealthStatusCategoryMockMvc;

    private HealthStatusCategory healthStatusCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HealthStatusCategory createEntity(EntityManager em) {
        HealthStatusCategory healthStatusCategory = new HealthStatusCategory().name(DEFAULT_NAME).archivated(DEFAULT_ARCHIVATED);
        return healthStatusCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HealthStatusCategory createUpdatedEntity(EntityManager em) {
        HealthStatusCategory healthStatusCategory = new HealthStatusCategory().name(UPDATED_NAME).archivated(UPDATED_ARCHIVATED);
        return healthStatusCategory;
    }

    @BeforeEach
    public void initTest() {
        healthStatusCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createHealthStatusCategory() throws Exception {
        int databaseSizeBeforeCreate = healthStatusCategoryRepository.findAll().size();
        // Create the HealthStatusCategory
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(healthStatusCategory);
        restHealthStatusCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        HealthStatusCategory testHealthStatusCategory = healthStatusCategoryList.get(healthStatusCategoryList.size() - 1);
        assertThat(testHealthStatusCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHealthStatusCategory.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createHealthStatusCategoryWithExistingId() throws Exception {
        // Create the HealthStatusCategory with an existing ID
        healthStatusCategory.setId(1L);
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(healthStatusCategory);

        int databaseSizeBeforeCreate = healthStatusCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthStatusCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthStatusCategoryRepository.findAll().size();
        // set the field null
        healthStatusCategory.setName(null);

        // Create the HealthStatusCategory, which fails.
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(healthStatusCategory);

        restHealthStatusCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHealthStatusCategories() throws Exception {
        // Initialize the database
        healthStatusCategoryRepository.saveAndFlush(healthStatusCategory);

        // Get all the healthStatusCategoryList
        restHealthStatusCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthStatusCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @Test
    @Transactional
    void getHealthStatusCategory() throws Exception {
        // Initialize the database
        healthStatusCategoryRepository.saveAndFlush(healthStatusCategory);

        // Get the healthStatusCategory
        restHealthStatusCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, healthStatusCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(healthStatusCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingHealthStatusCategory() throws Exception {
        // Get the healthStatusCategory
        restHealthStatusCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHealthStatusCategory() throws Exception {
        // Initialize the database
        healthStatusCategoryRepository.saveAndFlush(healthStatusCategory);

        int databaseSizeBeforeUpdate = healthStatusCategoryRepository.findAll().size();

        // Update the healthStatusCategory
        HealthStatusCategory updatedHealthStatusCategory = healthStatusCategoryRepository.findById(healthStatusCategory.getId()).get();
        // Disconnect from session so that the updates on updatedHealthStatusCategory are not directly saved in db
        em.detach(updatedHealthStatusCategory);
        updatedHealthStatusCategory.name(UPDATED_NAME).archivated(UPDATED_ARCHIVATED);
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(updatedHealthStatusCategory);

        restHealthStatusCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, healthStatusCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeUpdate);
        HealthStatusCategory testHealthStatusCategory = healthStatusCategoryList.get(healthStatusCategoryList.size() - 1);
        assertThat(testHealthStatusCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHealthStatusCategory.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingHealthStatusCategory() throws Exception {
        int databaseSizeBeforeUpdate = healthStatusCategoryRepository.findAll().size();
        healthStatusCategory.setId(count.incrementAndGet());

        // Create the HealthStatusCategory
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(healthStatusCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHealthStatusCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, healthStatusCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHealthStatusCategory() throws Exception {
        int databaseSizeBeforeUpdate = healthStatusCategoryRepository.findAll().size();
        healthStatusCategory.setId(count.incrementAndGet());

        // Create the HealthStatusCategory
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(healthStatusCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHealthStatusCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHealthStatusCategory() throws Exception {
        int databaseSizeBeforeUpdate = healthStatusCategoryRepository.findAll().size();
        healthStatusCategory.setId(count.incrementAndGet());

        // Create the HealthStatusCategory
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(healthStatusCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHealthStatusCategoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHealthStatusCategoryWithPatch() throws Exception {
        // Initialize the database
        healthStatusCategoryRepository.saveAndFlush(healthStatusCategory);

        int databaseSizeBeforeUpdate = healthStatusCategoryRepository.findAll().size();

        // Update the healthStatusCategory using partial update
        HealthStatusCategory partialUpdatedHealthStatusCategory = new HealthStatusCategory();
        partialUpdatedHealthStatusCategory.setId(healthStatusCategory.getId());

        partialUpdatedHealthStatusCategory.archivated(UPDATED_ARCHIVATED);

        restHealthStatusCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHealthStatusCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHealthStatusCategory))
            )
            .andExpect(status().isOk());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeUpdate);
        HealthStatusCategory testHealthStatusCategory = healthStatusCategoryList.get(healthStatusCategoryList.size() - 1);
        assertThat(testHealthStatusCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHealthStatusCategory.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateHealthStatusCategoryWithPatch() throws Exception {
        // Initialize the database
        healthStatusCategoryRepository.saveAndFlush(healthStatusCategory);

        int databaseSizeBeforeUpdate = healthStatusCategoryRepository.findAll().size();

        // Update the healthStatusCategory using partial update
        HealthStatusCategory partialUpdatedHealthStatusCategory = new HealthStatusCategory();
        partialUpdatedHealthStatusCategory.setId(healthStatusCategory.getId());

        partialUpdatedHealthStatusCategory.name(UPDATED_NAME).archivated(UPDATED_ARCHIVATED);

        restHealthStatusCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHealthStatusCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHealthStatusCategory))
            )
            .andExpect(status().isOk());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeUpdate);
        HealthStatusCategory testHealthStatusCategory = healthStatusCategoryList.get(healthStatusCategoryList.size() - 1);
        assertThat(testHealthStatusCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHealthStatusCategory.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingHealthStatusCategory() throws Exception {
        int databaseSizeBeforeUpdate = healthStatusCategoryRepository.findAll().size();
        healthStatusCategory.setId(count.incrementAndGet());

        // Create the HealthStatusCategory
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(healthStatusCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHealthStatusCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, healthStatusCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHealthStatusCategory() throws Exception {
        int databaseSizeBeforeUpdate = healthStatusCategoryRepository.findAll().size();
        healthStatusCategory.setId(count.incrementAndGet());

        // Create the HealthStatusCategory
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(healthStatusCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHealthStatusCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHealthStatusCategory() throws Exception {
        int databaseSizeBeforeUpdate = healthStatusCategoryRepository.findAll().size();
        healthStatusCategory.setId(count.incrementAndGet());

        // Create the HealthStatusCategory
        HealthStatusCategoryDTO healthStatusCategoryDTO = healthStatusCategoryMapper.toDto(healthStatusCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHealthStatusCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(healthStatusCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HealthStatusCategory in the database
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHealthStatusCategory() throws Exception {
        // Initialize the database
        healthStatusCategoryRepository.saveAndFlush(healthStatusCategory);

        int databaseSizeBeforeDelete = healthStatusCategoryRepository.findAll().size();

        // Delete the healthStatusCategory
        restHealthStatusCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, healthStatusCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HealthStatusCategory> healthStatusCategoryList = healthStatusCategoryRepository.findAll();
        assertThat(healthStatusCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
