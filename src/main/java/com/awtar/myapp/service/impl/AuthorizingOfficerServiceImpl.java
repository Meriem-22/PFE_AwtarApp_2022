package com.awtar.myapp.service.impl;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.repository.AuthorizingOfficerRepository;
import com.awtar.myapp.service.AuthorizingOfficerService;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.mapper.AuthorizingOfficerMapper;
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
 * Service Implementation for managing {@link AuthorizingOfficer}.
 */
@Service
@Transactional
public class AuthorizingOfficerServiceImpl implements AuthorizingOfficerService {

    private final Logger log = LoggerFactory.getLogger(AuthorizingOfficerServiceImpl.class);

    private final AuthorizingOfficerRepository authorizingOfficerRepository;

    private final AuthorizingOfficerMapper authorizingOfficerMapper;

    public AuthorizingOfficerServiceImpl(
        AuthorizingOfficerRepository authorizingOfficerRepository,
        AuthorizingOfficerMapper authorizingOfficerMapper
    ) {
        this.authorizingOfficerRepository = authorizingOfficerRepository;
        this.authorizingOfficerMapper = authorizingOfficerMapper;
    }

    @Override
    public AuthorizingOfficerDTO save(AuthorizingOfficerDTO authorizingOfficerDTO) {
        log.debug("Request to save AuthorizingOfficer : {}", authorizingOfficerDTO);
        AuthorizingOfficer authorizingOfficer = authorizingOfficerMapper.toEntity(authorizingOfficerDTO);
        authorizingOfficer = authorizingOfficerRepository.save(authorizingOfficer);
        return authorizingOfficerMapper.toDto(authorizingOfficer);
    }

    @Override
    public AuthorizingOfficerDTO update(AuthorizingOfficerDTO authorizingOfficerDTO) {
        log.debug("Request to save AuthorizingOfficer : {}", authorizingOfficerDTO);
        AuthorizingOfficer authorizingOfficer = authorizingOfficerMapper.toEntity(authorizingOfficerDTO);
        authorizingOfficer = authorizingOfficerRepository.save(authorizingOfficer);
        return authorizingOfficerMapper.toDto(authorizingOfficer);
    }

    @Override
    public Optional<AuthorizingOfficerDTO> partialUpdate(AuthorizingOfficerDTO authorizingOfficerDTO) {
        log.debug("Request to partially update AuthorizingOfficer : {}", authorizingOfficerDTO);

        return authorizingOfficerRepository
            .findById(authorizingOfficerDTO.getId())
            .map(existingAuthorizingOfficer -> {
                authorizingOfficerMapper.partialUpdate(existingAuthorizingOfficer, authorizingOfficerDTO);

                return existingAuthorizingOfficer;
            })
            .map(authorizingOfficerRepository::save)
            .map(authorizingOfficerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorizingOfficerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuthorizingOfficers");
        return authorizingOfficerRepository.findAll(pageable).map(authorizingOfficerMapper::toDto);
    }

    /**
     *  Get all the authorizingOfficers where AuthorizingOfficerProfile is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AuthorizingOfficerDTO> findAllWhereAuthorizingOfficerProfileIsNull() {
        log.debug("Request to get all authorizingOfficers where AuthorizingOfficerProfile is null");
        return StreamSupport
            .stream(authorizingOfficerRepository.findAll().spliterator(), false)
            .filter(authorizingOfficer -> authorizingOfficer.getAuthorizingOfficerProfile() == null)
            .map(authorizingOfficerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorizingOfficerDTO> findOne(Long id) {
        log.debug("Request to get AuthorizingOfficer : {}", id);
        return authorizingOfficerRepository.findById(id).map(authorizingOfficerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AuthorizingOfficer : {}", id);
        authorizingOfficerRepository.deleteById(id);
    }
}
