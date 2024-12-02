package com.example.hikaricp_demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tenant_config")
public class TenantConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "driver_class_name")
    private String driverClassName;

    @Column(name = "db_url")
    private String url;

    @Column(name = "db_username")
    private String username;

    @Column(name = "db_password")
    private String password;

    @Column(name = "db_schema")
    private String schema;

    @Column(name = "tenant_id", unique = true)
    private String tenantId;
}
