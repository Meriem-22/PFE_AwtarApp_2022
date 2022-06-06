package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.ChildStatus;
import com.awtar.myapp.domain.ChildStatusItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.service.dto.ChildStatusDTO;
import com.awtar.myapp.service.dto.ChildStatusItemDTO;
import com.awtar.myapp.service.dto.ItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChildStatusItem} and its DTO {@link ChildStatusItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChildStatusItemMapper extends EntityMapper<ChildStatusItemDTO, ChildStatusItem> {
    @Mapping(target = "item", source = "item", qualifiedByName = "itemName")
    @Mapping(target = "childStatus", source = "childStatus", qualifiedByName = "childStatusStaus")
    ChildStatusItemDTO toDto(ChildStatusItem s);

    @Named("itemName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ItemDTO toDtoItemName(Item item);

    @Named("childStatusStaus")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "staus", source = "staus")
    ChildStatusDTO toDtoChildStatusStaus(ChildStatus childStatus);
}
