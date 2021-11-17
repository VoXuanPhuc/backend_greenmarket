package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.AnhDanhGia;
import com.android.greenmarket.repository.AnhDanhGiaRepository;
import com.android.greenmarket.service.AnhDanhGiaService;
import com.android.greenmarket.service.dto.AnhDanhGiaDTO;
import com.android.greenmarket.service.mapper.AnhDanhGiaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnhDanhGia}.
 */
@Service
@Transactional
public class AnhDanhGiaServiceImpl implements AnhDanhGiaService {

    private final Logger log = LoggerFactory.getLogger(AnhDanhGiaServiceImpl.class);

    private final AnhDanhGiaRepository anhDanhGiaRepository;

    private final AnhDanhGiaMapper anhDanhGiaMapper;

    public AnhDanhGiaServiceImpl(AnhDanhGiaRepository anhDanhGiaRepository, AnhDanhGiaMapper anhDanhGiaMapper) {
        this.anhDanhGiaRepository = anhDanhGiaRepository;
        this.anhDanhGiaMapper = anhDanhGiaMapper;
    }

    @Override
    public AnhDanhGiaDTO save(AnhDanhGiaDTO anhDanhGiaDTO) {
        log.debug("Request to save AnhDanhGia : {}", anhDanhGiaDTO);
        AnhDanhGia anhDanhGia = anhDanhGiaMapper.toEntity(anhDanhGiaDTO);
        anhDanhGia = anhDanhGiaRepository.save(anhDanhGia);
        return anhDanhGiaMapper.toDto(anhDanhGia);
    }

    @Override
    public Optional<AnhDanhGiaDTO> partialUpdate(AnhDanhGiaDTO anhDanhGiaDTO) {
        log.debug("Request to partially update AnhDanhGia : {}", anhDanhGiaDTO);

        return anhDanhGiaRepository
            .findById(anhDanhGiaDTO.getId())
            .map(
                existingAnhDanhGia -> {
                    anhDanhGiaMapper.partialUpdate(existingAnhDanhGia, anhDanhGiaDTO);

                    return existingAnhDanhGia;
                }
            )
            .map(anhDanhGiaRepository::save)
            .map(anhDanhGiaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnhDanhGiaDTO> findAll() {
        log.debug("Request to get all AnhDanhGias");
        return anhDanhGiaRepository.findAll().stream().map(anhDanhGiaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnhDanhGiaDTO> findOne(Long id) {
        log.debug("Request to get AnhDanhGia : {}", id);
        return anhDanhGiaRepository.findById(id).map(anhDanhGiaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnhDanhGia : {}", id);
        anhDanhGiaRepository.deleteById(id);
    }
}
