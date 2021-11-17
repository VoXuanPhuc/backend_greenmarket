package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.DanhGia;
import com.android.greenmarket.repository.DanhGiaRepository;
import com.android.greenmarket.service.DanhGiaService;
import com.android.greenmarket.service.dto.DanhGiaDTO;
import com.android.greenmarket.service.mapper.DanhGiaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DanhGia}.
 */
@Service
@Transactional
public class DanhGiaServiceImpl implements DanhGiaService {

    private final Logger log = LoggerFactory.getLogger(DanhGiaServiceImpl.class);

    private final DanhGiaRepository danhGiaRepository;

    private final DanhGiaMapper danhGiaMapper;

    public DanhGiaServiceImpl(DanhGiaRepository danhGiaRepository, DanhGiaMapper danhGiaMapper) {
        this.danhGiaRepository = danhGiaRepository;
        this.danhGiaMapper = danhGiaMapper;
    }

    @Override
    public DanhGiaDTO save(DanhGiaDTO danhGiaDTO) {
        log.debug("Request to save DanhGia : {}", danhGiaDTO);
        DanhGia danhGia = danhGiaMapper.toEntity(danhGiaDTO);
        danhGia = danhGiaRepository.save(danhGia);
        return danhGiaMapper.toDto(danhGia);
    }

    @Override
    public Optional<DanhGiaDTO> partialUpdate(DanhGiaDTO danhGiaDTO) {
        log.debug("Request to partially update DanhGia : {}", danhGiaDTO);

        return danhGiaRepository
            .findById(danhGiaDTO.getId())
            .map(
                existingDanhGia -> {
                    danhGiaMapper.partialUpdate(existingDanhGia, danhGiaDTO);

                    return existingDanhGia;
                }
            )
            .map(danhGiaRepository::save)
            .map(danhGiaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DanhGiaDTO> findAll() {
        log.debug("Request to get all DanhGias");
        return danhGiaRepository.findAll().stream().map(danhGiaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DanhGiaDTO> findOne(Long id) {
        log.debug("Request to get DanhGia : {}", id);
        return danhGiaRepository.findById(id).map(danhGiaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DanhGia : {}", id);
        danhGiaRepository.deleteById(id);
    }
}
