package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.ChiTietHoaDonDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.ChiTietHoaDon}.
 */
public interface ChiTietHoaDonService {
    /**
     * Save a chiTietHoaDon.
     *
     * @param chiTietHoaDonDTO the entity to save.
     * @return the persisted entity.
     */
    ChiTietHoaDonDTO save(ChiTietHoaDonDTO chiTietHoaDonDTO);

    /**
     * Partially updates a chiTietHoaDon.
     *
     * @param chiTietHoaDonDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChiTietHoaDonDTO> partialUpdate(ChiTietHoaDonDTO chiTietHoaDonDTO);

    /**
     * Get all the chiTietHoaDons.
     *
     * @return the list of entities.
     */
    List<ChiTietHoaDonDTO> findAll();

    /**
     * Get the "id" chiTietHoaDon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChiTietHoaDonDTO> findOne(Long id);

    /**
     * Delete the "id" chiTietHoaDon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
