package com.example.hikaricp_demo.service.impl;

import com.example.hikaricp_demo.config.TenantService;
import com.example.hikaricp_demo.dao.CoQuanQuanLyDAO;
import com.example.hikaricp_demo.domain.CoQuanQuanLy;
import com.example.hikaricp_demo.exception.HandleException;
import com.example.hikaricp_demo.repository.CoQuanQuanLyRepository;
import com.example.hikaricp_demo.service.CoQuanQuanLyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class CoQuanQuanLyServiceImpl implements CoQuanQuanLyService {

    @Autowired
    private CoQuanQuanLyRepository coQuanQuanLyRepository;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private CoQuanQuanLyDAO coQuanQuanLyDAO;

    @Override
    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>();
        List<CoQuanQuanLy> listCoQuan = coQuanQuanLyDAO.findAll();
        listCoQuan.sort(Comparator.comparing(CoQuanQuanLy::getId));
        result.put("result", listCoQuan);
        result.put("status", true);
        result.put("msg", "Truy xuất dữ liệu thành công");
        return result;
    }

    @Override
    public Map<String, Object> add(CoQuanQuanLy coQuan) {
        Map<String, Object> result = new HashMap<>();
        boolean checkExistsTenantId = coQuanQuanLyRepository.existsByTenantId(coQuan.getTenantId());
        if (checkExistsTenantId) {
            throw new HandleException("TenantId đã tồn tại!");
        }
        CoQuanQuanLy newCoQuan = coQuanQuanLyRepository.save(coQuan);
        result.put("result", newCoQuan);
        result.put("status", true);
        result.put("msg", "Thêm mới dữ liệu thành công");
        tenantService.initSchema(coQuan.getTenantId());
        return result;
    }

    public Map<String, Object> delete(CoQuanQuanLy coQuan) {
        Map<String, Object> result = new HashMap<>();
        return null;
    }

    @Override
    public Map<String, Object> update(CoQuanQuanLy coQuan) {
        Map<String, Object> result = new HashMap<>();
        Optional<CoQuanQuanLy> opCoQuan = coQuanQuanLyRepository.findById(coQuan.getId());
        if (opCoQuan.isEmpty()) {
            throw new HandleException("Truy xuất dữ liệu thất bại!");
        }
        CoQuanQuanLy updateCoQuan = opCoQuan.get();
        updateCoQuan.setName(coQuan.getName());
        result.put("result", coQuanQuanLyRepository.save(updateCoQuan));
        result.put("status", true);
        result.put("msg", "Cập nhật dữ liệu thành công");
        return result;
    }

    @Override
    public Map<String, Object> getById(Long id) {
        Map<String, Object> result = new HashMap<>();
        Optional<CoQuanQuanLy> opCoQuan = coQuanQuanLyRepository.findById(id);
        if (opCoQuan.isEmpty()) {
            throw new HandleException("Không tìm thấy đối tượng với id = " + id);
        }
        result.put("result", opCoQuan.get());
        result.put("status", true);
        result.put("msg", "Truy xuất dữ liệu thành công");
        return result;
    }

    @Override
    public Map<String, Object> delete(Long[] listId) {
        Map<String, Object> result = new HashMap<>();
        List<CoQuanQuanLy> listCoQuanDelete = new ArrayList<>();
        for (Long id : listId) {
            Optional<CoQuanQuanLy> opCoQuan = coQuanQuanLyRepository.findById(id);
            if (opCoQuan.isEmpty()) {
                throw new HandleException("Không tìm thấy đối tượng để xóa!");
            }
            opCoQuan.get().setDeleted(true);
            listCoQuanDelete.add(opCoQuan.get());
        }
        coQuanQuanLyRepository.saveAll(listCoQuanDelete);
        result.put("status", true);
        result.put("msg", "Xóa dữ liệu thành công!");
        return result;
    }
}
