package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.AnhNongSan;
import com.android.greenmarket.repository.AnhNongSanRepository;
import com.android.greenmarket.service.AnhNongSanService;
import com.android.greenmarket.service.dto.AnhNongSanDTO;
import com.android.greenmarket.service.mapper.AnhNongSanMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnhNongSan}.
 */
@Service
@Transactional
public class AnhNongSanServiceImpl implements AnhNongSanService {

    private final Logger log = LoggerFactory.getLogger(AnhNongSanServiceImpl.class);

    private final AnhNongSanRepository anhNongSanRepository;

    private final AnhNongSanMapper anhNongSanMapper;

    public AnhNongSanServiceImpl(AnhNongSanRepository anhNongSanRepository, AnhNongSanMapper anhNongSanMapper) {
        this.anhNongSanRepository = anhNongSanRepository;
        this.anhNongSanMapper = anhNongSanMapper;
    }

    @Override
    public AnhNongSanDTO save(AnhNongSanDTO anhNongSanDTO) {
        log.debug("Request to save AnhNongSan : {}", anhNongSanDTO);
        AnhNongSan anhNongSan = anhNongSanMapper.toEntity(anhNongSanDTO);
        anhNongSan = anhNongSanRepository.save(anhNongSan);
        return anhNongSanMapper.toDto(anhNongSan);
    }

    @Override
    public Optional<AnhNongSanDTO> partialUpdate(AnhNongSanDTO anhNongSanDTO) {
        log.debug("Request to partially update AnhNongSan : {}", anhNongSanDTO);

        return anhNongSanRepository
            .findById(anhNongSanDTO.getId())
            .map(
                existingAnhNongSan -> {
                    anhNongSanMapper.partialUpdate(existingAnhNongSan, anhNongSanDTO);

                    return existingAnhNongSan;
                }
            )
            .map(anhNongSanRepository::save)
            .map(anhNongSanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnhNongSanDTO> findAll() {
        log.debug("Request to get all AnhNongSans");
        return anhNongSanRepository.findAll().stream().map(anhNongSanMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnhNongSanDTO> findOne(Long id) {
        log.debug("Request to get AnhNongSan : {}", id);
        return anhNongSanRepository.findById(id).map(anhNongSanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnhNongSan : {}", id);
        anhNongSanRepository.deleteById(id);
    }
}
