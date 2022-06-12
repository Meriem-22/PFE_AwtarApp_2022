package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.domain.DonationItemDetails;
import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.repository.BeneficiaryRepository;
import com.awtar.myapp.repository.ChildRepository;
import com.awtar.myapp.repository.DonationDetailsRepository;
import com.awtar.myapp.repository.DonationItemDetailsRepository;
import com.awtar.myapp.repository.DonationsIssuedRepository;
import com.awtar.myapp.repository.EstablishmentRepository;
import com.awtar.myapp.repository.FamilyRepository;
import com.awtar.myapp.repository.ProfileRepository;
import com.awtar.myapp.service.DonationsIssuedService;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
import com.awtar.myapp.service.dto.DonationItemDetailsDTO;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import com.awtar.myapp.service.mapper.DonationDetailsMapper;
import com.awtar.myapp.service.mapper.DonationItemDetailsMapper;
import com.awtar.myapp.service.mapper.DonationsIssuedMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DonationsIssued}.
 */
@Service
@Transactional
public class DonationsIssuedServiceImpl implements DonationsIssuedService {

    private final Logger log = LoggerFactory.getLogger(DonationsIssuedServiceImpl.class);

    private final DonationsIssuedRepository donationsIssuedRepository;

    private final DonationsIssuedMapper donationsIssuedMapper;

    private final DonationDetailsMapper donationDetailsMapper;

    private final DonationDetailsRepository donationDetailsRepository;

    private final DonationItemDetailsMapper donationItemDetailsMapper;

    private final DonationItemDetailsRepository donationItemDetailsRepository;

    private final FamilyRepository familyRepository;

    private final EstablishmentRepository establishmentRepository;

    private final BeneficiaryRepository beneficiaryRepository;

    private final ProfileRepository profileRepository;

