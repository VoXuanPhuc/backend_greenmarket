package com.android.greenmarket.service.impl;

import com.android.greenmarket.domain.NhaCungCap;
import com.android.greenmarket.repository.NhaCungCapRepository;
import com.android.greenmarket.service.NhaCungCapService;
import com.android.greenmarket.service.dto.NhaCungCapDTO;
import com.android.greenmarket.service.mapper.NhaCungCapMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NhaCungCap}.
 */
@Service
@Transactional
public class NhaCungCapServiceImpl implements NhaCungCapService {

    private final Logger log = LoggerFactory.getLogger(NhaCungCapServiceImpl.class);

    private final NhaCungCapRepository nhaCungCapRepository;

    private final NhaCungCapMapper nhaCungCapMapper;

    public NhaCungCapServiceImpl(NhaCungCapRepository nhaCungCapRepository, NhaCungCapMapper nhaCungCapMapper) {
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.nhaCungCapMapper = nhaCungCapMapper;
    }

    @Override
    public NhaCungCapDTO save(NhaCungCapDTO nhaCungCapDTO) {
        log.debug("Request to save NhaCungCap : {}", nhaCungCapDTO);
        NhaCungCap nhaCungCap = nhaCungCapMapper.toEntity(nhaCungCapDTO);
        nhaCungCap = nhaCungCapRepository.save(nhaCungCap);
        return nhaCungCapMapper.toDto(nhaCungCap);
    }

    @Override
    public Optional<NhaCungCapDTO> partialUpdate(NhaCungCapDTO nhaCungCapDTO) {
        log.debug("Request to partially update NhaCungCap : {}", nhaCungCapDTO);

        return nhaCungCapRepository
            .findById(nhaCungCapDTO.getId())
            .map(
                existingNhaCungCap -> {
                    nhaCungCapMapper.partialUpdate(existingNhaCungCap, nhaCungCapDTO);

                    return existingNhaCungCap;
                }
            )
            .map(nhaCungCapRepository::save)
            .map(nhaCungCapMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NhaCungCapDTO> findAll() {
        log.debug("Request to get all NhaCungCaps");
        return nhaCungCapRepository.findAll().stream().map(nhaCungCapMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NhaCungCapDTO> findOne(Long id) {
        log.debug("Request to get NhaCungCap : {}", id);
        return nhaCungCapRepository.findById(id).map(nhaCungCapMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NhaCungCap : {}", id);
        nhaCungCapRepository.deleteById(id);
    }
}
