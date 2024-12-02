package com.example.hikaricp_demo.repository;

import com.example.hikaricp_demo.domain.CoQuanQuanLy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoQuanQuanLyRepository extends JpaRepository<CoQuanQuanLy, Long> {

    boolean existsByTenantId(String tenantId);
}
