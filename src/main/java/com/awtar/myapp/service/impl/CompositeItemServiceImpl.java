package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.CompositeItem;
import com.awtar.myapp.repository.CompositeItemRepository;
import com.awtar.myapp.service.CompositeItemService;
import com.awtar.myapp.service.dto.CompositeItemDTO;
import com.awtar.myapp.service.mapper.CompositeItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompositeItem}.
 */
@Service
@Transactional
public class CompositeItemServiceImpl implements CompositeItemService {

    private final Logger log = LoggerFactory.getLogger(CompositeItemServiceImpl.class);

    private final CompositeItemRepository compositeItemRepository;

    private final CompositeItemMapper compositeItemMapper;

    public CompositeItemServiceImpl(CompositeItemRepository compositeItemRepository, CompositeItemMapper compositeItemMapper) {
        this.compositeItemRepository = compositeItemRepository;
        this.compositeItemMapper = compositeItemMapper;
    }

    @Override
    public CompositeItemDTO save(CompositeItemDTO compositeItemDTO) {
        log.debug("Request to save CompositeItem : {}", compositeItemDTO);
        CompositeItem compositeItem = compositeItemMapper.toEntity(compositeItemDTO);
        compositeItem = compositeItemRepository.save(compositeItem);
        return compositeItemMapper.toDto(compositeItem);
    }

    @Override
    public CompositeItemDTO update(CompositeItemDTO compositeItemDTO) {
        log.debug("Request to save CompositeItem : {}", compositeItemDTO);
        CompositeItem compositeItem = compositeItemMapper.toEntity(compositeItemDTO);
        compositeItem = compositeItemRepository.save(compositeItem);
        return compositeItemMapper.toDto(compositeItem);
    }

    @Override
    public Optional<CompositeItemDTO> partialUpdate(CompositeItemDTO compositeItemDTO) {
        log.debug("Request to partially update CompositeItem : {}", compositeItemDTO);

        return compositeItemRepository
            .findById(compositeItemDTO.getId())
            .map(existingCompositeItem -> {
                compositeItemMapper.partialUpdate(existingCompositeItem, compositeItemDTO);

                return existingCompositeItem;
            })
            .map(compositeItemRepository::save)
            .map(compositeItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompositeItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompositeItems");
        return compositeItemRepository.findAll(pageable).map(compositeItemMapper::toDto);
    }

    public Page<CompositeItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return compositeItemRepository.findAllWithEagerRelationships(pageable).map(compositeItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompositeItemDTO> findOne(Long id) {
        log.debug("Request to get CompositeItem : {}", id);
        return compositeItemRepository.findOneWithEagerRelationships(id).map(compositeItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompositeItem : {}", id);
        compositeItemRepository.deleteById(id);
    }
}
