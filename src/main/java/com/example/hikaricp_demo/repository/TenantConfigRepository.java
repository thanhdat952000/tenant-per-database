package com.example.hikaricp_demo.repository;

import com.example.hikaricp_demo.domain.TenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantConfigRepository extends JpaRepository<TenantConfig, Long> {
    TenantConfig findByTenantId(String tenantId);
}
