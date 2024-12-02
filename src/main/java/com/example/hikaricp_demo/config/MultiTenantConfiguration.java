package com.example.hikaricp_demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.yaml.snakeyaml.Yaml;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiTenantConfiguration {

    @Value("${defaultTenant}")
    private String defaultTenant;

    @SuppressWarnings("unchecked")
    @Bean
    public DataSource dataSource() {
        File[] files = Paths.get("allTenants").toFile().listFiles();
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        assert files != null;
        for (File propertyFile : files) {
            Yaml yaml = new Yaml();
            Map<String, Object> tenantProperties;

            try (InputStream inputStream = new FileInputStream(propertyFile)) {
                tenantProperties = yaml.load(inputStream);

                String tenantId = (String) tenantProperties.get("tenant-id");
                Map<String, Object> datasourceProperties = (Map<String, Object>) tenantProperties.get("datasource");
                String driverClassName = (String) datasourceProperties.get("driver-class-name");
                String url = (String) datasourceProperties.get("url");
                String username = (String) datasourceProperties.get("username");
                String password = (String) datasourceProperties.get("password");
                String schema = (String) datasourceProperties.get("schema");

                HikariConfig hikariConfig = new HikariConfig();
                hikariConfig.setDriverClassName(driverClassName);
                hikariConfig.setJdbcUrl(url);
                hikariConfig.setUsername(username);
                hikariConfig.setPassword(password);
                hikariConfig.setSchema(schema);

                HikariDataSource dataSource = new HikariDataSource(hikariConfig);
                resolvedDataSources.put(tenantId, dataSource);
            } catch (IOException exp) {
                throw new RuntimeException("Problem in tenant datasource:" + exp);
            }
        }

        AbstractRoutingDataSource dataSource = new MultiTenantDataSource();
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSources);
        dataSource.afterPropertiesSet();
        return dataSource;
    }
}