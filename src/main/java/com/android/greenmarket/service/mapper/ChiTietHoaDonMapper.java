package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.ChiTietHoaDonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChiTietHoaDon} and its DTO {@link ChiTietHoaDonDTO}.
 */
@Mapper(componentModel = "spring", uses = { NongSanMapper.class, HoaDonMapper.class })
public interface ChiTietHoaDonMapper extends EntityMapper<ChiTietHoaDonDTO, ChiTietHoaDon> {
    @Mapping(target = "nongsan", source = "nongsan", qualifiedByName = "id")
    @Mapping(target = "hoadon", source = "hoadon", qualifiedByName = "id")
    ChiTietHoaDonDTO toDto(ChiTietHoaDon s);
}
