//package com.example.hikaricp_demo.config;
//
//import com.example.hikaricp_demo.repository.TenantConfigRepository;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//
//@Component
//@Order(1)
//class TenantFilter1 implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        String tenantName = req.getHeader("X-TenantID");
//        TenantContext.setCurrentTenant(tenantName);
//        try {
//            chain.doFilter(request, response);
//        } finally {
//            TenantContext.setCurrentTenant("");
//        }
//    }
//}