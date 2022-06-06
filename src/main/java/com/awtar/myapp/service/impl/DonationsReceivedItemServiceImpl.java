package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.DonationsReceivedItem;
import com.awtar.myapp.repository.DonationsReceivedItemRepository;
import com.awtar.myapp.service.DonationsReceivedItemService;
import com.awtar.myapp.service.dto.DonationsReceivedItemDTO;
import com.awtar.myapp.service.mapper.DonationsReceivedItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DonationsReceivedItem}.
 */
@Service
@Transactional
public class DonationsReceivedItemServiceImpl implements DonationsReceivedItemService {

    private final Logger log = LoggerFactory.getLogger(DonationsReceivedItemServiceImpl.class);

    private final DonationsReceivedItemRepository donationsReceivedItemRepository;

    private final DonationsReceivedItemMapper donationsReceivedItemMapper;

    public DonationsReceivedItemServiceImpl(
        DonationsReceivedItemRepository donationsReceivedItemRepository,
        DonationsReceivedItemMapper donationsReceivedItemMapper
    ) {
        this.donationsReceivedItemRepository = donationsReceivedItemRepository;
        this.donationsReceivedItemMapper = donationsReceivedItemMapper;
    }

    @Override
    public DonationsReceivedItemDTO save(DonationsReceivedItemDTO donationsReceivedItemDTO) {
        log.debug("Request to save DonationsReceivedItem : {}", donationsReceivedItemDTO);
        DonationsReceivedItem donationsReceivedItem = donationsReceivedItemMapper.toEntity(donationsReceivedItemDTO);
        donationsReceivedItem = donationsReceivedItemRepository.save(donationsReceivedItem);
        return donationsReceivedItemMapper.toDto(donationsReceivedItem);
    }

    @Override
    public DonationsReceivedItemDTO update(DonationsReceivedItemDTO donationsReceivedItemDTO) {
        log.debug("Request to save DonationsReceivedItem : {}", donationsReceivedItemDTO);
        DonationsReceivedItem donationsReceivedItem = donationsReceivedItemMapper.toEntity(donationsReceivedItemDTO);
        donationsReceivedItem = donationsReceivedItemRepository.save(donationsReceivedItem);
        return donationsReceivedItemMapper.toDto(donationsReceivedItem);
    }

    @Override
    public Optional<DonationsReceivedItemDTO> partialUpdate(DonationsReceivedItemDTO donationsReceivedItemDTO) {
        log.debug("Request to partially update DonationsReceivedItem : {}", donationsReceivedItemDTO);

        return donationsReceivedItemRepository
            .findById(donationsReceivedItemDTO.getId())
            .map(existingDonationsReceivedItem -> {
                donationsReceivedItemMapper.partialUpdate(existingDonationsReceivedItem, donationsReceivedItemDTO);

                return existingDonationsReceivedItem;
            })
            .map(donationsReceivedItemRepository::save)
            .map(donationsReceivedItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonationsReceivedItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DonationsReceivedItems");
        return donationsReceivedItemRepository.findAll(pageable).map(donationsReceivedItemMapper::toDto);
    }

    public Page<DonationsReceivedItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return donationsReceivedItemRepository.findAllWithEagerRelationships(pageable).map(donationsReceivedItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonationsReceivedItemDTO> findOne(Long id) {
        log.debug("Request to get DonationsReceivedItem : {}", id);
        return donationsReceivedItemRepository.findOneWithEagerRelationships(id).map(donationsReceivedItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DonationsReceivedItem : {}", id);
        donationsReceivedItemRepository.deleteById(id);
    }
}
