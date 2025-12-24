package com.wissen.dta.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wissen.dta.dto.PensinerDetails;

@Repository
public class StoRepository {

	@Autowired
	private EntityManager entityManager;

	public Map<String, Object> authSto(String stoId, String password) {
		String st1 = "SELECT sto_id, password_changed,role FROM sto_users WHERE sto_id = :sto_id AND password = :password";
		Query query = entityManager.createNativeQuery(st1);
		query.setParameter("sto_id", stoId);
		query.setParameter("password", password);

		try {
			Object[] result = (Object[]) query.getSingleResult(); // Casting to Object array to handle multiple columns

			if (result != null && result.length > 0) {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("sto_id", result[0]); // sto_id
				resultMap.put("password_changed", result[1]); // password_changed
				resultMap.put("role", result[2]); 
				return resultMap;
			}
		} catch (NoResultException e) {
			return null;

		}
		return null; // Return null if no result or an exception occurs
	}

	@Transactional
	public void updatePassword(String code, String password, String newPassword) {
		String sql = "UPDATE sto_users " + "SET password = :newPassword, password_changed = :passwordChanged "
				+ "WHERE sto_id = :code AND password = :currentPassword";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("newPassword", newPassword);
		query.setParameter("passwordChanged", "true");
		query.setParameter("code", code);
		query.setParameter("currentPassword", password);

		try {
			int rowsUpdated = query.executeUpdate();
			if (rowsUpdated == 0) {
				System.out.println("No rows updated. Invalid sto_id or current password.");
			} else {
				System.out.println("Password updated successfully for sto_id: " + code);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Replace with proper logging in production
			throw new RuntimeException("Failed to update the password.");
		}
	}

	public List<PensinerDetails> getPensionersBySto(String stoId) {
		String sql = "SELECT a.ppo_id, a.ppo_name, a.pension_type, a.asto_status, "
				+ "a.photo_match_status, a.photo_match_percentage " + "FROM dta_registration a "
				+ "INNER JOIN sto_data b ON a.ppo_id = b.ppo_id " + "WHERE b.sto_id = :stoId "
				+ "AND a.photo_match_status = 'MATCHED' " + "AND a.asto_status IS NOT NULL "
				+ "AND a.sto_status IS NULL " + "AND (a.other_pension = 'Yes' " + "     OR a.re_employed = 'Yes' "
				+ " OR a.re_married = 'Yes')";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("stoId", stoId);
		List<Object[]> rawResults;
		List<PensinerDetails> pensioners = new ArrayList<>();
		try {
			rawResults = query.getResultList();
			// Map results to DTO
			for (Object[] row : rawResults) {
				PensinerDetails dto = new PensinerDetails();
				dto.setPpoId((String) row[0]);
				dto.setPpoName((String) row[1]);
				dto.setPensionerType((String) row[2]);
				dto.setAstoStatus((String) row[3]);
				dto.setPhotoMatchStatus((String) row[4]);
				dto.setPhotoMatchScore((String) row[5]);
				pensioners.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Use proper logging in production
		}

		return pensioners;
	}

	public PensinerDetails getPensioner(String ppoId) {
		PensinerDetails details = new PensinerDetails();
		String sql = "SELECT a.ppo_id, a.ppo_name, a.pension_type, input_photo_uuid, target_photo_uuid,dg_match_status,photo_match_status,photo_match_percentage,re_employed,re_married,other_pension FROM dta_registration a "
				+ "WHERE a.ppo_id = :ppoId AND a.photo_match_status = 'MATCHED' LIMIT 1";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("ppoId", ppoId);

		try {
			Object[] rawResults = (Object[]) query.getSingleResult();
			// Map the results to PensionerDetails
			details.setPpoId((String) rawResults[0]); // Ensure correct type casting
			details.setPpoName((String) rawResults[1]);
			details.setPensionerType((String) rawResults[2]);
			details.setInputPhoto((String) rawResults[3]);
			details.setDepartmentPhoto((String) rawResults[4]);
			details.setDgMatchStatus((String) rawResults[5]);
			details.setPhotoMatchStatus((String) rawResults[6]);
			details.setPhotoMatchScore((String) rawResults[7]);
			details.setOtherPension((String) rawResults[10]);
			details.setReEmployed((String) rawResults[8]);
			details.setReMarried((String) rawResults[9]);
			
		} catch (NoResultException e) {
			// Handle case when no result is found
			System.out.println("No matching pensioner found for PPO ID: " + ppoId);
		}

		return details;
	}

	@Transactional
	public String updatePensionerStatusBySto(String status, String ppoId, String mark) {
		// Get current timestamp and format it
		LocalDateTime currentTime = LocalDateTime.now();
		String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		String sql = "UPDATE dta_registration SET sto_status = :status, modified_time = STR_TO_DATE(:currentTime, '%Y-%m-%d %H:%i:%s') , re_mark =:mark WHERE ppo_id = :ppoId and photo_match_status = 'MATCHED' ";
		Query query = entityManager.createNativeQuery(sql);

		query.setParameter("status", status);
		query.setParameter("currentTime", formattedTime);
		query.setParameter("ppoId", ppoId);
		query.setParameter("mark", mark);
		int updatedRecords = 0;
		try {
			updatedRecords = query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedRecords > 0 ? "Updated records: " + updatedRecords : "No Data found";
	}

	public List<PensinerDetails> getPensionersByStoAndStatus(String status, String stoId) {
		String sql = "SELECT a.ppo_id, a.ppo_name, a.pension_type FROM dta_registration a "
				+ "INNER JOIN sto_data b ON a.ppo_id = b.ppo_id " + "WHERE b.sto_id = :stoId and sto_status=:status";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("stoId", stoId);
		query.setParameter("status", status);

		List<Object[]> rawResults;
		List<PensinerDetails> pensioners = new ArrayList<>();

		try {
			rawResults = query.getResultList();

			// Map results to DTO
			for (Object[] row : rawResults) {
				PensinerDetails dto = new PensinerDetails();
				dto.setPpoId((String) row[0]);
				dto.setPpoName((String) row[1]);
				dto.setPensionerType((String) row[2]);
				pensioners.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Log exception for debugging
		}

		return pensioners;
	}
}
