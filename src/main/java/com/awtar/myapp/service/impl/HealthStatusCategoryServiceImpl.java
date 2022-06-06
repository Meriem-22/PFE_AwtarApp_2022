package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.HealthStatusCategory;
import com.awtar.myapp.repository.HealthStatusCategoryRepository;
import com.awtar.myapp.service.HealthStatusCategoryService;
import com.awtar.myapp.service.dto.HealthStatusCategoryDTO;
import com.awtar.myapp.service.mapper.HealthStatusCategoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HealthStatusCategory}.
 */
@Service
@Transactional
public class HealthStatusCategoryServiceImpl implements HealthStatusCategoryService {

    private final Logger log = LoggerFactory.getLogger(HealthStatusCategoryServiceImpl.class);

    private final HealthStatusCategoryRepository healthStatusCategoryRepository;

    private final HealthStatusCategoryMapper healthStatusCategoryMapper;

    public HealthStatusCategoryServiceImpl(
        HealthStatusCategoryRepository healthStatusCategoryRepository,
        HealthStatusCategoryMapper healthStatusCategoryMapper
    ) {
        this.healthStatusCategoryRepository = healthStatusCategoryRepository;
        this.healthStatusCategoryMapper = healthStatusCategoryMapper;
    }

    @Override
    public HealthStatusCategoryDTO save(HealthStatusCategoryDTO healthStatusCategoryDTO) {
        log.debug("Request to save HealthStatusCategory : {}", healthStatusCategoryDTO);
        HealthStatusCategory healthStatusCategory = healthStatusCategoryMapper.toEntity(healthStatusCategoryDTO);
        healthStatusCategory = healthStatusCategoryRepository.save(healthStatusCategory);
        return healthStatusCategoryMapper.toDto(healthStatusCategory);
    }

    @Override
    public HealthStatusCategoryDTO update(HealthStatusCategoryDTO healthStatusCategoryDTO) {
        log.debug("Request to save HealthStatusCategory : {}", healthStatusCategoryDTO);
        HealthStatusCategory healthStatusCategory = healthStatusCategoryMapper.toEntity(healthStatusCategoryDTO);
        healthStatusCategory = healthStatusCategoryRepository.save(healthStatusCategory);
        return healthStatusCategoryMapper.toDto(healthStatusCategory);
    }

    @Override
    public Optional<HealthStatusCategoryDTO> partialUpdate(HealthStatusCategoryDTO healthStatusCategoryDTO) {
        log.debug("Request to partially update HealthStatusCategory : {}", healthStatusCategoryDTO);

        return healthStatusCategoryRepository
            .findById(healthStatusCategoryDTO.getId())
            .map(existingHealthStatusCategory -> {
                healthStatusCategoryMapper.partialUpdate(existingHealthStatusCategory, healthStatusCategoryDTO);

                return existingHealthStatusCategory;
            })
            .map(healthStatusCategoryRepository::save)
            .map(healthStatusCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HealthStatusCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HealthStatusCategories");
        return healthStatusCategoryRepository.findAll(pageable).map(healthStatusCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HealthStatusCategoryDTO> findOne(Long id) {
        log.debug("Request to get HealthStatusCategory : {}", id);
        return healthStatusCategoryRepository.findById(id).map(healthStatusCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HealthStatusCategory : {}", id);
        healthStatusCategoryRepository.deleteById(id);
    }
}
