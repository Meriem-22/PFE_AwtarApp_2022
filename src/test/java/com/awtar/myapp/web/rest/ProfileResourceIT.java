package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.enumeration.Gender;
import com.awtar.myapp.repository.ProfileRepository;
import com.awtar.myapp.service.ProfileService;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.mapper.ProfileMapper;
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
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProfileResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME_ARABIC = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.WOMAN;
    private static final Gender UPDATED_GENDER = Gender.MAN;

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CIN = "AAAAAAAAAA";
    private static final String UPDATED_CIN = "BBBBBBBBBB";

    private static final byte[] DEFAULT_URL_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_URL_CIN_ATTACHED = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_URL_CIN_ATTACHED = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_URL_CIN_ATTACHED_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_URL_CIN_ATTACHED_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfileRepository profileRepository;

    @Mock
    private ProfileRepository profileRepositoryMock;

    @Autowired
    private ProfileMapper profileMapper;

    @Mock
    private ProfileService profileServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileMockMvc;

    private Profile profile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .firstNameArabic(DEFAULT_FIRST_NAME_ARABIC)
            .lastNameArabic(DEFAULT_LAST_NAME_ARABIC)
            .gender(DEFAULT_GENDER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .cin(DEFAULT_CIN)
            .urlPhoto(DEFAULT_URL_PHOTO)
            .urlPhotoContentType(DEFAULT_URL_PHOTO_CONTENT_TYPE)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .urlCinAttached(DEFAULT_URL_CIN_ATTACHED)
            .urlCinAttachedContentType(DEFAULT_URL_CIN_ATTACHED_CONTENT_TYPE)
            .archivated(DEFAULT_ARCHIVATED);
        // Add required entity
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            city = CityResourceIT.createEntity(em);
            em.persist(city);
            em.flush();
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        profile.setBirthPlace(city);
        // Add required entity
        profile.setPlaceOfResidence(city);
        return profile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .firstNameArabic(UPDATED_FIRST_NAME_ARABIC)
            .lastNameArabic(UPDATED_LAST_NAME_ARABIC)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .cin(UPDATED_CIN)
            .urlPhoto(UPDATED_URL_PHOTO)
            .urlPhotoContentType(UPDATED_URL_PHOTO_CONTENT_TYPE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .urlCinAttached(UPDATED_URL_CIN_ATTACHED)
            .urlCinAttachedContentType(UPDATED_URL_CIN_ATTACHED_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);
        // Add required entity
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            city = CityResourceIT.createUpdatedEntity(em);
            em.persist(city);
            em.flush();
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        profile.setBirthPlace(city);
        // Add required entity
        profile.setPlaceOfResidence(city);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();
        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testProfile.getFirstNameArabic()).isEqualTo(DEFAULT_FIRST_NAME_ARABIC);
        assertThat(testProfile.getLastNameArabic()).isEqualTo(DEFAULT_LAST_NAME_ARABIC);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testProfile.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testProfile.getUrlPhoto()).isEqualTo(DEFAULT_URL_PHOTO);
        assertThat(testProfile.getUrlPhotoContentType()).isEqualTo(DEFAULT_URL_PHOTO_CONTENT_TYPE);
        assertThat(testProfile.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfile.getUrlCinAttached()).isEqualTo(DEFAULT_URL_CIN_ATTACHED);
        assertThat(testProfile.getUrlCinAttachedContentType()).isEqualTo(DEFAULT_URL_CIN_ATTACHED_CONTENT_TYPE);
        assertThat(testProfile.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createProfileWithExistingId() throws Exception {
        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setFirstName(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setLastName(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setGender(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setDateOfBirth(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstNameArabic").value(hasItem(DEFAULT_FIRST_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].lastNameArabic").value(hasItem(DEFAULT_LAST_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].urlPhotoContentType").value(hasItem(DEFAULT_URL_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_PHOTO))))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].urlCinAttachedContentType").value(hasItem(DEFAULT_URL_CIN_ATTACHED_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].urlCinAttached").value(hasItem(Base64Utils.encodeToString(DEFAULT_URL_CIN_ATTACHED))))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfilesWithEagerRelationshipsIsEnabled() throws Exception {
        when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(profileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(profileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProfileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(profileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstNameArabic").value(DEFAULT_FIRST_NAME_ARABIC))
            .andExpect(jsonPath("$.lastNameArabic").value(DEFAULT_LAST_NAME_ARABIC))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.urlPhotoContentType").value(DEFAULT_URL_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlPhoto").value(Base64Utils.encodeToString(DEFAULT_URL_PHOTO)))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.urlCinAttachedContentType").value(DEFAULT_URL_CIN_ATTACHED_CONTENT_TYPE))
            .andExpect(jsonPath("$.urlCinAttached").value(Base64Utils.encodeToString(DEFAULT_URL_CIN_ATTACHED)))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .firstNameArabic(UPDATED_FIRST_NAME_ARABIC)
            .lastNameArabic(UPDATED_LAST_NAME_ARABIC)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .cin(UPDATED_CIN)
            .urlPhoto(UPDATED_URL_PHOTO)
            .urlPhotoContentType(UPDATED_URL_PHOTO_CONTENT_TYPE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .urlCinAttached(UPDATED_URL_CIN_ATTACHED)
            .urlCinAttachedContentType(UPDATED_URL_CIN_ATTACHED_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProfile.getFirstNameArabic()).isEqualTo(UPDATED_FIRST_NAME_ARABIC);
        assertThat(testProfile.getLastNameArabic()).isEqualTo(UPDATED_LAST_NAME_ARABIC);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testProfile.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testProfile.getUrlPhoto()).isEqualTo(UPDATED_URL_PHOTO);
        assertThat(testProfile.getUrlPhotoContentType()).isEqualTo(UPDATED_URL_PHOTO_CONTENT_TYPE);
        assertThat(testProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfile.getUrlCinAttached()).isEqualTo(UPDATED_URL_CIN_ATTACHED);
        assertThat(testProfile.getUrlCinAttachedContentType()).isEqualTo(UPDATED_URL_CIN_ATTACHED_CONTENT_TYPE);
        assertThat(testProfile.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .firstNameArabic(UPDATED_FIRST_NAME_ARABIC)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .urlCinAttached(UPDATED_URL_CIN_ATTACHED)
            .urlCinAttachedContentType(UPDATED_URL_CIN_ATTACHED_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProfile.getFirstNameArabic()).isEqualTo(UPDATED_FIRST_NAME_ARABIC);
        assertThat(testProfile.getLastNameArabic()).isEqualTo(DEFAULT_LAST_NAME_ARABIC);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testProfile.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testProfile.getUrlPhoto()).isEqualTo(DEFAULT_URL_PHOTO);
        assertThat(testProfile.getUrlPhotoContentType()).isEqualTo(DEFAULT_URL_PHOTO_CONTENT_TYPE);
        assertThat(testProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfile.getUrlCinAttached()).isEqualTo(UPDATED_URL_CIN_ATTACHED);
        assertThat(testProfile.getUrlCinAttachedContentType()).isEqualTo(UPDATED_URL_CIN_ATTACHED_CONTENT_TYPE);
        assertThat(testProfile.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .firstNameArabic(UPDATED_FIRST_NAME_ARABIC)
            .lastNameArabic(UPDATED_LAST_NAME_ARABIC)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .cin(UPDATED_CIN)
            .urlPhoto(UPDATED_URL_PHOTO)
            .urlPhotoContentType(UPDATED_URL_PHOTO_CONTENT_TYPE)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .urlCinAttached(UPDATED_URL_CIN_ATTACHED)
            .urlCinAttachedContentType(UPDATED_URL_CIN_ATTACHED_CONTENT_TYPE)
            .archivated(UPDATED_ARCHIVATED);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProfile.getFirstNameArabic()).isEqualTo(UPDATED_FIRST_NAME_ARABIC);
        assertThat(testProfile.getLastNameArabic()).isEqualTo(UPDATED_LAST_NAME_ARABIC);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testProfile.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testProfile.getUrlPhoto()).isEqualTo(UPDATED_URL_PHOTO);
        assertThat(testProfile.getUrlPhotoContentType()).isEqualTo(UPDATED_URL_PHOTO_CONTENT_TYPE);
        assertThat(testProfile.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfile.getUrlCinAttached()).isEqualTo(UPDATED_URL_CIN_ATTACHED);
        assertThat(testProfile.getUrlCinAttachedContentType()).isEqualTo(UPDATED_URL_CIN_ATTACHED_CONTENT_TYPE);
        assertThat(testProfile.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(profileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, profile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
