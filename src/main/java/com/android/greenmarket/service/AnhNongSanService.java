package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.AnhNongSanDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.AnhNongSan}.
 */
public interface AnhNongSanService {
    /**
     * Save a anhNongSan.
     *
     * @param anhNongSanDTO the entity to save.
     * @return the persisted entity.
     */
    AnhNongSanDTO save(AnhNongSanDTO anhNongSanDTO);

    /**
     * Partially updates a anhNongSan.
     *
     * @param anhNongSanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnhNongSanDTO> partialUpdate(AnhNongSanDTO anhNongSanDTO);

    /**
     * Get all the anhNongSans.
     *
     * @return the list of entities.
     */
    List<AnhNongSanDTO> findAll();

    /**
     * Get the "id" anhNongSan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnhNongSanDTO> findOne(Long id);

    /**
     * Delete the "id" anhNongSan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
