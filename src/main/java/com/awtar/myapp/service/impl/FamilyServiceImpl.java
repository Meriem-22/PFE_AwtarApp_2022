package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.Family;
import com.awtar.myapp.repository.FamilyRepository;
import com.awtar.myapp.service.FamilyService;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.mapper.FamilyMapper;
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
 * Service Implementation for managing {@link Family}.
 */
@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {

    private final Logger log = LoggerFactory.getLogger(FamilyServiceImpl.class);

    private final FamilyRepository familyRepository;

    private final FamilyMapper familyMapper;

    public FamilyServiceImpl(FamilyRepository familyRepository, FamilyMapper familyMapper) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
    }

    @Override
    public FamilyDTO save(FamilyDTO familyDTO) {
        log.debug("Request to save Family : {}", familyDTO);
        Family family = familyMapper.toEntity(familyDTO);
        family = familyRepository.save(family);
        return familyMapper.toDto(family);
    }

    @Override
    public FamilyDTO update(FamilyDTO familyDTO) {
        log.debug("Request to save Family : {}", familyDTO);
        Family family = familyMapper.toEntity(familyDTO);
        family = familyRepository.save(family);
        return familyMapper.toDto(family);
    }

    @Override
    public Optional<FamilyDTO> partialUpdate(FamilyDTO familyDTO) {
        log.debug("Request to partially update Family : {}", familyDTO);

        return familyRepository
            .findById(familyDTO.getId())
            .map(existingFamily -> {
                familyMapper.partialUpdate(existingFamily, familyDTO);

                return existingFamily;
            })
            .map(familyRepository::save)
            .map(familyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FamilyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Families");
        return familyRepository.findAll(pageable).map(familyMapper::toDto);
    }

    /**
     *  Get all the families where Head is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FamilyDTO> findAllWhereHeadIsNull() {
        log.debug("Request to get all families where Head is null");
        return StreamSupport
            .stream(familyRepository.findAll().spliterator(), false)
            .filter(family -> family.getHead() == null)
            .map(familyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FamilyDTO> findOne(Long id) {
        log.debug("Request to get Family : {}", id);
        return familyRepository.findById(id).map(familyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Family : {}", id);
        familyRepository.deleteById(id);
    }
}
