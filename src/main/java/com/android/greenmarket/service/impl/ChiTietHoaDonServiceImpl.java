package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.ChiTietHoaDon;
import com.android.greenmarket.repository.ChiTietHoaDonRepository;
import com.android.greenmarket.service.ChiTietHoaDonService;
import com.android.greenmarket.service.dto.ChiTietHoaDonDTO;
import com.android.greenmarket.service.mapper.ChiTietHoaDonMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChiTietHoaDon}.
 */
@Service
@Transactional
public class ChiTietHoaDonServiceImpl implements ChiTietHoaDonService {

    private final Logger log = LoggerFactory.getLogger(ChiTietHoaDonServiceImpl.class);

    private final ChiTietHoaDonRepository chiTietHoaDonRepository;

    private final ChiTietHoaDonMapper chiTietHoaDonMapper;

    public ChiTietHoaDonServiceImpl(ChiTietHoaDonRepository chiTietHoaDonRepository, ChiTietHoaDonMapper chiTietHoaDonMapper) {
        this.chiTietHoaDonRepository = chiTietHoaDonRepository;
        this.chiTietHoaDonMapper = chiTietHoaDonMapper;
    }

    @Override
    public ChiTietHoaDonDTO save(ChiTietHoaDonDTO chiTietHoaDonDTO) {
        log.debug("Request to save ChiTietHoaDon : {}", chiTietHoaDonDTO);
        ChiTietHoaDon chiTietHoaDon = chiTietHoaDonMapper.toEntity(chiTietHoaDonDTO);
        chiTietHoaDon = chiTietHoaDonRepository.save(chiTietHoaDon);
        return chiTietHoaDonMapper.toDto(chiTietHoaDon);
    }

    @Override
    public Optional<ChiTietHoaDonDTO> partialUpdate(ChiTietHoaDonDTO chiTietHoaDonDTO) {
        log.debug("Request to partially update ChiTietHoaDon : {}", chiTietHoaDonDTO);

        return chiTietHoaDonRepository
            .findById(chiTietHoaDonDTO.getId())
            .map(
                existingChiTietHoaDon -> {
                    chiTietHoaDonMapper.partialUpdate(existingChiTietHoaDon, chiTietHoaDonDTO);

                    return existingChiTietHoaDon;
                }
            )
            .map(chiTietHoaDonRepository::save)
            .map(chiTietHoaDonMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChiTietHoaDonDTO> findAll() {
        log.debug("Request to get all ChiTietHoaDons");
        return chiTietHoaDonRepository.findAll().stream().map(chiTietHoaDonMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChiTietHoaDonDTO> findOne(Long id) {
        log.debug("Request to get ChiTietHoaDon : {}", id);
        return chiTietHoaDonRepository.findById(id).map(chiTietHoaDonMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChiTietHoaDon : {}", id);
        chiTietHoaDonRepository.deleteById(id);
    }
}
