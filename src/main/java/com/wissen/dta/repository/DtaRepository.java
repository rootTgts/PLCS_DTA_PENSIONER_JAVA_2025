package com.wissen.dta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wissen.dta.entity.DtaRegistration;

@Repository
public interface DtaRepository extends JpaRepository<DtaRegistration, String> {

	@Query(value = "SELECT dta.* FROM dta_registration dta " + "WHERE ppo_id = :ppoId "
			+ "AND photo_match_status = 'MATCHED'", nativeQuery = true)
	DtaRegistration findByPpoId(@Param("ppoId") String ppoId);

}
