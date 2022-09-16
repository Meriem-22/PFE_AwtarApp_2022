package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.ChildStatus;
import com.awtar.myapp.domain.ChildStatusItem;
import com.awtar.myapp.repository.ChildStatusItemRepository;
import com.awtar.myapp.repository.ChildStatusRepository;
import com.awtar.myapp.service.ChildStatusItemService;
import com.awtar.myapp.service.dto.ChildStatusDTO;
import com.awtar.myapp.service.dto.ChildStatusItemDTO;
import com.awtar.myapp.service.mapper.ChildStatusItemMapper;
import com.awtar.myapp.service.mapper.ChildStatusMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChildStatusItem}.
 */
@Service
@Transactional
public class ChildStatusItemServiceImpl implements ChildStatusItemService {

    private final Logger log = LoggerFactory.getLogger(ChildStatusItemServiceImpl.class);

    private final ChildStatusItemRepository childStatusItemRepository;

    private final ChildStatusItemMapper childStatusItemMapper;

    public ChildStatusItemServiceImpl(
        ChildStatusItemRepository childStatusItemRepository,
        ChildStatusItemMapper childStatusItemMapper,
        ChildStatusRepository childStatusRepository,
        ChildStatusMapper childStatusMapper
    ) {
        this.childStatusItemRepository = childStatusItemRepository;
        this.childStatusItemMapper = childStatusItemMapper;
    }

    @Override
    public ChildStatusItemDTO save(ChildStatusItemDTO childStatusItemDTO) {
        log.debug("Request to save ChildStatusItem : {}", childStatusItemDTO);
        ChildStatusItem childStatusItem = childStatusItemMapper.toEntity(childStatusItemDTO);
        childStatusItem = childStatusItemRepository.save(childStatusItem);
        return childStatusItemMapper.toDto(childStatusItem);
    }

    @Override
    public ChildStatusItemDTO update(ChildStatusItemDTO childStatusItemDTO) {
        log.debug("Request to save ChildStatusItem : {}", childStatusItemDTO);
        ChildStatusItem childStatusItem = childStatusItemMapper.toEntity(childStatusItemDTO);
        childStatusItem = childStatusItemRepository.save(childStatusItem);
        return childStatusItemMapper.toDto(childStatusItem);
    }

    @Override
    public Optional<ChildStatusItemDTO> partialUpdate(ChildStatusItemDTO childStatusItemDTO) {
        log.debug("Request to partially update ChildStatusItem : {}", childStatusItemDTO);

        return childStatusItemRepository
            .findById(childStatusItemDTO.getId())
            .map(existingChildStatusItem -> {
                childStatusItemMapper.partialUpdate(existingChildStatusItem, childStatusItemDTO);

                return existingChildStatusItem;
            })
            .map(childStatusItemRepository::save)
            .map(childStatusItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildStatusItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChildStatusItems");
        return childStatusItemRepository.findAll(pageable).map(childStatusItemMapper::toDto);
    }

    public Page<ChildStatusItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return childStatusItemRepository.findAllWithEagerRelationships(pageable).map(childStatusItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChildStatusItemDTO> findOne(Long id) {
        log.debug("Request to get ChildStatusItem : {}", id);
        return childStatusItemRepository.findOneWithEagerRelationships(id).map(childStatusItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChildStatusItem : {}", id);
        childStatusItemRepository.deleteById(id);
    }

    @Override
    public List<ChildStatusItemDTO> findAllStatusOfItem(Long id) {
        List<ChildStatusItemDTO> items = childStatusItemRepository.findAllChildStatusItem(id);
        return items;
    }
}
