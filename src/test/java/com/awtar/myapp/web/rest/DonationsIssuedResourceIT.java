package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.domain.enumeration.Period;
import com.awtar.myapp.repository.DonationsIssuedRepository;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import com.awtar.myapp.service.mapper.DonationsIssuedMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DonationsIssuedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DonationsIssuedResourceIT {

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VALIDATED = false;
    private static final Boolean UPDATED_IS_VALIDATED = true;

    private static final LocalDate DEFAULT_VALIDATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_VALIDATION_USER = 1L;
    private static final Long UPDATED_VALIDATION_USER = 2L;

    private static final LocalDate DEFAULT_DONATIONS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DONATIONS_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_CANCELED_DONATIONS = false;
    private static final Boolean UPDATED_CANCELED_DONATIONS = true;

    private static final LocalDate DEFAULT_CANCELED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CANCELED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CANCELED_BY = 1L;
    private static final Long UPDATED_CANCELED_BY = 2L;

    private static final String DEFAULT_CANCELLATION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION_REASON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RECURRING_DONATIONS = false;
    private static final Boolean UPDATED_RECURRING_DONATIONS = true;

    private static final Period DEFAULT_PERIODICITY = Period.PER_WEEK;
    private static final Period UPDATED_PERIODICITY = Period.PER_MONTH;

    private static final Integer DEFAULT_RECURRENCE = 1;
    private static final Integer UPDATED_RECURRENCE = 2;

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/donations-issueds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonationsIssuedRepository donationsIssuedRepository;

    @Autowired
    private DonationsIssuedMapper donationsIssuedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonationsIssuedMockMvc;

    private DonationsIssued donationsIssued;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationsIssued createEntity(EntityManager em) {
        DonationsIssued donationsIssued = new DonationsIssued()
            .model(DEFAULT_MODEL)
            .isValidated(DEFAULT_IS_VALIDATED)
            .validationDate(DEFAULT_VALIDATION_DATE)
            .validationUser(DEFAULT_VALIDATION_USER)
            .donationsDate(DEFAULT_DONATIONS_DATE)
            .canceledDonations(DEFAULT_CANCELED_DONATIONS)
            .canceledOn(DEFAULT_CANCELED_ON)
            .canceledBy(DEFAULT_CANCELED_BY)
            .cancellationReason(DEFAULT_CANCELLATION_REASON)
            .recurringDonations(DEFAULT_RECURRING_DONATIONS)
            .periodicity(DEFAULT_PERIODICITY)
            .recurrence(DEFAULT_RECURRENCE)
            .archivated(DEFAULT_ARCHIVATED);
        return donationsIssued;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationsIssued createUpdatedEntity(EntityManager em) {
        DonationsIssued donationsIssued = new DonationsIssued()
            .model(UPDATED_MODEL)
            .isValidated(UPDATED_IS_VALIDATED)
            .validationDate(UPDATED_VALIDATION_DATE)
            .validationUser(UPDATED_VALIDATION_USER)
            .donationsDate(UPDATED_DONATIONS_DATE)
            .canceledDonations(UPDATED_CANCELED_DONATIONS)
            .canceledOn(UPDATED_CANCELED_ON)
            .canceledBy(UPDATED_CANCELED_BY)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .recurringDonations(UPDATED_RECURRING_DONATIONS)
            .periodicity(UPDATED_PERIODICITY)
            .recurrence(UPDATED_RECURRENCE)
            .archivated(UPDATED_ARCHIVATED);
        return donationsIssued;
    }

    @BeforeEach
    public void initTest() {
        donationsIssued = createEntity(em);
    }

    @Test
    @Transactional
    void createDonationsIssued() throws Exception {
        int databaseSizeBeforeCreate = donationsIssuedRepository.findAll().size();
        // Create the DonationsIssued
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(donationsIssued);
        restDonationsIssuedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeCreate + 1);
        DonationsIssued testDonationsIssued = donationsIssuedList.get(donationsIssuedList.size() - 1);
        assertThat(testDonationsIssued.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testDonationsIssued.getIsValidated()).isEqualTo(DEFAULT_IS_VALIDATED);
        assertThat(testDonationsIssued.getValidationDate()).isEqualTo(DEFAULT_VALIDATION_DATE);
        assertThat(testDonationsIssued.getValidationUser()).isEqualTo(DEFAULT_VALIDATION_USER);
        assertThat(testDonationsIssued.getDonationsDate()).isEqualTo(DEFAULT_DONATIONS_DATE);
        assertThat(testDonationsIssued.getCanceledDonations()).isEqualTo(DEFAULT_CANCELED_DONATIONS);
        assertThat(testDonationsIssued.getCanceledOn()).isEqualTo(DEFAULT_CANCELED_ON);
        assertThat(testDonationsIssued.getCanceledBy()).isEqualTo(DEFAULT_CANCELED_BY);
        assertThat(testDonationsIssued.getCancellationReason()).isEqualTo(DEFAULT_CANCELLATION_REASON);
        assertThat(testDonationsIssued.getRecurringDonations()).isEqualTo(DEFAULT_RECURRING_DONATIONS);
        assertThat(testDonationsIssued.getPeriodicity()).isEqualTo(DEFAULT_PERIODICITY);
        assertThat(testDonationsIssued.getRecurrence()).isEqualTo(DEFAULT_RECURRENCE);
        assertThat(testDonationsIssued.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createDonationsIssuedWithExistingId() throws Exception {
        // Create the DonationsIssued with an existing ID
        donationsIssued.setId(1L);
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(donationsIssued);

        int databaseSizeBeforeCreate = donationsIssuedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonationsIssuedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationsIssuedRepository.findAll().size();
        // set the field null
        donationsIssued.setModel(null);

        // Create the DonationsIssued, which fails.
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(donationsIssued);

        restDonationsIssuedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isBadRequest());

        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDonationsIssueds() throws Exception {
        // Initialize the database
        donationsIssuedRepository.saveAndFlush(donationsIssued);

        // Get all the donationsIssuedList
        restDonationsIssuedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donationsIssued.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].isValidated").value(hasItem(DEFAULT_IS_VALIDATED.booleanValue())))
            .andExpect(jsonPath("$.[*].validationDate").value(hasItem(DEFAULT_VALIDATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].validationUser").value(hasItem(DEFAULT_VALIDATION_USER.intValue())))
            .andExpect(jsonPath("$.[*].donationsDate").value(hasItem(DEFAULT_DONATIONS_DATE.toString())))
            .andExpect(jsonPath("$.[*].canceledDonations").value(hasItem(DEFAULT_CANCELED_DONATIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].canceledOn").value(hasItem(DEFAULT_CANCELED_ON.toString())))
            .andExpect(jsonPath("$.[*].canceledBy").value(hasItem(DEFAULT_CANCELED_BY.intValue())))
            .andExpect(jsonPath("$.[*].cancellationReason").value(hasItem(DEFAULT_CANCELLATION_REASON)))
            .andExpect(jsonPath("$.[*].recurringDonations").value(hasItem(DEFAULT_RECURRING_DONATIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].periodicity").value(hasItem(DEFAULT_PERIODICITY.toString())))
            .andExpect(jsonPath("$.[*].recurrence").value(hasItem(DEFAULT_RECURRENCE)))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @Test
    @Transactional
    void getDonationsIssued() throws Exception {
        // Initialize the database
        donationsIssuedRepository.saveAndFlush(donationsIssued);

        // Get the donationsIssued
        restDonationsIssuedMockMvc
            .perform(get(ENTITY_API_URL_ID, donationsIssued.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donationsIssued.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.isValidated").value(DEFAULT_IS_VALIDATED.booleanValue()))
            .andExpect(jsonPath("$.validationDate").value(DEFAULT_VALIDATION_DATE.toString()))
            .andExpect(jsonPath("$.validationUser").value(DEFAULT_VALIDATION_USER.intValue()))
            .andExpect(jsonPath("$.donationsDate").value(DEFAULT_DONATIONS_DATE.toString()))
            .andExpect(jsonPath("$.canceledDonations").value(DEFAULT_CANCELED_DONATIONS.booleanValue()))
            .andExpect(jsonPath("$.canceledOn").value(DEFAULT_CANCELED_ON.toString()))
            .andExpect(jsonPath("$.canceledBy").value(DEFAULT_CANCELED_BY.intValue()))
            .andExpect(jsonPath("$.cancellationReason").value(DEFAULT_CANCELLATION_REASON))
            .andExpect(jsonPath("$.recurringDonations").value(DEFAULT_RECURRING_DONATIONS.booleanValue()))
            .andExpect(jsonPath("$.periodicity").value(DEFAULT_PERIODICITY.toString()))
            .andExpect(jsonPath("$.recurrence").value(DEFAULT_RECURRENCE))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDonationsIssued() throws Exception {
        // Get the donationsIssued
        restDonationsIssuedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDonationsIssued() throws Exception {
        // Initialize the database
        donationsIssuedRepository.saveAndFlush(donationsIssued);

        int databaseSizeBeforeUpdate = donationsIssuedRepository.findAll().size();

        // Update the donationsIssued
        DonationsIssued updatedDonationsIssued = donationsIssuedRepository.findById(donationsIssued.getId()).get();
        // Disconnect from session so that the updates on updatedDonationsIssued are not directly saved in db
        em.detach(updatedDonationsIssued);
        updatedDonationsIssued
            .model(UPDATED_MODEL)
            .isValidated(UPDATED_IS_VALIDATED)
            .validationDate(UPDATED_VALIDATION_DATE)
            .validationUser(UPDATED_VALIDATION_USER)
            .donationsDate(UPDATED_DONATIONS_DATE)
            .canceledDonations(UPDATED_CANCELED_DONATIONS)
            .canceledOn(UPDATED_CANCELED_ON)
            .canceledBy(UPDATED_CANCELED_BY)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .recurringDonations(UPDATED_RECURRING_DONATIONS)
            .periodicity(UPDATED_PERIODICITY)
            .recurrence(UPDATED_RECURRENCE)
            .archivated(UPDATED_ARCHIVATED);
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(updatedDonationsIssued);

        restDonationsIssuedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationsIssuedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isOk());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeUpdate);
        DonationsIssued testDonationsIssued = donationsIssuedList.get(donationsIssuedList.size() - 1);
        assertThat(testDonationsIssued.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testDonationsIssued.getIsValidated()).isEqualTo(UPDATED_IS_VALIDATED);
        assertThat(testDonationsIssued.getValidationDate()).isEqualTo(UPDATED_VALIDATION_DATE);
        assertThat(testDonationsIssued.getValidationUser()).isEqualTo(UPDATED_VALIDATION_USER);
        assertThat(testDonationsIssued.getDonationsDate()).isEqualTo(UPDATED_DONATIONS_DATE);
        assertThat(testDonationsIssued.getCanceledDonations()).isEqualTo(UPDATED_CANCELED_DONATIONS);
        assertThat(testDonationsIssued.getCanceledOn()).isEqualTo(UPDATED_CANCELED_ON);
        assertThat(testDonationsIssued.getCanceledBy()).isEqualTo(UPDATED_CANCELED_BY);
        assertThat(testDonationsIssued.getCancellationReason()).isEqualTo(UPDATED_CANCELLATION_REASON);
        assertThat(testDonationsIssued.getRecurringDonations()).isEqualTo(UPDATED_RECURRING_DONATIONS);
        assertThat(testDonationsIssued.getPeriodicity()).isEqualTo(UPDATED_PERIODICITY);
        assertThat(testDonationsIssued.getRecurrence()).isEqualTo(UPDATED_RECURRENCE);
        assertThat(testDonationsIssued.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingDonationsIssued() throws Exception {
        int databaseSizeBeforeUpdate = donationsIssuedRepository.findAll().size();
        donationsIssued.setId(count.incrementAndGet());

        // Create the DonationsIssued
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(donationsIssued);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationsIssuedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationsIssuedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonationsIssued() throws Exception {
        int databaseSizeBeforeUpdate = donationsIssuedRepository.findAll().size();
        donationsIssued.setId(count.incrementAndGet());

        // Create the DonationsIssued
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(donationsIssued);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsIssuedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonationsIssued() throws Exception {
        int databaseSizeBeforeUpdate = donationsIssuedRepository.findAll().size();
        donationsIssued.setId(count.incrementAndGet());

        // Create the DonationsIssued
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(donationsIssued);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsIssuedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonationsIssuedWithPatch() throws Exception {
        // Initialize the database
        donationsIssuedRepository.saveAndFlush(donationsIssued);

        int databaseSizeBeforeUpdate = donationsIssuedRepository.findAll().size();

        // Update the donationsIssued using partial update
        DonationsIssued partialUpdatedDonationsIssued = new DonationsIssued();
        partialUpdatedDonationsIssued.setId(donationsIssued.getId());

        partialUpdatedDonationsIssued
            .validationUser(UPDATED_VALIDATION_USER)
            .donationsDate(UPDATED_DONATIONS_DATE)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .recurringDonations(UPDATED_RECURRING_DONATIONS)
            .recurrence(UPDATED_RECURRENCE)
            .archivated(UPDATED_ARCHIVATED);

        restDonationsIssuedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationsIssued.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationsIssued))
            )
            .andExpect(status().isOk());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeUpdate);
        DonationsIssued testDonationsIssued = donationsIssuedList.get(donationsIssuedList.size() - 1);
        assertThat(testDonationsIssued.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testDonationsIssued.getIsValidated()).isEqualTo(DEFAULT_IS_VALIDATED);
        assertThat(testDonationsIssued.getValidationDate()).isEqualTo(DEFAULT_VALIDATION_DATE);
        assertThat(testDonationsIssued.getValidationUser()).isEqualTo(UPDATED_VALIDATION_USER);
        assertThat(testDonationsIssued.getDonationsDate()).isEqualTo(UPDATED_DONATIONS_DATE);
        assertThat(testDonationsIssued.getCanceledDonations()).isEqualTo(DEFAULT_CANCELED_DONATIONS);
        assertThat(testDonationsIssued.getCanceledOn()).isEqualTo(DEFAULT_CANCELED_ON);
        assertThat(testDonationsIssued.getCanceledBy()).isEqualTo(DEFAULT_CANCELED_BY);
        assertThat(testDonationsIssued.getCancellationReason()).isEqualTo(UPDATED_CANCELLATION_REASON);
        assertThat(testDonationsIssued.getRecurringDonations()).isEqualTo(UPDATED_RECURRING_DONATIONS);
        assertThat(testDonationsIssued.getPeriodicity()).isEqualTo(DEFAULT_PERIODICITY);
        assertThat(testDonationsIssued.getRecurrence()).isEqualTo(UPDATED_RECURRENCE);
        assertThat(testDonationsIssued.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateDonationsIssuedWithPatch() throws Exception {
        // Initialize the database
        donationsIssuedRepository.saveAndFlush(donationsIssued);

        int databaseSizeBeforeUpdate = donationsIssuedRepository.findAll().size();

        // Update the donationsIssued using partial update
        DonationsIssued partialUpdatedDonationsIssued = new DonationsIssued();
        partialUpdatedDonationsIssued.setId(donationsIssued.getId());

        partialUpdatedDonationsIssued
            .model(UPDATED_MODEL)
            .isValidated(UPDATED_IS_VALIDATED)
            .validationDate(UPDATED_VALIDATION_DATE)
            .validationUser(UPDATED_VALIDATION_USER)
            .donationsDate(UPDATED_DONATIONS_DATE)
            .canceledDonations(UPDATED_CANCELED_DONATIONS)
            .canceledOn(UPDATED_CANCELED_ON)
            .canceledBy(UPDATED_CANCELED_BY)
            .cancellationReason(UPDATED_CANCELLATION_REASON)
            .recurringDonations(UPDATED_RECURRING_DONATIONS)
            .periodicity(UPDATED_PERIODICITY)
            .recurrence(UPDATED_RECURRENCE)
            .archivated(UPDATED_ARCHIVATED);

        restDonationsIssuedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationsIssued.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationsIssued))
            )
            .andExpect(status().isOk());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeUpdate);
        DonationsIssued testDonationsIssued = donationsIssuedList.get(donationsIssuedList.size() - 1);
        assertThat(testDonationsIssued.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testDonationsIssued.getIsValidated()).isEqualTo(UPDATED_IS_VALIDATED);
        assertThat(testDonationsIssued.getValidationDate()).isEqualTo(UPDATED_VALIDATION_DATE);
        assertThat(testDonationsIssued.getValidationUser()).isEqualTo(UPDATED_VALIDATION_USER);
        assertThat(testDonationsIssued.getDonationsDate()).isEqualTo(UPDATED_DONATIONS_DATE);
        assertThat(testDonationsIssued.getCanceledDonations()).isEqualTo(UPDATED_CANCELED_DONATIONS);
        assertThat(testDonationsIssued.getCanceledOn()).isEqualTo(UPDATED_CANCELED_ON);
        assertThat(testDonationsIssued.getCanceledBy()).isEqualTo(UPDATED_CANCELED_BY);
        assertThat(testDonationsIssued.getCancellationReason()).isEqualTo(UPDATED_CANCELLATION_REASON);
        assertThat(testDonationsIssued.getRecurringDonations()).isEqualTo(UPDATED_RECURRING_DONATIONS);
        assertThat(testDonationsIssued.getPeriodicity()).isEqualTo(UPDATED_PERIODICITY);
        assertThat(testDonationsIssued.getRecurrence()).isEqualTo(UPDATED_RECURRENCE);
        assertThat(testDonationsIssued.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingDonationsIssued() throws Exception {
        int databaseSizeBeforeUpdate = donationsIssuedRepository.findAll().size();
        donationsIssued.setId(count.incrementAndGet());

        // Create the DonationsIssued
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(donationsIssued);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationsIssuedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donationsIssuedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonationsIssued() throws Exception {
        int databaseSizeBeforeUpdate = donationsIssuedRepository.findAll().size();
        donationsIssued.setId(count.incrementAndGet());

        // Create the DonationsIssued
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(donationsIssued);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsIssuedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonationsIssued() throws Exception {
        int databaseSizeBeforeUpdate = donationsIssuedRepository.findAll().size();
        donationsIssued.setId(count.incrementAndGet());

        // Create the DonationsIssued
        DonationsIssuedDTO donationsIssuedDTO = donationsIssuedMapper.toDto(donationsIssued);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsIssuedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationsIssuedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationsIssued in the database
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonationsIssued() throws Exception {
        // Initialize the database
        donationsIssuedRepository.saveAndFlush(donationsIssued);

        int databaseSizeBeforeDelete = donationsIssuedRepository.findAll().size();

        // Delete the donationsIssued
        restDonationsIssuedMockMvc
            .perform(delete(ENTITY_API_URL_ID, donationsIssued.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonationsIssued> donationsIssuedList = donationsIssuedRepository.findAll();
        assertThat(donationsIssuedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
