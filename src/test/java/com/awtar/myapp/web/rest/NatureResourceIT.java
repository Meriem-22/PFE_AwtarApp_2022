package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.domain.enumeration.Beneficiaries;
import com.awtar.myapp.repository.NatureRepository;
import com.awtar.myapp.service.dto.NatureDTO;
import com.awtar.myapp.service.mapper.NatureMapper;
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
 * Integration tests for the {@link NatureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NatureResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Beneficiaries DEFAULT_DESTINED_TO = Beneficiaries.FAMILY;
    private static final Beneficiaries UPDATED_DESTINED_TO = Beneficiaries.ESTABLISHMENT;

    private static final Boolean DEFAULT_NECESSITY_VALUE = false;
    private static final Boolean UPDATED_NECESSITY_VALUE = true;

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/natures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureRepository natureRepository;

    @Autowired
    private NatureMapper natureMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureMockMvc;

    private Nature nature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nature createEntity(EntityManager em) {
        Nature nature = new Nature()
            .name(DEFAULT_NAME)
            .destinedTo(DEFAULT_DESTINED_TO)
            .necessityValue(DEFAULT_NECESSITY_VALUE)
            .archivated(DEFAULT_ARCHIVATED);
        return nature;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nature createUpdatedEntity(EntityManager em) {
        Nature nature = new Nature()
            .name(UPDATED_NAME)
            .destinedTo(UPDATED_DESTINED_TO)
            .necessityValue(UPDATED_NECESSITY_VALUE)
            .archivated(UPDATED_ARCHIVATED);
        return nature;
    }

    @BeforeEach
    public void initTest() {
        nature = createEntity(em);
    }

    @Test
    @Transactional
    void createNature() throws Exception {
        int databaseSizeBeforeCreate = natureRepository.findAll().size();
        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);
        restNatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureDTO)))
            .andExpect(status().isCreated());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeCreate + 1);
        Nature testNature = natureList.get(natureList.size() - 1);
        assertThat(testNature.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNature.getDestinedTo()).isEqualTo(DEFAULT_DESTINED_TO);
        assertThat(testNature.getNecessityValue()).isEqualTo(DEFAULT_NECESSITY_VALUE);
        assertThat(testNature.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createNatureWithExistingId() throws Exception {
        // Create the Nature with an existing ID
        nature.setId(1L);
        NatureDTO natureDTO = natureMapper.toDto(nature);

        int databaseSizeBeforeCreate = natureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = natureRepository.findAll().size();
        // set the field null
        nature.setName(null);

        // Create the Nature, which fails.
        NatureDTO natureDTO = natureMapper.toDto(nature);

        restNatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureDTO)))
            .andExpect(status().isBadRequest());

        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDestinedToIsRequired() throws Exception {
        int databaseSizeBeforeTest = natureRepository.findAll().size();
        // set the field null
        nature.setDestinedTo(null);

        // Create the Nature, which fails.
        NatureDTO natureDTO = natureMapper.toDto(nature);

        restNatureMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureDTO)))
            .andExpect(status().isBadRequest());

        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNatures() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get all the natureList
        restNatureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nature.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].destinedTo").value(hasItem(DEFAULT_DESTINED_TO.toString())))
            .andExpect(jsonPath("$.[*].necessityValue").value(hasItem(DEFAULT_NECESSITY_VALUE.booleanValue())))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @Test
    @Transactional
    void getNature() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        // Get the nature
        restNatureMockMvc
            .perform(get(ENTITY_API_URL_ID, nature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nature.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.destinedTo").value(DEFAULT_DESTINED_TO.toString()))
            .andExpect(jsonPath("$.necessityValue").value(DEFAULT_NECESSITY_VALUE.booleanValue()))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingNature() throws Exception {
        // Get the nature
        restNatureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNature() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        int databaseSizeBeforeUpdate = natureRepository.findAll().size();

        // Update the nature
        Nature updatedNature = natureRepository.findById(nature.getId()).get();
        // Disconnect from session so that the updates on updatedNature are not directly saved in db
        em.detach(updatedNature);
        updatedNature
            .name(UPDATED_NAME)
            .destinedTo(UPDATED_DESTINED_TO)
            .necessityValue(UPDATED_NECESSITY_VALUE)
            .archivated(UPDATED_ARCHIVATED);
        NatureDTO natureDTO = natureMapper.toDto(updatedNature);

        restNatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
        Nature testNature = natureList.get(natureList.size() - 1);
        assertThat(testNature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNature.getDestinedTo()).isEqualTo(UPDATED_DESTINED_TO);
        assertThat(testNature.getNecessityValue()).isEqualTo(UPDATED_NECESSITY_VALUE);
        assertThat(testNature.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureWithPatch() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        int databaseSizeBeforeUpdate = natureRepository.findAll().size();

        // Update the nature using partial update
        Nature partialUpdatedNature = new Nature();
        partialUpdatedNature.setId(nature.getId());

        partialUpdatedNature.name(UPDATED_NAME).destinedTo(UPDATED_DESTINED_TO);

        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNature))
            )
            .andExpect(status().isOk());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
        Nature testNature = natureList.get(natureList.size() - 1);
        assertThat(testNature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNature.getDestinedTo()).isEqualTo(UPDATED_DESTINED_TO);
        assertThat(testNature.getNecessityValue()).isEqualTo(DEFAULT_NECESSITY_VALUE);
        assertThat(testNature.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateNatureWithPatch() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        int databaseSizeBeforeUpdate = natureRepository.findAll().size();

        // Update the nature using partial update
        Nature partialUpdatedNature = new Nature();
        partialUpdatedNature.setId(nature.getId());

        partialUpdatedNature
            .name(UPDATED_NAME)
            .destinedTo(UPDATED_DESTINED_TO)
            .necessityValue(UPDATED_NECESSITY_VALUE)
            .archivated(UPDATED_ARCHIVATED);

        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNature.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNature))
            )
            .andExpect(status().isOk());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
        Nature testNature = natureList.get(natureList.size() - 1);
        assertThat(testNature.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNature.getDestinedTo()).isEqualTo(UPDATED_DESTINED_TO);
        assertThat(testNature.getNecessityValue()).isEqualTo(UPDATED_NECESSITY_VALUE);
        assertThat(testNature.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNature() throws Exception {
        int databaseSizeBeforeUpdate = natureRepository.findAll().size();
        nature.setId(count.incrementAndGet());

        // Create the Nature
        NatureDTO natureDTO = natureMapper.toDto(nature);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(natureDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nature in the database
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNature() throws Exception {
        // Initialize the database
        natureRepository.saveAndFlush(nature);

        int databaseSizeBeforeDelete = natureRepository.findAll().size();

        // Delete the nature
        restNatureMockMvc
            .perform(delete(ENTITY_API_URL_ID, nature.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nature> natureList = natureRepository.findAll();
        assertThat(natureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
