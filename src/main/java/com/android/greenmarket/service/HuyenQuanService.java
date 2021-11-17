package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.HuyenQuanDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.HuyenQuan}.
 */
public interface HuyenQuanService {
    /**
     * Save a huyenQuan.
     *
     * @param huyenQuanDTO the entity to save.
     * @return the persisted entity.
     */
    HuyenQuanDTO save(HuyenQuanDTO huyenQuanDTO);

    /**
     * Partially updates a huyenQuan.
     *
     * @param huyenQuanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HuyenQuanDTO> partialUpdate(HuyenQuanDTO huyenQuanDTO);

    /**
     * Get all the huyenQuans.
     *
     * @return the list of entities.
     */
    List<HuyenQuanDTO> findAll();

    /**
     * Get the "id" huyenQuan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HuyenQuanDTO> findOne(Long id);

    /**
     * Delete the "id" huyenQuan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
