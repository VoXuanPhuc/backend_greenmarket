package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.TinhTPDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.TinhTP}.
 */
public interface TinhTPService {
    /**
     * Save a tinhTP.
     *
     * @param tinhTPDTO the entity to save.
     * @return the persisted entity.
     */
    TinhTPDTO save(TinhTPDTO tinhTPDTO);

    /**
     * Partially updates a tinhTP.
     *
     * @param tinhTPDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TinhTPDTO> partialUpdate(TinhTPDTO tinhTPDTO);

    /**
     * Get all the tinhTPS.
     *
     * @return the list of entities.
     */
    List<TinhTPDTO> findAll();

    /**
     * Get the "id" tinhTP.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TinhTPDTO> findOne(Long id);

    /**
     * Delete the "id" tinhTP.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
