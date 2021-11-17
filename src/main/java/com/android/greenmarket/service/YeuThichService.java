package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.YeuThichDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.YeuThich}.
 */
public interface YeuThichService {
    /**
     * Save a yeuThich.
     *
     * @param yeuThichDTO the entity to save.
     * @return the persisted entity.
     */
    YeuThichDTO save(YeuThichDTO yeuThichDTO);

    /**
     * Partially updates a yeuThich.
     *
     * @param yeuThichDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<YeuThichDTO> partialUpdate(YeuThichDTO yeuThichDTO);

    /**
     * Get all the yeuThiches.
     *
     * @return the list of entities.
     */
    List<YeuThichDTO> findAll();

    /**
     * Get the "id" yeuThich.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<YeuThichDTO> findOne(Long id);

    /**
     * Delete the "id" yeuThich.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
