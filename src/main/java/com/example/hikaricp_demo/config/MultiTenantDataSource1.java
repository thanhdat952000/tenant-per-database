//package com.example.hikaricp_demo.config;
//
//import com.example.hikaricp_demo.domain.TenantConfig;
//import com.example.hikaricp_demo.repository.TenantConfigRepository;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class MultiTenantDataSource1 extends AbstractRoutingDataSource {
//
//    @Override
//    protected Object determineCurrentLookupKey() {
//        return TenantContext.getCurrentTenant();
//    }
//
////    @Override
////    protected DataSource determineTargetDataSource() {
////        String tenantId = (String) determineCurrentLookupKey();
////        if (tenantId == null || tenantId.isEmpty()) {
////            throw new IllegalStateException("No tenant identifier found in context");
////        }
////
////        // Create and cache the DataSource if it doesn't exist
////        return dataSourceMap.computeIfAbsent(tenantId, this::createHikariDataSource);
////    }
////
////    private DataSource createHikariDataSource(String tenantId) {
////        TenantConfig tenantConfig = tenantConfigRepository.findByTenantId(tenantId)
////                .orElseThrow(() -> new IllegalArgumentException("No tenant found for id: " + tenantId));
////
////        HikariConfig config = new HikariConfig();
////        config.setDriverClassName(tenantConfig.getDriverClassName());
////        config.setJdbcUrl(tenantConfig.getUrl());
////        config.setUsername(tenantConfig.getUserName());
////        config.setPassword(tenantConfig.getPassword());
////        config.setMaximumPoolSize(10);
////        config.setMinimumIdle(2);
////        config.setIdleTimeout(30000);
////        config.setMaxLifetime(1800000);
////
////        return new HikariDataSource(config);
////    }
//}
