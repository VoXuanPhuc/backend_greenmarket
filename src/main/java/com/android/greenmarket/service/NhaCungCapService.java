package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.NhaCungCapDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.NhaCungCap}.
 */
public interface NhaCungCapService {
    /**
     * Save a nhaCungCap.
     *
     * @param nhaCungCapDTO the entity to save.
     * @return the persisted entity.
     */
    NhaCungCapDTO save(NhaCungCapDTO nhaCungCapDTO);

    /**
     * Partially updates a nhaCungCap.
     *
     * @param nhaCungCapDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NhaCungCapDTO> partialUpdate(NhaCungCapDTO nhaCungCapDTO);

    /**
     * Get all the nhaCungCaps.
     *
     * @return the list of entities.
     */
    List<NhaCungCapDTO> findAll();

    /**
     * Get the "id" nhaCungCap.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NhaCungCapDTO> findOne(Long id);

    /**
     * Delete the "id" nhaCungCap.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
