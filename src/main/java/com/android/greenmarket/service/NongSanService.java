package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.NongSanDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.NongSan}.
 */
public interface NongSanService {
    /**
     * Save a nongSan.
     *
     * @param nongSanDTO the entity to save.
     * @return the persisted entity.
     */
    NongSanDTO save(NongSanDTO nongSanDTO);

    /**
     * Partially updates a nongSan.
     *
     * @param nongSanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NongSanDTO> partialUpdate(NongSanDTO nongSanDTO);

    /**
     * Get all the nongSans.
     *
     * @return the list of entities.
     */
    List<NongSanDTO> findAll();

    /**
     * Get the "id" nongSan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NongSanDTO> findOne(Long id);

    /**
     * Delete the "id" nongSan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
