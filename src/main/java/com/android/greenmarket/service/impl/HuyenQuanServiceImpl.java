package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.HuyenQuan;
import com.android.greenmarket.repository.HuyenQuanRepository;
import com.android.greenmarket.service.HuyenQuanService;
import com.android.greenmarket.service.dto.HuyenQuanDTO;
import com.android.greenmarket.service.mapper.HuyenQuanMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HuyenQuan}.
 */
@Service
@Transactional
public class HuyenQuanServiceImpl implements HuyenQuanService {

    private final Logger log = LoggerFactory.getLogger(HuyenQuanServiceImpl.class);

    private final HuyenQuanRepository huyenQuanRepository;

    private final HuyenQuanMapper huyenQuanMapper;

    public HuyenQuanServiceImpl(HuyenQuanRepository huyenQuanRepository, HuyenQuanMapper huyenQuanMapper) {
        this.huyenQuanRepository = huyenQuanRepository;
        this.huyenQuanMapper = huyenQuanMapper;
    }

    @Override
    public HuyenQuanDTO save(HuyenQuanDTO huyenQuanDTO) {
        log.debug("Request to save HuyenQuan : {}", huyenQuanDTO);
        HuyenQuan huyenQuan = huyenQuanMapper.toEntity(huyenQuanDTO);
        huyenQuan = huyenQuanRepository.save(huyenQuan);
        return huyenQuanMapper.toDto(huyenQuan);
    }

    @Override
    public Optional<HuyenQuanDTO> partialUpdate(HuyenQuanDTO huyenQuanDTO) {
        log.debug("Request to partially update HuyenQuan : {}", huyenQuanDTO);

        return huyenQuanRepository
            .findById(huyenQuanDTO.getId())
            .map(
                existingHuyenQuan -> {
                    huyenQuanMapper.partialUpdate(existingHuyenQuan, huyenQuanDTO);

                    return existingHuyenQuan;
                }
            )
            .map(huyenQuanRepository::save)
            .map(huyenQuanMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HuyenQuanDTO> findAll() {
        log.debug("Request to get all HuyenQuans");
        return huyenQuanRepository.findAll().stream().map(huyenQuanMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HuyenQuanDTO> findOne(Long id) {
        log.debug("Request to get HuyenQuan : {}", id);
        return huyenQuanRepository.findById(id).map(huyenQuanMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HuyenQuan : {}", id);
        huyenQuanRepository.deleteById(id);
    }
}
