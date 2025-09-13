package com.iss4u.erp.services.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", 150);
        stats.put("totalProducts", 1250);
        stats.put("totalOrders", 340);
        stats.put("totalRevenue", 125000.50);
        stats.put("activeSuppliers", 25);
        stats.put("pendingPayments", 12);
        stats.put("lowStockItems", 8);
        stats.put("lastUpdated", System.currentTimeMillis());
        return stats;
    }

    public Map<String, String> getHealthStatus() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "erp-dashboard-service");
        health.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return health;
    }
}
