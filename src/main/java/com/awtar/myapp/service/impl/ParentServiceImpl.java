package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.repository.ParentRepository;
import com.awtar.myapp.repository.ProfileRepository;
import com.awtar.myapp.service.ParentService;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ParentDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.mapper.FamilyMapper;
import com.awtar.myapp.service.mapper.ParentMapper;
import com.awtar.myapp.service.mapper.ProfileMapper;
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

    private final ProfileMapper profileMapper;

    private final ProfileRepository profileRepository;

    private final FamilyMapper familyMapper;

    public ParentServiceImpl(
        ParentRepository parentRepository,
        ParentMapper parentMapper,
        ProfileMapper profileMapper,
        ProfileRepository profileRepository,
        FamilyMapper familyMapper
    ) {
        this.parentRepository = parentRepository;
        this.parentMapper = parentMapper;
        this.profileMapper = profileMapper;
        this.profileRepository = profileRepository;
        this.familyMapper = familyMapper;
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

    @Override
    public ParentDTO saveParentAllDetails(ParentDTO parentDTO) {
        ProfileDTO profile = parentDTO.getProfile();
        FamilyDTO family = parentDTO.getFamily();
        Family f = familyMapper.toEntity(family);
        Parent p = parentMapper.toEntity(parentDTO);
        p.setFamily(f);
        if (parentDTO.isHead()) p.setFamilyHead(f);
        p = parentRepository.save(p);
        Profile profileParent = profileMapper.toEntity(profile);
        profileParent.setParent(p);
        profileRepository.save(profileParent);
        return parentMapper.toDto(p);
    }
}
