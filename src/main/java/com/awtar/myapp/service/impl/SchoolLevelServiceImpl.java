package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.repository.SchoolLevelRepository;
import com.awtar.myapp.service.SchoolLevelService;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
import com.awtar.myapp.service.mapper.SchoolLevelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SchoolLevel}.
 */
@Service
@Transactional
public class SchoolLevelServiceImpl implements SchoolLevelService {

    private final Logger log = LoggerFactory.getLogger(SchoolLevelServiceImpl.class);

    private final SchoolLevelRepository schoolLevelRepository;

    private final SchoolLevelMapper schoolLevelMapper;

    public SchoolLevelServiceImpl(SchoolLevelRepository schoolLevelRepository, SchoolLevelMapper schoolLevelMapper) {
        this.schoolLevelRepository = schoolLevelRepository;
        this.schoolLevelMapper = schoolLevelMapper;
    }

    @Override
    public SchoolLevelDTO save(SchoolLevelDTO schoolLevelDTO) {
        log.debug("Request to save SchoolLevel : {}", schoolLevelDTO);
        SchoolLevel schoolLevel = schoolLevelMapper.toEntity(schoolLevelDTO);
        schoolLevel = schoolLevelRepository.save(schoolLevel);
        return schoolLevelMapper.toDto(schoolLevel);
    }

    @Override
    public SchoolLevelDTO update(SchoolLevelDTO schoolLevelDTO) {
        log.debug("Request to save SchoolLevel : {}", schoolLevelDTO);
        SchoolLevel schoolLevel = schoolLevelMapper.toEntity(schoolLevelDTO);
        schoolLevel = schoolLevelRepository.save(schoolLevel);
        return schoolLevelMapper.toDto(schoolLevel);
    }

    @Override
    public Optional<SchoolLevelDTO> partialUpdate(SchoolLevelDTO schoolLevelDTO) {
        log.debug("Request to partially update SchoolLevel : {}", schoolLevelDTO);

        return schoolLevelRepository
            .findById(schoolLevelDTO.getId())
            .map(existingSchoolLevel -> {
                schoolLevelMapper.partialUpdate(existingSchoolLevel, schoolLevelDTO);

                return existingSchoolLevel;
            })
            .map(schoolLevelRepository::save)
            .map(schoolLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SchoolLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchoolLevels");
        return schoolLevelRepository.findAll(pageable).map(schoolLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolLevelDTO> findOne(Long id) {
        log.debug("Request to get SchoolLevel : {}", id);
        return schoolLevelRepository.findById(id).map(schoolLevelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchoolLevel : {}", id);
        schoolLevelRepository.deleteById(id);
    }
}
