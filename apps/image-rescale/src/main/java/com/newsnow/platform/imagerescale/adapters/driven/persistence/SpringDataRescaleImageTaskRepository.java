package com.newsnow.platform.imagerescale.adapters.driven.persistence;

import com.newsnow.platform.imagerescale.core.domain.RescaleImageTask;
import com.newsnow.platform.imagerescale.core.ports.spi.RescaleImageTaskRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SpringDataRescaleImageTaskRepository implements RescaleImageTaskRepository {

    private final JpaRescaleImageTaskRepository jpaRepository;
    private final RescaleImageTaskJpaMapper mapper;

    public SpringDataRescaleImageTaskRepository(JpaRescaleImageTaskRepository jpaRepository, RescaleImageTaskJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public RescaleImageTask findBy(UUID taskId) {
        var jpaEntity = jpaRepository.findById(taskId).orElse(null);
        return mapper.toDomainEntity(jpaEntity);
    }

    @Override
    public void save(RescaleImageTask domainEntity) {
        var jpaEntity = mapper.toJpaEntity(domainEntity);
        jpaRepository.save(jpaEntity);
    }
}
