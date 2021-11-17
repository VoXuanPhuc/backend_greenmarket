package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.HinhThucGiaoHangDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HinhThucGiaoHang} and its DTO {@link HinhThucGiaoHangDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HinhThucGiaoHangMapper extends EntityMapper<HinhThucGiaoHangDTO, HinhThucGiaoHang> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HinhThucGiaoHangDTO toDtoId(HinhThucGiaoHang hinhThucGiaoHang);
}
