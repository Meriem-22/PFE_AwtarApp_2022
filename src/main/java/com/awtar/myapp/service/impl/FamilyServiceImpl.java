package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.repository.BeneficiaryRepository;
import com.awtar.myapp.repository.ChildRepository;
import com.awtar.myapp.repository.FamilyRepository;
import com.awtar.myapp.repository.ParentRepository;
import com.awtar.myapp.repository.ProfileRepository;
import com.awtar.myapp.service.BeneficiaryService;
import com.awtar.myapp.service.FamilyService;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ParentDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.mapper.BeneficiaryMapper;
import com.awtar.myapp.service.mapper.ChildMapper;
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
 * Service Implementation for managing {@link Family}.
 */
@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {

    private final Logger log = LoggerFactory.getLogger(FamilyServiceImpl.class);

    private final FamilyRepository familyRepository;
    private final ParentRepository parentRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final ChildRepository childRepository;
    private final ProfileRepository profileRepository;

    private final FamilyMapper familyMapper;
    private final ParentMapper parentMapper;
    private final BeneficiaryMapper beneficiaryMapper;
    private final ChildMapper childMapper;
    private final ProfileMapper profileMapper;
    private final BeneficiaryService beneficiaryService;

    public FamilyServiceImpl(
        FamilyRepository familyRepository,
        FamilyMapper familyMapper,
        ParentMapper parentMapper,
        ParentRepository parentRepository,
        BeneficiaryRepository beneficiaryRepository,
        ChildRepository childRepository,
        BeneficiaryMapper beneficiaryMapper,
        ChildMapper childMapper,
        ProfileRepository profileRepository,
        ProfileMapper profileMapper,
        BeneficiaryService beneficiaryService
    ) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
        this.parentMapper = parentMapper;
        this.parentRepository = parentRepository;
        this.beneficiaryMapper = beneficiaryMapper;
        this.beneficiaryRepository = beneficiaryRepository;
        this.childMapper = childMapper;
        this.childRepository = childRepository;
        this.profileMapper = profileMapper;
        this.profileRepository = profileRepository;
        this.beneficiaryService = beneficiaryService;
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

    @Override
    public FamilyDTO saveCompletedFamily(FamilyDTO familyDTO) {
        log.debug("Request to save completed Family  : {}", familyDTO);

        Family family = familyMapper.toEntity(familyDTO);
        BeneficiaryDTO beneficiarydto = familyDTO.getBeneficiary();
        Beneficiary beneficiary = beneficiaryMapper.toEntity(beneficiarydto);
        family = familyRepository.save(family);
        beneficiaryRepository.updateInfo(family.getId(), beneficiary.getAuthorizingOfficer(), beneficiary.getTutor());

        ParentDTO[] parentCollection;
        parentCollection = familyDTO.getParentsDetails();
        ChildDTO[] childCollection;
        childCollection = familyDTO.getChildrenDetails();

        for (int i = 0; i < parentCollection.length; i++) {
            ParentDTO parent = parentCollection[i];
            ProfileDTO profile = parent.getProfile();
            Parent p = parentMapper.toEntity(parent);
            p.setFamily(family);
            if (parent.isHead()) p.setFamilyHead(family);
            p = parentRepository.save(p);
            Profile profileParent = profileMapper.toEntity(profile);
            profileParent.setParent(p);
            profileRepository.save(profileParent);
        }

        for (int i = 0; i < childCollection.length; i++) {
            ChildDTO childdto = childCollection[i];
            ProfileDTO profiledto = childdto.getProfile();
            Profile profile = profileMapper.toEntity(profiledto);
            Child child = childMapper.toEntity(childdto);
            child.setFamily(family);
            child = childRepository.save(child);
            profile.setChild(child);
            profileRepository.save(profile);
        }

        return familyMapper.toDto(family);
    }
}
