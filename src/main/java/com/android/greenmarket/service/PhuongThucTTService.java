package com.android.greenmarket.service;

import com.android.greenmarket.service.dto.PhuongThucTTDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.android.greenmarket.domain.PhuongThucTT}.
 */
public interface PhuongThucTTService {
    /**
     * Save a phuongThucTT.
     *
     * @param phuongThucTTDTO the entity to save.
     * @return the persisted entity.
     */
    PhuongThucTTDTO save(PhuongThucTTDTO phuongThucTTDTO);

    /**
     * Partially updates a phuongThucTT.
     *
     * @param phuongThucTTDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PhuongThucTTDTO> partialUpdate(PhuongThucTTDTO phuongThucTTDTO);

    /**
     * Get all the phuongThucTTS.
     *
     * @return the list of entities.
     */
    List<PhuongThucTTDTO> findAll();

    /**
     * Get the "id" phuongThucTT.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhuongThucTTDTO> findOne(Long id);

    /**
     * Delete the "id" phuongThucTT.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
