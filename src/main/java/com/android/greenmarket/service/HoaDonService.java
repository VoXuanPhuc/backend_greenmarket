package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.HoaDonDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.HoaDon}.
 */
public interface HoaDonService {
    /**
     * Save a hoaDon.
     *
     * @param hoaDonDTO the entity to save.
     * @return the persisted entity.
     */
    HoaDonDTO save(HoaDonDTO hoaDonDTO);

    /**
     * Partially updates a hoaDon.
     *
     * @param hoaDonDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HoaDonDTO> partialUpdate(HoaDonDTO hoaDonDTO);

    /**
     * Get all the hoaDons.
     *
     * @return the list of entities.
     */
    List<HoaDonDTO> findAll();

    /**
     * Get the "id" hoaDon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HoaDonDTO> findOne(Long id);

    /**
     * Delete the "id" hoaDon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
