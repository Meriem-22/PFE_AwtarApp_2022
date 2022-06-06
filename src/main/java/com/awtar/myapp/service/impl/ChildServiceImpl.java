package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.Child;
import com.awtar.myapp.repository.ChildRepository;
import com.awtar.myapp.service.ChildService;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.mapper.ChildMapper;
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
 * Service Implementation for managing {@link Child}.
 */
@Service
@Transactional
public class ChildServiceImpl implements ChildService {

    private final Logger log = LoggerFactory.getLogger(ChildServiceImpl.class);

    private final ChildRepository childRepository;

    private final ChildMapper childMapper;

    public ChildServiceImpl(ChildRepository childRepository, ChildMapper childMapper) {
        this.childRepository = childRepository;
        this.childMapper = childMapper;
    }

    @Override
    public ChildDTO save(ChildDTO childDTO) {
        log.debug("Request to save Child : {}", childDTO);
        Child child = childMapper.toEntity(childDTO);
        child = childRepository.save(child);
        return childMapper.toDto(child);
    }

    @Override
    public ChildDTO update(ChildDTO childDTO) {
        log.debug("Request to save Child : {}", childDTO);
        Child child = childMapper.toEntity(childDTO);
        child = childRepository.save(child);
        return childMapper.toDto(child);
    }

    @Override
    public Optional<ChildDTO> partialUpdate(ChildDTO childDTO) {
        log.debug("Request to partially update Child : {}", childDTO);

        return childRepository
            .findById(childDTO.getId())
            .map(existingChild -> {
                childMapper.partialUpdate(existingChild, childDTO);

                return existingChild;
            })
            .map(childRepository::save)
            .map(childMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Children");
        return childRepository.findAll(pageable).map(childMapper::toDto);
    }

    /**
     *  Get all the children where ChildProfile is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ChildDTO> findAllWhereChildProfileIsNull() {
        log.debug("Request to get all children where ChildProfile is null");
        return StreamSupport
            .stream(childRepository.findAll().spliterator(), false)
            .filter(child -> child.getChildProfile() == null)
            .map(childMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChildDTO> findOne(Long id) {
        log.debug("Request to get Child : {}", id);
        return childRepository.findById(id).map(childMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Child : {}", id);
        childRepository.deleteById(id);
    }
}
