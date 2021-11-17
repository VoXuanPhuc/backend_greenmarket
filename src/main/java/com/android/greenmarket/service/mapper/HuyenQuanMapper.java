package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.HuyenQuanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HuyenQuan} and its DTO {@link HuyenQuanDTO}.
 */
@Mapper(componentModel = "spring", uses = { TinhTPMapper.class })
public interface HuyenQuanMapper extends EntityMapper<HuyenQuanDTO, HuyenQuan> {
    @Mapping(target = "tinh", source = "tinh", qualifiedByName = "id")
    HuyenQuanDTO toDto(HuyenQuan s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HuyenQuanDTO toDtoId(HuyenQuan huyenQuan);
}
