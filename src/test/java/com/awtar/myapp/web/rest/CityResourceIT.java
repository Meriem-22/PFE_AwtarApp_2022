package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.repository.CityRepository;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.mapper.CityMapper;
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
 * Integration tests for the {@link CityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GOVERNORATE = "AAAAAAAAAA";
    private static final String UPDATED_GOVERNORATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_GOVERNORATE = false;
    private static final Boolean UPDATED_IS_GOVERNORATE = true;

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityMockMvc;

    private City city;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createEntity(EntityManager em) {
        City city = new City()
            .name(DEFAULT_NAME)
            .governorate(DEFAULT_GOVERNORATE)
            .isGovernorate(DEFAULT_IS_GOVERNORATE)
            .archivated(DEFAULT_ARCHIVATED);
        return city;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createUpdatedEntity(EntityManager em) {
        City city = new City()
            .name(UPDATED_NAME)
            .governorate(UPDATED_GOVERNORATE)
            .isGovernorate(UPDATED_IS_GOVERNORATE)
            .archivated(UPDATED_ARCHIVATED);
        return city;
    }

    @BeforeEach
    public void initTest() {
        city = createEntity(em);
    }

    @Test
    @Transactional
    void createCity() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();
        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);
        restCityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate + 1);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCity.getGovernorate()).isEqualTo(DEFAULT_GOVERNORATE);
        assertThat(testCity.getIsGovernorate()).isEqualTo(DEFAULT_IS_GOVERNORATE);
        assertThat(testCity.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createCityWithExistingId() throws Exception {
        // Create the City with an existing ID
        city.setId(1L);
        CityDTO cityDTO = cityMapper.toDto(city);

        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cityRepository.findAll().size();
        // set the field null
        city.setName(null);

        // Create the City, which fails.
        CityDTO cityDTO = cityMapper.toDto(city);

        restCityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isBadRequest());

        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList
        restCityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].governorate").value(hasItem(DEFAULT_GOVERNORATE)))
            .andExpect(jsonPath("$.[*].isGovernorate").value(hasItem(DEFAULT_IS_GOVERNORATE.booleanValue())))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @Test
    @Transactional
    void getCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get the city
        restCityMockMvc
            .perform(get(ENTITY_API_URL_ID, city.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(city.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.governorate").value(DEFAULT_GOVERNORATE))
            .andExpect(jsonPath("$.isGovernorate").value(DEFAULT_IS_GOVERNORATE.booleanValue()))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city
        City updatedCity = cityRepository.findById(city.getId()).get();
        // Disconnect from session so that the updates on updatedCity are not directly saved in db
        em.detach(updatedCity);
        updatedCity
            .name(UPDATED_NAME)
            .governorate(UPDATED_GOVERNORATE)
            .isGovernorate(UPDATED_IS_GOVERNORATE)
            .archivated(UPDATED_ARCHIVATED);
        CityDTO cityDTO = cityMapper.toDto(updatedCity);

        restCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCity.getGovernorate()).isEqualTo(UPDATED_GOVERNORATE);
        assertThat(testCity.getIsGovernorate()).isEqualTo(UPDATED_IS_GOVERNORATE);
        assertThat(testCity.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCityWithPatch() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city using partial update
        City partialUpdatedCity = new City();
        partialUpdatedCity.setId(city.getId());

        partialUpdatedCity.governorate(UPDATED_GOVERNORATE);

        restCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCity))
            )
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCity.getGovernorate()).isEqualTo(UPDATED_GOVERNORATE);
        assertThat(testCity.getIsGovernorate()).isEqualTo(DEFAULT_IS_GOVERNORATE);
        assertThat(testCity.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateCityWithPatch() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city using partial update
        City partialUpdatedCity = new City();
        partialUpdatedCity.setId(city.getId());

        partialUpdatedCity
            .name(UPDATED_NAME)
            .governorate(UPDATED_GOVERNORATE)
            .isGovernorate(UPDATED_IS_GOVERNORATE)
            .archivated(UPDATED_ARCHIVATED);

        restCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCity))
            )
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCity.getGovernorate()).isEqualTo(UPDATED_GOVERNORATE);
        assertThat(testCity.getIsGovernorate()).isEqualTo(UPDATED_IS_GOVERNORATE);
        assertThat(testCity.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();
        city.setId(count.incrementAndGet());

        // Create the City
        CityDTO cityDTO = cityMapper.toDto(city);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        int databaseSizeBeforeDelete = cityRepository.findAll().size();

        // Delete the city
        restCityMockMvc
            .perform(delete(ENTITY_API_URL_ID, city.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
