package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.repository.ProfileRepository;
import com.awtar.myapp.repository.TutorRepository;
import com.awtar.myapp.service.TutorService;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import com.awtar.myapp.service.mapper.ProfileMapper;
import com.awtar.myapp.service.mapper.TutorMapper;
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
 * Service Implementation for managing {@link Tutor}.
 */
@Service
@Transactional
public class TutorServiceImpl implements TutorService {

    private final Logger log = LoggerFactory.getLogger(TutorServiceImpl.class);

    private final TutorRepository tutorRepository;

    private final TutorMapper tutorMapper;

    private final ProfileMapper profileMapper;

    private final ProfileRepository profileRepository;

    public TutorServiceImpl(
        TutorRepository tutorRepository,
        TutorMapper tutorMapper,
        ProfileMapper profileMapper,
        ProfileRepository profileRepository
    ) {
        this.tutorRepository = tutorRepository;
        this.tutorMapper = tutorMapper;
        this.profileMapper = profileMapper;
        this.profileRepository = profileRepository;
    }

    @Override
    public TutorDTO save(TutorDTO tutorDTO) {
        log.debug("Request to save Tutor : {}", tutorDTO);
        Tutor tutor = tutorMapper.toEntity(tutorDTO);
        tutor = tutorRepository.save(tutor);
        return tutorMapper.toDto(tutor);
    }

    @Override
    public TutorDTO update(TutorDTO tutorDTO) {
        log.debug("Request to save Tutor : {}", tutorDTO);
        Tutor tutor = tutorMapper.toEntity(tutorDTO);
        tutor = tutorRepository.save(tutor);
        return tutorMapper.toDto(tutor);
    }

    @Override
    public Optional<TutorDTO> partialUpdate(TutorDTO tutorDTO) {
        log.debug("Request to partially update Tutor : {}", tutorDTO);

        return tutorRepository
            .findById(tutorDTO.getId())
            .map(existingTutor -> {
                tutorMapper.partialUpdate(existingTutor, tutorDTO);

                return existingTutor;
            })
            .map(tutorRepository::save)
            .map(tutorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TutorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tutors");
        return tutorRepository.findAll(pageable).map(tutorMapper::toDto);
    }

    /**
     *  Get all the tutors where TutorProfile is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TutorDTO> findAllWhereTutorProfileIsNull() {
        log.debug("Request to get all tutors where TutorProfile is null");
        return StreamSupport
            .stream(tutorRepository.findAll().spliterator(), false)
            .filter(tutor -> tutor.getTutorProfile() == null)
            .map(tutorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TutorDTO> findOne(Long id) {
        log.debug("Request to get Tutor : {}", id);
        return tutorRepository.findById(id).map(tutorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tutor : {}", id);
        tutorRepository.deleteById(id);
    }

    @Override
    public TutorDTO add(TutorDTO tutorDTO) {
        ProfileDTO profiledto = tutorDTO.getProfile();
        Profile profile = profileMapper.toEntity(profiledto);
        Tutor tutor = tutorMapper.toEntity(tutorDTO);

        tutor = tutorRepository.save(tutor);
        profile.setTutor(tutor);
        profileRepository.save(profile);
        return tutorMapper.toDto(tutor);
    }

    @Override
    public List<TutorDTO> findTutorsDetails() {
        List<TutorDTO> l = tutorRepository.findTutorsDetails();
        return l;
    }
}
