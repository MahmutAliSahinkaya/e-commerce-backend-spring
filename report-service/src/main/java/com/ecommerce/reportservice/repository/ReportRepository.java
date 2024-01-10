package com.ecommerce.reportservice.repository;

import com.ecommerce.reportservice.entity.ReportItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportItem, Long> {
}
