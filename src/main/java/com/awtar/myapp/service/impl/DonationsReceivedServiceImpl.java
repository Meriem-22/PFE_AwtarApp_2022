package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.DonationsReceived;
import com.awtar.myapp.repository.DonationsReceivedRepository;
import com.awtar.myapp.service.DonationsReceivedService;
import com.awtar.myapp.service.dto.DonationsReceivedDTO;
import com.awtar.myapp.service.mapper.DonationsReceivedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DonationsReceived}.
 */
@Service
@Transactional
public class DonationsReceivedServiceImpl implements DonationsReceivedService {

    private final Logger log = LoggerFactory.getLogger(DonationsReceivedServiceImpl.class);

    private final DonationsReceivedRepository donationsReceivedRepository;

    private final DonationsReceivedMapper donationsReceivedMapper;

    public DonationsReceivedServiceImpl(
        DonationsReceivedRepository donationsReceivedRepository,
        DonationsReceivedMapper donationsReceivedMapper
    ) {
        this.donationsReceivedRepository = donationsReceivedRepository;
        this.donationsReceivedMapper = donationsReceivedMapper;
    }

    @Override
    public DonationsReceivedDTO save(DonationsReceivedDTO donationsReceivedDTO) {
        log.debug("Request to save DonationsReceived : {}", donationsReceivedDTO);
        DonationsReceived donationsReceived = donationsReceivedMapper.toEntity(donationsReceivedDTO);
        donationsReceived = donationsReceivedRepository.save(donationsReceived);
        return donationsReceivedMapper.toDto(donationsReceived);
    }

    @Override
    public DonationsReceivedDTO update(DonationsReceivedDTO donationsReceivedDTO) {
        log.debug("Request to save DonationsReceived : {}", donationsReceivedDTO);
        DonationsReceived donationsReceived = donationsReceivedMapper.toEntity(donationsReceivedDTO);
        donationsReceived = donationsReceivedRepository.save(donationsReceived);
        return donationsReceivedMapper.toDto(donationsReceived);
    }

    @Override
    public Optional<DonationsReceivedDTO> partialUpdate(DonationsReceivedDTO donationsReceivedDTO) {
        log.debug("Request to partially update DonationsReceived : {}", donationsReceivedDTO);

        return donationsReceivedRepository
            .findById(donationsReceivedDTO.getId())
            .map(existingDonationsReceived -> {
                donationsReceivedMapper.partialUpdate(existingDonationsReceived, donationsReceivedDTO);

                return existingDonationsReceived;
            })
            .map(donationsReceivedRepository::save)
            .map(donationsReceivedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonationsReceivedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DonationsReceiveds");
        return donationsReceivedRepository.findAll(pageable).map(donationsReceivedMapper::toDto);
    }

    public Page<DonationsReceivedDTO> findAllWithEagerRelationships(Pageable pageable) {
        return donationsReceivedRepository.findAllWithEagerRelationships(pageable).map(donationsReceivedMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonationsReceivedDTO> findOne(Long id) {
        log.debug("Request to get DonationsReceived : {}", id);
        return donationsReceivedRepository.findOneWithEagerRelationships(id).map(donationsReceivedMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DonationsReceived : {}", id);
        donationsReceivedRepository.deleteById(id);
    }
}