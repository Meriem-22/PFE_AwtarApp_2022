package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.ItemValue;
import com.awtar.myapp.repository.ItemValueRepository;
import com.awtar.myapp.service.ItemValueService;
import com.awtar.myapp.service.dto.ItemValueDTO;
import com.awtar.myapp.service.mapper.ItemValueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ItemValue}.
 */
@Service
@Transactional
public class ItemValueServiceImpl implements ItemValueService {

    private final Logger log = LoggerFactory.getLogger(ItemValueServiceImpl.class);

    private final ItemValueRepository itemValueRepository;

    private final ItemValueMapper itemValueMapper;

    public ItemValueServiceImpl(ItemValueRepository itemValueRepository, ItemValueMapper itemValueMapper) {
        this.itemValueRepository = itemValueRepository;
        this.itemValueMapper = itemValueMapper;
    }

    @Override
    public ItemValueDTO save(ItemValueDTO itemValueDTO) {
        log.debug("Request to save ItemValue : {}", itemValueDTO);
        ItemValue itemValue = itemValueMapper.toEntity(itemValueDTO);
        itemValue = itemValueRepository.save(itemValue);
        return itemValueMapper.toDto(itemValue);
    }

    @Override
    public ItemValueDTO update(ItemValueDTO itemValueDTO) {
        log.debug("Request to save ItemValue : {}", itemValueDTO);
        ItemValue itemValue = itemValueMapper.toEntity(itemValueDTO);
        itemValue = itemValueRepository.save(itemValue);
        return itemValueMapper.toDto(itemValue);
    }

    @Override
    public Optional<ItemValueDTO> partialUpdate(ItemValueDTO itemValueDTO) {
        log.debug("Request to partially update ItemValue : {}", itemValueDTO);

        return itemValueRepository
            .findById(itemValueDTO.getId())
            .map(existingItemValue -> {
                itemValueMapper.partialUpdate(existingItemValue, itemValueDTO);

                return existingItemValue;
            })
            .map(itemValueRepository::save)
            .map(itemValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemValues");
        return itemValueRepository.findAll(pageable).map(itemValueMapper::toDto);
    }

    public Page<ItemValueDTO> findAllWithEagerRelationships(Pageable pageable) {
        return itemValueRepository.findAllWithEagerRelationships(pageable).map(itemValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ItemValueDTO> findOne(Long id) {
        log.debug("Request to get ItemValue : {}", id);
        return itemValueRepository.findOneWithEagerRelationships(id).map(itemValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemValue : {}", id);
        itemValueRepository.deleteById(id);
    }

    @Override
    public Optional<ItemValueDTO> findItem(Long id) {
        return itemValueRepository.findwithItem(id).map(itemValueMapper::toDto);
    }
}
