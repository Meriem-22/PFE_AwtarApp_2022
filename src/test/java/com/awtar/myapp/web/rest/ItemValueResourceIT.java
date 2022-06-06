package com.awtar.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.awtar.myapp.IntegrationTest;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.ItemValue;
import com.awtar.myapp.repository.ItemValueRepository;
import com.awtar.myapp.service.ItemValueService;
import com.awtar.myapp.service.dto.ItemValueDTO;
import com.awtar.myapp.service.mapper.ItemValueMapper;
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
 * Integration tests for the {@link ItemValueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ItemValueResourceIT {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final LocalDate DEFAULT_PRICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_AVAILABLE_STOCK_QUANTITY = 1L;
    private static final Long UPDATED_AVAILABLE_STOCK_QUANTITY = 2L;

    private static final Boolean DEFAULT_ARCHIVATED = false;
    private static final Boolean UPDATED_ARCHIVATED = true;

    private static final String ENTITY_API_URL = "/api/item-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemValueRepository itemValueRepository;

    @Mock
    private ItemValueRepository itemValueRepositoryMock;

    @Autowired
    private ItemValueMapper itemValueMapper;

    @Mock
    private ItemValueService itemValueServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemValueMockMvc;

    private ItemValue itemValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemValue createEntity(EntityManager em) {
        ItemValue itemValue = new ItemValue()
            .price(DEFAULT_PRICE)
            .priceDate(DEFAULT_PRICE_DATE)
            .availableStockQuantity(DEFAULT_AVAILABLE_STOCK_QUANTITY)
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
        itemValue.setItem(item);
        return itemValue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemValue createUpdatedEntity(EntityManager em) {
        ItemValue itemValue = new ItemValue()
            .price(UPDATED_PRICE)
            .priceDate(UPDATED_PRICE_DATE)
            .availableStockQuantity(UPDATED_AVAILABLE_STOCK_QUANTITY)
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
        itemValue.setItem(item);
        return itemValue;
    }

    @BeforeEach
    public void initTest() {
        itemValue = createEntity(em);
    }

    @Test
    @Transactional
    void createItemValue() throws Exception {
        int databaseSizeBeforeCreate = itemValueRepository.findAll().size();
        // Create the ItemValue
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);
        restItemValueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemValueDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeCreate + 1);
        ItemValue testItemValue = itemValueList.get(itemValueList.size() - 1);
        assertThat(testItemValue.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testItemValue.getPriceDate()).isEqualTo(DEFAULT_PRICE_DATE);
        assertThat(testItemValue.getAvailableStockQuantity()).isEqualTo(DEFAULT_AVAILABLE_STOCK_QUANTITY);
        assertThat(testItemValue.getArchivated()).isEqualTo(DEFAULT_ARCHIVATED);
    }

    @Test
    @Transactional
    void createItemValueWithExistingId() throws Exception {
        // Create the ItemValue with an existing ID
        itemValue.setId(1L);
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);

        int databaseSizeBeforeCreate = itemValueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemValueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemValueRepository.findAll().size();
        // set the field null
        itemValue.setPrice(null);

        // Create the ItemValue, which fails.
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);

        restItemValueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemValueDTO)))
            .andExpect(status().isBadRequest());

        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemValueRepository.findAll().size();
        // set the field null
        itemValue.setPriceDate(null);

        // Create the ItemValue, which fails.
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);

        restItemValueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemValueDTO)))
            .andExpect(status().isBadRequest());

        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllItemValues() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        // Get all the itemValueList
        restItemValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].priceDate").value(hasItem(DEFAULT_PRICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].availableStockQuantity").value(hasItem(DEFAULT_AVAILABLE_STOCK_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].archivated").value(hasItem(DEFAULT_ARCHIVATED.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemValuesWithEagerRelationshipsIsEnabled() throws Exception {
        when(itemValueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemValueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(itemValueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemValuesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(itemValueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemValueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(itemValueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getItemValue() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        // Get the itemValue
        restItemValueMockMvc
            .perform(get(ENTITY_API_URL_ID, itemValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemValue.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.priceDate").value(DEFAULT_PRICE_DATE.toString()))
            .andExpect(jsonPath("$.availableStockQuantity").value(DEFAULT_AVAILABLE_STOCK_QUANTITY.intValue()))
            .andExpect(jsonPath("$.archivated").value(DEFAULT_ARCHIVATED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingItemValue() throws Exception {
        // Get the itemValue
        restItemValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewItemValue() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();

        // Update the itemValue
        ItemValue updatedItemValue = itemValueRepository.findById(itemValue.getId()).get();
        // Disconnect from session so that the updates on updatedItemValue are not directly saved in db
        em.detach(updatedItemValue);
        updatedItemValue
            .price(UPDATED_PRICE)
            .priceDate(UPDATED_PRICE_DATE)
            .availableStockQuantity(UPDATED_AVAILABLE_STOCK_QUANTITY)
            .archivated(UPDATED_ARCHIVATED);
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(updatedItemValue);

        restItemValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemValueDTO))
            )
            .andExpect(status().isOk());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
        ItemValue testItemValue = itemValueList.get(itemValueList.size() - 1);
        assertThat(testItemValue.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testItemValue.getPriceDate()).isEqualTo(UPDATED_PRICE_DATE);
        assertThat(testItemValue.getAvailableStockQuantity()).isEqualTo(UPDATED_AVAILABLE_STOCK_QUANTITY);
        assertThat(testItemValue.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void putNonExistingItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // Create the ItemValue
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // Create the ItemValue
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // Create the ItemValue
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemValueDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemValueWithPatch() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();

        // Update the itemValue using partial update
        ItemValue partialUpdatedItemValue = new ItemValue();
        partialUpdatedItemValue.setId(itemValue.getId());

        partialUpdatedItemValue.price(UPDATED_PRICE).priceDate(UPDATED_PRICE_DATE).archivated(UPDATED_ARCHIVATED);

        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemValue))
            )
            .andExpect(status().isOk());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
        ItemValue testItemValue = itemValueList.get(itemValueList.size() - 1);
        assertThat(testItemValue.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testItemValue.getPriceDate()).isEqualTo(UPDATED_PRICE_DATE);
        assertThat(testItemValue.getAvailableStockQuantity()).isEqualTo(DEFAULT_AVAILABLE_STOCK_QUANTITY);
        assertThat(testItemValue.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void fullUpdateItemValueWithPatch() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();

        // Update the itemValue using partial update
        ItemValue partialUpdatedItemValue = new ItemValue();
        partialUpdatedItemValue.setId(itemValue.getId());

        partialUpdatedItemValue
            .price(UPDATED_PRICE)
            .priceDate(UPDATED_PRICE_DATE)
            .availableStockQuantity(UPDATED_AVAILABLE_STOCK_QUANTITY)
            .archivated(UPDATED_ARCHIVATED);

        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItemValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItemValue))
            )
            .andExpect(status().isOk());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
        ItemValue testItemValue = itemValueList.get(itemValueList.size() - 1);
        assertThat(testItemValue.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testItemValue.getPriceDate()).isEqualTo(UPDATED_PRICE_DATE);
        assertThat(testItemValue.getAvailableStockQuantity()).isEqualTo(UPDATED_AVAILABLE_STOCK_QUANTITY);
        assertThat(testItemValue.getArchivated()).isEqualTo(UPDATED_ARCHIVATED);
    }

    @Test
    @Transactional
    void patchNonExistingItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // Create the ItemValue
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemValueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // Create the ItemValue
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItemValue() throws Exception {
        int databaseSizeBeforeUpdate = itemValueRepository.findAll().size();
        itemValue.setId(count.incrementAndGet());

        // Create the ItemValue
        ItemValueDTO itemValueDTO = itemValueMapper.toDto(itemValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemValueMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ItemValue in the database
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItemValue() throws Exception {
        // Initialize the database
        itemValueRepository.saveAndFlush(itemValue);

        int databaseSizeBeforeDelete = itemValueRepository.findAll().size();

        // Delete the itemValue
        restItemValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, itemValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemValue> itemValueList = itemValueRepository.findAll();
        assertThat(itemValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
