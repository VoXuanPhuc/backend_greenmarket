package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.KhachHangDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link KhachHang} and its DTO {@link KhachHangDTO}.
 */
@Mapper(componentModel = "spring", uses = { XaPhuongMapper.class })
public interface KhachHangMapper extends EntityMapper<KhachHangDTO, KhachHang> {
    @Mapping(target = "xa", source = "xa", qualifiedByName = "id")
    KhachHangDTO toDto(KhachHang s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    KhachHangDTO toDtoId(KhachHang khachHang);
}
