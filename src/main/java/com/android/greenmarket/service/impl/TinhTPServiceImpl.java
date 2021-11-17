package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.TinhTP;
import com.android.greenmarket.repository.TinhTPRepository;
import com.android.greenmarket.service.TinhTPService;
import com.android.greenmarket.service.dto.TinhTPDTO;
import com.android.greenmarket.service.mapper.TinhTPMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TinhTP}.
 */
@Service
@Transactional
public class TinhTPServiceImpl implements TinhTPService {

    private final Logger log = LoggerFactory.getLogger(TinhTPServiceImpl.class);

    private final TinhTPRepository tinhTPRepository;

    private final TinhTPMapper tinhTPMapper;

    public TinhTPServiceImpl(TinhTPRepository tinhTPRepository, TinhTPMapper tinhTPMapper) {
        this.tinhTPRepository = tinhTPRepository;
        this.tinhTPMapper = tinhTPMapper;
    }

    @Override
    public TinhTPDTO save(TinhTPDTO tinhTPDTO) {
        log.debug("Request to save TinhTP : {}", tinhTPDTO);
        TinhTP tinhTP = tinhTPMapper.toEntity(tinhTPDTO);
        tinhTP = tinhTPRepository.save(tinhTP);
        return tinhTPMapper.toDto(tinhTP);
    }

    @Override
    public Optional<TinhTPDTO> partialUpdate(TinhTPDTO tinhTPDTO) {
        log.debug("Request to partially update TinhTP : {}", tinhTPDTO);

        return tinhTPRepository
            .findById(tinhTPDTO.getId())
            .map(
                existingTinhTP -> {
                    tinhTPMapper.partialUpdate(existingTinhTP, tinhTPDTO);

                    return existingTinhTP;
                }
            )
            .map(tinhTPRepository::save)
            .map(tinhTPMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TinhTPDTO> findAll() {
        log.debug("Request to get all TinhTPS");
        return tinhTPRepository.findAll().stream().map(tinhTPMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TinhTPDTO> findOne(Long id) {
        log.debug("Request to get TinhTP : {}", id);
        return tinhTPRepository.findById(id).map(tinhTPMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TinhTP : {}", id);
        tinhTPRepository.deleteById(id);
    }
}
