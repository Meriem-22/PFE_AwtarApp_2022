package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.DonationItemDetails;
import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.repository.DonationItemDetailsRepository;
import com.awtar.myapp.repository.DonationsIssuedRepository;
import com.awtar.myapp.service.DonationItemDetailsService;
import com.awtar.myapp.service.dto.DonationItemDetailsDTO;
import com.awtar.myapp.service.mapper.DonationItemDetailsMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DonationItemDetails}.
 */
@Service
@Transactional
public class DonationItemDetailsServiceImpl implements DonationItemDetailsService {

    private final Logger log = LoggerFactory.getLogger(DonationItemDetailsServiceImpl.class);

    private final DonationItemDetailsRepository donationItemDetailsRepository;

    private final DonationItemDetailsMapper donationItemDetailsMapper;

    private final DonationsIssuedRepository donationsIssuedRepository;

    public DonationItemDetailsServiceImpl(
        DonationItemDetailsRepository donationItemDetailsRepository,
        DonationItemDetailsMapper donationItemDetailsMapper,
        DonationsIssuedRepository donationsIssuedRepository
    ) {
        this.donationItemDetailsRepository = donationItemDetailsRepository;
        this.donationItemDetailsMapper = donationItemDetailsMapper;
        this.donationsIssuedRepository = donationsIssuedRepository;
    }

    @Override
    public DonationItemDetailsDTO save(DonationItemDetailsDTO donationItemDetailsDTO) {
        log.debug("Request to save DonationItemDetails : {}", donationItemDetailsDTO);
        DonationItemDetails donationItemDetails = donationItemDetailsMapper.toEntity(donationItemDetailsDTO);
        donationItemDetails = donationItemDetailsRepository.save(donationItemDetails);
        return donationItemDetailsMapper.toDto(donationItemDetails);
    }

    @Override
    public DonationItemDetailsDTO update(DonationItemDetailsDTO donationItemDetailsDTO) {
        log.debug("Request to save DonationItemDetails : {}", donationItemDetailsDTO);
        DonationItemDetails donationItemDetails = donationItemDetailsMapper.toEntity(donationItemDetailsDTO);
        donationItemDetails = donationItemDetailsRepository.save(donationItemDetails);
        return donationItemDetailsMapper.toDto(donationItemDetails);
    }

    @Override
    public Optional<DonationItemDetailsDTO> partialUpdate(DonationItemDetailsDTO donationItemDetailsDTO) {
        log.debug("Request to partially update DonationItemDetails : {}", donationItemDetailsDTO);

        return donationItemDetailsRepository
            .findById(donationItemDetailsDTO.getId())
            .map(existingDonationItemDetails -> {
                donationItemDetailsMapper.partialUpdate(existingDonationItemDetails, donationItemDetailsDTO);

                return existingDonationItemDetails;
            })
            .map(donationItemDetailsRepository::save)
            .map(donationItemDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonationItemDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DonationItemDetails");
        return donationItemDetailsRepository.findAll(pageable).map(donationItemDetailsMapper::toDto);
    }

    public Page<DonationItemDetailsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return donationItemDetailsRepository.findAllWithEagerRelationships(pageable).map(donationItemDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonationItemDetailsDTO> findOne(Long id) {
        log.debug("Request to get DonationItemDetails : {}", id);
        return donationItemDetailsRepository.findOneWithEagerRelationships(id).map(donationItemDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DonationItemDetails : {}", id);
        donationItemDetailsRepository.deleteById(id);
    }

    @Override
    public List<DonationItemDetailsDTO> findAllOfOneDonation(Long id) {
        DonationsIssued donation = donationsIssuedRepository.getById(id);
        List<DonationItemDetails> donationsItem = donationItemDetailsRepository.findAllDetailsItemDonations(donation);

        return donationItemDetailsMapper.toDto(donationsItem);
    }
}
