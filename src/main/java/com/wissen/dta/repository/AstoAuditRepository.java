package com.wissen.dta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wissen.dta.entity.AstoAudit;

@Repository
public interface AstoAuditRepository extends JpaRepository<AstoAudit, Long> {

}
