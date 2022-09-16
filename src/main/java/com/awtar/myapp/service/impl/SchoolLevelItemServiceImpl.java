package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.SchoolLevelItem;
import com.awtar.myapp.repository.SchoolLevelItemRepository;
import com.awtar.myapp.service.SchoolLevelItemService;
import com.awtar.myapp.service.dto.SchoolLevelItemDTO;
import com.awtar.myapp.service.mapper.SchoolLevelItemMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SchoolLevelItem}.
 */
@Service
@Transactional
public class SchoolLevelItemServiceImpl implements SchoolLevelItemService {

    private final Logger log = LoggerFactory.getLogger(SchoolLevelItemServiceImpl.class);

    private final SchoolLevelItemRepository schoolLevelItemRepository;

    private final SchoolLevelItemMapper schoolLevelItemMapper;

    public SchoolLevelItemServiceImpl(SchoolLevelItemRepository schoolLevelItemRepository, SchoolLevelItemMapper schoolLevelItemMapper) {
        this.schoolLevelItemRepository = schoolLevelItemRepository;
        this.schoolLevelItemMapper = schoolLevelItemMapper;
    }

    @Override
    public SchoolLevelItemDTO save(SchoolLevelItemDTO schoolLevelItemDTO) {
        log.debug("Request to save SchoolLevelItem : {}", schoolLevelItemDTO);
        SchoolLevelItem schoolLevelItem = schoolLevelItemMapper.toEntity(schoolLevelItemDTO);
        schoolLevelItem = schoolLevelItemRepository.save(schoolLevelItem);
        return schoolLevelItemMapper.toDto(schoolLevelItem);
    }

    @Override
    public SchoolLevelItemDTO update(SchoolLevelItemDTO schoolLevelItemDTO) {
        log.debug("Request to save SchoolLevelItem : {}", schoolLevelItemDTO);
        SchoolLevelItem schoolLevelItem = schoolLevelItemMapper.toEntity(schoolLevelItemDTO);
        schoolLevelItem = schoolLevelItemRepository.save(schoolLevelItem);
        return schoolLevelItemMapper.toDto(schoolLevelItem);
    }

    @Override
    public Optional<SchoolLevelItemDTO> partialUpdate(SchoolLevelItemDTO schoolLevelItemDTO) {
        log.debug("Request to partially update SchoolLevelItem : {}", schoolLevelItemDTO);

        return schoolLevelItemRepository
            .findById(schoolLevelItemDTO.getId())
            .map(existingSchoolLevelItem -> {
                schoolLevelItemMapper.partialUpdate(existingSchoolLevelItem, schoolLevelItemDTO);

                return existingSchoolLevelItem;
            })
            .map(schoolLevelItemRepository::save)
            .map(schoolLevelItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SchoolLevelItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchoolLevelItems");
        return schoolLevelItemRepository.findAll(pageable).map(schoolLevelItemMapper::toDto);
    }

    public Page<SchoolLevelItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return schoolLevelItemRepository.findAllWithEagerRelationships(pageable).map(schoolLevelItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolLevelItemDTO> findOne(Long id) {
        log.debug("Request to get SchoolLevelItem : {}", id);
        return schoolLevelItemRepository.findOneWithEagerRelationships(id).map(schoolLevelItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchoolLevelItem : {}", id);
        schoolLevelItemRepository.deleteById(id);
    }

    @Override
    public List<SchoolLevelItemDTO> findSchoolLevelItemDetails(Long id) {
        List<SchoolLevelItemDTO> items = schoolLevelItemRepository.findSchoolLevelItemDetails(id);
        return items;
    }
}
