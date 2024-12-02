package com.example.hikaricp_demo.controller;

import com.example.hikaricp_demo.domain.City;
import com.example.hikaricp_demo.domain.CoQuanQuanLy;
import com.example.hikaricp_demo.service.CoQuanQuanLyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/coquanquanly")
public class CoQuanQuanLyController {
    @Autowired
    private CoQuanQuanLyService coQuanQuanLyService;

    @PostMapping
    public Map<String, Object> create(@RequestBody CoQuanQuanLy coQuan) {
        return coQuanQuanLyService.add(coQuan);
    }

    @GetMapping
    public Map<String, Object> getAll() {
        return coQuanQuanLyService.getAll();
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable(value = "id") Long id) {
        return coQuanQuanLyService.getById(id);
    }

    @PutMapping
    public Map<String, Object> update(@RequestBody CoQuanQuanLy coQuan) {
        return coQuanQuanLyService.update(coQuan);
    }

    @DeleteMapping
    public Map<String, Object> delete(@RequestBody Long[] listId) {
        return coQuanQuanLyService.delete(listId);
    }
}
