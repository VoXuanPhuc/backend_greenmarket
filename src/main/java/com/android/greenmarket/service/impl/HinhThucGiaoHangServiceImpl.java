package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.HinhThucGiaoHang;
import com.android.greenmarket.repository.HinhThucGiaoHangRepository;
import com.android.greenmarket.service.HinhThucGiaoHangService;
import com.android.greenmarket.service.dto.HinhThucGiaoHangDTO;
import com.android.greenmarket.service.mapper.HinhThucGiaoHangMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HinhThucGiaoHang}.
 */
@Service
@Transactional
public class HinhThucGiaoHangServiceImpl implements HinhThucGiaoHangService {

    private final Logger log = LoggerFactory.getLogger(HinhThucGiaoHangServiceImpl.class);

    private final HinhThucGiaoHangRepository hinhThucGiaoHangRepository;

    private final HinhThucGiaoHangMapper hinhThucGiaoHangMapper;

    public HinhThucGiaoHangServiceImpl(
        HinhThucGiaoHangRepository hinhThucGiaoHangRepository,
        HinhThucGiaoHangMapper hinhThucGiaoHangMapper
    ) {
        this.hinhThucGiaoHangRepository = hinhThucGiaoHangRepository;
        this.hinhThucGiaoHangMapper = hinhThucGiaoHangMapper;
    }

    @Override
    public HinhThucGiaoHangDTO save(HinhThucGiaoHangDTO hinhThucGiaoHangDTO) {
        log.debug("Request to save HinhThucGiaoHang : {}", hinhThucGiaoHangDTO);
        HinhThucGiaoHang hinhThucGiaoHang = hinhThucGiaoHangMapper.toEntity(hinhThucGiaoHangDTO);
        hinhThucGiaoHang = hinhThucGiaoHangRepository.save(hinhThucGiaoHang);
        return hinhThucGiaoHangMapper.toDto(hinhThucGiaoHang);
    }

    @Override
    public Optional<HinhThucGiaoHangDTO> partialUpdate(HinhThucGiaoHangDTO hinhThucGiaoHangDTO) {
        log.debug("Request to partially update HinhThucGiaoHang : {}", hinhThucGiaoHangDTO);

        return hinhThucGiaoHangRepository
            .findById(hinhThucGiaoHangDTO.getId())
            .map(
                existingHinhThucGiaoHang -> {
                    hinhThucGiaoHangMapper.partialUpdate(existingHinhThucGiaoHang, hinhThucGiaoHangDTO);

                    return existingHinhThucGiaoHang;
                }
            )
            .map(hinhThucGiaoHangRepository::save)
            .map(hinhThucGiaoHangMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HinhThucGiaoHangDTO> findAll() {
        log.debug("Request to get all HinhThucGiaoHangs");
        return hinhThucGiaoHangRepository
            .findAll()
            .stream()
            .map(hinhThucGiaoHangMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HinhThucGiaoHangDTO> findOne(Long id) {
        log.debug("Request to get HinhThucGiaoHang : {}", id);
        return hinhThucGiaoHangRepository.findById(id).map(hinhThucGiaoHangMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HinhThucGiaoHang : {}", id);
        hinhThucGiaoHangRepository.deleteById(id);
    }
}
