package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.repository.ParentRepository;
import com.awtar.myapp.service.ParentService;
import com.awtar.myapp.service.dto.ParentDTO;
import com.awtar.myapp.service.mapper.ParentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Parent}.
 */
@Service
@Transactional
public class ParentServiceImpl implements ParentService {

    private final Logger log = LoggerFactory.getLogger(ParentServiceImpl.class);

    private final ParentRepository parentRepository;

    private final ParentMapper parentMapper;

    public ParentServiceImpl(ParentRepository parentRepository, ParentMapper parentMapper) {
        this.parentRepository = parentRepository;
        this.parentMapper = parentMapper;
    }

    @Override
    public ParentDTO save(ParentDTO parentDTO) {
        log.debug("Request to save Parent : {}", parentDTO);
        Parent parent = parentMapper.toEntity(parentDTO);
        parent = parentRepository.save(parent);
        return parentMapper.toDto(parent);
    }

    @Override
    public ParentDTO update(ParentDTO parentDTO) {
        log.debug("Request to save Parent : {}", parentDTO);
        Parent parent = parentMapper.toEntity(parentDTO);
        parent = parentRepository.save(parent);
        return parentMapper.toDto(parent);
    }

    @Override
    public Optional<ParentDTO> partialUpdate(ParentDTO parentDTO) {
        log.debug("Request to partially update Parent : {}", parentDTO);

        return parentRepository
            .findById(parentDTO.getId())
            .map(existingParent -> {
                parentMapper.partialUpdate(existingParent, parentDTO);

                return existingParent;
            })
            .map(parentRepository::save)
            .map(parentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Parents");
        return parentRepository.findAll(pageable).map(parentMapper::toDto);
    }

    /**
     *  Get all the parents where ParentProfile is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ParentDTO> findAllWhereParentProfileIsNull() {
        log.debug("Request to get all parents where ParentProfile is null");
        return StreamSupport
            .stream(parentRepository.findAll().spliterator(), false)
            .filter(parent -> parent.getParentProfile() == null)
            .map(parentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParentDTO> findOne(Long id) {
        log.debug("Request to get Parent : {}", id);
        return parentRepository.findById(id).map(parentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parent : {}", id);
        parentRepository.deleteById(id);
    }
}