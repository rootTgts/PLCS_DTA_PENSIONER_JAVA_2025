package com.wissen.dta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.dta.entity.StoAudit;

@Repository
public interface StoAuditRepository extends JpaRepository<StoAudit, Long> {

}
