package com.newsnow.platform.imagerescale.adapters.driven.persistence;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RescaleImageTaskMapper {

    @Mapping(target = "originalImageHash.value", source = "originalImageHash")
    @Mapping(target = "appliedResolution.width", source = "width")
    @Mapping(target = "appliedResolution.height", source = "height")
    RescaleImageTask toDomainEntity(RescaleImageTaskJpaEntity jpaEntity);

    @Mapping(target = "originalImageHash", source = "originalImageHash.value")
    @Mapping(target = "width", source = "appliedResolution.width")
    @Mapping(target = "height", source = "appliedResolution.height")
    RescaleImageTaskJpaEntity toJpaEntity(RescaleImageTask domainEntity);
}
