package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.AnhDanhGiaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.AnhDanhGia}.
 */
public interface AnhDanhGiaService {
    /**
     * Save a anhDanhGia.
     *
     * @param anhDanhGiaDTO the entity to save.
     * @return the persisted entity.
     */
    AnhDanhGiaDTO save(AnhDanhGiaDTO anhDanhGiaDTO);

    /**
     * Partially updates a anhDanhGia.
     *
     * @param anhDanhGiaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnhDanhGiaDTO> partialUpdate(AnhDanhGiaDTO anhDanhGiaDTO);

    /**
     * Get all the anhDanhGias.
     *
     * @return the list of entities.
     */
    List<AnhDanhGiaDTO> findAll();

    /**
     * Get the "id" anhDanhGia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnhDanhGiaDTO> findOne(Long id);

    /**
     * Delete the "id" anhDanhGia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
