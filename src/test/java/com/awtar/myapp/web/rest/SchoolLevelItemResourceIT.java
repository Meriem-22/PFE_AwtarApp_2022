package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.domain.SchoolLevelItem;
import com.awtar.myapp.repository.SchoolLevelItemRepository;
import com.awtar.myapp.service.SchoolLevelItemService;
import com.awtar.myapp.service.dto.SchoolLevelItemDTO;
import com.awtar.myapp.service.mapper.SchoolLevelItemMapper;
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
 * Integration tests for the {@link SchoolLevelItemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SchoolLevelItemResourceIT {

    private static final Boolean DEFAULT_IS_SCHOOL_ITEM = false;
    private static final Boolean UPDATED_IS_SCHOOL_ITEM = true;

    private static final Integer DEFAULT_QUANTITY_NEEDED = 1;
    private static final Integer UPDATED_QUANTITY_NEEDED = 2;

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/school-level-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchoolLevelItemRepository schoolLevelItemRepository;

    @Mock
    private SchoolLevelItemRepository schoolLevelItemRepositoryMock;

    @Autowired
    private SchoolLevelItemMapper schoolLevelItemMapper;

    @Mock
    private SchoolLevelItemService schoolLevelItemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchoolLevelItemMockMvc;

    private SchoolLevelItem schoolLevelItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchoolLevelItem createEntity(EntityManager em) {
        SchoolLevelItem schoolLevelItem = new SchoolLevelItem()
            .isSchoolItem(DEFAULT_IS_SCHOOL_ITEM)
            .quantityNeeded(DEFAULT_QUANTITY_NEEDED)
            .archivated(DEFAULT_ARCHIVATED);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        schoolLevelItem.setItem(item);
        // Add required entity
        SchoolLevel schoolLevel;
        if (TestUtil.findAll(em, SchoolLevel.class).isEmpty()) {
            schoolLevel = SchoolLevelResourceIT.createEntity(em);
            em.persist(schoolLevel);
            em.flush();
        } else {
            schoolLevel = TestUtil.findAll(em, SchoolLevel.class).get(0);
        }
        schoolLevelItem.setSchoolLevel(schoolLevel);
        return schoolLevelItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchoolLevelItem createUpdatedEntity(EntityManager em) {
        SchoolLevelItem schoolLevelItem = new SchoolLevelItem()
            .isSchoolItem(UPDATED_IS_SCHOOL_ITEM)
            .quantityNeeded(UPDATED_QUANTITY_NEEDED)
            .archivated(UPDATED_ARCHIVATED);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createUpdatedEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        schoolLevelItem.setItem(item);
        // Add required entity
        SchoolLevel schoolLevel;
        if (TestUtil.findAll(em, SchoolLevel.class).isEmpty()) {
            schoolLevel = SchoolLevelResourceIT.createUpdatedEntity(em);
            em.persist(schoolLevel);
            em.flush();
        } else {
            schoolLevel = TestUtil.findAll(em, SchoolLevel.class).get(0);
        }
        schoolLevelItem.setSchoolLevel(schoolLevel);
        return schoolLevelItem;
    }

    @BeforeEach
    public void initTest() {
        schoolLevelItem = createEntity(em);
    }

    @Test
    @Transactional
    void createSchoolLevelItem() throws Exception {
        int databaseSizeBeforeCreate = schoolLevelItemRepository.findAll().size();
        // Create the SchoolLevelItem
        SchoolLevelItemDTO schoolLevelItemDTO = schoolLevelItemMapper.toDto(schoolLevelItem);
        restSchoolLevelItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolLevelItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeCreate + 1);
        SchoolLevelItem testSchoolLevelItem = schoolLevelItemList.get(schoolLevelItemList.size() - 1);
        assertThat(testSchoolLevelItem.getIsSchoolItem()).isEqualTo(DEFAULT_IS_SCHOOL_ITEM);
        assertThat(testSchoolLevelItem.getQuantityNeeded()).isEqualTo(DEFAULT_QUANTITY_NEEDED);
        assertThat(testSchoolLevelItem.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createSchoolLevelItemWithExistingId() throws Exception {
        // Create the SchoolLevelItem with an existing ID
        schoolLevelItem.setId(1L);
        SchoolLevelItemDTO schoolLevelItemDTO = schoolLevelItemMapper.toDto(schoolLevelItem);

        int databaseSizeBeforeCreate = schoolLevelItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolLevelItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolLevelItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSchoolLevelItems() throws Exception {
        // Initialize the database
        schoolLevelItemRepository.saveAndFlush(schoolLevelItem);

        // Get all the schoolLevelItemList
        restSchoolLevelItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolLevelItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].isSchoolItem").value(hasItem(DEFAULT_IS_SCHOOL_ITEM.booleanValue())))
            .andExpect(jsonPath("$.[*].quantityNeeded").value(hasItem(DEFAULT_QUANTITY_NEEDED)))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSchoolLevelItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(schoolLevelItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSchoolLevelItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(schoolLevelItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSchoolLevelItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(schoolLevelItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSchoolLevelItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(schoolLevelItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSchoolLevelItem() throws Exception {
        // Initialize the database
        schoolLevelItemRepository.saveAndFlush(schoolLevelItem);

        // Get the schoolLevelItem
        restSchoolLevelItemMockMvc
            .perform(get(ENTITY_API_URL_ID, schoolLevelItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schoolLevelItem.getId().intValue()))
            .andExpect(jsonPath("$.isSchoolItem").value(DEFAULT_IS_SCHOOL_ITEM.booleanValue()))
            .andExpect(jsonPath("$.quantityNeeded").value(DEFAULT_QUANTITY_NEEDED))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSchoolLevelItem() throws Exception {
        // Get the schoolLevelItem
        restSchoolLevelItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSchoolLevelItem() throws Exception {
        // Initialize the database
        schoolLevelItemRepository.saveAndFlush(schoolLevelItem);

        int databaseSizeBeforeUpdate = schoolLevelItemRepository.findAll().size();

        // Update the schoolLevelItem
        SchoolLevelItem updatedSchoolLevelItem = schoolLevelItemRepository.findById(schoolLevelItem.getId()).get();
        // Disconnect from session so that the updates on updatedSchoolLevelItem are not directly saved in db
        em.detach(updatedSchoolLevelItem);
        updatedSchoolLevelItem.isSchoolItem(UPDATED_IS_SCHOOL_ITEM).quantityNeeded(UPDATED_QUANTITY_NEEDED).archivated(UPDATED_ARCHIVATED);
        SchoolLevelItemDTO schoolLevelItemDTO = schoolLevelItemMapper.toDto(updatedSchoolLevelItem);

        restSchoolLevelItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schoolLevelItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeUpdate);
        SchoolLevelItem testSchoolLevelItem = schoolLevelItemList.get(schoolLevelItemList.size() - 1);
        assertThat(testSchoolLevelItem.getIsSchoolItem()).isEqualTo(UPDATED_IS_SCHOOL_ITEM);
        assertThat(testSchoolLevelItem.getQuantityNeeded()).isEqualTo(UPDATED_QUANTITY_NEEDED);
        assertThat(testSchoolLevelItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingSchoolLevelItem() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelItemRepository.findAll().size();
        schoolLevelItem.setId(count.incrementAndGet());

        // Create the SchoolLevelItem
        SchoolLevelItemDTO schoolLevelItemDTO = schoolLevelItemMapper.toDto(schoolLevelItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolLevelItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schoolLevelItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchoolLevelItem() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelItemRepository.findAll().size();
        schoolLevelItem.setId(count.incrementAndGet());

        // Create the SchoolLevelItem
        SchoolLevelItemDTO schoolLevelItemDTO = schoolLevelItemMapper.toDto(schoolLevelItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolLevelItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchoolLevelItem() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelItemRepository.findAll().size();
        schoolLevelItem.setId(count.incrementAndGet());

        // Create the SchoolLevelItem
        SchoolLevelItemDTO schoolLevelItemDTO = schoolLevelItemMapper.toDto(schoolLevelItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolLevelItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schoolLevelItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchoolLevelItemWithPatch() throws Exception {
        // Initialize the database
        schoolLevelItemRepository.saveAndFlush(schoolLevelItem);

        int databaseSizeBeforeUpdate = schoolLevelItemRepository.findAll().size();

        // Update the schoolLevelItem using partial update
        SchoolLevelItem partialUpdatedSchoolLevelItem = new SchoolLevelItem();
        partialUpdatedSchoolLevelItem.setId(schoolLevelItem.getId());

        partialUpdatedSchoolLevelItem.archivated(UPDATED_ARCHIVATED);

        restSchoolLevelItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchoolLevelItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchoolLevelItem))
            )
            .andExpect(status().isOk());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeUpdate);
        SchoolLevelItem testSchoolLevelItem = schoolLevelItemList.get(schoolLevelItemList.size() - 1);
        assertThat(testSchoolLevelItem.getIsSchoolItem()).isEqualTo(DEFAULT_IS_SCHOOL_ITEM);
        assertThat(testSchoolLevelItem.getQuantityNeeded()).isEqualTo(DEFAULT_QUANTITY_NEEDED);
        assertThat(testSchoolLevelItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateSchoolLevelItemWithPatch() throws Exception {
        // Initialize the database
        schoolLevelItemRepository.saveAndFlush(schoolLevelItem);

        int databaseSizeBeforeUpdate = schoolLevelItemRepository.findAll().size();

        // Update the schoolLevelItem using partial update
        SchoolLevelItem partialUpdatedSchoolLevelItem = new SchoolLevelItem();
        partialUpdatedSchoolLevelItem.setId(schoolLevelItem.getId());

        partialUpdatedSchoolLevelItem
            .isSchoolItem(UPDATED_IS_SCHOOL_ITEM)
            .quantityNeeded(UPDATED_QUANTITY_NEEDED)
            .archivated(UPDATED_ARCHIVATED);

        restSchoolLevelItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchoolLevelItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchoolLevelItem))
            )
            .andExpect(status().isOk());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeUpdate);
        SchoolLevelItem testSchoolLevelItem = schoolLevelItemList.get(schoolLevelItemList.size() - 1);
        assertThat(testSchoolLevelItem.getIsSchoolItem()).isEqualTo(UPDATED_IS_SCHOOL_ITEM);
        assertThat(testSchoolLevelItem.getQuantityNeeded()).isEqualTo(UPDATED_QUANTITY_NEEDED);
        assertThat(testSchoolLevelItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingSchoolLevelItem() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelItemRepository.findAll().size();
        schoolLevelItem.setId(count.incrementAndGet());

        // Create the SchoolLevelItem
        SchoolLevelItemDTO schoolLevelItemDTO = schoolLevelItemMapper.toDto(schoolLevelItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolLevelItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schoolLevelItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchoolLevelItem() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelItemRepository.findAll().size();
        schoolLevelItem.setId(count.incrementAndGet());

        // Create the SchoolLevelItem
        SchoolLevelItemDTO schoolLevelItemDTO = schoolLevelItemMapper.toDto(schoolLevelItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolLevelItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchoolLevelItem() throws Exception {
        int databaseSizeBeforeUpdate = schoolLevelItemRepository.findAll().size();
        schoolLevelItem.setId(count.incrementAndGet());

        // Create the SchoolLevelItem
        SchoolLevelItemDTO schoolLevelItemDTO = schoolLevelItemMapper.toDto(schoolLevelItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchoolLevelItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schoolLevelItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchoolLevelItem in the database
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchoolLevelItem() throws Exception {
        // Initialize the database
        schoolLevelItemRepository.saveAndFlush(schoolLevelItem);

        int databaseSizeBeforeDelete = schoolLevelItemRepository.findAll().size();

        // Delete the schoolLevelItem
        restSchoolLevelItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, schoolLevelItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchoolLevelItem> schoolLevelItemList = schoolLevelItemRepository.findAll();
        assertThat(schoolLevelItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
