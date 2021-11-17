package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.DanhMuc;
import com.android.greenmarket.repository.DanhMucRepository;
import com.android.greenmarket.service.DanhMucService;
import com.android.greenmarket.service.dto.DanhMucDTO;
import com.android.greenmarket.service.mapper.DanhMucMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DanhMuc}.
 */
@Service
@Transactional
public class DanhMucServiceImpl implements DanhMucService {

    private final Logger log = LoggerFactory.getLogger(DanhMucServiceImpl.class);

    private final DanhMucRepository danhMucRepository;

    private final DanhMucMapper danhMucMapper;

    public DanhMucServiceImpl(DanhMucRepository danhMucRepository, DanhMucMapper danhMucMapper) {
        this.danhMucRepository = danhMucRepository;
        this.danhMucMapper = danhMucMapper;
    }

    @Override
    public DanhMucDTO save(DanhMucDTO danhMucDTO) {
        log.debug("Request to save DanhMuc : {}", danhMucDTO);
        DanhMuc danhMuc = danhMucMapper.toEntity(danhMucDTO);
        danhMuc = danhMucRepository.save(danhMuc);
        return danhMucMapper.toDto(danhMuc);
    }

    @Override
    public Optional<DanhMucDTO> partialUpdate(DanhMucDTO danhMucDTO) {
        log.debug("Request to partially update DanhMuc : {}", danhMucDTO);

        return danhMucRepository
            .findById(danhMucDTO.getId())
            .map(
                existingDanhMuc -> {
                    danhMucMapper.partialUpdate(existingDanhMuc, danhMucDTO);

                    return existingDanhMuc;
                }
            )
            .map(danhMucRepository::save)
            .map(danhMucMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DanhMucDTO> findAll() {
        log.debug("Request to get all DanhMucs");
        return danhMucRepository.findAll().stream().map(danhMucMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DanhMucDTO> findOne(Long id) {
        log.debug("Request to get DanhMuc : {}", id);
        return danhMucRepository.findById(id).map(danhMucMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DanhMuc : {}", id);
        danhMucRepository.deleteById(id);
    }
}
