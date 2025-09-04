package com.newsnow.platform.imagerescale.adapters.driven.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaRescaleImageTaskRepository extends JpaRepository<RescaleImageTaskJpaEntity, UUID> {
}
