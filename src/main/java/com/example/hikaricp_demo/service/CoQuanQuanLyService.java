package com.example.hikaricp_demo.service;

import com.example.hikaricp_demo.domain.CoQuanQuanLy;

import java.util.Map;

public interface CoQuanQuanLyService {
    Map<String, Object> getAll();

    Map<String, Object> add(CoQuanQuanLy coQuan);

    Map<String, Object> update(CoQuanQuanLy coQuan);

    Map<String, Object> getById(Long id);

    Map<String, Object> delete(Long[] listId);
}
