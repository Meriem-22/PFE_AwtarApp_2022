package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.service.dto.ItemDTO;
import com.awtar.myapp.service.dto.NatureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Item} and its DTO {@link ItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {
    @Mapping(target = "nature", source = "nature", qualifiedByName = "natureName")
    ItemDTO toDto(Item s);

    @Named("natureName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    NatureDTO toDtoNatureName(Nature nature);
}
