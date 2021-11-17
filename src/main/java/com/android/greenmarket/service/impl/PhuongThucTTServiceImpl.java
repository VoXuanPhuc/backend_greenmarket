package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.PhuongThucTT;
import com.android.greenmarket.repository.PhuongThucTTRepository;
import com.android.greenmarket.service.PhuongThucTTService;
import com.android.greenmarket.service.dto.PhuongThucTTDTO;
import com.android.greenmarket.service.mapper.PhuongThucTTMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PhuongThucTT}.
 */
@Service
@Transactional
public class PhuongThucTTServiceImpl implements PhuongThucTTService {

    private final Logger log = LoggerFactory.getLogger(PhuongThucTTServiceImpl.class);

    private final PhuongThucTTRepository phuongThucTTRepository;

    private final PhuongThucTTMapper phuongThucTTMapper;

    public PhuongThucTTServiceImpl(PhuongThucTTRepository phuongThucTTRepository, PhuongThucTTMapper phuongThucTTMapper) {
        this.phuongThucTTRepository = phuongThucTTRepository;
        this.phuongThucTTMapper = phuongThucTTMapper;
    }

    @Override
    public PhuongThucTTDTO save(PhuongThucTTDTO phuongThucTTDTO) {
        log.debug("Request to save PhuongThucTT : {}", phuongThucTTDTO);
        PhuongThucTT phuongThucTT = phuongThucTTMapper.toEntity(phuongThucTTDTO);
        phuongThucTT = phuongThucTTRepository.save(phuongThucTT);
        return phuongThucTTMapper.toDto(phuongThucTT);
    }

    @Override
    public Optional<PhuongThucTTDTO> partialUpdate(PhuongThucTTDTO phuongThucTTDTO) {
        log.debug("Request to partially update PhuongThucTT : {}", phuongThucTTDTO);

        return phuongThucTTRepository
            .findById(phuongThucTTDTO.getId())
            .map(
                existingPhuongThucTT -> {
                    phuongThucTTMapper.partialUpdate(existingPhuongThucTT, phuongThucTTDTO);

                    return existingPhuongThucTT;
                }
            )
            .map(phuongThucTTRepository::save)
            .map(phuongThucTTMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhuongThucTTDTO> findAll() {
        log.debug("Request to get all PhuongThucTTS");
        return phuongThucTTRepository.findAll().stream().map(phuongThucTTMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhuongThucTTDTO> findOne(Long id) {
        log.debug("Request to get PhuongThucTT : {}", id);
        return phuongThucTTRepository.findById(id).map(phuongThucTTMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PhuongThucTT : {}", id);
        phuongThucTTRepository.deleteById(id);
    }
}
