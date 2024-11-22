package com.example.hikaricp_demo.repository;

import com.example.hikaricp_demo.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
