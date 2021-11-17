package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.NongSan;
import com.android.greenmarket.repository.NongSanRepository;
import com.android.greenmarket.service.NongSanService;
import com.android.greenmarket.service.dto.NongSanDTO;
import com.android.greenmarket.service.mapper.NongSanMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NongSan}.
 */
@Service
@Transactional
public class NongSanServiceImpl implements NongSanService {

    private final Logger log = LoggerFactory.getLogger(NongSanServiceImpl.class);

    private final NongSanRepository nongSanRepository;

    private final NongSanMapper nongSanMapper;

    public NongSanServiceImpl(NongSanRepository nongSanRepository, NongSanMapper nongSanMapper) {
        this.nongSanRepository = nongSanRepository;
        this.nongSanMapper = nongSanMapper;
    }

    @Override
    public NongSanDTO save(NongSanDTO nongSanDTO) {
        log.debug("Request to save NongSan : {}", nongSanDTO);
        NongSan nongSan = nongSanMapper.toEntity(nongSanDTO);
        nongSan = nongSanRepository.save(nongSan);
        return nongSanMapper.toDto(nongSan);
    }

    @Override
    public Optional<NongSanDTO> partialUpdate(NongSanDTO nongSanDTO) {
        log.debug("Request to partially update NongSan : {}", nongSanDTO);

        return nongSanRepository
            .findById(nongSanDTO.getId())
            .map(
                existingNongSan -> {
                    nongSanMapper.partialUpdate(existingNongSan, nongSanDTO);

                    return existingNongSan;
                }
            )
            .map(nongSanRepository::save)
            .map(nongSanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NongSanDTO> findAll() {
        log.debug("Request to get all NongSans");
        return nongSanRepository.findAll().stream().map(nongSanMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NongSanDTO> findOne(Long id) {
        log.debug("Request to get NongSan : {}", id);
        return nongSanRepository.findById(id).map(nongSanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NongSan : {}", id);
        nongSanRepository.deleteById(id);
    }
}
