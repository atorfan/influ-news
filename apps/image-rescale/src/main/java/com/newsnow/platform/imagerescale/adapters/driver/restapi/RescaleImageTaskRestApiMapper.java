package com.newsnow.platform.imagerescale.adapters.driver.restapi;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
interface RescaleImageTaskRestApiMapper {

    @Mapping(target = "originalImageHash", source = "originalImageHash.value")
    @Mapping(target = "width", source = "appliedResolution.width")
    @Mapping(target = "height", source = "appliedResolution.height")
    RescaleImageTaskRestApiDto toRestApiDto(RescaleImageTask domainEntity);
}
