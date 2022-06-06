package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.ChildStatus;
import com.awtar.myapp.domain.enumeration.Status;
import com.awtar.myapp.repository.ChildStatusRepository;
import com.awtar.myapp.service.dto.ChildStatusDTO;
import com.awtar.myapp.service.mapper.ChildStatusMapper;
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
 * Integration tests for the {@link ChildStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChildStatusResourceIT {

    private static final Status DEFAULT_STAUS = Status.ADMITTED;
    private static final Status UPDATED_STAUS = Status.REPEATING;

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/child-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChildStatusRepository childStatusRepository;

    @Autowired
    private ChildStatusMapper childStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChildStatusMockMvc;

    private ChildStatus childStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildStatus createEntity(EntityManager em) {
        ChildStatus childStatus = new ChildStatus().staus(DEFAULT_STAUS).archivated(DEFAULT_ARCHIVATED);
        return childStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildStatus createUpdatedEntity(EntityManager em) {
        ChildStatus childStatus = new ChildStatus().staus(UPDATED_STAUS).archivated(UPDATED_ARCHIVATED);
        return childStatus;
    }

    @BeforeEach
    public void initTest() {
        childStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createChildStatus() throws Exception {
        int databaseSizeBeforeCreate = childStatusRepository.findAll().size();
        // Create the ChildStatus
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(childStatus);
        restChildStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ChildStatus testChildStatus = childStatusList.get(childStatusList.size() - 1);
        assertThat(testChildStatus.getStaus()).isEqualTo(DEFAULT_STAUS);
        assertThat(testChildStatus.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createChildStatusWithExistingId() throws Exception {
        // Create the ChildStatus with an existing ID
        childStatus.setId(1L);
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(childStatus);

        int databaseSizeBeforeCreate = childStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStausIsRequired() throws Exception {
        int databaseSizeBeforeTest = childStatusRepository.findAll().size();
        // set the field null
        childStatus.setStaus(null);

        // Create the ChildStatus, which fails.
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(childStatus);

        restChildStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChildStatuses() throws Exception {
        // Initialize the database
        childStatusRepository.saveAndFlush(childStatus);

        // Get all the childStatusList
        restChildStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].staus").value(hasItem(DEFAULT_STAUS.toString())))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @Test
    @Transactional
    void getChildStatus() throws Exception {
        // Initialize the database
        childStatusRepository.saveAndFlush(childStatus);

        // Get the childStatus
        restChildStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, childStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(childStatus.getId().intValue()))
            .andExpect(jsonPath("$.staus").value(DEFAULT_STAUS.toString()))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingChildStatus() throws Exception {
        // Get the childStatus
        restChildStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChildStatus() throws Exception {
        // Initialize the database
        childStatusRepository.saveAndFlush(childStatus);

        int databaseSizeBeforeUpdate = childStatusRepository.findAll().size();

        // Update the childStatus
        ChildStatus updatedChildStatus = childStatusRepository.findById(childStatus.getId()).get();
        // Disconnect from session so that the updates on updatedChildStatus are not directly saved in db
        em.detach(updatedChildStatus);
        updatedChildStatus.staus(UPDATED_STAUS).archivated(UPDATED_ARCHIVATED);
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(updatedChildStatus);

        restChildStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, childStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeUpdate);
        ChildStatus testChildStatus = childStatusList.get(childStatusList.size() - 1);
        assertThat(testChildStatus.getStaus()).isEqualTo(UPDATED_STAUS);
        assertThat(testChildStatus.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingChildStatus() throws Exception {
        int databaseSizeBeforeUpdate = childStatusRepository.findAll().size();
        childStatus.setId(count.incrementAndGet());

        // Create the ChildStatus
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(childStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, childStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChildStatus() throws Exception {
        int databaseSizeBeforeUpdate = childStatusRepository.findAll().size();
        childStatus.setId(count.incrementAndGet());

        // Create the ChildStatus
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(childStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChildStatus() throws Exception {
        int databaseSizeBeforeUpdate = childStatusRepository.findAll().size();
        childStatus.setId(count.incrementAndGet());

        // Create the ChildStatus
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(childStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChildStatusWithPatch() throws Exception {
        // Initialize the database
        childStatusRepository.saveAndFlush(childStatus);

        int databaseSizeBeforeUpdate = childStatusRepository.findAll().size();

        // Update the childStatus using partial update
        ChildStatus partialUpdatedChildStatus = new ChildStatus();
        partialUpdatedChildStatus.setId(childStatus.getId());

        partialUpdatedChildStatus.staus(UPDATED_STAUS);

        restChildStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChildStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChildStatus))
            )
            .andExpect(status().isOk());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeUpdate);
        ChildStatus testChildStatus = childStatusList.get(childStatusList.size() - 1);
        assertThat(testChildStatus.getStaus()).isEqualTo(UPDATED_STAUS);
        assertThat(testChildStatus.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateChildStatusWithPatch() throws Exception {
        // Initialize the database
        childStatusRepository.saveAndFlush(childStatus);

        int databaseSizeBeforeUpdate = childStatusRepository.findAll().size();

        // Update the childStatus using partial update
        ChildStatus partialUpdatedChildStatus = new ChildStatus();
        partialUpdatedChildStatus.setId(childStatus.getId());

        partialUpdatedChildStatus.staus(UPDATED_STAUS).archivated(UPDATED_ARCHIVATED);

        restChildStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChildStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChildStatus))
            )
            .andExpect(status().isOk());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeUpdate);
        ChildStatus testChildStatus = childStatusList.get(childStatusList.size() - 1);
        assertThat(testChildStatus.getStaus()).isEqualTo(UPDATED_STAUS);
        assertThat(testChildStatus.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingChildStatus() throws Exception {
        int databaseSizeBeforeUpdate = childStatusRepository.findAll().size();
        childStatus.setId(count.incrementAndGet());

        // Create the ChildStatus
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(childStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, childStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(childStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChildStatus() throws Exception {
        int databaseSizeBeforeUpdate = childStatusRepository.findAll().size();
        childStatus.setId(count.incrementAndGet());

        // Create the ChildStatus
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(childStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(childStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChildStatus() throws Exception {
        int databaseSizeBeforeUpdate = childStatusRepository.findAll().size();
        childStatus.setId(count.incrementAndGet());

        // Create the ChildStatus
        ChildStatusDTO childStatusDTO = childStatusMapper.toDto(childStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(childStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChildStatus in the database
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChildStatus() throws Exception {
        // Initialize the database
        childStatusRepository.saveAndFlush(childStatus);

        int databaseSizeBeforeDelete = childStatusRepository.findAll().size();

        // Delete the childStatus
        restChildStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, childStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChildStatus> childStatusList = childStatusRepository.findAll();
        assertThat(childStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
