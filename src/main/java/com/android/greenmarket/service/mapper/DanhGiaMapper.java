package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.DanhGiaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DanhGia} and its DTO {@link DanhGiaDTO}.
 */
@Mapper(componentModel = "spring", uses = { NongSanMapper.class, KhachHangMapper.class })
public interface DanhGiaMapper extends EntityMapper<DanhGiaDTO, DanhGia> {
    @Mapping(target = "nongsan", source = "nongsan", qualifiedByName = "id")
    @Mapping(target = "khachhang", source = "khachhang", qualifiedByName = "id")
    DanhGiaDTO toDto(DanhGia s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DanhGiaDTO toDtoId(DanhGia danhGia);
}
