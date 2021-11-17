package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.XaPhuongDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link XaPhuong} and its DTO {@link XaPhuongDTO}.
 */
@Mapper(componentModel = "spring", uses = { HuyenQuanMapper.class })
public interface XaPhuongMapper extends EntityMapper<XaPhuongDTO, XaPhuong> {
    @Mapping(target = "huyen", source = "huyen", qualifiedByName = "id")
    XaPhuongDTO toDto(XaPhuong s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    XaPhuongDTO toDtoId(XaPhuong xaPhuong);
}
