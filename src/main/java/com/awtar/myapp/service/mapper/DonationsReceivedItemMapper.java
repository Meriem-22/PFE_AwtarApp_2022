package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.DonationsReceived;
import com.awtar.myapp.domain.DonationsReceivedItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.service.dto.DonationsReceivedDTO;
import com.awtar.myapp.service.dto.DonationsReceivedItemDTO;
import com.awtar.myapp.service.dto.ItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DonationsReceivedItem} and its DTO {@link DonationsReceivedItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonationsReceivedItemMapper extends EntityMapper<DonationsReceivedItemDTO, DonationsReceivedItem> {
    @Mapping(target = "item", source = "item", qualifiedByName = "itemName")
    @Mapping(target = "donationsReceived", source = "donationsReceived", qualifiedByName = "donationsReceivedId")
    DonationsReceivedItemDTO toDto(DonationsReceivedItem s);

    @Named("itemName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ItemDTO toDtoItemName(Item item);

    @Named("donationsReceivedId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonationsReceivedDTO toDtoDonationsReceivedId(DonationsReceived donationsReceived);
}
