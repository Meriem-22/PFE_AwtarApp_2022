package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.EducationalInstitution;
import com.awtar.myapp.repository.EducationalInstitutionRepository;
import com.awtar.myapp.service.EducationalInstitutionService;
import com.awtar.myapp.service.dto.EducationalInstitutionDTO;
import com.awtar.myapp.service.mapper.EducationalInstitutionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EducationalInstitutionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EducationalInstitutionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/educational-institutions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    @Mock
    private EducationalInstitutionRepository educationalInstitutionRepositoryMock;

    @Autowired
    private EducationalInstitutionMapper educationalInstitutionMapper;

    @Mock
    private EducationalInstitutionService educationalInstitutionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationalInstitutionMockMvc;

    private EducationalInstitution educationalInstitution;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationalInstitution createEntity(EntityManager em) {
        EducationalInstitution educationalInstitution = new EducationalInstitution()
            .name(DEFAULT_NAME)
            .adress(DEFAULT_ADRESS)
            .archivated(DEFAULT_ARCHIVATED);
        return educationalInstitution;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducationalInstitution createUpdatedEntity(EntityManager em) {
        EducationalInstitution educationalInstitution = new EducationalInstitution()
            .name(UPDATED_NAME)
            .adress(UPDATED_ADRESS)
            .archivated(UPDATED_ARCHIVATED);
        return educationalInstitution;
    }

    @BeforeEach
    public void initTest() {
        educationalInstitution = createEntity(em);
    }

    @Test
    @Transactional
    void createEducationalInstitution() throws Exception {
        int databaseSizeBeforeCreate = educationalInstitutionRepository.findAll().size();
        // Create the EducationalInstitution
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(educationalInstitution);
        restEducationalInstitutionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeCreate + 1);
        EducationalInstitution testEducationalInstitution = educationalInstitutionList.get(educationalInstitutionList.size() - 1);
        assertThat(testEducationalInstitution.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEducationalInstitution.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testEducationalInstitution.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createEducationalInstitutionWithExistingId() throws Exception {
        // Create the EducationalInstitution with an existing ID
        educationalInstitution.setId(1L);
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(educationalInstitution);

        int databaseSizeBeforeCreate = educationalInstitutionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationalInstitutionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationalInstitutionRepository.findAll().size();
        // set the field null
        educationalInstitution.setName(null);

        // Create the EducationalInstitution, which fails.
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(educationalInstitution);

        restEducationalInstitutionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEducationalInstitutions() throws Exception {
        // Initialize the database
        educationalInstitutionRepository.saveAndFlush(educationalInstitution);

        // Get all the educationalInstitutionList
        restEducationalInstitutionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationalInstitution.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEducationalInstitutionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(educationalInstitutionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEducationalInstitutionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(educationalInstitutionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEducationalInstitutionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(educationalInstitutionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEducationalInstitutionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(educationalInstitutionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEducationalInstitution() throws Exception {
        // Initialize the database
        educationalInstitutionRepository.saveAndFlush(educationalInstitution);

        // Get the educationalInstitution
        restEducationalInstitutionMockMvc
            .perform(get(ENTITY_API_URL_ID, educationalInstitution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(educationalInstitution.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEducationalInstitution() throws Exception {
        // Get the educationalInstitution
        restEducationalInstitutionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEducationalInstitution() throws Exception {
        // Initialize the database
        educationalInstitutionRepository.saveAndFlush(educationalInstitution);

        int databaseSizeBeforeUpdate = educationalInstitutionRepository.findAll().size();

        // Update the educationalInstitution
        EducationalInstitution updatedEducationalInstitution = educationalInstitutionRepository
            .findById(educationalInstitution.getId())
            .get();
        // Disconnect from session so that the updates on updatedEducationalInstitution are not directly saved in db
        em.detach(updatedEducationalInstitution);
        updatedEducationalInstitution.name(UPDATED_NAME).adress(UPDATED_ADRESS).archivated(UPDATED_ARCHIVATED);
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(updatedEducationalInstitution);

        restEducationalInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationalInstitutionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isOk());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeUpdate);
        EducationalInstitution testEducationalInstitution = educationalInstitutionList.get(educationalInstitutionList.size() - 1);
        assertThat(testEducationalInstitution.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEducationalInstitution.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testEducationalInstitution.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingEducationalInstitution() throws Exception {
        int databaseSizeBeforeUpdate = educationalInstitutionRepository.findAll().size();
        educationalInstitution.setId(count.incrementAndGet());

        // Create the EducationalInstitution
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(educationalInstitution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationalInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationalInstitutionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEducationalInstitution() throws Exception {
        int databaseSizeBeforeUpdate = educationalInstitutionRepository.findAll().size();
        educationalInstitution.setId(count.incrementAndGet());

        // Create the EducationalInstitution
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(educationalInstitution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationalInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEducationalInstitution() throws Exception {
        int databaseSizeBeforeUpdate = educationalInstitutionRepository.findAll().size();
        educationalInstitution.setId(count.incrementAndGet());

        // Create the EducationalInstitution
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(educationalInstitution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationalInstitutionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEducationalInstitutionWithPatch() throws Exception {
        // Initialize the database
        educationalInstitutionRepository.saveAndFlush(educationalInstitution);

        int databaseSizeBeforeUpdate = educationalInstitutionRepository.findAll().size();

        // Update the educationalInstitution using partial update
        EducationalInstitution partialUpdatedEducationalInstitution = new EducationalInstitution();
        partialUpdatedEducationalInstitution.setId(educationalInstitution.getId());

        partialUpdatedEducationalInstitution.name(UPDATED_NAME).adress(UPDATED_ADRESS).archivated(UPDATED_ARCHIVATED);

        restEducationalInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducationalInstitution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducationalInstitution))
            )
            .andExpect(status().isOk());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeUpdate);
        EducationalInstitution testEducationalInstitution = educationalInstitutionList.get(educationalInstitutionList.size() - 1);
        assertThat(testEducationalInstitution.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEducationalInstitution.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testEducationalInstitution.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateEducationalInstitutionWithPatch() throws Exception {
        // Initialize the database
        educationalInstitutionRepository.saveAndFlush(educationalInstitution);

        int databaseSizeBeforeUpdate = educationalInstitutionRepository.findAll().size();

        // Update the educationalInstitution using partial update
        EducationalInstitution partialUpdatedEducationalInstitution = new EducationalInstitution();
        partialUpdatedEducationalInstitution.setId(educationalInstitution.getId());

        partialUpdatedEducationalInstitution.name(UPDATED_NAME).adress(UPDATED_ADRESS).archivated(UPDATED_ARCHIVATED);

        restEducationalInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducationalInstitution.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducationalInstitution))
            )
            .andExpect(status().isOk());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeUpdate);
        EducationalInstitution testEducationalInstitution = educationalInstitutionList.get(educationalInstitutionList.size() - 1);
        assertThat(testEducationalInstitution.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEducationalInstitution.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testEducationalInstitution.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingEducationalInstitution() throws Exception {
        int databaseSizeBeforeUpdate = educationalInstitutionRepository.findAll().size();
        educationalInstitution.setId(count.incrementAndGet());

        // Create the EducationalInstitution
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(educationalInstitution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationalInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educationalInstitutionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEducationalInstitution() throws Exception {
        int databaseSizeBeforeUpdate = educationalInstitutionRepository.findAll().size();
        educationalInstitution.setId(count.incrementAndGet());

        // Create the EducationalInstitution
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(educationalInstitution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationalInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEducationalInstitution() throws Exception {
        int databaseSizeBeforeUpdate = educationalInstitutionRepository.findAll().size();
        educationalInstitution.setId(count.incrementAndGet());

        // Create the EducationalInstitution
        EducationalInstitutionDTO educationalInstitutionDTO = educationalInstitutionMapper.toDto(educationalInstitution);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationalInstitutionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationalInstitutionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EducationalInstitution in the database
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEducationalInstitution() throws Exception {
        // Initialize the database
        educationalInstitutionRepository.saveAndFlush(educationalInstitution);

        int databaseSizeBeforeDelete = educationalInstitutionRepository.findAll().size();

        // Delete the educationalInstitution
        restEducationalInstitutionMockMvc
            .perform(delete(ENTITY_API_URL_ID, educationalInstitution.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EducationalInstitution> educationalInstitutionList = educationalInstitutionRepository.findAll();
        assertThat(educationalInstitutionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
