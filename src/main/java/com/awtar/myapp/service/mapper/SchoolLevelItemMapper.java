package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.domain.SchoolLevelItem;
import com.awtar.myapp.service.dto.ItemDTO;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
import com.awtar.myapp.service.dto.SchoolLevelItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchoolLevelItem} and its DTO {@link SchoolLevelItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface SchoolLevelItemMapper extends EntityMapper<SchoolLevelItemDTO, SchoolLevelItem> {
    @Mapping(target = "item", source = "item", qualifiedByName = "itemName")
    @Mapping(target = "schoolLevel", source = "schoolLevel", qualifiedByName = "schoolLevelSchoolLevel")
    SchoolLevelItemDTO toDto(SchoolLevelItem s);

    @Named("itemName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ItemDTO toDtoItemName(Item item);

    @Named("schoolLevelSchoolLevel")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "schoolLevel", source = "schoolLevel")
    SchoolLevelDTO toDtoSchoolLevelSchoolLevel(SchoolLevel schoolLevel);
}
