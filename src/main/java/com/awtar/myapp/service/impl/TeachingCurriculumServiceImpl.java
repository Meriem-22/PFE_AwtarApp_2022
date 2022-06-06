package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.TeachingCurriculum;
import com.awtar.myapp.repository.TeachingCurriculumRepository;
import com.awtar.myapp.service.TeachingCurriculumService;
import com.awtar.myapp.service.dto.TeachingCurriculumDTO;
import com.awtar.myapp.service.mapper.TeachingCurriculumMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TeachingCurriculum}.
 */
@Service
@Transactional
public class TeachingCurriculumServiceImpl implements TeachingCurriculumService {

    private final Logger log = LoggerFactory.getLogger(TeachingCurriculumServiceImpl.class);

    private final TeachingCurriculumRepository teachingCurriculumRepository;

    private final TeachingCurriculumMapper teachingCurriculumMapper;

    public TeachingCurriculumServiceImpl(
        TeachingCurriculumRepository teachingCurriculumRepository,
        TeachingCurriculumMapper teachingCurriculumMapper
    ) {
        this.teachingCurriculumRepository = teachingCurriculumRepository;
        this.teachingCurriculumMapper = teachingCurriculumMapper;
    }

    @Override
    public TeachingCurriculumDTO save(TeachingCurriculumDTO teachingCurriculumDTO) {
        log.debug("Request to save TeachingCurriculum : {}", teachingCurriculumDTO);
        TeachingCurriculum teachingCurriculum = teachingCurriculumMapper.toEntity(teachingCurriculumDTO);
        teachingCurriculum = teachingCurriculumRepository.save(teachingCurriculum);
        return teachingCurriculumMapper.toDto(teachingCurriculum);
    }

    @Override
    public TeachingCurriculumDTO update(TeachingCurriculumDTO teachingCurriculumDTO) {
        log.debug("Request to save TeachingCurriculum : {}", teachingCurriculumDTO);
        TeachingCurriculum teachingCurriculum = teachingCurriculumMapper.toEntity(teachingCurriculumDTO);
        teachingCurriculum = teachingCurriculumRepository.save(teachingCurriculum);
        return teachingCurriculumMapper.toDto(teachingCurriculum);
    }

    @Override
    public Optional<TeachingCurriculumDTO> partialUpdate(TeachingCurriculumDTO teachingCurriculumDTO) {
        log.debug("Request to partially update TeachingCurriculum : {}", teachingCurriculumDTO);

        return teachingCurriculumRepository
            .findById(teachingCurriculumDTO.getId())
            .map(existingTeachingCurriculum -> {
                teachingCurriculumMapper.partialUpdate(existingTeachingCurriculum, teachingCurriculumDTO);

                return existingTeachingCurriculum;
            })
            .map(teachingCurriculumRepository::save)
            .map(teachingCurriculumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeachingCurriculumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeachingCurricula");
        return teachingCurriculumRepository.findAll(pageable).map(teachingCurriculumMapper::toDto);
    }

    public Page<TeachingCurriculumDTO> findAllWithEagerRelationships(Pageable pageable) {
        return teachingCurriculumRepository.findAllWithEagerRelationships(pageable).map(teachingCurriculumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeachingCurriculumDTO> findOne(Long id) {
        log.debug("Request to get TeachingCurriculum : {}", id);
        return teachingCurriculumRepository.findOneWithEagerRelationships(id).map(teachingCurriculumMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeachingCurriculum : {}", id);
        teachingCurriculumRepository.deleteById(id);
    }
}
