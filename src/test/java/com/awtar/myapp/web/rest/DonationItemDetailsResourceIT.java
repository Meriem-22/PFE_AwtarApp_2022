package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.domain.DonationItemDetails;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.repository.DonationItemDetailsRepository;
import com.awtar.myapp.service.DonationItemDetailsService;
import com.awtar.myapp.service.dto.DonationItemDetailsDTO;
import com.awtar.myapp.service.mapper.DonationItemDetailsMapper;
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

/**
 * Integration tests for the {@link DonationItemDetailsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DonationItemDetailsResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/donation-item-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonationItemDetailsRepository donationItemDetailsRepository;

    @Mock
    private DonationItemDetailsRepository donationItemDetailsRepositoryMock;

    @Autowired
    private DonationItemDetailsMapper donationItemDetailsMapper;

    @Mock
    private DonationItemDetailsService donationItemDetailsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonationItemDetailsMockMvc;

    private DonationItemDetails donationItemDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationItemDetails createEntity(EntityManager em) {
        DonationItemDetails donationItemDetails = new DonationItemDetails()
            .quantity(DEFAULT_QUANTITY)
            .date(DEFAULT_DATE)
            .archivated(DEFAULT_ARCHIVATED);
        // Add required entity
        DonationDetails donationDetails;
        if (TestUtil.findAll(em, DonationDetails.class).isEmpty()) {
            donationDetails = DonationDetailsResourceIT.createEntity(em);
            em.persist(donationDetails);
            em.flush();
        } else {
            donationDetails = TestUtil.findAll(em, DonationDetails.class).get(0);
        }
        donationItemDetails.setDonationDetails(donationDetails);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        donationItemDetails.setItem(item);
        return donationItemDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationItemDetails createUpdatedEntity(EntityManager em) {
        DonationItemDetails donationItemDetails = new DonationItemDetails()
            .quantity(UPDATED_QUANTITY)
            .date(UPDATED_DATE)
            .archivated(UPDATED_ARCHIVATED);
        // Add required entity
        DonationDetails donationDetails;
        if (TestUtil.findAll(em, DonationDetails.class).isEmpty()) {
            donationDetails = DonationDetailsResourceIT.createUpdatedEntity(em);
            em.persist(donationDetails);
            em.flush();
        } else {
            donationDetails = TestUtil.findAll(em, DonationDetails.class).get(0);
        }
        donationItemDetails.setDonationDetails(donationDetails);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createUpdatedEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        donationItemDetails.setItem(item);
        return donationItemDetails;
    }

    @BeforeEach
    public void initTest() {
        donationItemDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createDonationItemDetails() throws Exception {
        int databaseSizeBeforeCreate = donationItemDetailsRepository.findAll().size();
        // Create the DonationItemDetails
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);
        restDonationItemDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DonationItemDetails testDonationItemDetails = donationItemDetailsList.get(donationItemDetailsList.size() - 1);
        assertThat(testDonationItemDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDonationItemDetails.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDonationItemDetails.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createDonationItemDetailsWithExistingId() throws Exception {
        // Create the DonationItemDetails with an existing ID
        donationItemDetails.setId(1L);
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);

        int databaseSizeBeforeCreate = donationItemDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonationItemDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationItemDetailsRepository.findAll().size();
        // set the field null
        donationItemDetails.setQuantity(null);

        // Create the DonationItemDetails, which fails.
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);

        restDonationItemDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationItemDetailsRepository.findAll().size();
        // set the field null
        donationItemDetails.setDate(null);

        // Create the DonationItemDetails, which fails.
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);

        restDonationItemDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDonationItemDetails() throws Exception {
        // Initialize the database
        donationItemDetailsRepository.saveAndFlush(donationItemDetails);

        // Get all the donationItemDetailsList
        restDonationItemDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donationItemDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonationItemDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(donationItemDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonationItemDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(donationItemDetailsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonationItemDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(donationItemDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonationItemDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(donationItemDetailsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDonationItemDetails() throws Exception {
        // Initialize the database
        donationItemDetailsRepository.saveAndFlush(donationItemDetails);

        // Get the donationItemDetails
        restDonationItemDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, donationItemDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donationItemDetails.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDonationItemDetails() throws Exception {
        // Get the donationItemDetails
        restDonationItemDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDonationItemDetails() throws Exception {
        // Initialize the database
        donationItemDetailsRepository.saveAndFlush(donationItemDetails);

        int databaseSizeBeforeUpdate = donationItemDetailsRepository.findAll().size();

        // Update the donationItemDetails
        DonationItemDetails updatedDonationItemDetails = donationItemDetailsRepository.findById(donationItemDetails.getId()).get();
        // Disconnect from session so that the updates on updatedDonationItemDetails are not directly saved in db
        em.detach(updatedDonationItemDetails);
        updatedDonationItemDetails.quantity(UPDATED_QUANTITY).date(UPDATED_DATE).archivated(UPDATED_ARCHIVATED);
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(updatedDonationItemDetails);

        restDonationItemDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationItemDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeUpdate);
        DonationItemDetails testDonationItemDetails = donationItemDetailsList.get(donationItemDetailsList.size() - 1);
        assertThat(testDonationItemDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDonationItemDetails.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDonationItemDetails.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingDonationItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationItemDetailsRepository.findAll().size();
        donationItemDetails.setId(count.incrementAndGet());

        // Create the DonationItemDetails
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationItemDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationItemDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonationItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationItemDetailsRepository.findAll().size();
        donationItemDetails.setId(count.incrementAndGet());

        // Create the DonationItemDetails
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationItemDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonationItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationItemDetailsRepository.findAll().size();
        donationItemDetails.setId(count.incrementAndGet());

        // Create the DonationItemDetails
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationItemDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonationItemDetailsWithPatch() throws Exception {
        // Initialize the database
        donationItemDetailsRepository.saveAndFlush(donationItemDetails);

        int databaseSizeBeforeUpdate = donationItemDetailsRepository.findAll().size();

        // Update the donationItemDetails using partial update
        DonationItemDetails partialUpdatedDonationItemDetails = new DonationItemDetails();
        partialUpdatedDonationItemDetails.setId(donationItemDetails.getId());

        partialUpdatedDonationItemDetails.quantity(UPDATED_QUANTITY);

        restDonationItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationItemDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationItemDetails))
            )
            .andExpect(status().isOk());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeUpdate);
        DonationItemDetails testDonationItemDetails = donationItemDetailsList.get(donationItemDetailsList.size() - 1);
        assertThat(testDonationItemDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDonationItemDetails.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDonationItemDetails.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateDonationItemDetailsWithPatch() throws Exception {
        // Initialize the database
        donationItemDetailsRepository.saveAndFlush(donationItemDetails);

        int databaseSizeBeforeUpdate = donationItemDetailsRepository.findAll().size();

        // Update the donationItemDetails using partial update
        DonationItemDetails partialUpdatedDonationItemDetails = new DonationItemDetails();
        partialUpdatedDonationItemDetails.setId(donationItemDetails.getId());

        partialUpdatedDonationItemDetails.quantity(UPDATED_QUANTITY).date(UPDATED_DATE).archivated(UPDATED_ARCHIVATED);

        restDonationItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationItemDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationItemDetails))
            )
            .andExpect(status().isOk());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeUpdate);
        DonationItemDetails testDonationItemDetails = donationItemDetailsList.get(donationItemDetailsList.size() - 1);
        assertThat(testDonationItemDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDonationItemDetails.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDonationItemDetails.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingDonationItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationItemDetailsRepository.findAll().size();
        donationItemDetails.setId(count.incrementAndGet());

        // Create the DonationItemDetails
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donationItemDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonationItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationItemDetailsRepository.findAll().size();
        donationItemDetails.setId(count.incrementAndGet());

        // Create the DonationItemDetails
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonationItemDetails() throws Exception {
        int databaseSizeBeforeUpdate = donationItemDetailsRepository.findAll().size();
        donationItemDetails.setId(count.incrementAndGet());

        // Create the DonationItemDetails
        DonationItemDetailsDTO donationItemDetailsDTO = donationItemDetailsMapper.toDto(donationItemDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationItemDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationItemDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationItemDetails in the database
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonationItemDetails() throws Exception {
        // Initialize the database
        donationItemDetailsRepository.saveAndFlush(donationItemDetails);

        int databaseSizeBeforeDelete = donationItemDetailsRepository.findAll().size();

        // Delete the donationItemDetails
        restDonationItemDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, donationItemDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonationItemDetails> donationItemDetailsList = donationItemDetailsRepository.findAll();
        assertThat(donationItemDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
