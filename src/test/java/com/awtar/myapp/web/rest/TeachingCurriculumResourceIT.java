package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.EducationalInstitution;
import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.domain.TeachingCurriculum;
import com.awtar.myapp.domain.enumeration.Status;
import com.awtar.myapp.repository.TeachingCurriculumRepository;
import com.awtar.myapp.service.TeachingCurriculumService;
import com.awtar.myapp.service.dto.TeachingCurriculumDTO;
import com.awtar.myapp.service.mapper.TeachingCurriculumMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TeachingCurriculumResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TeachingCurriculumResourceIT {

    private static final String DEFAULT_BEGINNING_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_BEGINNING_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_END_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_END_YEAR = "BBBBBBBBBB";

    private static final Double DEFAULT_ANNUAL_RESULT = 1D;
    private static final Double UPDATED_ANNUAL_RESULT = 2D;

    private static final Status DEFAULT_RESULT = Status.ADMITTED;
    private static final Status UPDATED_RESULT = Status.REPEATING;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHED_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHED_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHED_FILE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/teaching-curricula";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeachingCurriculumRepository teachingCurriculumRepository;

    @Mock
    private TeachingCurriculumRepository teachingCurriculumRepositoryMock;

    @Autowired
    private TeachingCurriculumMapper teachingCurriculumMapper;

    @Mock
    private TeachingCurriculumService teachingCurriculumServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeachingCurriculumMockMvc;

    private TeachingCurriculum teachingCurriculum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeachingCurriculum createEntity(EntityManager em) {
        TeachingCurriculum teachingCurriculum = new TeachingCurriculum()
            .beginningYear(DEFAULT_BEGINNING_YEAR)
            .endYear(DEFAULT_END_YEAR)
            .annualResult(DEFAULT_ANNUAL_RESULT)
            .result(DEFAULT_RESULT)
            .remarks(DEFAULT_REMARKS)
            .attachedFile(DEFAULT_ATTACHED_FILE)
            .attachedFileContentType(DEFAULT_ATTACHED_FILE_CONTENT_TYPE)
            .archivated(DEFAULT_ARCHIVATED);
        // Add required entity
        SchoolLevel schoolLevel;
        if (TestUtil.findAll(em, SchoolLevel.class).isEmpty()) {
            schoolLevel = SchoolLevelResourceIT.createEntity(em);
            em.persist(schoolLevel);
            em.flush();
        } else {
            schoolLevel = TestUtil.findAll(em, SchoolLevel.class).get(0);
        }
        teachingCurriculum.setSchoolLevel(schoolLevel);
        // Add required entity
        Child child;
        if (TestUtil.findAll(em, Child.class).isEmpty()) {
            child = ChildResourceIT.createEntity(em);
            em.persist(child);
            em.flush();
        } else {
            child = TestUtil.findAll(em, Child.class).get(0);
        }
        teachingCurriculum.setChild(child);
        // Add required entity
        EducationalInstitution educationalInstitution;
        if (TestUtil.findAll(em, EducationalInstitution.class).isEmpty()) {
            educationalInstitution = EducationalInstitutionResourceIT.createEntity(em);
            em.persist(educationalInstitution);
            em.flush();
        } else {
            educationalInstitution = TestUtil.findAll(em, EducationalInstitution.class).get(0);
        }
        teachingCurriculum.setEducationalInstitution(educationalInstitution);
        return teachingCurriculum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeachingCurriculum createUpdatedEntity(EntityManager em) {
        TeachingCurriculum teachingCurriculum = new TeachingCurriculum()
            .beginningYear(UPDATED_BEGINNING_YEAR)
            .endYear(UPDATED_END_YEAR)
            .annualResult(UPDATED_ANNUAL_RESULT)
            .result(UPDATED_RESULT)
            .remarks(UPDATED_REMARKS)
            .attachedFile(UPDATED_ATTACHED_FILE)
            .attachedFileContentType(UPDATED_ATTACHED_FILE_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);
        // Add required entity
        SchoolLevel schoolLevel;
        if (TestUtil.findAll(em, SchoolLevel.class).isEmpty()) {
            schoolLevel = SchoolLevelResourceIT.createUpdatedEntity(em);
            em.persist(schoolLevel);
            em.flush();
        } else {
            schoolLevel = TestUtil.findAll(em, SchoolLevel.class).get(0);
        }
        teachingCurriculum.setSchoolLevel(schoolLevel);
        // Add required entity
        Child child;
        if (TestUtil.findAll(em, Child.class).isEmpty()) {
            child = ChildResourceIT.createUpdatedEntity(em);
            em.persist(child);
            em.flush();
        } else {
            child = TestUtil.findAll(em, Child.class).get(0);
        }
        teachingCurriculum.setChild(child);
        // Add required entity
        EducationalInstitution educationalInstitution;
        if (TestUtil.findAll(em, EducationalInstitution.class).isEmpty()) {
            educationalInstitution = EducationalInstitutionResourceIT.createUpdatedEntity(em);
            em.persist(educationalInstitution);
            em.flush();
        } else {
            educationalInstitution = TestUtil.findAll(em, EducationalInstitution.class).get(0);
        }
        teachingCurriculum.setEducationalInstitution(educationalInstitution);
        return teachingCurriculum;
    }

    @BeforeEach
    public void initTest() {
        teachingCurriculum = createEntity(em);
    }

    @Test
    @Transactional
    void createTeachingCurriculum() throws Exception {
        int databaseSizeBeforeCreate = teachingCurriculumRepository.findAll().size();
        // Create the TeachingCurriculum
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);
        restTeachingCurriculumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeCreate + 1);
        TeachingCurriculum testTeachingCurriculum = teachingCurriculumList.get(teachingCurriculumList.size() - 1);
        assertThat(testTeachingCurriculum.getBeginningYear()).isEqualTo(DEFAULT_BEGINNING_YEAR);
        assertThat(testTeachingCurriculum.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
        assertThat(testTeachingCurriculum.getAnnualResult()).isEqualTo(DEFAULT_ANNUAL_RESULT);
        assertThat(testTeachingCurriculum.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testTeachingCurriculum.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTeachingCurriculum.getAttachedFile()).isEqualTo(DEFAULT_ATTACHED_FILE);
        assertThat(testTeachingCurriculum.getAttachedFileContentType()).isEqualTo(DEFAULT_ATTACHED_FILE_CONTENT_TYPE);
        assertThat(testTeachingCurriculum.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createTeachingCurriculumWithExistingId() throws Exception {
        // Create the TeachingCurriculum with an existing ID
        teachingCurriculum.setId(1L);
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        int databaseSizeBeforeCreate = teachingCurriculumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeachingCurriculumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBeginningYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = teachingCurriculumRepository.findAll().size();
        // set the field null
        teachingCurriculum.setBeginningYear(null);

        // Create the TeachingCurriculum, which fails.
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        restTeachingCurriculumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = teachingCurriculumRepository.findAll().size();
        // set the field null
        teachingCurriculum.setEndYear(null);

        // Create the TeachingCurriculum, which fails.
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        restTeachingCurriculumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = teachingCurriculumRepository.findAll().size();
        // set the field null
        teachingCurriculum.setResult(null);

        // Create the TeachingCurriculum, which fails.
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        restTeachingCurriculumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isBadRequest());

        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTeachingCurricula() throws Exception {
        // Initialize the database
        teachingCurriculumRepository.saveAndFlush(teachingCurriculum);

        // Get all the teachingCurriculumList
        restTeachingCurriculumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachingCurriculum.getId().intValue())))
            .andExpect(jsonPath("$.[*].beginningYear").value(hasItem(DEFAULT_BEGINNING_YEAR)))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR)))
            .andExpect(jsonPath("$.[*].annualResult").value(hasItem(DEFAULT_ANNUAL_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].attachedFileContentType").value(hasItem(DEFAULT_ATTACHED_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachedFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHED_FILE))))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTeachingCurriculaWithEagerRelationshipsIsEnabled() throws Exception {
        when(teachingCurriculumServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeachingCurriculumMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(teachingCurriculumServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTeachingCurriculaWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(teachingCurriculumServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeachingCurriculumMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(teachingCurriculumServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTeachingCurriculum() throws Exception {
        // Initialize the database
        teachingCurriculumRepository.saveAndFlush(teachingCurriculum);

        // Get the teachingCurriculum
        restTeachingCurriculumMockMvc
            .perform(get(ENTITY_API_URL_ID, teachingCurriculum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teachingCurriculum.getId().intValue()))
            .andExpect(jsonPath("$.beginningYear").value(DEFAULT_BEGINNING_YEAR))
            .andExpect(jsonPath("$.endYear").value(DEFAULT_END_YEAR))
            .andExpect(jsonPath("$.annualResult").value(DEFAULT_ANNUAL_RESULT.doubleValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.attachedFileContentType").value(DEFAULT_ATTACHED_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachedFile").value(Base64Utils.encodeToString(DEFAULT_ATTACHED_FILE)))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTeachingCurriculum() throws Exception {
        // Get the teachingCurriculum
        restTeachingCurriculumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTeachingCurriculum() throws Exception {
        // Initialize the database
        teachingCurriculumRepository.saveAndFlush(teachingCurriculum);

        int databaseSizeBeforeUpdate = teachingCurriculumRepository.findAll().size();

        // Update the teachingCurriculum
        TeachingCurriculum updatedTeachingCurriculum = teachingCurriculumRepository.findById(teachingCurriculum.getId()).get();
        // Disconnect from session so that the updates on updatedTeachingCurriculum are not directly saved in db
        em.detach(updatedTeachingCurriculum);
        updatedTeachingCurriculum
            .beginningYear(UPDATED_BEGINNING_YEAR)
            .endYear(UPDATED_END_YEAR)
            .annualResult(UPDATED_ANNUAL_RESULT)
            .result(UPDATED_RESULT)
            .remarks(UPDATED_REMARKS)
            .attachedFile(UPDATED_ATTACHED_FILE)
            .attachedFileContentType(UPDATED_ATTACHED_FILE_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(updatedTeachingCurriculum);

        restTeachingCurriculumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teachingCurriculumDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isOk());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeUpdate);
        TeachingCurriculum testTeachingCurriculum = teachingCurriculumList.get(teachingCurriculumList.size() - 1);
        assertThat(testTeachingCurriculum.getBeginningYear()).isEqualTo(UPDATED_BEGINNING_YEAR);
        assertThat(testTeachingCurriculum.getEndYear()).isEqualTo(UPDATED_END_YEAR);
        assertThat(testTeachingCurriculum.getAnnualResult()).isEqualTo(UPDATED_ANNUAL_RESULT);
        assertThat(testTeachingCurriculum.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testTeachingCurriculum.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTeachingCurriculum.getAttachedFile()).isEqualTo(UPDATED_ATTACHED_FILE);
        assertThat(testTeachingCurriculum.getAttachedFileContentType()).isEqualTo(UPDATED_ATTACHED_FILE_CONTENT_TYPE);
        assertThat(testTeachingCurriculum.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingTeachingCurriculum() throws Exception {
        int databaseSizeBeforeUpdate = teachingCurriculumRepository.findAll().size();
        teachingCurriculum.setId(count.incrementAndGet());

        // Create the TeachingCurriculum
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachingCurriculumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teachingCurriculumDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeachingCurriculum() throws Exception {
        int databaseSizeBeforeUpdate = teachingCurriculumRepository.findAll().size();
        teachingCurriculum.setId(count.incrementAndGet());

        // Create the TeachingCurriculum
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachingCurriculumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeachingCurriculum() throws Exception {
        int databaseSizeBeforeUpdate = teachingCurriculumRepository.findAll().size();
        teachingCurriculum.setId(count.incrementAndGet());

        // Create the TeachingCurriculum
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachingCurriculumMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeachingCurriculumWithPatch() throws Exception {
        // Initialize the database
        teachingCurriculumRepository.saveAndFlush(teachingCurriculum);

        int databaseSizeBeforeUpdate = teachingCurriculumRepository.findAll().size();

        // Update the teachingCurriculum using partial update
        TeachingCurriculum partialUpdatedTeachingCurriculum = new TeachingCurriculum();
        partialUpdatedTeachingCurriculum.setId(teachingCurriculum.getId());

        partialUpdatedTeachingCurriculum.beginningYear(UPDATED_BEGINNING_YEAR);

        restTeachingCurriculumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeachingCurriculum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeachingCurriculum))
            )
            .andExpect(status().isOk());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeUpdate);
        TeachingCurriculum testTeachingCurriculum = teachingCurriculumList.get(teachingCurriculumList.size() - 1);
        assertThat(testTeachingCurriculum.getBeginningYear()).isEqualTo(UPDATED_BEGINNING_YEAR);
        assertThat(testTeachingCurriculum.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
        assertThat(testTeachingCurriculum.getAnnualResult()).isEqualTo(DEFAULT_ANNUAL_RESULT);
        assertThat(testTeachingCurriculum.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testTeachingCurriculum.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testTeachingCurriculum.getAttachedFile()).isEqualTo(DEFAULT_ATTACHED_FILE);
        assertThat(testTeachingCurriculum.getAttachedFileContentType()).isEqualTo(DEFAULT_ATTACHED_FILE_CONTENT_TYPE);
        assertThat(testTeachingCurriculum.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateTeachingCurriculumWithPatch() throws Exception {
        // Initialize the database
        teachingCurriculumRepository.saveAndFlush(teachingCurriculum);

        int databaseSizeBeforeUpdate = teachingCurriculumRepository.findAll().size();

        // Update the teachingCurriculum using partial update
        TeachingCurriculum partialUpdatedTeachingCurriculum = new TeachingCurriculum();
        partialUpdatedTeachingCurriculum.setId(teachingCurriculum.getId());

        partialUpdatedTeachingCurriculum
            .beginningYear(UPDATED_BEGINNING_YEAR)
            .endYear(UPDATED_END_YEAR)
            .annualResult(UPDATED_ANNUAL_RESULT)
            .result(UPDATED_RESULT)
            .remarks(UPDATED_REMARKS)
            .attachedFile(UPDATED_ATTACHED_FILE)
            .attachedFileContentType(UPDATED_ATTACHED_FILE_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);

        restTeachingCurriculumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeachingCurriculum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeachingCurriculum))
            )
            .andExpect(status().isOk());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeUpdate);
        TeachingCurriculum testTeachingCurriculum = teachingCurriculumList.get(teachingCurriculumList.size() - 1);
        assertThat(testTeachingCurriculum.getBeginningYear()).isEqualTo(UPDATED_BEGINNING_YEAR);
        assertThat(testTeachingCurriculum.getEndYear()).isEqualTo(UPDATED_END_YEAR);
        assertThat(testTeachingCurriculum.getAnnualResult()).isEqualTo(UPDATED_ANNUAL_RESULT);
        assertThat(testTeachingCurriculum.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testTeachingCurriculum.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testTeachingCurriculum.getAttachedFile()).isEqualTo(UPDATED_ATTACHED_FILE);
        assertThat(testTeachingCurriculum.getAttachedFileContentType()).isEqualTo(UPDATED_ATTACHED_FILE_CONTENT_TYPE);
        assertThat(testTeachingCurriculum.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingTeachingCurriculum() throws Exception {
        int databaseSizeBeforeUpdate = teachingCurriculumRepository.findAll().size();
        teachingCurriculum.setId(count.incrementAndGet());

        // Create the TeachingCurriculum
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachingCurriculumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teachingCurriculumDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeachingCurriculum() throws Exception {
        int databaseSizeBeforeUpdate = teachingCurriculumRepository.findAll().size();
        teachingCurriculum.setId(count.incrementAndGet());

        // Create the TeachingCurriculum
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachingCurriculumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeachingCurriculum() throws Exception {
        int databaseSizeBeforeUpdate = teachingCurriculumRepository.findAll().size();
        teachingCurriculum.setId(count.incrementAndGet());

        // Create the TeachingCurriculum
        TeachingCurriculumDTO teachingCurriculumDTO = teachingCurriculumMapper.toDto(teachingCurriculum);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeachingCurriculumMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teachingCurriculumDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeachingCurriculum in the database
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeachingCurriculum() throws Exception {
        // Initialize the database
        teachingCurriculumRepository.saveAndFlush(teachingCurriculum);

        int databaseSizeBeforeDelete = teachingCurriculumRepository.findAll().size();

        // Delete the teachingCurriculum
        restTeachingCurriculumMockMvc
            .perform(delete(ENTITY_API_URL_ID, teachingCurriculum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeachingCurriculum> teachingCurriculumList = teachingCurriculumRepository.findAll();
        assertThat(teachingCurriculumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
