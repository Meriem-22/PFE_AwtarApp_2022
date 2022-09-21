package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.ChildStatus;
import com.awtar.myapp.domain.ChildStatusItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.ItemValue;
import com.awtar.myapp.domain.SchoolLevelItem;
import com.awtar.myapp.domain.enumeration.Status;
import com.awtar.myapp.repository.ChildStatusItemRepository;
import com.awtar.myapp.repository.ChildStatusRepository;
import com.awtar.myapp.repository.ItemRepository;
import com.awtar.myapp.repository.ItemValueRepository;
import com.awtar.myapp.repository.SchoolLevelItemRepository;
import com.awtar.myapp.repository.SchoolLevelRepository;
import com.awtar.myapp.service.ItemService;
import com.awtar.myapp.service.dto.ItemDTO;
import com.awtar.myapp.service.mapper.ItemMapper;
import com.awtar.myapp.service.mapper.ItemValueMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Item}.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final ItemValueMapper itemValueMapper;

    private final ItemValueRepository itemValueRepository;

    private final SchoolLevelRepository schoolLevelRepository;

    private final SchoolLevelItemRepository schoolLevelItemRepository;

    private final ChildStatusItemRepository childStatusItemRepository;

    private final ChildStatusRepository childStatusRepository;

    public ItemServiceImpl(
        ItemRepository itemRepository,
        ItemMapper itemMapper,
        ItemValueMapper itemValueMapper,
        ItemValueRepository itemValueRepository,
        SchoolLevelRepository schoolLevelRepository,
        SchoolLevelItemRepository schoolLevelItemRepository,
        ChildStatusItemRepository childStatusItemRepository,
        ChildStatusRepository childStatusRepository
    ) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
        this.itemValueMapper = itemValueMapper;
        this.itemValueRepository = itemValueRepository;
        this.schoolLevelRepository = schoolLevelRepository;
        this.schoolLevelItemRepository = schoolLevelItemRepository;
        this.childStatusItemRepository = childStatusItemRepository;
        this.childStatusRepository = childStatusRepository;
    }

    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        log.debug("Request to save Item : {}", itemDTO);
        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        return itemMapper.toDto(item);
    }

    @Override
    public ItemDTO update(ItemDTO itemDTO) {
        log.debug("Request to save Item : {}", itemDTO);
        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        return itemMapper.toDto(item);
    }

    @Override
    public Optional<ItemDTO> partialUpdate(ItemDTO itemDTO) {
        log.debug("Request to partially update Item : {}", itemDTO);

        return itemRepository
            .findById(itemDTO.getId())
            .map(existingItem -> {
                itemMapper.partialUpdate(existingItem, itemDTO);

                return existingItem;
            })
            .map(itemRepository::save)
            .map(itemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        return itemRepository.findAll(pageable).map(itemMapper::toDto);
    }

    public Page<ItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return itemRepository.findAllWithEagerRelationships(pageable).map(itemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemDTO> findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        return itemRepository.findOneWithEagerRelationships(id).map(itemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.deleteById(id);
    }

    @Override
    public List<ItemDTO> findItemsWithNature(Long id) {
        List<Item> items = itemRepository.findItemsWithNature(id);
        return itemMapper.toDto(items);
    }

    @Override
    public List<ItemDTO> findAllItems() {
        List<Item> items = itemRepository.findAllItems();
        return itemMapper.toDto(items);
    }

    @Override
    public List<ItemDTO> findAllDetailsItems() {
        List<ItemDTO> items = itemRepository.findAllDetailsItems();
        return items;
    }

    @Override
    public List<ItemDTO> findAllSchoolItemsDetails() {
        List<ItemDTO> items = itemRepository.findAllSchoolItemsDetails();
        return items;
    }

    @Override
    public List<ItemDTO> findAllCompositeurItemsDetails(Long id) {
        List<ItemDTO> items = itemRepository.findAllCompositeurDetailItems(id);
        return items;
    }

    @Override
    public ItemDTO addSimpleItem(ItemDTO itemDTO) {
        ItemValue value = new ItemValue();
        Item item = itemMapper.toEntity(itemDTO);
        item = itemRepository.save(item);
        value.setPrice(itemDTO.getPrice());
        value.setPriceDate(itemDTO.getPriceDate());
        value.setArchivated(false);
        value.setAvailableStockQuantity(itemDTO.getQuantityToAdd());
        value.setItem(item);
        value = itemValueRepository.save(value);
        return itemMapper.toDto(item);
    }

    @Override
    public ItemDTO addSimpleSchoolItem(ItemDTO itemDTO) {
        ItemValue value = new ItemValue();
        Item item = itemMapper.toEntity(itemDTO);
        Long levelsId[];
        Integer qtOfLevel[];
        ChildStatus status[];
        item = itemRepository.save(item);
        value.setPrice(itemDTO.getPrice());
        value.setPriceDate(itemDTO.getPriceDate());
        value.setArchivated(false);
        value.setAvailableStockQuantity(itemDTO.getQuantityToAdd());
        value.setItem(item);
        value = itemValueRepository.save(value);
        levelsId = itemDTO.getSchoolLevelIemTab();
        qtOfLevel = itemDTO.getQuantitySchoolItemTab();

        status = itemDTO.getChildStatus();

        for (int i = 0; i < status.length; i++) {
            ChildStatusItem childStatus = new ChildStatusItem();
            ChildStatus s = status[i];
            childStatus.setAffected(true);
            childStatus.setArchivated(false);
            childStatus.setItem(item);
            childStatus.setChildStatus(s);
            childStatus = childStatusItemRepository.save(childStatus);
        }

        for (int j = 0; j < qtOfLevel.length; j++) {
            SchoolLevelItem sl = new SchoolLevelItem();
            sl.setArchivated(false);
            sl.setIsSchoolItem(true);
            sl.setItem(item);
            sl.setSchoolLevel(schoolLevelRepository.getById(levelsId[j]));
            sl.setQuantityNeeded(qtOfLevel[j]);
            sl = schoolLevelItemRepository.save(sl);
        }
        return itemMapper.toDto(item);
    }
}
