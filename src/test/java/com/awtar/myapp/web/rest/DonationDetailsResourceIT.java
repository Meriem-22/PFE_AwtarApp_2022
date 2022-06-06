package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.repository.DonationDetailsRepository;
import com.awtar.myapp.service.DonationDetailsService;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
import com.awtar.myapp.service.mapper.DonationDetailsMapper;
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
 * Integration tests for the {@link DonationDetailsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DonationDetailsResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/donation-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonationDetailsRepository donationDetailsRepository;

    @Mock
    private DonationDetailsRepository donationDetailsRepositoryMock;

    @Autowired
    private DonationDetailsMapper donationDetailsMapper;

    @Mock
    private DonationDetailsService donationDetailsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonationDetailsMockMvc;

    private DonationDetails donationDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationDetails createEntity(EntityManager em) {
        DonationDetails donationDetails = new DonationDetails().description(DEFAULT_DESCRIPTION).archivated(DEFAULT_ARCHIVATED);
        // Add required entity
        DonationsIssued donationsIssued;
        if (TestUtil.findAll(em, DonationsIssued.class).isEmpty()) {
            donationsIssued = DonationsIssuedResourceIT.createEntity(em);
            em.persist(donationsIssued);
            em.flush();
        } else {
            donationsIssued = TestUtil.findAll(em, DonationsIssued.class).get(0);
        }
        donationDetails.setDonationsIssued(donationsIssued);
        // Add required entity
        Nature nature;
        if (TestUtil.findAll(em, Nature.class).isEmpty()) {
            nature = NatureResourceIT.createEntity(em);
            em.persist(nature);
            em.flush();
        } else {
            nature = TestUtil.findAll(em, Nature.class).get(0);
        }
        donationDetails.setNature(nature);
        // Add required entity
        Beneficiary beneficiary;
        if (TestUtil.findAll(em, Beneficiary.class).isEmpty()) {
            beneficiary = BeneficiaryResourceIT.createEntity(em);
            em.persist(beneficiary);
            em.flush();
        } else {
            beneficiary = TestUtil.findAll(em, Beneficiary.class).get(0);
        }
        donationDetails.setBeneficiary(beneficiary);
        return donationDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationDetails createUpdatedEntity(EntityManager em) {
        DonationDetails donationDetails = new DonationDetails().description(UPDATED_DESCRIPTION).archivated(UPDATED_ARCHIVATED);
        // Add required entity
        DonationsIssued donationsIssued;
        if (TestUtil.findAll(em, DonationsIssued.class).isEmpty()) {
            donationsIssued = DonationsIssuedResourceIT.createUpdatedEntity(em);
            em.persist(donationsIssued);
            em.flush();
        } else {
            donationsIssued = TestUtil.findAll(em, DonationsIssued.class).get(0);
        }
        donationDetails.setDonationsIssued(donationsIssued);
        // Add required entity
        Nature nature;
        if (TestUtil.findAll(em, Nature.class).isEmpty()) {
            nature = NatureResourceIT.createUpdatedEntity(em);
            em.persist(nature);
            em.flush();
        } else {
            nature = TestUtil.findAll(em, Nature.class).get(0);
        }
        donationDetails.setNature(nature);
        // Add required entity
        Beneficiary beneficiary;
        if (TestUtil.findAll(em, Beneficiary.class).isEmpty()) {
            beneficiary = BeneficiaryResourceIT.createUpdatedEntity(em);
            em.persist(beneficiary);
            em.flush();
        } else {
            beneficiary = TestUtil.findAll(em, Beneficiary.class).get(0);
        }
        donationDetails.setBeneficiary(beneficiary);
        return donationDetails;
    }

    @BeforeEach
    public void initTest() {
        donationDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createDonationDetails() throws Exception {
        int databaseSizeBeforeCreate = donationDetailsRepository.findAll().size();
        // Create the DonationDetails
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(donationDetails);
        restDonationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DonationDetails testDonationDetails = donationDetailsList.get(donationDetailsList.size() - 1);
        assertThat(testDonationDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDonationDetails.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createDonationDetailsWithExistingId() throws Exception {
        // Create the DonationDetails with an existing ID
        donationDetails.setId(1L);
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(donationDetails);

        int databaseSizeBeforeCreate = donationDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationDetailsRepository.findAll().size();
        // set the field null
        donationDetails.setDescription(null);

        // Create the DonationDetails, which fails.
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(donationDetails);

        restDonationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDonationDetails() throws Exception {
        // Initialize the database
        donationDetailsRepository.saveAndFlush(donationDetails);

        // Get all the donationDetailsList
        restDonationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonationDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(donationDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonationDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(donationDetailsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonationDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(donationDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonationDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(donationDetailsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDonationDetails() throws Exception {
        // Initialize the database
        donationDetailsRepository.saveAndFlush(donationDetails);

        // Get the donationDetails
        restDonationDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, donationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donationDetails.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDonationDetails() throws Exception {
        // Get the donationDetails
        restDonationDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDonationDetails() throws Exception {
        // Initialize the database
        donationDetailsRepository.saveAndFlush(donationDetails);

        int databaseSizeBeforeUpdate = donationDetailsRepository.findAll().size();

        // Update the donationDetails
        DonationDetails updatedDonationDetails = donationDetailsRepository.findById(donationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedDonationDetails are not directly saved in db
        em.detach(updatedDonationDetails);
        updatedDonationDetails.description(UPDATED_DESCRIPTION).archivated(UPDATED_ARCHIVATED);
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(updatedDonationDetails);

        restDonationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeUpdate);
        DonationDetails testDonationDetails = donationDetailsList.get(donationDetailsList.size() - 1);
        assertThat(testDonationDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDonationDetails.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingDonationDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationDetailsRepository.findAll().size();
        donationDetails.setId(count.incrementAndGet());

        // Create the DonationDetails
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(donationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonationDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationDetailsRepository.findAll().size();
        donationDetails.setId(count.incrementAndGet());

        // Create the DonationDetails
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(donationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonationDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationDetailsRepository.findAll().size();
        donationDetails.setId(count.incrementAndGet());

        // Create the DonationDetails
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(donationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonationDetailsWithPatch() throws Exception {
        // Initialize the database
        donationDetailsRepository.saveAndFlush(donationDetails);

        int databaseSizeBeforeUpdate = donationDetailsRepository.findAll().size();

        // Update the donationDetails using partial update
        DonationDetails partialUpdatedDonationDetails = new DonationDetails();
        partialUpdatedDonationDetails.setId(donationDetails.getId());

        partialUpdatedDonationDetails.description(UPDATED_DESCRIPTION).archivated(UPDATED_ARCHIVATED);

        restDonationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationDetails))
            )
            .andExpect(status().isOk());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeUpdate);
        DonationDetails testDonationDetails = donationDetailsList.get(donationDetailsList.size() - 1);
        assertThat(testDonationDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDonationDetails.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateDonationDetailsWithPatch() throws Exception {
        // Initialize the database
        donationDetailsRepository.saveAndFlush(donationDetails);

        int databaseSizeBeforeUpdate = donationDetailsRepository.findAll().size();

        // Update the donationDetails using partial update
        DonationDetails partialUpdatedDonationDetails = new DonationDetails();
        partialUpdatedDonationDetails.setId(donationDetails.getId());

        partialUpdatedDonationDetails.description(UPDATED_DESCRIPTION).archivated(UPDATED_ARCHIVATED);

        restDonationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationDetails))
            )
            .andExpect(status().isOk());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeUpdate);
        DonationDetails testDonationDetails = donationDetailsList.get(donationDetailsList.size() - 1);
        assertThat(testDonationDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDonationDetails.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingDonationDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationDetailsRepository.findAll().size();
        donationDetails.setId(count.incrementAndGet());

        // Create the DonationDetails
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(donationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donationDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonationDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationDetailsRepository.findAll().size();
        donationDetails.setId(count.incrementAndGet());

        // Create the DonationDetails
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(donationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonationDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationDetailsRepository.findAll().size();
        donationDetails.setId(count.incrementAndGet());

        // Create the DonationDetails
        DonationDetailsDTO donationDetailsDTO = donationDetailsMapper.toDto(donationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationDetails in the database
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonationDetails() throws Exception {
        // Initialize the database
        donationDetailsRepository.saveAndFlush(donationDetails);

        int databaseSizeBeforeDelete = donationDetailsRepository.findAll().size();

        // Delete the donationDetails
        restDonationDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, donationDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonationDetails> donationDetailsList = donationDetailsRepository.findAll();
        assertThat(donationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
