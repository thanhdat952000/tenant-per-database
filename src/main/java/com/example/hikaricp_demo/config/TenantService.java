package com.example.hikaricp_demo.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class TenantService {
    @Autowired
    private DataSource dataSource;

    public void initDatabase(String database) {
        String createDbQuery = "CREATE DATABASE " + database;
        String jdbcUrlDefault = "jdbc:postgresql://localhost:5432/default";
        String username = "postgres";
        String password = "Dbc@1234";
        try (Connection connection = DriverManager.getConnection(jdbcUrlDefault, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createDbQuery);
            System.out.println("Database " + database + " created successfully.");

            String jdbcUrlNewDb = "jdbc:postgresql://localhost:5432/" + database;

            Flyway flyway = Flyway.configure()
                    .dataSource(jdbcUrlNewDb, username, password)
                    .load();
            flyway.migrate();
            createTenantYml(database, "org.postgresql.Driver", jdbcUrlNewDb, username, password, "public");

        } catch (SQLException e) {
            throw new RuntimeException("Error while creating database: " + database, e);
        }
    }

    public void initSchema(String schema) {
        String createSchemaQuery = "CREATE SCHEMA " + schema;
        String jdbcUrlDefault = "jdbc:postgresql://localhost:9500/default2";
        String username = "postgres";
        String password = "Dbc@1234";
        try (Connection connection = DriverManager.getConnection(jdbcUrlDefault, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createSchemaQuery);
            System.out.println("Schema " + schema + " created successfully.");

            Flyway flyway = Flyway.configure()
                    .dataSource(jdbcUrlDefault, username, password)
                    .schemas(schema)
                    .load();
            flyway.migrate();
            createTenantYml(schema, "org.postgresql.Driver", jdbcUrlDefault, username, password, schema);

        } catch (SQLException e) {
            throw new RuntimeException("Error while creating schema: " + schema, e);
        }
    }

    public void createTenantYml(String tenantId, String driverClassName, String url, String username, String password, String schema) {
        String fileName = "allTenants/" + tenantId + ".yml";
        String ymlContent = """
        tenant-id: %s
        datasource:
          driver-class-name: %s
          url: %s
          username: %s
          password: %s
          schema: %s
        """.formatted(tenantId, driverClassName, url, username, password, schema);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(ymlContent);
            System.out.println("YAML configuration created for tenant: " + tenantId);
        } catch (IOException e) {
            throw new RuntimeException("Error creating YAML configuration for tenant: " + tenantId, e);
        }
    }

}