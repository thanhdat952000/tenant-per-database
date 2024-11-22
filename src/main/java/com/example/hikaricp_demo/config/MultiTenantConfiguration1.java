//package com.example.hikaricp_demo.config;
//
//import com.example.hikaricp_demo.repository.TenantConfigRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class MultiTenantConfiguration1 {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Value("${defaultTenant}")
//    private String defaultTenant;
//
//    @Bean
//    public DataSource dataSource() {
//        Map<Object, Object> tenantDataSources = readTenantDataSources();
//        System.out.println(" ========================== " + tenantDataSources);
//        AbstractRoutingDataSource dataSource = new MultiTenantDataSource1();
//
//        dataSource.setDefaultTargetDataSource(tenantDataSources.get(defaultTenant));
//        dataSource.setTargetDataSources(tenantDataSources);
//        dataSource.afterPropertiesSet();
//
//        return dataSource;
//    }
//
//    private Map<Object, Object> readTenantDataSources() {
//        Map<Object, Object> tenantDataSources = new HashMap<>();
//
//        try (Connection conn = dataSource.getConnection()) {
//            String sql = "SELECT * FROM tenant_config";
//            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
//                while (rs.next()) {
//                    String tenantId = rs.getString("tenant_id");
//                    String driverClassName = rs.getString("driver_class_name");
//                    String url = rs.getString("db_url");
//                    String username = rs.getString("db_username");
//                    String password = rs.getString("db_password");
//
//                    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create()
//                            .driverClassName(driverClassName)
//                            .url(url)
//                            .username(username)
//                            .password(password);
//
//                    DataSource tenantDataSource = dataSourceBuilder.build();
//                    tenantDataSources.put(tenantId, tenantDataSource);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error reading tenant data sources", e);
//        }
//
//        return tenantDataSources;
//    }
//}
