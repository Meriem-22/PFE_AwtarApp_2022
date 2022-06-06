package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.DonationsReceived;
import com.awtar.myapp.repository.DonationsReceivedRepository;
import com.awtar.myapp.service.DonationsReceivedService;
import com.awtar.myapp.service.dto.DonationsReceivedDTO;
import com.awtar.myapp.service.mapper.DonationsReceivedMapper;
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
 * Integration tests for the {@link DonationsReceivedResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DonationsReceivedResourceIT {

    private static final byte[] DEFAULT_URL_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_PHOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/donations-receiveds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonationsReceivedRepository donationsReceivedRepository;

    @Mock
    private DonationsReceivedRepository donationsReceivedRepositoryMock;

    @Autowired
    private DonationsReceivedMapper donationsReceivedMapper;

    @Mock
    private DonationsReceivedService donationsReceivedServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonationsReceivedMockMvc;

    private DonationsReceived donationsReceived;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationsReceived createEntity(EntityManager em) {
        DonationsReceived donationsReceived = new DonationsReceived()
            .urlPhoto(DEFAULT_URL_PHOTO)
            .urlPhotoContentType(DEFAULT_URL_PHOTO_CONTENT_TYPE)
            .archivated(DEFAULT_ARCHIVATED);
        // Add required entity
        AuthorizingOfficer authorizingOfficer;
        if (TestUtil.findAll(em, AuthorizingOfficer.class).isEmpty()) {
            authorizingOfficer = AuthorizingOfficerResourceIT.createEntity(em);
            em.persist(authorizingOfficer);
            em.flush();
        } else {
            authorizingOfficer = TestUtil.findAll(em, AuthorizingOfficer.class).get(0);
        }
        donationsReceived.setAuthorizingOfficer(authorizingOfficer);
        return donationsReceived;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationsReceived createUpdatedEntity(EntityManager em) {
        DonationsReceived donationsReceived = new DonationsReceived()
            .urlPhoto(UPDATED_URL_PHOTO)
            .urlPhotoContentType(UPDATED_URL_PHOTO_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);
        // Add required entity
        AuthorizingOfficer authorizingOfficer;
        if (TestUtil.findAll(em, AuthorizingOfficer.class).isEmpty()) {
            authorizingOfficer = AuthorizingOfficerResourceIT.createUpdatedEntity(em);
            em.persist(authorizingOfficer);
            em.flush();
        } else {
            authorizingOfficer = TestUtil.findAll(em, AuthorizingOfficer.class).get(0);
        }
        donationsReceived.setAuthorizingOfficer(authorizingOfficer);
        return donationsReceived;
    }

    @BeforeEach
    public void initTest() {
        donationsReceived = createEntity(em);
    }

    @Test
    @Transactional
    void createDonationsReceived() throws Exception {
        int databaseSizeBeforeCreate = donationsReceivedRepository.findAll().size();
        // Create the DonationsReceived
        DonationsReceivedDTO donationsReceivedDTO = donationsReceivedMapper.toDto(donationsReceived);
        restDonationsReceivedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeCreate + 1);
        DonationsReceived testDonationsReceived = donationsReceivedList.get(donationsReceivedList.size() - 1);
        assertThat(testDonationsReceived.getUrlPhoto()).isEqualTo(DEFAULT_URL_PHOTO);
        assertThat(testDonationsReceived.getUrlPhotoContentType()).isEqualTo(DEFAULT_URL_PHOTO_CONTENT_TYPE);
        assertThat(testDonationsReceived.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createDonationsReceivedWithExistingId() throws Exception {
        // Create the DonationsReceived with an existing ID
        donationsReceived.setId(1L);
        DonationsReceivedDTO donationsReceivedDTO = donationsReceivedMapper.toDto(donationsReceived);

        int databaseSizeBeforeCreate = donationsReceivedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonationsReceivedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDonationsReceiveds() throws Exception {
        // Initialize the database
        donationsReceivedRepository.saveAndFlush(donationsReceived);

        // Get all the donationsReceivedList
        restDonationsReceivedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donationsReceived.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlPhotoContentType").value(hasItem(DEFAULT_URL_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_PHOTO))))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonationsReceivedsWithEagerRelationshipsIsEnabled() throws Exception {
        when(donationsReceivedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonationsReceivedMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(donationsReceivedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonationsReceivedsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(donationsReceivedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonationsReceivedMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(donationsReceivedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDonationsReceived() throws Exception {
        // Initialize the database
        donationsReceivedRepository.saveAndFlush(donationsReceived);

        // Get the donationsReceived
        restDonationsReceivedMockMvc
            .perform(get(ENTITY_API_URL_ID, donationsReceived.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donationsReceived.getId().intValue()))
            .andExpect(jsonPath("$.urlPhotoContentType").value(DEFAULT_URL_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlPhoto").value(Base64Utils.encodeToString(DEFAULT_URL_PHOTO)))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDonationsReceived() throws Exception {
        // Get the donationsReceived
        restDonationsReceivedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDonationsReceived() throws Exception {
        // Initialize the database
        donationsReceivedRepository.saveAndFlush(donationsReceived);

        int databaseSizeBeforeUpdate = donationsReceivedRepository.findAll().size();

        // Update the donationsReceived
        DonationsReceived updatedDonationsReceived = donationsReceivedRepository.findById(donationsReceived.getId()).get();
        // Disconnect from session so that the updates on updatedDonationsReceived are not directly saved in db
        em.detach(updatedDonationsReceived);
        updatedDonationsReceived
            .urlPhoto(UPDATED_URL_PHOTO)
            .urlPhotoContentType(UPDATED_URL_PHOTO_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);
        DonationsReceivedDTO donationsReceivedDTO = donationsReceivedMapper.toDto(updatedDonationsReceived);

        restDonationsReceivedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationsReceivedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedDTO))
            )
            .andExpect(status().isOk());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeUpdate);
        DonationsReceived testDonationsReceived = donationsReceivedList.get(donationsReceivedList.size() - 1);
        assertThat(testDonationsReceived.getUrlPhoto()).isEqualTo(UPDATED_URL_PHOTO);
        assertThat(testDonationsReceived.getUrlPhotoContentType()).isEqualTo(UPDATED_URL_PHOTO_CONTENT_TYPE);
        assertThat(testDonationsReceived.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingDonationsReceived() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedRepository.findAll().size();
        donationsReceived.setId(count.incrementAndGet());

        // Create the DonationsReceived
        DonationsReceivedDTO donationsReceivedDTO = donationsReceivedMapper.toDto(donationsReceived);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationsReceivedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationsReceivedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonationsReceived() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedRepository.findAll().size();
        donationsReceived.setId(count.incrementAndGet());

        // Create the DonationsReceived
        DonationsReceivedDTO donationsReceivedDTO = donationsReceivedMapper.toDto(donationsReceived);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsReceivedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonationsReceived() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedRepository.findAll().size();
        donationsReceived.setId(count.incrementAndGet());

        // Create the DonationsReceived
        DonationsReceivedDTO donationsReceivedDTO = donationsReceivedMapper.toDto(donationsReceived);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsReceivedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donationsReceivedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonationsReceivedWithPatch() throws Exception {
        // Initialize the database
        donationsReceivedRepository.saveAndFlush(donationsReceived);

        int databaseSizeBeforeUpdate = donationsReceivedRepository.findAll().size();

        // Update the donationsReceived using partial update
        DonationsReceived partialUpdatedDonationsReceived = new DonationsReceived();
        partialUpdatedDonationsReceived.setId(donationsReceived.getId());

        partialUpdatedDonationsReceived.archivated(UPDATED_ARCHIVATED);

        restDonationsReceivedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationsReceived.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationsReceived))
            )
            .andExpect(status().isOk());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeUpdate);
        DonationsReceived testDonationsReceived = donationsReceivedList.get(donationsReceivedList.size() - 1);
        assertThat(testDonationsReceived.getUrlPhoto()).isEqualTo(DEFAULT_URL_PHOTO);
        assertThat(testDonationsReceived.getUrlPhotoContentType()).isEqualTo(DEFAULT_URL_PHOTO_CONTENT_TYPE);
        assertThat(testDonationsReceived.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateDonationsReceivedWithPatch() throws Exception {
        // Initialize the database
        donationsReceivedRepository.saveAndFlush(donationsReceived);

        int databaseSizeBeforeUpdate = donationsReceivedRepository.findAll().size();

        // Update the donationsReceived using partial update
        DonationsReceived partialUpdatedDonationsReceived = new DonationsReceived();
        partialUpdatedDonationsReceived.setId(donationsReceived.getId());

        partialUpdatedDonationsReceived
            .urlPhoto(UPDATED_URL_PHOTO)
            .urlPhotoContentType(UPDATED_URL_PHOTO_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);

        restDonationsReceivedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationsReceived.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationsReceived))
            )
            .andExpect(status().isOk());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeUpdate);
        DonationsReceived testDonationsReceived = donationsReceivedList.get(donationsReceivedList.size() - 1);
        assertThat(testDonationsReceived.getUrlPhoto()).isEqualTo(UPDATED_URL_PHOTO);
        assertThat(testDonationsReceived.getUrlPhotoContentType()).isEqualTo(UPDATED_URL_PHOTO_CONTENT_TYPE);
        assertThat(testDonationsReceived.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingDonationsReceived() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedRepository.findAll().size();
        donationsReceived.setId(count.incrementAndGet());

        // Create the DonationsReceived
        DonationsReceivedDTO donationsReceivedDTO = donationsReceivedMapper.toDto(donationsReceived);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationsReceivedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donationsReceivedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonationsReceived() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedRepository.findAll().size();
        donationsReceived.setId(count.incrementAndGet());

        // Create the DonationsReceived
        DonationsReceivedDTO donationsReceivedDTO = donationsReceivedMapper.toDto(donationsReceived);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsReceivedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonationsReceived() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedRepository.findAll().size();
        donationsReceived.setId(count.incrementAndGet());

        // Create the DonationsReceived
        DonationsReceivedDTO donationsReceivedDTO = donationsReceivedMapper.toDto(donationsReceived);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsReceivedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationsReceived in the database
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonationsReceived() throws Exception {
        // Initialize the database
        donationsReceivedRepository.saveAndFlush(donationsReceived);

        int databaseSizeBeforeDelete = donationsReceivedRepository.findAll().size();

        // Delete the donationsReceived
        restDonationsReceivedMockMvc
            .perform(delete(ENTITY_API_URL_ID, donationsReceived.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonationsReceived> donationsReceivedList = donationsReceivedRepository.findAll();
        assertThat(donationsReceivedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
