package com.example.hikaricp_demo.dao;

import com.example.hikaricp_demo.domain.CoQuanQuanLy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoQuanQuanLyDAO {
    @Autowired
    private EntityManager entityManager;

    public List<CoQuanQuanLy> findAll() {
        String sql = " FROM CoQuanQuanLy cq  " +
                " WHERE cq.isDeleted = false ";
        TypedQuery<CoQuanQuanLy> query = entityManager.createQuery(sql, CoQuanQuanLy.class);
       return query.getResultList();
    }
}
