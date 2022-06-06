package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.EstablishmentType;
import com.awtar.myapp.repository.EstablishmentTypeRepository;
import com.awtar.myapp.service.dto.EstablishmentTypeDTO;
import com.awtar.myapp.service.mapper.EstablishmentTypeMapper;
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
 * Integration tests for the {@link EstablishmentTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstablishmentTypeResourceIT {

    private static final String DEFAULT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/establishment-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstablishmentTypeRepository establishmentTypeRepository;

    @Autowired
    private EstablishmentTypeMapper establishmentTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstablishmentTypeMockMvc;

    private EstablishmentType establishmentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstablishmentType createEntity(EntityManager em) {
        EstablishmentType establishmentType = new EstablishmentType().typeName(DEFAULT_TYPE_NAME).archivated(DEFAULT_ARCHIVATED);
        return establishmentType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstablishmentType createUpdatedEntity(EntityManager em) {
        EstablishmentType establishmentType = new EstablishmentType().typeName(UPDATED_TYPE_NAME).archivated(UPDATED_ARCHIVATED);
        return establishmentType;
    }

    @BeforeEach
    public void initTest() {
        establishmentType = createEntity(em);
    }

    @Test
    @Transactional
    void createEstablishmentType() throws Exception {
        int databaseSizeBeforeCreate = establishmentTypeRepository.findAll().size();
        // Create the EstablishmentType
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(establishmentType);
        restEstablishmentTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EstablishmentType testEstablishmentType = establishmentTypeList.get(establishmentTypeList.size() - 1);
        assertThat(testEstablishmentType.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
        assertThat(testEstablishmentType.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createEstablishmentTypeWithExistingId() throws Exception {
        // Create the EstablishmentType with an existing ID
        establishmentType.setId(1L);
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(establishmentType);

        int databaseSizeBeforeCreate = establishmentTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstablishmentTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentTypeRepository.findAll().size();
        // set the field null
        establishmentType.setTypeName(null);

        // Create the EstablishmentType, which fails.
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(establishmentType);

        restEstablishmentTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstablishmentTypes() throws Exception {
        // Initialize the database
        establishmentTypeRepository.saveAndFlush(establishmentType);

        // Get all the establishmentTypeList
        restEstablishmentTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(establishmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @Test
    @Transactional
    void getEstablishmentType() throws Exception {
        // Initialize the database
        establishmentTypeRepository.saveAndFlush(establishmentType);

        // Get the establishmentType
        restEstablishmentTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, establishmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(establishmentType.getId().intValue()))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEstablishmentType() throws Exception {
        // Get the establishmentType
        restEstablishmentTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstablishmentType() throws Exception {
        // Initialize the database
        establishmentTypeRepository.saveAndFlush(establishmentType);

        int databaseSizeBeforeUpdate = establishmentTypeRepository.findAll().size();

        // Update the establishmentType
        EstablishmentType updatedEstablishmentType = establishmentTypeRepository.findById(establishmentType.getId()).get();
        // Disconnect from session so that the updates on updatedEstablishmentType are not directly saved in db
        em.detach(updatedEstablishmentType);
        updatedEstablishmentType.typeName(UPDATED_TYPE_NAME).archivated(UPDATED_ARCHIVATED);
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(updatedEstablishmentType);

        restEstablishmentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, establishmentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeUpdate);
        EstablishmentType testEstablishmentType = establishmentTypeList.get(establishmentTypeList.size() - 1);
        assertThat(testEstablishmentType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testEstablishmentType.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingEstablishmentType() throws Exception {
        int databaseSizeBeforeUpdate = establishmentTypeRepository.findAll().size();
        establishmentType.setId(count.incrementAndGet());

        // Create the EstablishmentType
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(establishmentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablishmentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, establishmentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstablishmentType() throws Exception {
        int databaseSizeBeforeUpdate = establishmentTypeRepository.findAll().size();
        establishmentType.setId(count.incrementAndGet());

        // Create the EstablishmentType
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(establishmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstablishmentType() throws Exception {
        int databaseSizeBeforeUpdate = establishmentTypeRepository.findAll().size();
        establishmentType.setId(count.incrementAndGet());

        // Create the EstablishmentType
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(establishmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstablishmentTypeWithPatch() throws Exception {
        // Initialize the database
        establishmentTypeRepository.saveAndFlush(establishmentType);

        int databaseSizeBeforeUpdate = establishmentTypeRepository.findAll().size();

        // Update the establishmentType using partial update
        EstablishmentType partialUpdatedEstablishmentType = new EstablishmentType();
        partialUpdatedEstablishmentType.setId(establishmentType.getId());

        partialUpdatedEstablishmentType.typeName(UPDATED_TYPE_NAME);

        restEstablishmentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablishmentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstablishmentType))
            )
            .andExpect(status().isOk());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeUpdate);
        EstablishmentType testEstablishmentType = establishmentTypeList.get(establishmentTypeList.size() - 1);
        assertThat(testEstablishmentType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testEstablishmentType.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateEstablishmentTypeWithPatch() throws Exception {
        // Initialize the database
        establishmentTypeRepository.saveAndFlush(establishmentType);

        int databaseSizeBeforeUpdate = establishmentTypeRepository.findAll().size();

        // Update the establishmentType using partial update
        EstablishmentType partialUpdatedEstablishmentType = new EstablishmentType();
        partialUpdatedEstablishmentType.setId(establishmentType.getId());

        partialUpdatedEstablishmentType.typeName(UPDATED_TYPE_NAME).archivated(UPDATED_ARCHIVATED);

        restEstablishmentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablishmentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstablishmentType))
            )
            .andExpect(status().isOk());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeUpdate);
        EstablishmentType testEstablishmentType = establishmentTypeList.get(establishmentTypeList.size() - 1);
        assertThat(testEstablishmentType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
        assertThat(testEstablishmentType.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingEstablishmentType() throws Exception {
        int databaseSizeBeforeUpdate = establishmentTypeRepository.findAll().size();
        establishmentType.setId(count.incrementAndGet());

        // Create the EstablishmentType
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(establishmentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablishmentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, establishmentTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstablishmentType() throws Exception {
        int databaseSizeBeforeUpdate = establishmentTypeRepository.findAll().size();
        establishmentType.setId(count.incrementAndGet());

        // Create the EstablishmentType
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(establishmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstablishmentType() throws Exception {
        int databaseSizeBeforeUpdate = establishmentTypeRepository.findAll().size();
        establishmentType.setId(count.incrementAndGet());

        // Create the EstablishmentType
        EstablishmentTypeDTO establishmentTypeDTO = establishmentTypeMapper.toDto(establishmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishmentTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstablishmentType in the database
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstablishmentType() throws Exception {
        // Initialize the database
        establishmentTypeRepository.saveAndFlush(establishmentType);

        int databaseSizeBeforeDelete = establishmentTypeRepository.findAll().size();

        // Delete the establishmentType
        restEstablishmentTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, establishmentType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstablishmentType> establishmentTypeList = establishmentTypeRepository.findAll();
        assertThat(establishmentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
