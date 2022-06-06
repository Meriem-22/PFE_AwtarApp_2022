package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.ChildStatus;
import com.awtar.myapp.domain.ChildStatusItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.repository.ChildStatusItemRepository;
import com.awtar.myapp.service.ChildStatusItemService;
import com.awtar.myapp.service.dto.ChildStatusItemDTO;
import com.awtar.myapp.service.mapper.ChildStatusItemMapper;
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
 * Integration tests for the {@link ChildStatusItemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ChildStatusItemResourceIT {

    private static final Boolean DEFAULT_AFFECTED = false;
    private static final Boolean UPDATED_AFFECTED = true;

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/child-status-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChildStatusItemRepository childStatusItemRepository;

    @Mock
    private ChildStatusItemRepository childStatusItemRepositoryMock;

    @Autowired
    private ChildStatusItemMapper childStatusItemMapper;

    @Mock
    private ChildStatusItemService childStatusItemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChildStatusItemMockMvc;

    private ChildStatusItem childStatusItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildStatusItem createEntity(EntityManager em) {
        ChildStatusItem childStatusItem = new ChildStatusItem().affected(DEFAULT_AFFECTED).archivated(DEFAULT_ARCHIVATED);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        childStatusItem.setItem(item);
        // Add required entity
        ChildStatus childStatus;
        if (TestUtil.findAll(em, ChildStatus.class).isEmpty()) {
            childStatus = ChildStatusResourceIT.createEntity(em);
            em.persist(childStatus);
            em.flush();
        } else {
            childStatus = TestUtil.findAll(em, ChildStatus.class).get(0);
        }
        childStatusItem.setChildStatus(childStatus);
        return childStatusItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChildStatusItem createUpdatedEntity(EntityManager em) {
        ChildStatusItem childStatusItem = new ChildStatusItem().affected(UPDATED_AFFECTED).archivated(UPDATED_ARCHIVATED);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createUpdatedEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        childStatusItem.setItem(item);
        // Add required entity
        ChildStatus childStatus;
        if (TestUtil.findAll(em, ChildStatus.class).isEmpty()) {
            childStatus = ChildStatusResourceIT.createUpdatedEntity(em);
            em.persist(childStatus);
            em.flush();
        } else {
            childStatus = TestUtil.findAll(em, ChildStatus.class).get(0);
        }
        childStatusItem.setChildStatus(childStatus);
        return childStatusItem;
    }

    @BeforeEach
    public void initTest() {
        childStatusItem = createEntity(em);
    }

    @Test
    @Transactional
    void createChildStatusItem() throws Exception {
        int databaseSizeBeforeCreate = childStatusItemRepository.findAll().size();
        // Create the ChildStatusItem
        ChildStatusItemDTO childStatusItemDTO = childStatusItemMapper.toDto(childStatusItem);
        restChildStatusItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childStatusItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeCreate + 1);
        ChildStatusItem testChildStatusItem = childStatusItemList.get(childStatusItemList.size() - 1);
        assertThat(testChildStatusItem.getAffected()).isEqualTo(DEFAULT_AFFECTED);
        assertThat(testChildStatusItem.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createChildStatusItemWithExistingId() throws Exception {
        // Create the ChildStatusItem with an existing ID
        childStatusItem.setId(1L);
        ChildStatusItemDTO childStatusItemDTO = childStatusItemMapper.toDto(childStatusItem);

        int databaseSizeBeforeCreate = childStatusItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChildStatusItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childStatusItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChildStatusItems() throws Exception {
        // Initialize the database
        childStatusItemRepository.saveAndFlush(childStatusItem);

        // Get all the childStatusItemList
        restChildStatusItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(childStatusItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].affected").value(hasItem(DEFAULT_AFFECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChildStatusItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(childStatusItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChildStatusItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(childStatusItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChildStatusItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(childStatusItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChildStatusItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(childStatusItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getChildStatusItem() throws Exception {
        // Initialize the database
        childStatusItemRepository.saveAndFlush(childStatusItem);

        // Get the childStatusItem
        restChildStatusItemMockMvc
            .perform(get(ENTITY_API_URL_ID, childStatusItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(childStatusItem.getId().intValue()))
            .andExpect(jsonPath("$.affected").value(DEFAULT_AFFECTED.booleanValue()))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingChildStatusItem() throws Exception {
        // Get the childStatusItem
        restChildStatusItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChildStatusItem() throws Exception {
        // Initialize the database
        childStatusItemRepository.saveAndFlush(childStatusItem);

        int databaseSizeBeforeUpdate = childStatusItemRepository.findAll().size();

        // Update the childStatusItem
        ChildStatusItem updatedChildStatusItem = childStatusItemRepository.findById(childStatusItem.getId()).get();
        // Disconnect from session so that the updates on updatedChildStatusItem are not directly saved in db
        em.detach(updatedChildStatusItem);
        updatedChildStatusItem.affected(UPDATED_AFFECTED).archivated(UPDATED_ARCHIVATED);
        ChildStatusItemDTO childStatusItemDTO = childStatusItemMapper.toDto(updatedChildStatusItem);

        restChildStatusItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, childStatusItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childStatusItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeUpdate);
        ChildStatusItem testChildStatusItem = childStatusItemList.get(childStatusItemList.size() - 1);
        assertThat(testChildStatusItem.getAffected()).isEqualTo(UPDATED_AFFECTED);
        assertThat(testChildStatusItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingChildStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = childStatusItemRepository.findAll().size();
        childStatusItem.setId(count.incrementAndGet());

        // Create the ChildStatusItem
        ChildStatusItemDTO childStatusItemDTO = childStatusItemMapper.toDto(childStatusItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildStatusItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, childStatusItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childStatusItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChildStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = childStatusItemRepository.findAll().size();
        childStatusItem.setId(count.incrementAndGet());

        // Create the ChildStatusItem
        ChildStatusItemDTO childStatusItemDTO = childStatusItemMapper.toDto(childStatusItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildStatusItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(childStatusItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChildStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = childStatusItemRepository.findAll().size();
        childStatusItem.setId(count.incrementAndGet());

        // Create the ChildStatusItem
        ChildStatusItemDTO childStatusItemDTO = childStatusItemMapper.toDto(childStatusItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildStatusItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(childStatusItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChildStatusItemWithPatch() throws Exception {
        // Initialize the database
        childStatusItemRepository.saveAndFlush(childStatusItem);

        int databaseSizeBeforeUpdate = childStatusItemRepository.findAll().size();

        // Update the childStatusItem using partial update
        ChildStatusItem partialUpdatedChildStatusItem = new ChildStatusItem();
        partialUpdatedChildStatusItem.setId(childStatusItem.getId());

        partialUpdatedChildStatusItem.affected(UPDATED_AFFECTED).archivated(UPDATED_ARCHIVATED);

        restChildStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChildStatusItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChildStatusItem))
            )
            .andExpect(status().isOk());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeUpdate);
        ChildStatusItem testChildStatusItem = childStatusItemList.get(childStatusItemList.size() - 1);
        assertThat(testChildStatusItem.getAffected()).isEqualTo(UPDATED_AFFECTED);
        assertThat(testChildStatusItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateChildStatusItemWithPatch() throws Exception {
        // Initialize the database
        childStatusItemRepository.saveAndFlush(childStatusItem);

        int databaseSizeBeforeUpdate = childStatusItemRepository.findAll().size();

        // Update the childStatusItem using partial update
        ChildStatusItem partialUpdatedChildStatusItem = new ChildStatusItem();
        partialUpdatedChildStatusItem.setId(childStatusItem.getId());

        partialUpdatedChildStatusItem.affected(UPDATED_AFFECTED).archivated(UPDATED_ARCHIVATED);

        restChildStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChildStatusItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChildStatusItem))
            )
            .andExpect(status().isOk());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeUpdate);
        ChildStatusItem testChildStatusItem = childStatusItemList.get(childStatusItemList.size() - 1);
        assertThat(testChildStatusItem.getAffected()).isEqualTo(UPDATED_AFFECTED);
        assertThat(testChildStatusItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingChildStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = childStatusItemRepository.findAll().size();
        childStatusItem.setId(count.incrementAndGet());

        // Create the ChildStatusItem
        ChildStatusItemDTO childStatusItemDTO = childStatusItemMapper.toDto(childStatusItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChildStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, childStatusItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(childStatusItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChildStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = childStatusItemRepository.findAll().size();
        childStatusItem.setId(count.incrementAndGet());

        // Create the ChildStatusItem
        ChildStatusItemDTO childStatusItemDTO = childStatusItemMapper.toDto(childStatusItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(childStatusItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChildStatusItem() throws Exception {
        int databaseSizeBeforeUpdate = childStatusItemRepository.findAll().size();
        childStatusItem.setId(count.incrementAndGet());

        // Create the ChildStatusItem
        ChildStatusItemDTO childStatusItemDTO = childStatusItemMapper.toDto(childStatusItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChildStatusItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(childStatusItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChildStatusItem in the database
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChildStatusItem() throws Exception {
        // Initialize the database
        childStatusItemRepository.saveAndFlush(childStatusItem);

        int databaseSizeBeforeDelete = childStatusItemRepository.findAll().size();

        // Delete the childStatusItem
        restChildStatusItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, childStatusItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChildStatusItem> childStatusItemList = childStatusItemRepository.findAll();
        assertThat(childStatusItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
