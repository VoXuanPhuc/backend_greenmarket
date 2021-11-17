package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.YeuThich;
import com.android.greenmarket.repository.YeuThichRepository;
import com.android.greenmarket.service.YeuThichService;
import com.android.greenmarket.service.dto.YeuThichDTO;
import com.android.greenmarket.service.mapper.YeuThichMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link YeuThich}.
 */
@Service
@Transactional
public class YeuThichServiceImpl implements YeuThichService {

    private final Logger log = LoggerFactory.getLogger(YeuThichServiceImpl.class);

    private final YeuThichRepository yeuThichRepository;

    private final YeuThichMapper yeuThichMapper;

    public YeuThichServiceImpl(YeuThichRepository yeuThichRepository, YeuThichMapper yeuThichMapper) {
        this.yeuThichRepository = yeuThichRepository;
        this.yeuThichMapper = yeuThichMapper;
    }

    @Override
    public YeuThichDTO save(YeuThichDTO yeuThichDTO) {
        log.debug("Request to save YeuThich : {}", yeuThichDTO);
        YeuThich yeuThich = yeuThichMapper.toEntity(yeuThichDTO);
        yeuThich = yeuThichRepository.save(yeuThich);
        return yeuThichMapper.toDto(yeuThich);
    }

    @Override
    public Optional<YeuThichDTO> partialUpdate(YeuThichDTO yeuThichDTO) {
        log.debug("Request to partially update YeuThich : {}", yeuThichDTO);

        return yeuThichRepository
            .findById(yeuThichDTO.getId())
            .map(
                existingYeuThich -> {
                    yeuThichMapper.partialUpdate(existingYeuThich, yeuThichDTO);

                    return existingYeuThich;
                }
            )
            .map(yeuThichRepository::save)
            .map(yeuThichMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<YeuThichDTO> findAll() {
        log.debug("Request to get all YeuThiches");
        return yeuThichRepository.findAll().stream().map(yeuThichMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<YeuThichDTO> findOne(Long id) {
        log.debug("Request to get YeuThich : {}", id);
        return yeuThichRepository.findById(id).map(yeuThichMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete YeuThich : {}", id);
        yeuThichRepository.deleteById(id);
    }
}
