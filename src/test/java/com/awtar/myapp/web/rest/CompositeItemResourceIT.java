package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.CompositeItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.repository.CompositeItemRepository;
import com.awtar.myapp.service.CompositeItemService;
import com.awtar.myapp.service.dto.CompositeItemDTO;
import com.awtar.myapp.service.mapper.CompositeItemMapper;
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
 * Integration tests for the {@link CompositeItemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CompositeItemResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/composite-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompositeItemRepository compositeItemRepository;

    @Mock
    private CompositeItemRepository compositeItemRepositoryMock;

    @Autowired
    private CompositeItemMapper compositeItemMapper;

    @Mock
    private CompositeItemService compositeItemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompositeItemMockMvc;

    private CompositeItem compositeItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompositeItem createEntity(EntityManager em) {
        CompositeItem compositeItem = new CompositeItem().quantity(DEFAULT_QUANTITY).archivated(DEFAULT_ARCHIVATED);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        compositeItem.setComposant(item);
        // Add required entity
        compositeItem.setComposer(item);
        return compositeItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompositeItem createUpdatedEntity(EntityManager em) {
        CompositeItem compositeItem = new CompositeItem().quantity(UPDATED_QUANTITY).archivated(UPDATED_ARCHIVATED);
        // Add required entity
        Item item;
        if (TestUtil.findAll(em, Item.class).isEmpty()) {
            item = ItemResourceIT.createUpdatedEntity(em);
            em.persist(item);
            em.flush();
        } else {
            item = TestUtil.findAll(em, Item.class).get(0);
        }
        compositeItem.setComposant(item);
        // Add required entity
        compositeItem.setComposer(item);
        return compositeItem;
    }

    @BeforeEach
    public void initTest() {
        compositeItem = createEntity(em);
    }

    @Test
    @Transactional
    void createCompositeItem() throws Exception {
        int databaseSizeBeforeCreate = compositeItemRepository.findAll().size();
        // Create the CompositeItem
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(compositeItem);
        restCompositeItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeCreate + 1);
        CompositeItem testCompositeItem = compositeItemList.get(compositeItemList.size() - 1);
        assertThat(testCompositeItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCompositeItem.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createCompositeItemWithExistingId() throws Exception {
        // Create the CompositeItem with an existing ID
        compositeItem.setId(1L);
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(compositeItem);

        int databaseSizeBeforeCreate = compositeItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompositeItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = compositeItemRepository.findAll().size();
        // set the field null
        compositeItem.setQuantity(null);

        // Create the CompositeItem, which fails.
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(compositeItem);

        restCompositeItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompositeItems() throws Exception {
        // Initialize the database
        compositeItemRepository.saveAndFlush(compositeItem);

        // Get all the compositeItemList
        restCompositeItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compositeItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompositeItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(compositeItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompositeItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(compositeItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCompositeItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(compositeItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompositeItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(compositeItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCompositeItem() throws Exception {
        // Initialize the database
        compositeItemRepository.saveAndFlush(compositeItem);

        // Get the compositeItem
        restCompositeItemMockMvc
            .perform(get(ENTITY_API_URL_ID, compositeItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compositeItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCompositeItem() throws Exception {
        // Get the compositeItem
        restCompositeItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompositeItem() throws Exception {
        // Initialize the database
        compositeItemRepository.saveAndFlush(compositeItem);

        int databaseSizeBeforeUpdate = compositeItemRepository.findAll().size();

        // Update the compositeItem
        CompositeItem updatedCompositeItem = compositeItemRepository.findById(compositeItem.getId()).get();
        // Disconnect from session so that the updates on updatedCompositeItem are not directly saved in db
        em.detach(updatedCompositeItem);
        updatedCompositeItem.quantity(UPDATED_QUANTITY).archivated(UPDATED_ARCHIVATED);
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(updatedCompositeItem);

        restCompositeItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compositeItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeUpdate);
        CompositeItem testCompositeItem = compositeItemList.get(compositeItemList.size() - 1);
        assertThat(testCompositeItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCompositeItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingCompositeItem() throws Exception {
        int databaseSizeBeforeUpdate = compositeItemRepository.findAll().size();
        compositeItem.setId(count.incrementAndGet());

        // Create the CompositeItem
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(compositeItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompositeItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compositeItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompositeItem() throws Exception {
        int databaseSizeBeforeUpdate = compositeItemRepository.findAll().size();
        compositeItem.setId(count.incrementAndGet());

        // Create the CompositeItem
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(compositeItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompositeItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompositeItem() throws Exception {
        int databaseSizeBeforeUpdate = compositeItemRepository.findAll().size();
        compositeItem.setId(count.incrementAndGet());

        // Create the CompositeItem
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(compositeItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompositeItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompositeItemWithPatch() throws Exception {
        // Initialize the database
        compositeItemRepository.saveAndFlush(compositeItem);

        int databaseSizeBeforeUpdate = compositeItemRepository.findAll().size();

        // Update the compositeItem using partial update
        CompositeItem partialUpdatedCompositeItem = new CompositeItem();
        partialUpdatedCompositeItem.setId(compositeItem.getId());

        partialUpdatedCompositeItem.archivated(UPDATED_ARCHIVATED);

        restCompositeItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompositeItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompositeItem))
            )
            .andExpect(status().isOk());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeUpdate);
        CompositeItem testCompositeItem = compositeItemList.get(compositeItemList.size() - 1);
        assertThat(testCompositeItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCompositeItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateCompositeItemWithPatch() throws Exception {
        // Initialize the database
        compositeItemRepository.saveAndFlush(compositeItem);

        int databaseSizeBeforeUpdate = compositeItemRepository.findAll().size();

        // Update the compositeItem using partial update
        CompositeItem partialUpdatedCompositeItem = new CompositeItem();
        partialUpdatedCompositeItem.setId(compositeItem.getId());

        partialUpdatedCompositeItem.quantity(UPDATED_QUANTITY).archivated(UPDATED_ARCHIVATED);

        restCompositeItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompositeItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompositeItem))
            )
            .andExpect(status().isOk());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeUpdate);
        CompositeItem testCompositeItem = compositeItemList.get(compositeItemList.size() - 1);
        assertThat(testCompositeItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCompositeItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingCompositeItem() throws Exception {
        int databaseSizeBeforeUpdate = compositeItemRepository.findAll().size();
        compositeItem.setId(count.incrementAndGet());

        // Create the CompositeItem
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(compositeItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompositeItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, compositeItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompositeItem() throws Exception {
        int databaseSizeBeforeUpdate = compositeItemRepository.findAll().size();
        compositeItem.setId(count.incrementAndGet());

        // Create the CompositeItem
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(compositeItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompositeItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompositeItem() throws Exception {
        int databaseSizeBeforeUpdate = compositeItemRepository.findAll().size();
        compositeItem.setId(count.incrementAndGet());

        // Create the CompositeItem
        CompositeItemDTO compositeItemDTO = compositeItemMapper.toDto(compositeItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompositeItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compositeItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompositeItem in the database
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompositeItem() throws Exception {
        // Initialize the database
        compositeItemRepository.saveAndFlush(compositeItem);

        int databaseSizeBeforeDelete = compositeItemRepository.findAll().size();

        // Delete the compositeItem
        restCompositeItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, compositeItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompositeItem> compositeItemList = compositeItemRepository.findAll();
        assertThat(compositeItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
