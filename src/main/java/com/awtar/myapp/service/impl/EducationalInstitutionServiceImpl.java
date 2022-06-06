package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.EducationalInstitution;
import com.awtar.myapp.repository.EducationalInstitutionRepository;
import com.awtar.myapp.service.EducationalInstitutionService;
import com.awtar.myapp.service.dto.EducationalInstitutionDTO;
import com.awtar.myapp.service.mapper.EducationalInstitutionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EducationalInstitution}.
 */
@Service
@Transactional
public class EducationalInstitutionServiceImpl implements EducationalInstitutionService {

    private final Logger log = LoggerFactory.getLogger(EducationalInstitutionServiceImpl.class);

    private final EducationalInstitutionRepository educationalInstitutionRepository;

    private final EducationalInstitutionMapper educationalInstitutionMapper;

    public EducationalInstitutionServiceImpl(
        EducationalInstitutionRepository educationalInstitutionRepository,
        EducationalInstitutionMapper educationalInstitutionMapper
    ) {
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.educationalInstitutionMapper = educationalInstitutionMapper;
    }

    @Override
    public EducationalInstitutionDTO save(EducationalInstitutionDTO educationalInstitutionDTO) {
        log.debug("Request to save EducationalInstitution : {}", educationalInstitutionDTO);
        EducationalInstitution educationalInstitution = educationalInstitutionMapper.toEntity(educationalInstitutionDTO);
        educationalInstitution = educationalInstitutionRepository.save(educationalInstitution);
        return educationalInstitutionMapper.toDto(educationalInstitution);
    }

    @Override
    public EducationalInstitutionDTO update(EducationalInstitutionDTO educationalInstitutionDTO) {
        log.debug("Request to save EducationalInstitution : {}", educationalInstitutionDTO);
        EducationalInstitution educationalInstitution = educationalInstitutionMapper.toEntity(educationalInstitutionDTO);
        educationalInstitution = educationalInstitutionRepository.save(educationalInstitution);
        return educationalInstitutionMapper.toDto(educationalInstitution);
    }

    @Override
    public Optional<EducationalInstitutionDTO> partialUpdate(EducationalInstitutionDTO educationalInstitutionDTO) {
        log.debug("Request to partially update EducationalInstitution : {}", educationalInstitutionDTO);

        return educationalInstitutionRepository
            .findById(educationalInstitutionDTO.getId())
            .map(existingEducationalInstitution -> {
                educationalInstitutionMapper.partialUpdate(existingEducationalInstitution, educationalInstitutionDTO);

                return existingEducationalInstitution;
            })
            .map(educationalInstitutionRepository::save)
            .map(educationalInstitutionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EducationalInstitutionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EducationalInstitutions");
        return educationalInstitutionRepository.findAll(pageable).map(educationalInstitutionMapper::toDto);
    }

    public Page<EducationalInstitutionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return educationalInstitutionRepository.findAllWithEagerRelationships(pageable).map(educationalInstitutionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EducationalInstitutionDTO> findOne(Long id) {
        log.debug("Request to get EducationalInstitution : {}", id);
        return educationalInstitutionRepository.findOneWithEagerRelationships(id).map(educationalInstitutionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EducationalInstitution : {}", id);
        educationalInstitutionRepository.deleteById(id);
    }
}
