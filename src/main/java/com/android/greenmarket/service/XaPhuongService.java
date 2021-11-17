package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.XaPhuongDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.XaPhuong}.
 */
public interface XaPhuongService {
    /**
     * Save a xaPhuong.
     *
     * @param xaPhuongDTO the entity to save.
     * @return the persisted entity.
     */
    XaPhuongDTO save(XaPhuongDTO xaPhuongDTO);

    /**
     * Partially updates a xaPhuong.
     *
     * @param xaPhuongDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<XaPhuongDTO> partialUpdate(XaPhuongDTO xaPhuongDTO);

    /**
     * Get all the xaPhuongs.
     *
     * @return the list of entities.
     */
    List<XaPhuongDTO> findAll();

    /**
     * Get the "id" xaPhuong.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<XaPhuongDTO> findOne(Long id);

    /**
     * Delete the "id" xaPhuong.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
