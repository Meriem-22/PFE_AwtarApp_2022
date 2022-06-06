package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.HealthStatusCategory;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.StatusOfHealth;
import com.awtar.myapp.repository.StatusOfHealthRepository;
import com.awtar.myapp.service.StatusOfHealthService;
import com.awtar.myapp.service.dto.StatusOfHealthDTO;
import com.awtar.myapp.service.mapper.StatusOfHealthMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link StatusOfHealthResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StatusOfHealthResourceIT {

    private static final LocalDate DEFAULT_HEALTH_STATUS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HEALTH_STATUS_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_URL_DETAILS_ATTACHED = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL_DETAILS_ATTACHED = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_DETAILS_ATTACHED_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_DETAILS_ATTACHED_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/status-of-healths";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatusOfHealthRepository statusOfHealthRepository;

    @Mock
    private StatusOfHealthRepository statusOfHealthRepositoryMock;

    @Autowired
    private StatusOfHealthMapper statusOfHealthMapper;

    @Mock
    private StatusOfHealthService statusOfHealthServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusOfHealthMockMvc;

    private StatusOfHealth statusOfHealth;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusOfHealth createEntity(EntityManager em) {
        StatusOfHealth statusOfHealth = new StatusOfHealth()
            .healthStatusDate(DEFAULT_HEALTH_STATUS_DATE)
            .urlDetailsAttached(DEFAULT_URL_DETAILS_ATTACHED)
            .urlDetailsAttachedContentType(DEFAULT_URL_DETAILS_ATTACHED_CONTENT_TYPE)
            .archivated(DEFAULT_ARCHIVATED);
        // Add required entity
        Profile profile;
        if (TestUtil.findAll(em, Profile.class).isEmpty()) {
            profile = ProfileResourceIT.createEntity(em);
            em.persist(profile);
            em.flush();
        } else {
            profile = TestUtil.findAll(em, Profile.class).get(0);
        }
        statusOfHealth.setPerson(profile);
        // Add required entity
        HealthStatusCategory healthStatusCategory;
        if (TestUtil.findAll(em, HealthStatusCategory.class).isEmpty()) {
            healthStatusCategory = HealthStatusCategoryResourceIT.createEntity(em);
            em.persist(healthStatusCategory);
            em.flush();
        } else {
            healthStatusCategory = TestUtil.findAll(em, HealthStatusCategory.class).get(0);
        }
        statusOfHealth.setHealthStatusCategory(healthStatusCategory);
        return statusOfHealth;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusOfHealth createUpdatedEntity(EntityManager em) {
        StatusOfHealth statusOfHealth = new StatusOfHealth()
            .healthStatusDate(UPDATED_HEALTH_STATUS_DATE)
            .urlDetailsAttached(UPDATED_URL_DETAILS_ATTACHED)
            .urlDetailsAttachedContentType(UPDATED_URL_DETAILS_ATTACHED_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);
        // Add required entity
        Profile profile;
        if (TestUtil.findAll(em, Profile.class).isEmpty()) {
            profile = ProfileResourceIT.createUpdatedEntity(em);
            em.persist(profile);
            em.flush();
        } else {
            profile = TestUtil.findAll(em, Profile.class).get(0);
        }
        statusOfHealth.setPerson(profile);
        // Add required entity
        HealthStatusCategory healthStatusCategory;
        if (TestUtil.findAll(em, HealthStatusCategory.class).isEmpty()) {
            healthStatusCategory = HealthStatusCategoryResourceIT.createUpdatedEntity(em);
            em.persist(healthStatusCategory);
            em.flush();
        } else {
            healthStatusCategory = TestUtil.findAll(em, HealthStatusCategory.class).get(0);
        }
        statusOfHealth.setHealthStatusCategory(healthStatusCategory);
        return statusOfHealth;
    }

    @BeforeEach
    public void initTest() {
        statusOfHealth = createEntity(em);
    }

    @Test
    @Transactional
    void createStatusOfHealth() throws Exception {
        int databaseSizeBeforeCreate = statusOfHealthRepository.findAll().size();
        // Create the StatusOfHealth
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(statusOfHealth);
        restStatusOfHealthMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeCreate + 1);
        StatusOfHealth testStatusOfHealth = statusOfHealthList.get(statusOfHealthList.size() - 1);
        assertThat(testStatusOfHealth.getHealthStatusDate()).isEqualTo(DEFAULT_HEALTH_STATUS_DATE);
        assertThat(testStatusOfHealth.getUrlDetailsAttached()).isEqualTo(DEFAULT_URL_DETAILS_ATTACHED);
        assertThat(testStatusOfHealth.getUrlDetailsAttachedContentType()).isEqualTo(DEFAULT_URL_DETAILS_ATTACHED_CONTENT_TYPE);
        assertThat(testStatusOfHealth.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createStatusOfHealthWithExistingId() throws Exception {
        // Create the StatusOfHealth with an existing ID
        statusOfHealth.setId(1L);
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(statusOfHealth);

        int databaseSizeBeforeCreate = statusOfHealthRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusOfHealthMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHealthStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusOfHealthRepository.findAll().size();
        // set the field null
        statusOfHealth.setHealthStatusDate(null);

        // Create the StatusOfHealth, which fails.
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(statusOfHealth);

        restStatusOfHealthMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isBadRequest());

        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStatusOfHealths() throws Exception {
        // Initialize the database
        statusOfHealthRepository.saveAndFlush(statusOfHealth);

        // Get all the statusOfHealthList
        restStatusOfHealthMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusOfHealth.getId().intValue())))
            .andExpect(jsonPath("$.[*].healthStatusDate").value(hasItem(DEFAULT_HEALTH_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].urlDetailsAttachedContentType").value(hasItem(DEFAULT_URL_DETAILS_ATTACHED_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlDetailsAttached").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_DETAILS_ATTACHED))))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStatusOfHealthsWithEagerRelationshipsIsEnabled() throws Exception {
        when(statusOfHealthServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStatusOfHealthMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(statusOfHealthServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStatusOfHealthsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(statusOfHealthServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStatusOfHealthMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(statusOfHealthServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getStatusOfHealth() throws Exception {
        // Initialize the database
        statusOfHealthRepository.saveAndFlush(statusOfHealth);

        // Get the statusOfHealth
        restStatusOfHealthMockMvc
            .perform(get(ENTITY_API_URL_ID, statusOfHealth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusOfHealth.getId().intValue()))
            .andExpect(jsonPath("$.healthStatusDate").value(DEFAULT_HEALTH_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.urlDetailsAttachedContentType").value(DEFAULT_URL_DETAILS_ATTACHED_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlDetailsAttached").value(Base64Utils.encodeToString(DEFAULT_URL_DETAILS_ATTACHED)))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingStatusOfHealth() throws Exception {
        // Get the statusOfHealth
        restStatusOfHealthMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStatusOfHealth() throws Exception {
        // Initialize the database
        statusOfHealthRepository.saveAndFlush(statusOfHealth);

        int databaseSizeBeforeUpdate = statusOfHealthRepository.findAll().size();

        // Update the statusOfHealth
        StatusOfHealth updatedStatusOfHealth = statusOfHealthRepository.findById(statusOfHealth.getId()).get();
        // Disconnect from session so that the updates on updatedStatusOfHealth are not directly saved in db
        em.detach(updatedStatusOfHealth);
        updatedStatusOfHealth
            .healthStatusDate(UPDATED_HEALTH_STATUS_DATE)
            .urlDetailsAttached(UPDATED_URL_DETAILS_ATTACHED)
            .urlDetailsAttachedContentType(UPDATED_URL_DETAILS_ATTACHED_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(updatedStatusOfHealth);

        restStatusOfHealthMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusOfHealthDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isOk());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeUpdate);
        StatusOfHealth testStatusOfHealth = statusOfHealthList.get(statusOfHealthList.size() - 1);
        assertThat(testStatusOfHealth.getHealthStatusDate()).isEqualTo(UPDATED_HEALTH_STATUS_DATE);
        assertThat(testStatusOfHealth.getUrlDetailsAttached()).isEqualTo(UPDATED_URL_DETAILS_ATTACHED);
        assertThat(testStatusOfHealth.getUrlDetailsAttachedContentType()).isEqualTo(UPDATED_URL_DETAILS_ATTACHED_CONTENT_TYPE);
        assertThat(testStatusOfHealth.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingStatusOfHealth() throws Exception {
        int databaseSizeBeforeUpdate = statusOfHealthRepository.findAll().size();
        statusOfHealth.setId(count.incrementAndGet());

        // Create the StatusOfHealth
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(statusOfHealth);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusOfHealthMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statusOfHealthDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatusOfHealth() throws Exception {
        int databaseSizeBeforeUpdate = statusOfHealthRepository.findAll().size();
        statusOfHealth.setId(count.incrementAndGet());

        // Create the StatusOfHealth
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(statusOfHealth);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusOfHealthMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatusOfHealth() throws Exception {
        int databaseSizeBeforeUpdate = statusOfHealthRepository.findAll().size();
        statusOfHealth.setId(count.incrementAndGet());

        // Create the StatusOfHealth
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(statusOfHealth);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusOfHealthMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatusOfHealthWithPatch() throws Exception {
        // Initialize the database
        statusOfHealthRepository.saveAndFlush(statusOfHealth);

        int databaseSizeBeforeUpdate = statusOfHealthRepository.findAll().size();

        // Update the statusOfHealth using partial update
        StatusOfHealth partialUpdatedStatusOfHealth = new StatusOfHealth();
        partialUpdatedStatusOfHealth.setId(statusOfHealth.getId());

        partialUpdatedStatusOfHealth
            .urlDetailsAttached(UPDATED_URL_DETAILS_ATTACHED)
            .urlDetailsAttachedContentType(UPDATED_URL_DETAILS_ATTACHED_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);

        restStatusOfHealthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusOfHealth.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusOfHealth))
            )
            .andExpect(status().isOk());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeUpdate);
        StatusOfHealth testStatusOfHealth = statusOfHealthList.get(statusOfHealthList.size() - 1);
        assertThat(testStatusOfHealth.getHealthStatusDate()).isEqualTo(DEFAULT_HEALTH_STATUS_DATE);
        assertThat(testStatusOfHealth.getUrlDetailsAttached()).isEqualTo(UPDATED_URL_DETAILS_ATTACHED);
        assertThat(testStatusOfHealth.getUrlDetailsAttachedContentType()).isEqualTo(UPDATED_URL_DETAILS_ATTACHED_CONTENT_TYPE);
        assertThat(testStatusOfHealth.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateStatusOfHealthWithPatch() throws Exception {
        // Initialize the database
        statusOfHealthRepository.saveAndFlush(statusOfHealth);

        int databaseSizeBeforeUpdate = statusOfHealthRepository.findAll().size();

        // Update the statusOfHealth using partial update
        StatusOfHealth partialUpdatedStatusOfHealth = new StatusOfHealth();
        partialUpdatedStatusOfHealth.setId(statusOfHealth.getId());

        partialUpdatedStatusOfHealth
            .healthStatusDate(UPDATED_HEALTH_STATUS_DATE)
            .urlDetailsAttached(UPDATED_URL_DETAILS_ATTACHED)
            .urlDetailsAttachedContentType(UPDATED_URL_DETAILS_ATTACHED_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);

        restStatusOfHealthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatusOfHealth.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatusOfHealth))
            )
            .andExpect(status().isOk());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeUpdate);
        StatusOfHealth testStatusOfHealth = statusOfHealthList.get(statusOfHealthList.size() - 1);
        assertThat(testStatusOfHealth.getHealthStatusDate()).isEqualTo(UPDATED_HEALTH_STATUS_DATE);
        assertThat(testStatusOfHealth.getUrlDetailsAttached()).isEqualTo(UPDATED_URL_DETAILS_ATTACHED);
        assertThat(testStatusOfHealth.getUrlDetailsAttachedContentType()).isEqualTo(UPDATED_URL_DETAILS_ATTACHED_CONTENT_TYPE);
        assertThat(testStatusOfHealth.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingStatusOfHealth() throws Exception {
        int databaseSizeBeforeUpdate = statusOfHealthRepository.findAll().size();
        statusOfHealth.setId(count.incrementAndGet());

        // Create the StatusOfHealth
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(statusOfHealth);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusOfHealthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statusOfHealthDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatusOfHealth() throws Exception {
        int databaseSizeBeforeUpdate = statusOfHealthRepository.findAll().size();
        statusOfHealth.setId(count.incrementAndGet());

        // Create the StatusOfHealth
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(statusOfHealth);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusOfHealthMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatusOfHealth() throws Exception {
        int databaseSizeBeforeUpdate = statusOfHealthRepository.findAll().size();
        statusOfHealth.setId(count.incrementAndGet());

        // Create the StatusOfHealth
        StatusOfHealthDTO statusOfHealthDTO = statusOfHealthMapper.toDto(statusOfHealth);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatusOfHealthMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statusOfHealthDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatusOfHealth in the database
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatusOfHealth() throws Exception {
        // Initialize the database
        statusOfHealthRepository.saveAndFlush(statusOfHealth);

        int databaseSizeBeforeDelete = statusOfHealthRepository.findAll().size();

        // Delete the statusOfHealth
        restStatusOfHealthMockMvc
            .perform(delete(ENTITY_API_URL_ID, statusOfHealth.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusOfHealth> statusOfHealthList = statusOfHealthRepository.findAll();
        assertThat(statusOfHealthList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
