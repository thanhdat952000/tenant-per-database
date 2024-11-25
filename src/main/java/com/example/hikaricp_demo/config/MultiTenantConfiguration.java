package com.example.hikaricp_demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
import java.util.Properties;

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

                String tenantId = (String) tenantProperties.get("name");
                Map<String, Object> datasourceProperties = (Map<String, Object>) tenantProperties.get("datasource");

                HikariConfig hikariConfig = new HikariConfig();
                hikariConfig.setDriverClassName((String) datasourceProperties.get("driver-class-name"));
                hikariConfig.setJdbcUrl((String) datasourceProperties.get("url"));
                hikariConfig.setUsername((String) datasourceProperties.get("username"));
                hikariConfig.setPassword((String) datasourceProperties.get("password"));
//                hikariConfig.setSchema((String) datasourceProperties.get("schema"));
//                hikariConfig.setMaximumPoolSize(10);
//                hikariConfig.setConnectionTimeout(30000);

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