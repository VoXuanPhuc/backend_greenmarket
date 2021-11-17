package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.NhaCungCapDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NhaCungCap} and its DTO {@link NhaCungCapDTO}.
 */
@Mapper(componentModel = "spring", uses = { XaPhuongMapper.class })
public interface NhaCungCapMapper extends EntityMapper<NhaCungCapDTO, NhaCungCap> {
    @Mapping(target = "diaChi", source = "diaChi", qualifiedByName = "id")
    NhaCungCapDTO toDto(NhaCungCap s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NhaCungCapDTO toDtoId(NhaCungCap nhaCungCap);
}
