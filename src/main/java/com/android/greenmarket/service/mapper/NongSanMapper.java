package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.NongSanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NongSan} and its DTO {@link NongSanDTO}.
 */
@Mapper(componentModel = "spring", uses = { DanhMucMapper.class, NhaCungCapMapper.class })
public interface NongSanMapper extends EntityMapper<NongSanDTO, NongSan> {
    @Mapping(target = "danhmuc", source = "danhmuc", qualifiedByName = "id")
    @Mapping(target = "nhacc", source = "nhacc", qualifiedByName = "id")
    NongSanDTO toDto(NongSan s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NongSanDTO toDtoId(NongSan nongSan);
}
