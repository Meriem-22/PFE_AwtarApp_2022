package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.ChildStatus;
import com.awtar.myapp.repository.ChildStatusRepository;
import com.awtar.myapp.service.ChildStatusService;
import com.awtar.myapp.service.dto.ChildStatusDTO;
import com.awtar.myapp.service.mapper.ChildStatusMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChildStatus}.
 */
@Service
@Transactional
public class ChildStatusServiceImpl implements ChildStatusService {

    private final Logger log = LoggerFactory.getLogger(ChildStatusServiceImpl.class);

    private final ChildStatusRepository childStatusRepository;

    private final ChildStatusMapper childStatusMapper;

    public ChildStatusServiceImpl(ChildStatusRepository childStatusRepository, ChildStatusMapper childStatusMapper) {
        this.childStatusRepository = childStatusRepository;
        this.childStatusMapper = childStatusMapper;
    }

    @Override
    public ChildStatusDTO save(ChildStatusDTO childStatusDTO) {
        log.debug("Request to save ChildStatus : {}", childStatusDTO);
        ChildStatus childStatus = childStatusMapper.toEntity(childStatusDTO);
        childStatus = childStatusRepository.save(childStatus);
        return childStatusMapper.toDto(childStatus);
    }

    @Override
    public ChildStatusDTO update(ChildStatusDTO childStatusDTO) {
        log.debug("Request to save ChildStatus : {}", childStatusDTO);
        ChildStatus childStatus = childStatusMapper.toEntity(childStatusDTO);
        childStatus = childStatusRepository.save(childStatus);
        return childStatusMapper.toDto(childStatus);
    }

    @Override
    public Optional<ChildStatusDTO> partialUpdate(ChildStatusDTO childStatusDTO) {
        log.debug("Request to partially update ChildStatus : {}", childStatusDTO);

        return childStatusRepository
            .findById(childStatusDTO.getId())
            .map(existingChildStatus -> {
                childStatusMapper.partialUpdate(existingChildStatus, childStatusDTO);

                return existingChildStatus;
            })
            .map(childStatusRepository::save)
            .map(childStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChildStatuses");
        return childStatusRepository.findAll(pageable).map(childStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChildStatusDTO> findOne(Long id) {
        log.debug("Request to get ChildStatus : {}", id);
        return childStatusRepository.findById(id).map(childStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChildStatus : {}", id);
        childStatusRepository.deleteById(id);
    }
}
