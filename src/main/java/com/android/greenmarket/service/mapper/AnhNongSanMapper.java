package com.android.greenmarket.service.mapper;

import com.android.greenmarket.domain.*;
import com.android.greenmarket.service.dto.AnhNongSanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnhNongSan} and its DTO {@link AnhNongSanDTO}.
 */
@Mapper(componentModel = "spring", uses = { NongSanMapper.class })
public interface AnhNongSanMapper extends EntityMapper<AnhNongSanDTO, AnhNongSan> {
    @Mapping(target = "anhnongsan", source = "anhnongsan", qualifiedByName = "id")
    AnhNongSanDTO toDto(AnhNongSan s);
}
