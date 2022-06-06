package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.EstablishmentType;
import com.awtar.myapp.repository.EstablishmentTypeRepository;
import com.awtar.myapp.service.EstablishmentTypeService;
import com.awtar.myapp.service.dto.EstablishmentTypeDTO;
import com.awtar.myapp.service.mapper.EstablishmentTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstablishmentType}.
 */
@Service
@Transactional
public class EstablishmentTypeServiceImpl implements EstablishmentTypeService {

    private final Logger log = LoggerFactory.getLogger(EstablishmentTypeServiceImpl.class);

    private final EstablishmentTypeRepository establishmentTypeRepository;

    private final EstablishmentTypeMapper establishmentTypeMapper;

    public EstablishmentTypeServiceImpl(
        EstablishmentTypeRepository establishmentTypeRepository,
        EstablishmentTypeMapper establishmentTypeMapper
    ) {
        this.establishmentTypeRepository = establishmentTypeRepository;
        this.establishmentTypeMapper = establishmentTypeMapper;
    }

    @Override
    public EstablishmentTypeDTO save(EstablishmentTypeDTO establishmentTypeDTO) {
        log.debug("Request to save EstablishmentType : {}", establishmentTypeDTO);
        EstablishmentType establishmentType = establishmentTypeMapper.toEntity(establishmentTypeDTO);
        establishmentType = establishmentTypeRepository.save(establishmentType);
        return establishmentTypeMapper.toDto(establishmentType);
    }

    @Override
    public EstablishmentTypeDTO update(EstablishmentTypeDTO establishmentTypeDTO) {
        log.debug("Request to save EstablishmentType : {}", establishmentTypeDTO);
        EstablishmentType establishmentType = establishmentTypeMapper.toEntity(establishmentTypeDTO);
        establishmentType = establishmentTypeRepository.save(establishmentType);
        return establishmentTypeMapper.toDto(establishmentType);
    }

    @Override
    public Optional<EstablishmentTypeDTO> partialUpdate(EstablishmentTypeDTO establishmentTypeDTO) {
        log.debug("Request to partially update EstablishmentType : {}", establishmentTypeDTO);

        return establishmentTypeRepository
            .findById(establishmentTypeDTO.getId())
            .map(existingEstablishmentType -> {
                establishmentTypeMapper.partialUpdate(existingEstablishmentType, establishmentTypeDTO);

                return existingEstablishmentType;
            })
            .map(establishmentTypeRepository::save)
            .map(establishmentTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstablishmentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstablishmentTypes");
        return establishmentTypeRepository.findAll(pageable).map(establishmentTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstablishmentTypeDTO> findOne(Long id) {
        log.debug("Request to get EstablishmentType : {}", id);
        return establishmentTypeRepository.findById(id).map(establishmentTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstablishmentType : {}", id);
        establishmentTypeRepository.deleteById(id);
    }
}
