//package com.example.hikaricp_demo.config;
//
//import com.example.hikaricp_demo.domain.TenantConfig;
//import com.example.hikaricp_demo.repository.TenantConfigRepository;
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Configuration
//public class ConfigurationDataSource {
//
//    @Autowired
//    private TenantConfigRepository repository;
//
//    private List<TenantConfig> getListTenant(){
//        return repository.findAll();
//    }
//
//    TenantConfig tenantDefault() {
//        return repository.findByTenantId("tenantDefault");
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        Map<Object, Object> resolvedDataSources = new HashMap<>();
//        List<TenantConfig> listTenant = getListTenant();
//
//        for (TenantConfig item : listTenant) {
//            HikariConfig hikariConfig = new HikariConfig();
//            hikariConfig.setDriverClassName(item.getDriverClassName());
//            hikariConfig.setJdbcUrl(item.getUrl());
//            hikariConfig.setUsername(item.getUsername());
//            hikariConfig.setPassword(item.getPassword());
//            hikariConfig.setSchema(item.getSchema());
//
//            HikariDataSource dataSource = new HikariDataSource(hikariConfig);
//            resolvedDataSources.put(item.getTenantId(), dataSource);
//        }
//
//        AbstractRoutingDataSource dataSource = new MultiTenantDataSource();
//        dataSource.setDefaultTargetDataSource(tenantDefault());
//        dataSource.setTargetDataSources(resolvedDataSources);
//        dataSource.afterPropertiesSet();
//        return dataSource;
//    }
//}
