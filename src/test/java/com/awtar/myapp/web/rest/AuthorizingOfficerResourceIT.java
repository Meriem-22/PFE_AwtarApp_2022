package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.repository.AuthorizingOfficerRepository;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.mapper.AuthorizingOfficerMapper;
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
 * Integration tests for the {@link AuthorizingOfficerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuthorizingOfficerResourceIT {

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_CIN = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_CIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/authorizing-officers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthorizingOfficerRepository authorizingOfficerRepository;

    @Autowired
    private AuthorizingOfficerMapper authorizingOfficerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthorizingOfficerMockMvc;

    private AuthorizingOfficer authorizingOfficer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthorizingOfficer createEntity(EntityManager em) {
        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer()
            .abbreviation(DEFAULT_ABBREVIATION)
            .activity(DEFAULT_ACTIVITY)
            .manager(DEFAULT_MANAGER)
            .managerCin(DEFAULT_MANAGER_CIN);
        return authorizingOfficer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthorizingOfficer createUpdatedEntity(EntityManager em) {
        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer()
            .abbreviation(UPDATED_ABBREVIATION)
            .activity(UPDATED_ACTIVITY)
            .manager(UPDATED_MANAGER)
            .managerCin(UPDATED_MANAGER_CIN);
        return authorizingOfficer;
    }

    @BeforeEach
    public void initTest() {
        authorizingOfficer = createEntity(em);
    }

    @Test
    @Transactional
    void createAuthorizingOfficer() throws Exception {
        int databaseSizeBeforeCreate = authorizingOfficerRepository.findAll().size();
        // Create the AuthorizingOfficer
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(authorizingOfficer);
        restAuthorizingOfficerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeCreate + 1);
        AuthorizingOfficer testAuthorizingOfficer = authorizingOfficerList.get(authorizingOfficerList.size() - 1);
        assertThat(testAuthorizingOfficer.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
        assertThat(testAuthorizingOfficer.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testAuthorizingOfficer.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testAuthorizingOfficer.getManagerCin()).isEqualTo(DEFAULT_MANAGER_CIN);
    }

    @Test
    @Transactional
    void createAuthorizingOfficerWithExistingId() throws Exception {
        // Create the AuthorizingOfficer with an existing ID
        authorizingOfficer.setId(1L);
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(authorizingOfficer);

        int databaseSizeBeforeCreate = authorizingOfficerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorizingOfficerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAbbreviationIsRequired() throws Exception {
        int databaseSizeBeforeTest = authorizingOfficerRepository.findAll().size();
        // set the field null
        authorizingOfficer.setAbbreviation(null);

        // Create the AuthorizingOfficer, which fails.
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(authorizingOfficer);

        restAuthorizingOfficerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isBadRequest());

        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuthorizingOfficers() throws Exception {
        // Initialize the database
        authorizingOfficerRepository.saveAndFlush(authorizingOfficer);

        // Get all the authorizingOfficerList
        restAuthorizingOfficerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authorizingOfficer.getId().intValue())))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].managerCin").value(hasItem(DEFAULT_MANAGER_CIN)));
    }

    @Test
    @Transactional
    void getAuthorizingOfficer() throws Exception {
        // Initialize the database
        authorizingOfficerRepository.saveAndFlush(authorizingOfficer);

        // Get the authorizingOfficer
        restAuthorizingOfficerMockMvc
            .perform(get(ENTITY_API_URL_ID, authorizingOfficer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(authorizingOfficer.getId().intValue()))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER))
            .andExpect(jsonPath("$.managerCin").value(DEFAULT_MANAGER_CIN));
    }

    @Test
    @Transactional
    void getNonExistingAuthorizingOfficer() throws Exception {
        // Get the authorizingOfficer
        restAuthorizingOfficerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAuthorizingOfficer() throws Exception {
        // Initialize the database
        authorizingOfficerRepository.saveAndFlush(authorizingOfficer);

        int databaseSizeBeforeUpdate = authorizingOfficerRepository.findAll().size();

        // Update the authorizingOfficer
        AuthorizingOfficer updatedAuthorizingOfficer = authorizingOfficerRepository.findById(authorizingOfficer.getId()).get();
        // Disconnect from session so that the updates on updatedAuthorizingOfficer are not directly saved in db
        em.detach(updatedAuthorizingOfficer);
        updatedAuthorizingOfficer
            .abbreviation(UPDATED_ABBREVIATION)
            .activity(UPDATED_ACTIVITY)
            .manager(UPDATED_MANAGER)
            .managerCin(UPDATED_MANAGER_CIN);
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(updatedAuthorizingOfficer);

        restAuthorizingOfficerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorizingOfficerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isOk());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeUpdate);
        AuthorizingOfficer testAuthorizingOfficer = authorizingOfficerList.get(authorizingOfficerList.size() - 1);
        assertThat(testAuthorizingOfficer.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
        assertThat(testAuthorizingOfficer.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testAuthorizingOfficer.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testAuthorizingOfficer.getManagerCin()).isEqualTo(UPDATED_MANAGER_CIN);
    }

    @Test
    @Transactional
    void putNonExistingAuthorizingOfficer() throws Exception {
        int databaseSizeBeforeUpdate = authorizingOfficerRepository.findAll().size();
        authorizingOfficer.setId(count.incrementAndGet());

        // Create the AuthorizingOfficer
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(authorizingOfficer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorizingOfficerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorizingOfficerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthorizingOfficer() throws Exception {
        int databaseSizeBeforeUpdate = authorizingOfficerRepository.findAll().size();
        authorizingOfficer.setId(count.incrementAndGet());

        // Create the AuthorizingOfficer
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(authorizingOfficer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorizingOfficerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthorizingOfficer() throws Exception {
        int databaseSizeBeforeUpdate = authorizingOfficerRepository.findAll().size();
        authorizingOfficer.setId(count.incrementAndGet());

        // Create the AuthorizingOfficer
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(authorizingOfficer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorizingOfficerMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthorizingOfficerWithPatch() throws Exception {
        // Initialize the database
        authorizingOfficerRepository.saveAndFlush(authorizingOfficer);

        int databaseSizeBeforeUpdate = authorizingOfficerRepository.findAll().size();

        // Update the authorizingOfficer using partial update
        AuthorizingOfficer partialUpdatedAuthorizingOfficer = new AuthorizingOfficer();
        partialUpdatedAuthorizingOfficer.setId(authorizingOfficer.getId());

        restAuthorizingOfficerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthorizingOfficer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthorizingOfficer))
            )
            .andExpect(status().isOk());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeUpdate);
        AuthorizingOfficer testAuthorizingOfficer = authorizingOfficerList.get(authorizingOfficerList.size() - 1);
        assertThat(testAuthorizingOfficer.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
        assertThat(testAuthorizingOfficer.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testAuthorizingOfficer.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testAuthorizingOfficer.getManagerCin()).isEqualTo(DEFAULT_MANAGER_CIN);
    }

    @Test
    @Transactional
    void fullUpdateAuthorizingOfficerWithPatch() throws Exception {
        // Initialize the database
        authorizingOfficerRepository.saveAndFlush(authorizingOfficer);

        int databaseSizeBeforeUpdate = authorizingOfficerRepository.findAll().size();

        // Update the authorizingOfficer using partial update
        AuthorizingOfficer partialUpdatedAuthorizingOfficer = new AuthorizingOfficer();
        partialUpdatedAuthorizingOfficer.setId(authorizingOfficer.getId());

        partialUpdatedAuthorizingOfficer
            .abbreviation(UPDATED_ABBREVIATION)
            .activity(UPDATED_ACTIVITY)
            .manager(UPDATED_MANAGER)
            .managerCin(UPDATED_MANAGER_CIN);

        restAuthorizingOfficerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthorizingOfficer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthorizingOfficer))
            )
            .andExpect(status().isOk());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeUpdate);
        AuthorizingOfficer testAuthorizingOfficer = authorizingOfficerList.get(authorizingOfficerList.size() - 1);
        assertThat(testAuthorizingOfficer.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
        assertThat(testAuthorizingOfficer.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testAuthorizingOfficer.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testAuthorizingOfficer.getManagerCin()).isEqualTo(UPDATED_MANAGER_CIN);
    }

    @Test
    @Transactional
    void patchNonExistingAuthorizingOfficer() throws Exception {
        int databaseSizeBeforeUpdate = authorizingOfficerRepository.findAll().size();
        authorizingOfficer.setId(count.incrementAndGet());

        // Create the AuthorizingOfficer
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(authorizingOfficer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorizingOfficerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authorizingOfficerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthorizingOfficer() throws Exception {
        int databaseSizeBeforeUpdate = authorizingOfficerRepository.findAll().size();
        authorizingOfficer.setId(count.incrementAndGet());

        // Create the AuthorizingOfficer
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(authorizingOfficer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorizingOfficerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthorizingOfficer() throws Exception {
        int databaseSizeBeforeUpdate = authorizingOfficerRepository.findAll().size();
        authorizingOfficer.setId(count.incrementAndGet());

        // Create the AuthorizingOfficer
        AuthorizingOfficerDTO authorizingOfficerDTO = authorizingOfficerMapper.toDto(authorizingOfficer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorizingOfficerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorizingOfficerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthorizingOfficer in the database
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthorizingOfficer() throws Exception {
        // Initialize the database
        authorizingOfficerRepository.saveAndFlush(authorizingOfficer);

        int databaseSizeBeforeDelete = authorizingOfficerRepository.findAll().size();

        // Delete the authorizingOfficer
        restAuthorizingOfficerMockMvc
            .perform(delete(ENTITY_API_URL_ID, authorizingOfficer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuthorizingOfficer> authorizingOfficerList = authorizingOfficerRepository.findAll();
        assertThat(authorizingOfficerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
