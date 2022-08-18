package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.Establishment;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.enumeration.Beneficiaries;
import com.awtar.myapp.repository.AuthorizingOfficerRepository;
import com.awtar.myapp.repository.BeneficiaryRepository;
import com.awtar.myapp.repository.EstablishmentRepository;
import com.awtar.myapp.repository.ProfileRepository;
import com.awtar.myapp.repository.TutorRepository;
import com.awtar.myapp.service.EstablishmentService;
import com.awtar.myapp.service.dto.EstablishmentDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import com.awtar.myapp.service.mapper.AuthorizingOfficerMapper;
import com.awtar.myapp.service.mapper.EstablishmentMapper;
import com.awtar.myapp.service.mapper.ProfileMapper;
import com.awtar.myapp.service.mapper.TutorMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Establishment}.
 */
@Service
@Transactional
public class EstablishmentServiceImpl implements EstablishmentService {

    private final Logger log = LoggerFactory.getLogger(EstablishmentServiceImpl.class);

    private final EstablishmentRepository establishmentRepository;

    private final EstablishmentMapper establishmentMapper;

    private final AuthorizingOfficerMapper authorizingOfficerMapper;

    private final TutorMapper tutorMapper;

    private final AuthorizingOfficerRepository authorizingOfficerRepository;

    private final TutorRepository tutorRepository;

    private final ProfileMapper profileMapper;

    private final ProfileRepository profileRepository;

    private final BeneficiaryRepository beneficiaryRepository;

    public EstablishmentServiceImpl(
        EstablishmentRepository establishmentRepository,
        EstablishmentMapper establishmentMapper,
        AuthorizingOfficerMapper authorizingOfficerMapper,
        TutorMapper tutorMapper,
        AuthorizingOfficerRepository authorizingOfficerRepository,
        TutorRepository tutorRepository,
        ProfileMapper profileMapper,
        ProfileRepository profileRepository,
        BeneficiaryRepository beneficiaryRepository
    ) {
        this.establishmentRepository = establishmentRepository;
        this.establishmentMapper = establishmentMapper;
        this.authorizingOfficerMapper = authorizingOfficerMapper;
        this.tutorMapper = tutorMapper;
        this.authorizingOfficerRepository = authorizingOfficerRepository;
        this.tutorRepository = tutorRepository;
        this.profileMapper = profileMapper;
        this.profileRepository = profileRepository;
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public EstablishmentDTO save(EstablishmentDTO establishmentDTO) {
        log.debug("Request to save Establishment : {}", establishmentDTO);
        Establishment establishment = establishmentMapper.toEntity(establishmentDTO);
        establishment.setBeneficiaryType(Beneficiaries.ESTABLISHMENT);
        establishment = establishmentRepository.save(establishment);
        return establishmentMapper.toDto(establishment);
    }

    @Override
    public EstablishmentDTO update(EstablishmentDTO establishmentDTO) {
        log.debug("Request to save Establishment : {}", establishmentDTO);
        Establishment establishment = establishmentMapper.toEntity(establishmentDTO);
        establishment.setBeneficiaryType(Beneficiaries.ESTABLISHMENT);
        Beneficiary beneficiary = beneficiaryRepository.getById(establishment.getId());
        establishment.setAuthorizingOfficer(beneficiary.getAuthorizingOfficer());
        establishment.setTutor(beneficiary.getTutor());
        establishment.setBeneficiaryReference(beneficiary.getBeneficiaryReference());
        establishment = establishmentRepository.save(establishment);
        return establishmentMapper.toDto(establishment);
    }

    @Override
    public Optional<EstablishmentDTO> partialUpdate(EstablishmentDTO establishmentDTO) {
        log.debug("Request to partially update Establishment : {}", establishmentDTO);

        return establishmentRepository
            .findById(establishmentDTO.getId())
            .map(existingEstablishment -> {
                establishmentMapper.partialUpdate(existingEstablishment, establishmentDTO);

                return existingEstablishment;
            })
            .map(establishmentRepository::save)
            .map(establishmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstablishmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Establishments");
        return establishmentRepository.findAll(pageable).map(establishmentMapper::toDto);
    }

    public Page<EstablishmentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return establishmentRepository.findAllWithEagerRelationships(pageable).map(establishmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstablishmentDTO> findOne(Long id) {
        log.debug("Request to get Establishment : {}", id);
        return establishmentRepository.findOneWithEagerRelationships(id).map(establishmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Establishment : {}", id);
        establishmentRepository.deleteById(id);
    }

    @Override
    public List<EstablishmentDTO> findEstablishments() {
        List<Establishment> establishments = establishmentRepository.findAllWithToOneRelationships();
        return establishmentMapper.toDto(establishments);
    }

    @Override
    public EstablishmentDTO add(EstablishmentDTO establishmentDTO) {
        ProfileDTO a = establishmentDTO.getAuthorizingOfficer();
        ProfileDTO t = establishmentDTO.getTutor();

        Profile pa = profileMapper.toEntity(a);
        Profile pt = profileMapper.toEntity(t);

        Establishment establishment = establishmentMapper.toEntity(establishmentDTO);
        if (pa != null) {
            establishment.setAuthorizingOfficer(authorizingOfficerRepository.getById(pa.getAuthorizingOfficer().getId()));
        }
        if (pt != null) {
            establishment.setTutor(tutorRepository.getById(pt.getTutor().getId()));
        }
        establishment.setBeneficiaryType(Beneficiaries.ESTABLISHMENT);
        establishment = establishmentRepository.save(establishment);

        String reference;
        if (pa != null) {
            reference =
                "Ref_ET/" +
                authorizingOfficerRepository.getById(a.getAuthorizingOfficer().getId()).getAbbreviation() +
                "-" +
                establishment.getId().toString();
            establishment.setBeneficiaryReference(reference);
        }
        if (pa == null) {
            reference = "Ref_ET/" + "-" + establishment.getId().toString();
            establishment.setBeneficiaryReference(reference);
        }
        establishmentRepository.save(establishment);
        return establishmentMapper.toDto(establishment);
    }
}
