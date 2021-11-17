package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.HoaDon;
import com.android.greenmarket.repository.HoaDonRepository;
import com.android.greenmarket.service.HoaDonService;
import com.android.greenmarket.service.dto.HoaDonDTO;
import com.android.greenmarket.service.mapper.HoaDonMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HoaDon}.
 */
@Service
@Transactional
public class HoaDonServiceImpl implements HoaDonService {

    private final Logger log = LoggerFactory.getLogger(HoaDonServiceImpl.class);

    private final HoaDonRepository hoaDonRepository;

    private final HoaDonMapper hoaDonMapper;

    public HoaDonServiceImpl(HoaDonRepository hoaDonRepository, HoaDonMapper hoaDonMapper) {
        this.hoaDonRepository = hoaDonRepository;
        this.hoaDonMapper = hoaDonMapper;
    }

    @Override
    public HoaDonDTO save(HoaDonDTO hoaDonDTO) {
        log.debug("Request to save HoaDon : {}", hoaDonDTO);
        HoaDon hoaDon = hoaDonMapper.toEntity(hoaDonDTO);
        hoaDon = hoaDonRepository.save(hoaDon);
        return hoaDonMapper.toDto(hoaDon);
    }

    @Override
    public Optional<HoaDonDTO> partialUpdate(HoaDonDTO hoaDonDTO) {
        log.debug("Request to partially update HoaDon : {}", hoaDonDTO);

        return hoaDonRepository
            .findById(hoaDonDTO.getId())
            .map(
                existingHoaDon -> {
                    hoaDonMapper.partialUpdate(existingHoaDon, hoaDonDTO);

                    return existingHoaDon;
                }
            )
            .map(hoaDonRepository::save)
            .map(hoaDonMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HoaDonDTO> findAll() {
        log.debug("Request to get all HoaDons");
        return hoaDonRepository.findAll().stream().map(hoaDonMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HoaDonDTO> findOne(Long id) {
        log.debug("Request to get HoaDon : {}", id);
        return hoaDonRepository.findById(id).map(hoaDonMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HoaDon : {}", id);
        hoaDonRepository.deleteById(id);
    }
}