    public DonationsIssuedServiceImpl(
        DonationsIssuedRepository donationsIssuedRepository,
        DonationsIssuedMapper donationsIssuedMapper,
        DonationDetailsMapper donationDetailsMapper,
        DonationDetailsRepository donationDetailsRepository,
        DonationItemDetailsMapper donationItemDetailsMapper,
        DonationItemDetailsRepository donationItemDetailsRepository,
        FamilyRepository familyRepository,
        EstablishmentRepository establishmentRepository,
        ProfileRepository profileRepository,
        BeneficiaryRepository beneficiaryRepository
    ) {
        this.donationsIssuedRepository = donationsIssuedRepository;
        this.donationsIssuedMapper = donationsIssuedMapper;
        this.donationDetailsMapper = donationDetailsMapper;
        this.donationDetailsRepository = donationDetailsRepository;
        this.donationItemDetailsMapper = donationItemDetailsMapper;
        this.donationItemDetailsRepository = donationItemDetailsRepository;
        this.familyRepository = familyRepository;
        this.establishmentRepository = establishmentRepository;
        this.profileRepository = profileRepository;
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public DonationsIssuedDTO save(DonationsIssuedDTO donationsIssuedDTO) {
        log.debug("Request to save DonationsIssued : {}", donationsIssuedDTO);
        DonationsIssued donationsIssued = donationsIssuedMapper.toEntity(donationsIssuedDTO);
        donationsIssued = donationsIssuedRepository.save(donationsIssued);
        return donationsIssuedMapper.toDto(donationsIssued);
    }

    @Override
    public DonationsIssuedDTO update(DonationsIssuedDTO donationsIssuedDTO) {
        log.debug("Request to save DonationsIssued : {}", donationsIssuedDTO);
        DonationsIssued donationsIssued = donationsIssuedMapper.toEntity(donationsIssuedDTO);
        donationsIssued = donationsIssuedRepository.save(donationsIssued);
        return donationsIssuedMapper.toDto(donationsIssued);
    }

    @Override
    public Optional<DonationsIssuedDTO> partialUpdate(DonationsIssuedDTO donationsIssuedDTO) {
        log.debug("Request to partially update DonationsIssued : {}", donationsIssuedDTO);

        return donationsIssuedRepository
            .findById(donationsIssuedDTO.getId())
            .map(existingDonationsIssued -> {
                donationsIssuedMapper.partialUpdate(existingDonationsIssued, donationsIssuedDTO);

                return existingDonationsIssued;
            })
            .map(donationsIssuedRepository::save)
            .map(donationsIssuedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonationsIssuedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DonationsIssueds");
        return donationsIssuedRepository.findAll(pageable).map(donationsIssuedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonationsIssuedDTO> findOne(Long id) {
        log.debug("Request to get DonationsIssued : {}", id);
        return donationsIssuedRepository.findById(id).map(donationsIssuedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DonationsIssued : {}", id);
        donationsIssuedRepository.deleteById(id);
    }

    @Override
    public DonationsIssuedDTO saveCompletedDonationsIssued(DonationsIssuedDTO donationsIssuedDTO) {
        log.debug("Request to save DonationsIssued : {}", donationsIssuedDTO);
        DonationsIssued donationsIssued = donationsIssuedMapper.toEntity(donationsIssuedDTO);
        donationsIssued = donationsIssuedRepository.save(donationsIssued);

        DonationDetailsDTO donationDetails;
        donationDetails = donationsIssuedDTO.getDonationsDetailsN();

        DonationItemDetailsDTO[] detailsItems;
        detailsItems = donationDetails.getDonationItemDetails();

        Long[] benefID = donationsIssuedDTO.getIdsBeneficiary();

        if (benefID[0] == 1) {
            for (int i = 1; i < benefID.length; i++) {
                DonationDetails details = donationDetailsMapper.toEntity(donationDetails);
                details.setBeneficiary(beneficiaryRepository.getById(benefID[i]));
                details.setDonationsIssued(donationsIssued);
                details = donationDetailsRepository.save(details);
                for (int j = 0; j < detailsItems.length; j++) {
                    DonationItemDetails donationItemDetails = donationItemDetailsMapper.toEntity(detailsItems[j]);
                    donationItemDetails.setDonationDetails(details);
                    donationItemDetails.setDate(donationsIssued.getDonationsDate());
                    donationItemDetailsRepository.save(donationItemDetails);
                }
            }
        }

        if (benefID[0] == 2) {
            for (int i = 1; i < benefID.length; i++) {
                DonationDetails details = donationDetailsMapper.toEntity(donationDetails);
                Long id = profileRepository.getById(benefID[i]).getChild().getId();
                details.setBeneficiary(beneficiaryRepository.getById(id));
                details.setDonationsIssued(donationsIssued);
                details = donationDetailsRepository.save(details);
                for (int j = 0; j < detailsItems.length; j++) {
                    DonationItemDetails donationItemDetails = donationItemDetailsMapper.toEntity(detailsItems[j]);
                    donationItemDetails.setDonationDetails(details);
                    donationItemDetails.setDate(donationsIssued.getDonationsDate());
                    donationItemDetailsRepository.save(donationItemDetails);
                }
            }
        }

        if (benefID[0] == 3) {
            for (int i = 1; i < benefID.length - 1; i++) {
                DonationDetails details = donationDetailsMapper.toEntity(donationDetails);
                details.setBeneficiary(beneficiaryRepository.getById(benefID[i]));
                details.setDonationsIssued(donationsIssued);
                details = donationDetailsRepository.save(details);
                for (int j = 0; j < detailsItems.length; j++) {
                    DonationItemDetails donationItemDetails = donationItemDetailsMapper.toEntity(detailsItems[j]);
                    donationItemDetails.setDonationDetails(details);
                    donationItemDetails.setDate(donationsIssued.getDonationsDate());
                    donationItemDetailsRepository.save(donationItemDetails);
                }
            }
        }

        return donationsIssuedMapper.toDto(donationsIssued);
    }

    @Override
    public List<DonationsIssuedDTO> getLastValidatedDonations() {
        List<DonationsIssued> donations = donationsIssuedRepository.findAllDetailsItemDonations();
        return donationsIssuedMapper.toDto(donations);
    }
}
