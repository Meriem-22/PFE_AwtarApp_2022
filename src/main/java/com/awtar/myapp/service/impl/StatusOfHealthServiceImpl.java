package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.StatusOfHealth;
import com.awtar.myapp.repository.StatusOfHealthRepository;
import com.awtar.myapp.service.StatusOfHealthService;
import com.awtar.myapp.service.dto.StatusOfHealthDTO;
import com.awtar.myapp.service.mapper.StatusOfHealthMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StatusOfHealth}.
 */
@Service
@Transactional
public class StatusOfHealthServiceImpl implements StatusOfHealthService {

    private final Logger log = LoggerFactory.getLogger(StatusOfHealthServiceImpl.class);

    private final StatusOfHealthRepository statusOfHealthRepository;

    private final StatusOfHealthMapper statusOfHealthMapper;

    public StatusOfHealthServiceImpl(StatusOfHealthRepository statusOfHealthRepository, StatusOfHealthMapper statusOfHealthMapper) {
        this.statusOfHealthRepository = statusOfHealthRepository;
        this.statusOfHealthMapper = statusOfHealthMapper;
    }

    @Override
    public StatusOfHealthDTO save(StatusOfHealthDTO statusOfHealthDTO) {
        log.debug("Request to save StatusOfHealth : {}", statusOfHealthDTO);
        StatusOfHealth statusOfHealth = statusOfHealthMapper.toEntity(statusOfHealthDTO);
        statusOfHealth = statusOfHealthRepository.save(statusOfHealth);
        return statusOfHealthMapper.toDto(statusOfHealth);
    }

    @Override
    public StatusOfHealthDTO update(StatusOfHealthDTO statusOfHealthDTO) {
        log.debug("Request to save StatusOfHealth : {}", statusOfHealthDTO);
        StatusOfHealth statusOfHealth = statusOfHealthMapper.toEntity(statusOfHealthDTO);
        statusOfHealth = statusOfHealthRepository.save(statusOfHealth);
        return statusOfHealthMapper.toDto(statusOfHealth);
    }

    @Override
    public Optional<StatusOfHealthDTO> partialUpdate(StatusOfHealthDTO statusOfHealthDTO) {
        log.debug("Request to partially update StatusOfHealth : {}", statusOfHealthDTO);

        return statusOfHealthRepository
            .findById(statusOfHealthDTO.getId())
            .map(existingStatusOfHealth -> {
                statusOfHealthMapper.partialUpdate(existingStatusOfHealth, statusOfHealthDTO);

                return existingStatusOfHealth;
            })
            .map(statusOfHealthRepository::save)
            .map(statusOfHealthMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusOfHealthDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StatusOfHealths");
        return statusOfHealthRepository.findAll(pageable).map(statusOfHealthMapper::toDto);
    }

    public Page<StatusOfHealthDTO> findAllWithEagerRelationships(Pageable pageable) {
        return statusOfHealthRepository.findAllWithEagerRelationships(pageable).map(statusOfHealthMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StatusOfHealthDTO> findOne(Long id) {
        log.debug("Request to get StatusOfHealth : {}", id);
        return statusOfHealthRepository.findOneWithEagerRelationships(id).map(statusOfHealthMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatusOfHealth : {}", id);
        statusOfHealthRepository.deleteById(id);
    }
}
