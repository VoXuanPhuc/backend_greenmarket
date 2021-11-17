package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.PhuongThucTTDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PhuongThucTT} and its DTO {@link PhuongThucTTDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhuongThucTTMapper extends EntityMapper<PhuongThucTTDTO, PhuongThucTT> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PhuongThucTTDTO toDtoId(PhuongThucTT phuongThucTT);
}
