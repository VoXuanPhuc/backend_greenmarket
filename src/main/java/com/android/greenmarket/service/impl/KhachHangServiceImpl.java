package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.KhachHang;
import com.android.greenmarket.repository.KhachHangRepository;
import com.android.greenmarket.service.KhachHangService;
import com.android.greenmarket.service.dto.KhachHangDTO;
import com.android.greenmarket.service.mapper.KhachHangMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link KhachHang}.
 */
@Service
@Transactional
public class KhachHangServiceImpl implements KhachHangService {

    private final Logger log = LoggerFactory.getLogger(KhachHangServiceImpl.class);

    private final KhachHangRepository khachHangRepository;

    private final KhachHangMapper khachHangMapper;

    public KhachHangServiceImpl(KhachHangRepository khachHangRepository, KhachHangMapper khachHangMapper) {
        this.khachHangRepository = khachHangRepository;
        this.khachHangMapper = khachHangMapper;
    }

    @Override
    public KhachHangDTO save(KhachHangDTO khachHangDTO) {
        log.debug("Request to save KhachHang : {}", khachHangDTO);
        KhachHang khachHang = khachHangMapper.toEntity(khachHangDTO);
        khachHang = khachHangRepository.save(khachHang);
        return khachHangMapper.toDto(khachHang);
    }

    @Override
    public Optional<KhachHangDTO> partialUpdate(KhachHangDTO khachHangDTO) {
        log.debug("Request to partially update KhachHang : {}", khachHangDTO);

        return khachHangRepository
            .findById(khachHangDTO.getId())
            .map(
                existingKhachHang -> {
                    khachHangMapper.partialUpdate(existingKhachHang, khachHangDTO);

                    return existingKhachHang;
                }
            )
            .map(khachHangRepository::save)
            .map(khachHangMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KhachHangDTO> findAll() {
        log.debug("Request to get all KhachHangs");
        return khachHangRepository.findAll().stream().map(khachHangMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<KhachHangDTO> findOne(Long id) {
        log.debug("Request to get KhachHang : {}", id);
        return khachHangRepository.findById(id).map(khachHangMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete KhachHang : {}", id);
        khachHangRepository.deleteById(id);
    }
}
