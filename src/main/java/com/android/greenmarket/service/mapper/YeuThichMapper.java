package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.YeuThichDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link YeuThich} and its DTO {@link YeuThichDTO}.
 */
@Mapper(componentModel = "spring", uses = { NongSanMapper.class, KhachHangMapper.class })
public interface YeuThichMapper extends EntityMapper<YeuThichDTO, YeuThich> {
    @Mapping(target = "nongsan", source = "nongsan", qualifiedByName = "id")
    @Mapping(target = "khachhang", source = "khachhang", qualifiedByName = "id")
    YeuThichDTO toDto(YeuThich s);
}
