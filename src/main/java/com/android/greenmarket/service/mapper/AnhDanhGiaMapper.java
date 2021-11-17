package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.AnhDanhGiaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnhDanhGia} and its DTO {@link AnhDanhGiaDTO}.
 */
@Mapper(componentModel = "spring", uses = { DanhGiaMapper.class })
public interface AnhDanhGiaMapper extends EntityMapper<AnhDanhGiaDTO, AnhDanhGia> {
    @Mapping(target = "danhgia", source = "danhgia", qualifiedByName = "id")
    AnhDanhGiaDTO toDto(AnhDanhGia s);
}
