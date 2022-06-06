package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.ItemValue;
import com.awtar.myapp.service.dto.ItemDTO;
import com.awtar.myapp.service.dto.ItemValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ItemValue} and its DTO {@link ItemValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface ItemValueMapper extends EntityMapper<ItemValueDTO, ItemValue> {
    @Mapping(target = "item", source = "item", qualifiedByName = "itemName")
    ItemValueDTO toDto(ItemValue s);

    @Named("itemName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ItemDTO toDtoItemName(Item item);
}
