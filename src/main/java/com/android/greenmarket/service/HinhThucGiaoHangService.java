package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.HinhThucGiaoHangDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.HinhThucGiaoHang}.
 */
public interface HinhThucGiaoHangService {
    /**
     * Save a hinhThucGiaoHang.
     *
     * @param hinhThucGiaoHangDTO the entity to save.
     * @return the persisted entity.
     */
    HinhThucGiaoHangDTO save(HinhThucGiaoHangDTO hinhThucGiaoHangDTO);

    /**
     * Partially updates a hinhThucGiaoHang.
     *
     * @param hinhThucGiaoHangDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HinhThucGiaoHangDTO> partialUpdate(HinhThucGiaoHangDTO hinhThucGiaoHangDTO);

    /**
     * Get all the hinhThucGiaoHangs.
     *
     * @return the list of entities.
     */
    List<HinhThucGiaoHangDTO> findAll();

    /**
     * Get the "id" hinhThucGiaoHang.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HinhThucGiaoHangDTO> findOne(Long id);

    /**
     * Delete the "id" hinhThucGiaoHang.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
