package com.wissen.dta.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wissen.dta.dto.AuthPensionerReq;
import com.wissen.dta.dto.AuthPensionerRes;
import com.wissen.dta.dto.PensinerDetails;
import com.wissen.dta.dto.RetypePasswod;
import com.wissen.dta.dto.UpdateDetails;
import com.wissen.dta.entity.DtaRegistration;
import com.wissen.dta.entity.StoAudit;
import com.wissen.dta.repository.DtaRepository;
import com.wissen.dta.repository.StoAuditRepository;
import com.wissen.dta.repository.StoRepository;

@Service
public class StoService {

	private static final Log logger = LogFactory.getLog(StoService.class);

	@Autowired
	private StoRepository repository;

	@Autowired
	private StoAuditRepository stoAuditRepository;

	@Autowired
	private DtaRepository dtaRepo;

	@Value("${image.path}")
	private String imagePath;

	@Autowired
	private IfmisService ifmisService;

	public Map<String, Object> authSto(String code, String password) {

		return repository.authSto(code, password);
	}

	public String reTypePassword(RetypePasswod request) {
		Map<String, Object> code = repository.authSto(request.getCode(), request.getOldPassword());
		if (code != null) {
			repository.updatePassword(request.getCode(), request.getOldPassword(), request.getNewPassword());
			return code + "";
		}
		return null;
	}

	public List<PensinerDetails> getPensionerBySto(String stoCode) {
		return repository.getPensionersBySto(stoCode);
	}

	public PensinerDetails findPensionerByPpoId(String ppoId) {

		PensinerDetails response = repository.getPensioner(ppoId);
		String inputImage = getInputImageByUuid(response.getInputPhoto(), response.getPpoId());
		String departmentPhoto = getTargetImageByUuid(response.getDepartmentPhoto(), response.getPpoId());
		response.setInputPhoto(inputImage);
		response.setDepartmentPhoto(departmentPhoto);
		return response;
	}

	public String getInputImageByUuid(String uuid, String pensionerId) {
		String path = imagePath + "/InputImages/images/" + uuid.replace("-", "/") + "/" + pensionerId + ".png";
		logger.info("Attempting to retrieve image from path: " + path); // Log the
		// path being accessed

		File imageFile = new File(path);

		if (!imageFile.exists()) {
			String errorMessage = "Image file not found at path: " + path;
			logger.error(errorMessage);
			return null;// You can handle the exception as per your logic
		}

		byte[] imageBytes = null;
		try {
			imageBytes = Files.readAllBytes(imageFile.toPath());
			logger.info("Successfully read the image file from path: " + path);
		} catch (IOException e) {
			String errorMessage = "Error reading image file from path: " + path;
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}

		String encodedImage = null;
		try {
			encodedImage = Base64.getEncoder().encodeToString(imageBytes);
			logger.info("Successfully encoded the image for Pensioner ID: " + pensionerId);
		} catch (Exception e) {
			String errorMessage = "Error encoding image to Base64 for Pensioner ID: " + pensionerId;
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}
		return encodedImage;
	}

	public String getTargetImageByUuid(String uuid, String pensionerId) {
		String path = imagePath + "/AadhaarImage/images/" + uuid.replace("-", "/") + "/" + pensionerId + ".png";
		logger.info("Attempting to retrieve image from path: " + path); // Log the
		// path being accessed

		File imageFile = new File(path);

		if (!imageFile.exists()) {
			String errorMessage = "Image file not found at path: " + path;
			logger.error(errorMessage);
			return null;
			// throw new RuntimeException(errorMessage); // You can handle the exception as
			// per your logic
		}

		byte[] imageBytes = null;
		try {
			imageBytes = Files.readAllBytes(imageFile.toPath());
			logger.info("Successfully read the image file from path: " + path);
		} catch (IOException e) {
			String errorMessage = "Error reading image file from path: " + path;
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}

		String encodedImage = null;
		try {
			encodedImage = Base64.getEncoder().encodeToString(imageBytes);
			logger.info("Successfully encoded the image for Pensioner ID: " + pensionerId);
		} catch (Exception e) {
			String errorMessage = "Error encoding image to Base64 for Pensioner ID: " + pensionerId;
			// logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}
		return encodedImage;
	}

	@Transactional
	public String VerifyPensionerBySto(UpdateDetails details) {
		StoAudit entity = new StoAudit();

		try {
			BeanUtils.copyProperties(details, entity);

			DtaRegistration pensionerEntity = dtaRepo.findByPpoId(details.getPpoId());
			pensionerEntity.setStoStatus(details.getStatus());
			pensionerEntity.setReMark(details.getReMark());
			// response.setModifiedDate("");

			AuthPensionerReq requ = new AuthPensionerReq();
			requ.setPpo_id(details.getPpoId());

			if (!"Rejected".equalsIgnoreCase(details.getStatus())) {

				logger.info("Calling Authintication API : {} " + details.getPpoId());
				AuthPensionerRes ifmisResponse = ifmisService.authenticatePensioner(requ);
				logger.info("Completed Authintication API and Response is  : {} " + ifmisResponse);

				pensionerEntity.setApplicationNumber(ifmisResponse.getAcknowledgeNo());
				pensionerEntity.setIfmisStatus(ifmisResponse.getStatus());
				pensionerEntity.setMessage(ifmisResponse.getMessage());

				// String inputDateTime =ifmisResponse.getAuthorizationDate();

				// Define the input format
				// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
				// HH:mm:ss.SSSSSS");

				// Parse the input string to LocalDateTime
				// LocalDateTime localDateTime = LocalDateTime.parse(inputDateTime, formatter);
				// pensionerEntity.setModifiedDate(localDateTime);

			}

			DtaRegistration dbResponse = dtaRepo.save(pensionerEntity);
			StoAudit responseAudit = stoAuditRepository.save(entity);

		} catch (Exception e) {
			return "null";
		}
		return "Updated Successfully";

	}

	public List<PensinerDetails> VerifyPensionerByStoAndStatus(String status, String stoId) {
		return repository.getPensionersByStoAndStatus(status, stoId);
	}
}
