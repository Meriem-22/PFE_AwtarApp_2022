package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.DonationsReceived;
import com.awtar.myapp.domain.DonationsReceivedItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.repository.DonationsReceivedItemRepository;
import com.awtar.myapp.service.DonationsReceivedItemService;
import com.awtar.myapp.service.dto.DonationsReceivedItemDTO;
import com.awtar.myapp.service.mapper.DonationsReceivedItemMapper;
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

/**
 * Integration tests for the {@link DonationsReceivedItemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DonationsReceivedItemResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/donations-received-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonationsReceivedItemRepository donationsReceivedItemRepository;

    @Mock
    private DonationsReceivedItemRepository donationsReceivedItemRepositoryMock;

    @Autowired
    private DonationsReceivedItemMapper donationsReceivedItemMapper;

    @Mock
    private DonationsReceivedItemService donationsReceivedItemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonationsReceivedItemMockMvc;

    private DonationsReceivedItem donationsReceivedItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationsReceivedItem createEntity(EntityManager em) {
        DonationsReceivedItem donationsReceivedItem = new DonationsReceivedItem()
            .quantity(DEFAULT_QUANTITY)
            .date(DEFAULT_DATE)
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
        donationsReceivedItem.setItem(item);
        // Add required entity
        DonationsReceived donationsReceived;
        if (TestUtil.findAll(em, DonationsReceived.class).isEmpty()) {
            donationsReceived = DonationsReceivedResourceIT.createEntity(em);
            em.persist(donationsReceived);
            em.flush();
        } else {
            donationsReceived = TestUtil.findAll(em, DonationsReceived.class).get(0);
        }
        donationsReceivedItem.setDonationsReceived(donationsReceived);
        return donationsReceivedItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DonationsReceivedItem createUpdatedEntity(EntityManager em) {
        DonationsReceivedItem donationsReceivedItem = new DonationsReceivedItem()
            .quantity(UPDATED_QUANTITY)
            .date(UPDATED_DATE)
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
        donationsReceivedItem.setItem(item);
        // Add required entity
        DonationsReceived donationsReceived;
        if (TestUtil.findAll(em, DonationsReceived.class).isEmpty()) {
            donationsReceived = DonationsReceivedResourceIT.createUpdatedEntity(em);
            em.persist(donationsReceived);
            em.flush();
        } else {
            donationsReceived = TestUtil.findAll(em, DonationsReceived.class).get(0);
        }
        donationsReceivedItem.setDonationsReceived(donationsReceived);
        return donationsReceivedItem;
    }

    @BeforeEach
    public void initTest() {
        donationsReceivedItem = createEntity(em);
    }

    @Test
    @Transactional
    void createDonationsReceivedItem() throws Exception {
        int databaseSizeBeforeCreate = donationsReceivedItemRepository.findAll().size();
        // Create the DonationsReceivedItem
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);
        restDonationsReceivedItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeCreate + 1);
        DonationsReceivedItem testDonationsReceivedItem = donationsReceivedItemList.get(donationsReceivedItemList.size() - 1);
        assertThat(testDonationsReceivedItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDonationsReceivedItem.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDonationsReceivedItem.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createDonationsReceivedItemWithExistingId() throws Exception {
        // Create the DonationsReceivedItem with an existing ID
        donationsReceivedItem.setId(1L);
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);

        int databaseSizeBeforeCreate = donationsReceivedItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonationsReceivedItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationsReceivedItemRepository.findAll().size();
        // set the field null
        donationsReceivedItem.setQuantity(null);

        // Create the DonationsReceivedItem, which fails.
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);

        restDonationsReceivedItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationsReceivedItemRepository.findAll().size();
        // set the field null
        donationsReceivedItem.setDate(null);

        // Create the DonationsReceivedItem, which fails.
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);

        restDonationsReceivedItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDonationsReceivedItems() throws Exception {
        // Initialize the database
        donationsReceivedItemRepository.saveAndFlush(donationsReceivedItem);

        // Get all the donationsReceivedItemList
        restDonationsReceivedItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donationsReceivedItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonationsReceivedItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(donationsReceivedItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonationsReceivedItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(donationsReceivedItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonationsReceivedItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(donationsReceivedItemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonationsReceivedItemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(donationsReceivedItemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDonationsReceivedItem() throws Exception {
        // Initialize the database
        donationsReceivedItemRepository.saveAndFlush(donationsReceivedItem);

        // Get the donationsReceivedItem
        restDonationsReceivedItemMockMvc
            .perform(get(ENTITY_API_URL_ID, donationsReceivedItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donationsReceivedItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingDonationsReceivedItem() throws Exception {
        // Get the donationsReceivedItem
        restDonationsReceivedItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDonationsReceivedItem() throws Exception {
        // Initialize the database
        donationsReceivedItemRepository.saveAndFlush(donationsReceivedItem);

        int databaseSizeBeforeUpdate = donationsReceivedItemRepository.findAll().size();

        // Update the donationsReceivedItem
        DonationsReceivedItem updatedDonationsReceivedItem = donationsReceivedItemRepository.findById(donationsReceivedItem.getId()).get();
        // Disconnect from session so that the updates on updatedDonationsReceivedItem are not directly saved in db
        em.detach(updatedDonationsReceivedItem);
        updatedDonationsReceivedItem.quantity(UPDATED_QUANTITY).date(UPDATED_DATE).archivated(UPDATED_ARCHIVATED);
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(updatedDonationsReceivedItem);

        restDonationsReceivedItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationsReceivedItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeUpdate);
        DonationsReceivedItem testDonationsReceivedItem = donationsReceivedItemList.get(donationsReceivedItemList.size() - 1);
        assertThat(testDonationsReceivedItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDonationsReceivedItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDonationsReceivedItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingDonationsReceivedItem() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedItemRepository.findAll().size();
        donationsReceivedItem.setId(count.incrementAndGet());

        // Create the DonationsReceivedItem
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationsReceivedItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donationsReceivedItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonationsReceivedItem() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedItemRepository.findAll().size();
        donationsReceivedItem.setId(count.incrementAndGet());

        // Create the DonationsReceivedItem
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsReceivedItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonationsReceivedItem() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedItemRepository.findAll().size();
        donationsReceivedItem.setId(count.incrementAndGet());

        // Create the DonationsReceivedItem
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsReceivedItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonationsReceivedItemWithPatch() throws Exception {
        // Initialize the database
        donationsReceivedItemRepository.saveAndFlush(donationsReceivedItem);

        int databaseSizeBeforeUpdate = donationsReceivedItemRepository.findAll().size();

        // Update the donationsReceivedItem using partial update
        DonationsReceivedItem partialUpdatedDonationsReceivedItem = new DonationsReceivedItem();
        partialUpdatedDonationsReceivedItem.setId(donationsReceivedItem.getId());

        restDonationsReceivedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationsReceivedItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationsReceivedItem))
            )
            .andExpect(status().isOk());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeUpdate);
        DonationsReceivedItem testDonationsReceivedItem = donationsReceivedItemList.get(donationsReceivedItemList.size() - 1);
        assertThat(testDonationsReceivedItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDonationsReceivedItem.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDonationsReceivedItem.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateDonationsReceivedItemWithPatch() throws Exception {
        // Initialize the database
        donationsReceivedItemRepository.saveAndFlush(donationsReceivedItem);

        int databaseSizeBeforeUpdate = donationsReceivedItemRepository.findAll().size();

        // Update the donationsReceivedItem using partial update
        DonationsReceivedItem partialUpdatedDonationsReceivedItem = new DonationsReceivedItem();
        partialUpdatedDonationsReceivedItem.setId(donationsReceivedItem.getId());

        partialUpdatedDonationsReceivedItem.quantity(UPDATED_QUANTITY).date(UPDATED_DATE).archivated(UPDATED_ARCHIVATED);

        restDonationsReceivedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonationsReceivedItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonationsReceivedItem))
            )
            .andExpect(status().isOk());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeUpdate);
        DonationsReceivedItem testDonationsReceivedItem = donationsReceivedItemList.get(donationsReceivedItemList.size() - 1);
        assertThat(testDonationsReceivedItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDonationsReceivedItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDonationsReceivedItem.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingDonationsReceivedItem() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedItemRepository.findAll().size();
        donationsReceivedItem.setId(count.incrementAndGet());

        // Create the DonationsReceivedItem
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationsReceivedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donationsReceivedItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonationsReceivedItem() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedItemRepository.findAll().size();
        donationsReceivedItem.setId(count.incrementAndGet());

        // Create the DonationsReceivedItem
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsReceivedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonationsReceivedItem() throws Exception {
        int databaseSizeBeforeUpdate = donationsReceivedItemRepository.findAll().size();
        donationsReceivedItem.setId(count.incrementAndGet());

        // Create the DonationsReceivedItem
        DonationsReceivedItemDTO donationsReceivedItemDTO = donationsReceivedItemMapper.toDto(donationsReceivedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonationsReceivedItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donationsReceivedItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DonationsReceivedItem in the database
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonationsReceivedItem() throws Exception {
        // Initialize the database
        donationsReceivedItemRepository.saveAndFlush(donationsReceivedItem);

        int databaseSizeBeforeDelete = donationsReceivedItemRepository.findAll().size();

        // Delete the donationsReceivedItem
        restDonationsReceivedItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, donationsReceivedItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DonationsReceivedItem> donationsReceivedItemList = donationsReceivedItemRepository.findAll();
        assertThat(donationsReceivedItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
