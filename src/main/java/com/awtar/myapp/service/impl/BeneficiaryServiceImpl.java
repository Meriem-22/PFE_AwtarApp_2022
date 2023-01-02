package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.Establishment;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.repository.AuthorizingOfficerRepository;
import com.awtar.myapp.repository.BeneficiaryRepository;
import com.awtar.myapp.repository.ChildRepository;
import com.awtar.myapp.repository.EstablishmentRepository;
import com.awtar.myapp.repository.FamilyRepository;
import com.awtar.myapp.repository.TutorRepository;
import com.awtar.myapp.service.BeneficiaryService;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import com.awtar.myapp.service.mapper.BeneficiaryMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Beneficiary}.
 */
@Service
@Transactional
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);

    private final BeneficiaryRepository beneficiaryRepository;

    private final BeneficiaryMapper beneficiaryMapper;

    private final FamilyRepository familyRepository;

    private final ChildRepository childRepository;

    private final EstablishmentRepository establishmentRepository;

    private final AuthorizingOfficerRepository authorizingOfficerRepository;

    private final TutorRepository tutorRepository;

    public BeneficiaryServiceImpl(
        BeneficiaryRepository beneficiaryRepository,
        BeneficiaryMapper beneficiaryMapper,
        FamilyRepository familyRepository,
        ChildRepository childRepository,
        EstablishmentRepository establishmentRepository,
        AuthorizingOfficerRepository authorizingOfficerRepository,
        TutorRepository tutorRepository
    ) {
        this.beneficiaryRepository = beneficiaryRepository;
        this.beneficiaryMapper = beneficiaryMapper;
        this.familyRepository = familyRepository;
        this.childRepository = childRepository;
        this.establishmentRepository = establishmentRepository;
        this.authorizingOfficerRepository = authorizingOfficerRepository;
        this.tutorRepository = tutorRepository;
    }

    @Override
    public BeneficiaryDTO save(BeneficiaryDTO beneficiaryDTO) {
        log.debug("Request to save Beneficiary : {}", beneficiaryDTO);
        Beneficiary beneficiary = beneficiaryMapper.toEntity(beneficiaryDTO);
        beneficiary = beneficiaryRepository.save(beneficiary);
        return beneficiaryMapper.toDto(beneficiary);
    }

    @Override
    public BeneficiaryDTO update(BeneficiaryDTO beneficiaryDTO) {
        log.debug("Request to save Beneficiary : {}", beneficiaryDTO);
        Beneficiary beneficiary = beneficiaryMapper.toEntity(beneficiaryDTO);
        Family family = familyRepository.getById(beneficiary.getId());
        family.setAuthorizingOfficer(beneficiary.getAuthorizingOfficer());
        family.setTutor(beneficiary.getTutor());
        family = familyRepository.save(family);
        /* 
        Child child= childRepository.getById(beneficiary.getId());
        Establishment establishment = establishmentRepository.getById(beneficiary.getId());
        beneficiary = beneficiaryRepository.save(beneficiary);*/
        return beneficiaryMapper.toDto(beneficiary);
    }

    @Override
    public Optional<BeneficiaryDTO> partialUpdate(BeneficiaryDTO beneficiaryDTO) {
        log.debug("Request to partially update Beneficiary : {}", beneficiaryDTO);

        return beneficiaryRepository
            .findById(beneficiaryDTO.getId())
            .map(existingBeneficiary -> {
                beneficiaryMapper.partialUpdate(existingBeneficiary, beneficiaryDTO);

                return existingBeneficiary;
            })
            .map(beneficiaryRepository::save)
            .map(beneficiaryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BeneficiaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Beneficiaries");
        return beneficiaryRepository.findAll(pageable).map(beneficiaryMapper::toDto);
    }

    public Page<BeneficiaryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return beneficiaryRepository.findAllWithEagerRelationships(pageable).map(beneficiaryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeneficiaryDTO> findOne(Long id) {
        log.debug("Request to get Beneficiary : {}", id);
        return beneficiaryRepository.findOneWithEagerRelationships(id).map(beneficiaryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Beneficiary : {}", id);
        beneficiaryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BeneficiaryDTO calculReference(BeneficiaryDTO beneficiaryDTO) {
        log.debug("Request to calcul Beneficiary Reference : {}", beneficiaryDTO);
        Beneficiary beneficiary = beneficiaryMapper.toEntity(beneficiaryDTO);
        AuthorizingOfficer authorizingOfficer = beneficiary.getAuthorizingOfficer();
        String reference = "Ref-" + authorizingOfficer.getAbbreviation() + "-" + beneficiary.getId().toString();
        Long id = beneficiary.getId();
        beneficiaryRepository.setBeneficiaryReference(id, reference, authorizingOfficer);
        return beneficiaryMapper.toDto(beneficiary);
    }

    @Override
    public BeneficiaryDTO updateAuthorizingOfficer(BeneficiaryDTO beneficiaryDTO) {
        Beneficiary nBeneficiary = beneficiaryMapper.toEntity(beneficiaryDTO);
        Long id = nBeneficiary.getId();
        String ref = nBeneficiary.getBeneficiaryReference();
        int index1;
        int index2;
        Tutor tutor = null;
        AuthorizingOfficer authorizingOfficer = null;
        String nRef = "";
        index1 = ref.indexOf("/");
        index2 = ref.indexOf("-");
        if (authorizingOfficerRepository.getById(beneficiaryDTO.getIdContributor()) != null) {
            authorizingOfficer = authorizingOfficerRepository.getById(beneficiaryDTO.getIdContributor());
            String abbreviation = authorizingOfficer.getAbbreviation();
            nRef =
                String.copyValueOf(ref.toCharArray(), 0, index1 + 1) +
                abbreviation +
                String.copyValueOf(ref.toCharArray(), index2, ref.length() - index2);
            tutor = nBeneficiary.getTutor();
        }

        beneficiaryRepository.setBeneficiaryContributor(id, nRef, authorizingOfficer, tutor);

        Beneficiary beneficiary = beneficiaryRepository.getById(id);
        return beneficiaryMapper.toDto(beneficiary);
    }

    @Override
    public List<BeneficiaryDTO> findAllFamiliesContributor(Long id) {
        List<BeneficiaryDTO> l = beneficiaryRepository.findAllFamiliesByContributors(id);
        return l;
    }

    @Override
    public List<BeneficiaryDTO> findAllChildrenContributor(Long id) {
        List<BeneficiaryDTO> l = beneficiaryRepository.findAllChildrenByContributors(id);
        return l;
    }

    @Override
    public List<BeneficiaryDTO> findAllEstablishmentsContributor(Long id) {
        List<BeneficiaryDTO> l = beneficiaryRepository.findAllEstablishmentsByContributors(id);
        return l;
    }

    @Override
    public BeneficiaryDTO updateTutor(BeneficiaryDTO beneficiaryDTO) {
        Beneficiary nBeneficiary = beneficiaryMapper.toEntity(beneficiaryDTO);
        Long id = nBeneficiary.getId();
        String ref = nBeneficiary.getBeneficiaryReference();

        Tutor tutor = null;
        AuthorizingOfficer authorizingOfficer = null;
        String nRef = "";

        if (tutorRepository.getById(beneficiaryDTO.getIdContributor()) != null) {
            tutor = tutorRepository.getById(beneficiaryDTO.getIdContributor());
            authorizingOfficer = nBeneficiary.getAuthorizingOfficer();
            nRef = ref;
        }
        beneficiaryRepository.setBeneficiaryContributor(id, nRef, authorizingOfficer, tutor);

        Beneficiary beneficiary = beneficiaryRepository.getById(id);
        return beneficiaryMapper.toDto(beneficiary);
    }

    @Override
    public BeneficiaryDTO removeAuthorizingOfficer(BeneficiaryDTO beneficiaryDTO) {
        Beneficiary beneficiary = beneficiaryMapper.toEntity(beneficiaryDTO);
        Beneficiary benef = new Beneficiary();
        benef.setId(beneficiary.getId());
        benef.setBeneficiaryReference(beneficiary.getBeneficiaryReference());
        benef.setBeneficiaryType(beneficiary.getBeneficiaryType());
        benef.setArchivated(false);
        benef.setTutor(beneficiary.getTutor());
        benef = beneficiaryRepository.save(benef);
        return beneficiaryMapper.toDto(benef);
    }

    @Override
    public BeneficiaryDTO removeTutor(BeneficiaryDTO beneficiaryDTO) {
        Beneficiary beneficiary = beneficiaryMapper.toEntity(beneficiaryDTO);
        beneficiary.removeTutor();
        beneficiary = beneficiaryRepository.save(beneficiary);
        return beneficiaryMapper.toDto(beneficiary);
    }

    @Override
    public List<BeneficiaryDTO> TotalBeneficiaries() {
        List<Beneficiary> beneficiaires = beneficiaryRepository.TotalBeneficiaries();
        return beneficiaryMapper.toDto(beneficiaires);
    }
}
