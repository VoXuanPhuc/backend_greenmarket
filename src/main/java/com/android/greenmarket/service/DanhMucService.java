package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.DanhMucDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.DanhMuc}.
 */
public interface DanhMucService {
    /**
     * Save a danhMuc.
     *
     * @param danhMucDTO the entity to save.
     * @return the persisted entity.
     */
    DanhMucDTO save(DanhMucDTO danhMucDTO);

    /**
     * Partially updates a danhMuc.
     *
     * @param danhMucDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DanhMucDTO> partialUpdate(DanhMucDTO danhMucDTO);

    /**
     * Get all the danhMucs.
     *
     * @return the list of entities.
     */
    List<DanhMucDTO> findAll();

    /**
     * Get the "id" danhMuc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DanhMucDTO> findOne(Long id);

    /**
     * Delete the "id" danhMuc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
