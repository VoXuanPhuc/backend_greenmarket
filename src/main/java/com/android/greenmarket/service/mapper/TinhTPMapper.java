package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.TinhTPDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TinhTP} and its DTO {@link TinhTPDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TinhTPMapper extends EntityMapper<TinhTPDTO, TinhTP> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TinhTPDTO toDtoId(TinhTP tinhTP);
}
