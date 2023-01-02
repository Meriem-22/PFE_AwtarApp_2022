package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.enumeration.Beneficiaries;
import com.awtar.myapp.repository.AuthorizingOfficerRepository;
import com.awtar.myapp.repository.ChildRepository;
import com.awtar.myapp.repository.FamilyRepository;
import com.awtar.myapp.repository.ProfileRepository;
import com.awtar.myapp.repository.TutorRepository;
import com.awtar.myapp.service.ChildService;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.mapper.ChildMapper;
import com.awtar.myapp.service.mapper.FamilyMapper;
import com.awtar.myapp.service.mapper.ProfileMapper;
import java.util.Date;
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
 * Service Implementation for managing {@link Child}.
 */
@Service
@Transactional
public class ChildServiceImpl implements ChildService {

    private final Logger log = LoggerFactory.getLogger(ChildServiceImpl.class);

    private final ChildRepository childRepository;

    private final ChildMapper childMapper;

    private final ProfileMapper profileMapper;

    private final ProfileRepository profileRepository;

    private final FamilyMapper familyMapper;

    private final AuthorizingOfficerRepository authorizingOfficerRepository;

    private final TutorRepository tutorRepository;

    private final FamilyRepository familyRepository;

    public ChildServiceImpl(
        ChildRepository childRepository,
        ChildMapper childMapper,
        ProfileMapper profileMapper,
        ProfileRepository profileRepository,
        FamilyMapper familyMapper,
        AuthorizingOfficerRepository authorizingOfficerRepository,
        TutorRepository tutorRepository,
        FamilyRepository familyRepository
    ) {
        this.childRepository = childRepository;
        this.childMapper = childMapper;
        this.profileMapper = profileMapper;
        this.profileRepository = profileRepository;
        this.familyMapper = familyMapper;
        this.authorizingOfficerRepository = authorizingOfficerRepository;
        this.tutorRepository = tutorRepository;
        this.familyRepository = familyRepository;
    }

    @Override
    public ChildDTO save(ChildDTO childDTO) {
        log.debug("Request to save Child : {}", childDTO);
        Child child = childMapper.toEntity(childDTO);
        child = childRepository.save(child);
        return childMapper.toDto(child);
    }

    @Override
    public ChildDTO update(ChildDTO childDTO) {
        log.debug("Request to save Child : {}", childDTO);
        Child child = childMapper.toEntity(childDTO);
        child = childRepository.save(child);
        return childMapper.toDto(child);
    }

    @Override
    public Optional<ChildDTO> partialUpdate(ChildDTO childDTO) {
        log.debug("Request to partially update Child : {}", childDTO);

        return childRepository
            .findById(childDTO.getId())
            .map(existingChild -> {
                childMapper.partialUpdate(existingChild, childDTO);

                return existingChild;
            })
            .map(childRepository::save)
            .map(childMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Children");
        return childRepository.findAll(pageable).map(childMapper::toDto);
    }

    /**
     *  Get all the children where ChildProfile is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ChildDTO> findAllWhereChildProfileIsNull() {
        log.debug("Request to get all children where ChildProfile is null");
        return StreamSupport
            .stream(childRepository.findAll().spliterator(), false)
            .filter(child -> child.getChildProfile() == null)
            .map(childMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChildDTO> findOne(Long id) {
        log.debug("Request to get Child : {}", id);
        return childRepository.findById(id).map(childMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Child : {}", id);
        childRepository.deleteById(id);
    }

    @Override
    public ChildDTO saveChildAllDetails(ChildDTO childDTO) {
        ProfileDTO a = childDTO.getAuthorizingOfficer();
        ProfileDTO t = childDTO.getTutor();

        Profile pa = profileMapper.toEntity(a);
        Profile pt = profileMapper.toEntity(t);

        String reference;

        ProfileDTO profiledto = childDTO.getProfile();
        FamilyDTO family = childDTO.getFamily();
        Family f = familyMapper.toEntity(family);
        Profile profile = profileMapper.toEntity(profiledto);
        Child child = childMapper.toEntity(childDTO);
        child.setFamily(f);
        child.setBeneficiaryType(Beneficiaries.CHILD);
        if (pa != null) {
            child.setAuthorizingOfficer(authorizingOfficerRepository.getById(pa.getAuthorizingOfficer().getId()));
        }
        if (pt != null) {
            child.setTutor(tutorRepository.getById(pt.getTutor().getId()));
        }

        if ((pt == null) & (f != null)) {
            if (f.getTutor() != null) {
                child.setTutor(tutorRepository.getById(f.getTutor().getId()));
            }
        }

        child = childRepository.save(child);
        if ((pa != null) & (f == null)) {
            reference =
                "Ref_EN/" +
                authorizingOfficerRepository.getById(a.getAuthorizingOfficer().getId()).getAbbreviation() +
                "-" +
                child.getId().toString();
            child.setBeneficiaryReference(reference);
        }
        if ((pa == null) & (f != null)) {
            List<Profile> children = profileRepository.findChildrenOfOneFamily(f);
            int i = children.size() + 1;
            if (f.getAuthorizingOfficer() != null) {
                reference =
                    "Ref_EN/" +
                    authorizingOfficerRepository.getById(f.getAuthorizingOfficer().getId()).getAbbreviation() +
                    "-" +
                    f.getId().toString() +
                    "-" +
                    i;
                child.setBeneficiaryReference(reference);
            }
            if (f.getAuthorizingOfficer() == null) {
                reference = "Ref_EN/" + "-" + f.getId().toString() + "-" + i;
                child.setBeneficiaryReference(reference);
            }
        }
        if ((pa == null) & (f == null)) {
            reference = "Ref_EN/" + "-" + child.getId().toString();
            child.setBeneficiaryReference(reference);
        }
        if ((pa != null) & (f != null)) {
            List<Profile> children = profileRepository.findChildrenOfOneFamily(f);
            int i = children.size() + 1;
            reference =
                "Ref_EN/" +
                authorizingOfficerRepository.getById(a.getAuthorizingOfficer().getId()).getAbbreviation() +
                "-" +
                f.getId().toString() +
                "-" +
                i;
            child.setBeneficiaryReference(reference);
        }
        child = childRepository.save(child);

        profile.setChild(child);
        profileRepository.save(profile);
        return childMapper.toDto(child);
    }

    @Override
    public List<ChildDTO> getAllChildrenDetails(String beginningYear) {
        return childRepository.findAllChildrenDetails(beginningYear);
    }

    @Override
    public List<ChildDTO> getChildrenDetails() {
        return childRepository.findChildrenDetails();
    }

    @Override
    public List<ChildDTO> getAllChildrenSchoolDetails(String beginningYear) {
        return childRepository.findChildEducationDetails(beginningYear);
    }

    @Override
    public List<ChildDTO> getChildrenWithoutFamilyDetails() {
        return childRepository.findChildrenWithoutFamilyDetails();
    }

    @Override
    public ChildDTO getChildrenNumberByCity(Long id) {
        ChildDTO n = childRepository.findChildrenInCity(id);
        return n;
    }
}
