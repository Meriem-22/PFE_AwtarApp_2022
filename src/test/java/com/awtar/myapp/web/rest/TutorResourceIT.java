package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.repository.TutorRepository;
import com.awtar.myapp.service.dto.TutorDTO;
import com.awtar.myapp.service.mapper.TutorMapper;
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
 * Integration tests for the {@link TutorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TutorResourceIT {

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_CIN = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_CIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tutors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTutorMockMvc;

    private Tutor tutor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tutor createEntity(EntityManager em) {
        Tutor tutor = new Tutor().activity(DEFAULT_ACTIVITY).manager(DEFAULT_MANAGER).managerCin(DEFAULT_MANAGER_CIN);
        return tutor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tutor createUpdatedEntity(EntityManager em) {
        Tutor tutor = new Tutor().activity(UPDATED_ACTIVITY).manager(UPDATED_MANAGER).managerCin(UPDATED_MANAGER_CIN);
        return tutor;
    }

    @BeforeEach
    public void initTest() {
        tutor = createEntity(em);
    }

    @Test
    @Transactional
    void createTutor() throws Exception {
        int databaseSizeBeforeCreate = tutorRepository.findAll().size();
        // Create the Tutor
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);
        restTutorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tutorDTO)))
            .andExpect(status().isCreated());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeCreate + 1);
        Tutor testTutor = tutorList.get(tutorList.size() - 1);
        assertThat(testTutor.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testTutor.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testTutor.getManagerCin()).isEqualTo(DEFAULT_MANAGER_CIN);
    }

    @Test
    @Transactional
    void createTutorWithExistingId() throws Exception {
        // Create the Tutor with an existing ID
        tutor.setId(1L);
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        int databaseSizeBeforeCreate = tutorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTutorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tutorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTutors() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get all the tutorList
        restTutorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tutor.getId().intValue())))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].managerCin").value(hasItem(DEFAULT_MANAGER_CIN)));
    }

    @Test
    @Transactional
    void getTutor() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        // Get the tutor
        restTutorMockMvc
            .perform(get(ENTITY_API_URL_ID, tutor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tutor.getId().intValue()))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER))
            .andExpect(jsonPath("$.managerCin").value(DEFAULT_MANAGER_CIN));
    }

    @Test
    @Transactional
    void getNonExistingTutor() throws Exception {
        // Get the tutor
        restTutorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTutor() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();

        // Update the tutor
        Tutor updatedTutor = tutorRepository.findById(tutor.getId()).get();
        // Disconnect from session so that the updates on updatedTutor are not directly saved in db
        em.detach(updatedTutor);
        updatedTutor.activity(UPDATED_ACTIVITY).manager(UPDATED_MANAGER).managerCin(UPDATED_MANAGER_CIN);
        TutorDTO tutorDTO = tutorMapper.toDto(updatedTutor);

        restTutorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tutorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tutorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
        Tutor testTutor = tutorList.get(tutorList.size() - 1);
        assertThat(testTutor.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testTutor.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testTutor.getManagerCin()).isEqualTo(UPDATED_MANAGER_CIN);
    }

    @Test
    @Transactional
    void putNonExistingTutor() throws Exception {
        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();
        tutor.setId(count.incrementAndGet());

        // Create the Tutor
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTutorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tutorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tutorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTutor() throws Exception {
        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();
        tutor.setId(count.incrementAndGet());

        // Create the Tutor
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tutorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTutor() throws Exception {
        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();
        tutor.setId(count.incrementAndGet());

        // Create the Tutor
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tutorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTutorWithPatch() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();

        // Update the tutor using partial update
        Tutor partialUpdatedTutor = new Tutor();
        partialUpdatedTutor.setId(tutor.getId());

        partialUpdatedTutor.activity(UPDATED_ACTIVITY).managerCin(UPDATED_MANAGER_CIN);

        restTutorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTutor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTutor))
            )
            .andExpect(status().isOk());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
        Tutor testTutor = tutorList.get(tutorList.size() - 1);
        assertThat(testTutor.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testTutor.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testTutor.getManagerCin()).isEqualTo(UPDATED_MANAGER_CIN);
    }

    @Test
    @Transactional
    void fullUpdateTutorWithPatch() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();

        // Update the tutor using partial update
        Tutor partialUpdatedTutor = new Tutor();
        partialUpdatedTutor.setId(tutor.getId());

        partialUpdatedTutor.activity(UPDATED_ACTIVITY).manager(UPDATED_MANAGER).managerCin(UPDATED_MANAGER_CIN);

        restTutorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTutor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTutor))
            )
            .andExpect(status().isOk());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
        Tutor testTutor = tutorList.get(tutorList.size() - 1);
        assertThat(testTutor.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testTutor.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testTutor.getManagerCin()).isEqualTo(UPDATED_MANAGER_CIN);
    }

    @Test
    @Transactional
    void patchNonExistingTutor() throws Exception {
        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();
        tutor.setId(count.incrementAndGet());

        // Create the Tutor
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTutorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tutorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tutorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTutor() throws Exception {
        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();
        tutor.setId(count.incrementAndGet());

        // Create the Tutor
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tutorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTutor() throws Exception {
        int databaseSizeBeforeUpdate = tutorRepository.findAll().size();
        tutor.setId(count.incrementAndGet());

        // Create the Tutor
        TutorDTO tutorDTO = tutorMapper.toDto(tutor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTutorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tutorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tutor in the database
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTutor() throws Exception {
        // Initialize the database
        tutorRepository.saveAndFlush(tutor);

        int databaseSizeBeforeDelete = tutorRepository.findAll().size();

        // Delete the tutor
        restTutorMockMvc
            .perform(delete(ENTITY_API_URL_ID, tutor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tutor> tutorList = tutorRepository.findAll();
        assertThat(tutorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
