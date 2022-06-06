package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.repository.DonationsIssuedRepository;
import com.awtar.myapp.service.DonationsIssuedService;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import com.awtar.myapp.service.mapper.DonationsIssuedMapper;
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

    public DonationsIssuedServiceImpl(DonationsIssuedRepository donationsIssuedRepository, DonationsIssuedMapper donationsIssuedMapper) {
        this.donationsIssuedRepository = donationsIssuedRepository;
        this.donationsIssuedMapper = donationsIssuedMapper;
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
}
