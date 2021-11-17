package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.XaPhuong;
import com.android.greenmarket.repository.XaPhuongRepository;
import com.android.greenmarket.service.XaPhuongService;
import com.android.greenmarket.service.dto.XaPhuongDTO;
import com.android.greenmarket.service.mapper.XaPhuongMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link XaPhuong}.
 */
@Service
@Transactional
public class XaPhuongServiceImpl implements XaPhuongService {

    private final Logger log = LoggerFactory.getLogger(XaPhuongServiceImpl.class);

    private final XaPhuongRepository xaPhuongRepository;

    private final XaPhuongMapper xaPhuongMapper;

    public XaPhuongServiceImpl(XaPhuongRepository xaPhuongRepository, XaPhuongMapper xaPhuongMapper) {
        this.xaPhuongRepository = xaPhuongRepository;
        this.xaPhuongMapper = xaPhuongMapper;
    }

    @Override
    public XaPhuongDTO save(XaPhuongDTO xaPhuongDTO) {
        log.debug("Request to save XaPhuong : {}", xaPhuongDTO);
        XaPhuong xaPhuong = xaPhuongMapper.toEntity(xaPhuongDTO);
        xaPhuong = xaPhuongRepository.save(xaPhuong);
        return xaPhuongMapper.toDto(xaPhuong);
    }

    @Override
    public Optional<XaPhuongDTO> partialUpdate(XaPhuongDTO xaPhuongDTO) {
        log.debug("Request to partially update XaPhuong : {}", xaPhuongDTO);

        return xaPhuongRepository
            .findById(xaPhuongDTO.getId())
            .map(
                existingXaPhuong -> {
                    xaPhuongMapper.partialUpdate(existingXaPhuong, xaPhuongDTO);

                    return existingXaPhuong;
                }
            )
            .map(xaPhuongRepository::save)
            .map(xaPhuongMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<XaPhuongDTO> findAll() {
        log.debug("Request to get all XaPhuongs");
        return xaPhuongRepository.findAll().stream().map(xaPhuongMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<XaPhuongDTO> findOne(Long id) {
        log.debug("Request to get XaPhuong : {}", id);
        return xaPhuongRepository.findById(id).map(xaPhuongMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete XaPhuong : {}", id);
        xaPhuongRepository.deleteById(id);
    }
}
