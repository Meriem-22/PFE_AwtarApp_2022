package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.Establishment;
import com.awtar.myapp.domain.EstablishmentType;
import com.awtar.myapp.repository.EstablishmentRepository;
import com.awtar.myapp.service.EstablishmentService;
import com.awtar.myapp.service.dto.EstablishmentDTO;
import com.awtar.myapp.service.mapper.EstablishmentMapper;
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
 * Integration tests for the {@link EstablishmentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EstablishmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_CIN = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_CIN = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CP = "AAAAAAAAAA";
    private static final String UPDATED_CP = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/establishments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Mock
    private EstablishmentRepository establishmentRepositoryMock;

    @Autowired
    private EstablishmentMapper establishmentMapper;

    @Mock
    private EstablishmentService establishmentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstablishmentMockMvc;

    private Establishment establishment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Establishment createEntity(EntityManager em) {
        Establishment establishment = new Establishment()
            .name(DEFAULT_NAME)
            .activity(DEFAULT_ACTIVITY)
            .manager(DEFAULT_MANAGER)
            .managerCin(DEFAULT_MANAGER_CIN)
            .contact(DEFAULT_CONTACT)
            .adress(DEFAULT_ADRESS)
            .cp(DEFAULT_CP)
            .region(DEFAULT_REGION)
            .phone(DEFAULT_PHONE)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .site(DEFAULT_SITE)
            .remark(DEFAULT_REMARK);
        // Add required entity
        EstablishmentType establishmentType;
        if (TestUtil.findAll(em, EstablishmentType.class).isEmpty()) {
            establishmentType = EstablishmentTypeResourceIT.createEntity(em);
            em.persist(establishmentType);
            em.flush();
        } else {
            establishmentType = TestUtil.findAll(em, EstablishmentType.class).get(0);
        }
        establishment.setEstablishmentType(establishmentType);
        // Add required entity
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            city = CityResourceIT.createEntity(em);
            em.persist(city);
            em.flush();
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        establishment.setCity(city);
        return establishment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Establishment createUpdatedEntity(EntityManager em) {
        Establishment establishment = new Establishment()
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .manager(UPDATED_MANAGER)
            .managerCin(UPDATED_MANAGER_CIN)
            .contact(UPDATED_CONTACT)
            .adress(UPDATED_ADRESS)
            .cp(UPDATED_CP)
            .region(UPDATED_REGION)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .site(UPDATED_SITE)
            .remark(UPDATED_REMARK);
        // Add required entity
        EstablishmentType establishmentType;
        if (TestUtil.findAll(em, EstablishmentType.class).isEmpty()) {
            establishmentType = EstablishmentTypeResourceIT.createUpdatedEntity(em);
            em.persist(establishmentType);
            em.flush();
        } else {
            establishmentType = TestUtil.findAll(em, EstablishmentType.class).get(0);
        }
        establishment.setEstablishmentType(establishmentType);
        // Add required entity
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            city = CityResourceIT.createUpdatedEntity(em);
            em.persist(city);
            em.flush();
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        establishment.setCity(city);
        return establishment;
    }

    @BeforeEach
    public void initTest() {
        establishment = createEntity(em);
    }

    @Test
    @Transactional
    void createEstablishment() throws Exception {
        int databaseSizeBeforeCreate = establishmentRepository.findAll().size();
        // Create the Establishment
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(establishment);
        restEstablishmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeCreate + 1);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEstablishment.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testEstablishment.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testEstablishment.getManagerCin()).isEqualTo(DEFAULT_MANAGER_CIN);
        assertThat(testEstablishment.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testEstablishment.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testEstablishment.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testEstablishment.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testEstablishment.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testEstablishment.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testEstablishment.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEstablishment.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testEstablishment.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    void createEstablishmentWithExistingId() throws Exception {
        // Create the Establishment with an existing ID
        establishment.setId(1L);
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(establishment);

        int databaseSizeBeforeCreate = establishmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstablishmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentRepository.findAll().size();
        // set the field null
        establishment.setName(null);

        // Create the Establishment, which fails.
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(establishment);

        restEstablishmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstablishments() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        // Get all the establishmentList
        restEstablishmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(establishment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER)))
            .andExpect(jsonPath("$.[*].managerCin").value(hasItem(DEFAULT_MANAGER_CIN)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEstablishmentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(establishmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEstablishmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(establishmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEstablishmentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(establishmentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEstablishmentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(establishmentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        // Get the establishment
        restEstablishmentMockMvc
            .perform(get(ENTITY_API_URL_ID, establishment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(establishment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER))
            .andExpect(jsonPath("$.managerCin").value(DEFAULT_MANAGER_CIN))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS))
            .andExpect(jsonPath("$.cp").value(DEFAULT_CP))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK));
    }

    @Test
    @Transactional
    void getNonExistingEstablishment() throws Exception {
        // Get the establishment
        restEstablishmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Update the establishment
        Establishment updatedEstablishment = establishmentRepository.findById(establishment.getId()).get();
        // Disconnect from session so that the updates on updatedEstablishment are not directly saved in db
        em.detach(updatedEstablishment);
        updatedEstablishment
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .manager(UPDATED_MANAGER)
            .managerCin(UPDATED_MANAGER_CIN)
            .contact(UPDATED_CONTACT)
            .adress(UPDATED_ADRESS)
            .cp(UPDATED_CP)
            .region(UPDATED_REGION)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .site(UPDATED_SITE)
            .remark(UPDATED_REMARK);
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(updatedEstablishment);

        restEstablishmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, establishmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEstablishment.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testEstablishment.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testEstablishment.getManagerCin()).isEqualTo(UPDATED_MANAGER_CIN);
        assertThat(testEstablishment.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testEstablishment.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testEstablishment.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testEstablishment.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testEstablishment.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testEstablishment.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testEstablishment.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEstablishment.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testEstablishment.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    void putNonExistingEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // Create the Establishment
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(establishment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, establishmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // Create the Establishment
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(establishment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // Create the Establishment
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(establishment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstablishmentWithPatch() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Update the establishment using partial update
        Establishment partialUpdatedEstablishment = new Establishment();
        partialUpdatedEstablishment.setId(establishment.getId());

        partialUpdatedEstablishment
            .manager(UPDATED_MANAGER)
            .managerCin(UPDATED_MANAGER_CIN)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .site(UPDATED_SITE)
            .remark(UPDATED_REMARK);

        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablishment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstablishment))
            )
            .andExpect(status().isOk());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEstablishment.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testEstablishment.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testEstablishment.getManagerCin()).isEqualTo(UPDATED_MANAGER_CIN);
        assertThat(testEstablishment.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testEstablishment.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testEstablishment.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testEstablishment.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testEstablishment.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testEstablishment.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testEstablishment.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEstablishment.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testEstablishment.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    void fullUpdateEstablishmentWithPatch() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Update the establishment using partial update
        Establishment partialUpdatedEstablishment = new Establishment();
        partialUpdatedEstablishment.setId(establishment.getId());

        partialUpdatedEstablishment
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .manager(UPDATED_MANAGER)
            .managerCin(UPDATED_MANAGER_CIN)
            .contact(UPDATED_CONTACT)
            .adress(UPDATED_ADRESS)
            .cp(UPDATED_CP)
            .region(UPDATED_REGION)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .site(UPDATED_SITE)
            .remark(UPDATED_REMARK);

        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablishment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstablishment))
            )
            .andExpect(status().isOk());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEstablishment.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testEstablishment.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testEstablishment.getManagerCin()).isEqualTo(UPDATED_MANAGER_CIN);
        assertThat(testEstablishment.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testEstablishment.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testEstablishment.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testEstablishment.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testEstablishment.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testEstablishment.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testEstablishment.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEstablishment.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testEstablishment.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    void patchNonExistingEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // Create the Establishment
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(establishment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, establishmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // Create the Establishment
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(establishment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // Create the Establishment
        EstablishmentDTO establishmentDTO = establishmentMapper.toDto(establishment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeDelete = establishmentRepository.findAll().size();

        // Delete the establishment
        restEstablishmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, establishment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
