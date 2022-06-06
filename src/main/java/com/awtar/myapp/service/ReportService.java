package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.ReportDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.Report}.
 */
public interface ReportService {
    /**
     * Save a report.
     *
     * @param reportDTO the entity to save.
     * @return the persisted entity.
     */
    ReportDTO save(ReportDTO reportDTO);

    /**
     * Updates a report.
     *
     * @param reportDTO the entity to update.
     * @return the persisted entity.
     */
    ReportDTO update(ReportDTO reportDTO);

    /**
     * Partially updates a report.
     *
     * @param reportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReportDTO> partialUpdate(ReportDTO reportDTO);

    /**
     * Get all the reports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReportDTO> findAll(Pageable pageable);

    /**
     * Get all the reports with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReportDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" report.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReportDTO> findOne(Long id);

    /**
     * Delete the "id" report.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
