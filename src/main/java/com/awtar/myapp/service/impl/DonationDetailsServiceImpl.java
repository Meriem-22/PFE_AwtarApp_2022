package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.repository.BeneficiaryRepository;
import com.awtar.myapp.repository.DonationDetailsRepository;
import com.awtar.myapp.service.DonationDetailsService;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
import com.awtar.myapp.service.mapper.DonationDetailsMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DonationDetails}.
 */
@Service
@Transactional
public class DonationDetailsServiceImpl implements DonationDetailsService {

    private final Logger log = LoggerFactory.getLogger(DonationDetailsServiceImpl.class);

    private final DonationDetailsRepository donationDetailsRepository;

    private final DonationDetailsMapper donationDetailsMapper;

    private final BeneficiaryRepository beneficiaryRepository;

    public DonationDetailsServiceImpl(
        DonationDetailsRepository donationDetailsRepository,
        DonationDetailsMapper donationDetailsMapper,
        BeneficiaryRepository beneficiaryRepository
    ) {
        this.donationDetailsRepository = donationDetailsRepository;
        this.donationDetailsMapper = donationDetailsMapper;
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public DonationDetailsDTO save(DonationDetailsDTO donationDetailsDTO) {
        log.debug("Request to save DonationDetails : {}", donationDetailsDTO);
        DonationDetails donationDetails = donationDetailsMapper.toEntity(donationDetailsDTO);
        donationDetails = donationDetailsRepository.save(donationDetails);
        return donationDetailsMapper.toDto(donationDetails);
    }

    @Override
    public DonationDetailsDTO update(DonationDetailsDTO donationDetailsDTO) {
        log.debug("Request to save DonationDetails : {}", donationDetailsDTO);
        DonationDetails donationDetails = donationDetailsMapper.toEntity(donationDetailsDTO);
        donationDetails = donationDetailsRepository.save(donationDetails);
        return donationDetailsMapper.toDto(donationDetails);
    }

    @Override
    public Optional<DonationDetailsDTO> partialUpdate(DonationDetailsDTO donationDetailsDTO) {
        log.debug("Request to partially update DonationDetails : {}", donationDetailsDTO);

        return donationDetailsRepository
            .findById(donationDetailsDTO.getId())
            .map(existingDonationDetails -> {
                donationDetailsMapper.partialUpdate(existingDonationDetails, donationDetailsDTO);

                return existingDonationDetails;
            })
            .map(donationDetailsRepository::save)
            .map(donationDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonationDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DonationDetails");
        return donationDetailsRepository.findAll(pageable).map(donationDetailsMapper::toDto);
    }

    public Page<DonationDetailsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return donationDetailsRepository.findAllWithEagerRelationships(pageable).map(donationDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonationDetailsDTO> findOne(Long id) {
        log.debug("Request to get DonationDetails : {}", id);
        return donationDetailsRepository.findOneWithEagerRelationships(id).map(donationDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DonationDetails : {}", id);
        donationDetailsRepository.deleteById(id);
    }

    @Override
    public Page<DonationDetailsDTO> findAllBeneficiaryDonation(Long id, Pageable pageable) {
        Page<DonationDetailsDTO> donation = donationDetailsRepository.findAllDonations(beneficiaryRepository.getById(id), pageable);
        return donation;
    }

    @Override
    public List<DonationDetailsDTO> findAllBeneficiaryDonationList(Long id) {
        List<DonationDetailsDTO> donation = donationDetailsRepository.findAllDonationsList(beneficiaryRepository.getById(id));
        return donation;
    }
}
