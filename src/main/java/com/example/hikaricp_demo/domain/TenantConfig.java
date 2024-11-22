package com.example.hikaricp_demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "tenant_config")
public class TenantConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id", nullable = false, unique = true)
    private String tenantId;

    @Column(name = "db_url")
    private String url;

    @Column(name = "db_username")
    private String userName;

    @Column(name = "db_password")
    private String password;

    @Column(name = "driver_class_name")
    private String driverClassName;
}
