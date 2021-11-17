package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.DanhGiaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.DanhGia}.
 */
public interface DanhGiaService {
    /**
     * Save a danhGia.
     *
     * @param danhGiaDTO the entity to save.
     * @return the persisted entity.
     */
    DanhGiaDTO save(DanhGiaDTO danhGiaDTO);

    /**
     * Partially updates a danhGia.
     *
     * @param danhGiaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DanhGiaDTO> partialUpdate(DanhGiaDTO danhGiaDTO);

    /**
     * Get all the danhGias.
     *
     * @return the list of entities.
     */
    List<DanhGiaDTO> findAll();

    /**
     * Get the "id" danhGia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DanhGiaDTO> findOne(Long id);

    /**
     * Delete the "id" danhGia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
