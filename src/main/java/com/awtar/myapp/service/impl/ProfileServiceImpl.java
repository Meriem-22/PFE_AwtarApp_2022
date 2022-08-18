package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.repository.FamilyRepository;
import com.awtar.myapp.repository.ParentRepository;
import com.awtar.myapp.repository.ProfileRepository;
import com.awtar.myapp.repository.TutorRepository;
import com.awtar.myapp.service.ProfileService;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.mapper.ProfileMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Profile}.
 */
@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    private final FamilyRepository familyRepository;

    private final TutorRepository tutorRepository;

    private final ParentRepository parentRepository;

    public ProfileServiceImpl(
        ProfileRepository profileRepository,
        ProfileMapper profileMapper,
        FamilyRepository familyRepository,
        TutorRepository tutorRepository,
        ParentRepository parentRepository
    ) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.familyRepository = familyRepository;
        this.tutorRepository = tutorRepository;
        this.parentRepository = parentRepository;
    }

    @Override
    public ProfileDTO save(ProfileDTO profileDTO) {
        log.debug("Request to save Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    @Override
    public ProfileDTO update(ProfileDTO profileDTO) {
        log.debug("Request to save Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    @Override
    public Optional<ProfileDTO> partialUpdate(ProfileDTO profileDTO) {
        log.debug("Request to partially update Profile : {}", profileDTO);

        return profileRepository
            .findById(profileDTO.getId())
            .map(existingProfile -> {
                profileMapper.partialUpdate(existingProfile, profileDTO);

                return existingProfile;
            })
            .map(profileRepository::save)
            .map(profileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll(pageable).map(profileMapper::toDto);
    }

    public Page<ProfileDTO> findAllWithEagerRelationships(Pageable pageable) {
        return profileRepository.findAllWithEagerRelationships(pageable).map(profileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProfileDTO> findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findOneWithEagerRelationships(id).map(profileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
    }

    @Override
    public List<ProfileDTO> findFamilyParents(Long id) {
        Family family = familyRepository.getById(id);
        List<ProfileDTO> parents = profileRepository.findParentsOfOneFamily(family);

        return parents;
    }

    @Override
    public List<ProfileDTO> findFamilyChildren(Long id) {
        Family family = familyRepository.getById(id);
        List<Profile> children = profileRepository.findChildrenOfOneFamily(family);

        return profileMapper.toDto(children);
    }

    @Override
    public List<ProfileDTO> findProfileChildren() {
        List<Profile> profile = profileRepository.findAllProfileChild();
        return profileMapper.toDto(profile);
    }

    @Override
    public List<ProfileDTO> findTutorProfile() {
        List<Profile> profiles = profileRepository.findProfileTutor();
        return profileMapper.toDto(profiles);
    }

    @Override
    public List<ProfileDTO> findAuthorizingOfficerProfile() {
        List<Profile> profiles = profileRepository.findProfileAuthorizingOfficer();
        return profileMapper.toDto(profiles);
    }

    @Override
    public Optional<ProfileDTO> findProfileX(Long id) {
        return profileRepository.findProfileX(id).map(profileMapper::toDto);
    }

    @Override
    public List<ProfileDTO> findOtherAuthorizingOfficers(Long id) {
        List<ProfileDTO> l = profileRepository.findOthersAuthorizingOfficersProfiles(id);

        return l;
    }

    @Override
    public List<ProfileDTO> findOthersTutors(Long id) {
        List<ProfileDTO> l = profileRepository.findOthersTutorsProfiles(id);

        return l;
    }
}
