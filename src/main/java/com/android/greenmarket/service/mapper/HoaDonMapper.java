package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.HoaDonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HoaDon} and its DTO {@link HoaDonDTO}.
 */
@Mapper(componentModel = "spring", uses = { PhuongThucTTMapper.class, HinhThucGiaoHangMapper.class, KhachHangMapper.class })
public interface HoaDonMapper extends EntityMapper<HoaDonDTO, HoaDon> {
    @Mapping(target = "phuongthucTT", source = "phuongthucTT", qualifiedByName = "id")
    @Mapping(target = "phuongthucGH", source = "phuongthucGH", qualifiedByName = "id")
    @Mapping(target = "khachhang", source = "khachhang", qualifiedByName = "id")
    HoaDonDTO toDto(HoaDon s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HoaDonDTO toDtoId(HoaDon hoaDon);
}
