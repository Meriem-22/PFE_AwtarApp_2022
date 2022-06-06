package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.domain.DonationItemDetails;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
import com.awtar.myapp.service.dto.DonationItemDetailsDTO;
import com.awtar.myapp.service.dto.ItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DonationItemDetails} and its DTO {@link DonationItemDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonationItemDetailsMapper extends EntityMapper<DonationItemDetailsDTO, DonationItemDetails> {
    @Mapping(target = "donationDetails", source = "donationDetails", qualifiedByName = "donationDetailsId")
    @Mapping(target = "item", source = "item", qualifiedByName = "itemName")
    DonationItemDetailsDTO toDto(DonationItemDetails s);

    @Named("donationDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonationDetailsDTO toDtoDonationDetailsId(DonationDetails donationDetails);

    @Named("itemName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ItemDTO toDtoItemName(Item item);
}
