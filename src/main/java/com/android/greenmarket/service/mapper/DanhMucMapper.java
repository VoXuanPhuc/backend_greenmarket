package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.DanhMucDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DanhMuc} and its DTO {@link DanhMucDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DanhMucMapper extends EntityMapper<DanhMucDTO, DanhMuc> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DanhMucDTO toDtoId(DanhMuc danhMuc);
}
