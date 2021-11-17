package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.KhachHangDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.KhachHang}.
 */
public interface KhachHangService {
    /**
     * Save a khachHang.
     *
     * @param khachHangDTO the entity to save.
     * @return the persisted entity.
     */
    KhachHangDTO save(KhachHangDTO khachHangDTO);

    /**
     * Partially updates a khachHang.
     *
     * @param khachHangDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<KhachHangDTO> partialUpdate(KhachHangDTO khachHangDTO);

    /**
     * Get all the khachHangs.
     *
     * @return the list of entities.
     */
    List<KhachHangDTO> findAll();

    /**
     * Get the "id" khachHang.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KhachHangDTO> findOne(Long id);

    /**
     * Delete the "id" khachHang.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
