package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.CompositeItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.service.dto.CompositeItemDTO;
import com.awtar.myapp.service.dto.ItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompositeItem} and its DTO {@link CompositeItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompositeItemMapper extends EntityMapper<CompositeItemDTO, CompositeItem> {
    @Mapping(target = "composant", source = "composant", qualifiedByName = "itemName")
    @Mapping(target = "composer", source = "composer", qualifiedByName = "itemName")
    CompositeItemDTO toDto(CompositeItem s);

    @Named("itemName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ItemDTO toDtoItemName(Item item);
}
