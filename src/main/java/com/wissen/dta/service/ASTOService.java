package com.wissen.dta.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wissen.dta.dto.PensinerDetails;
import com.wissen.dta.dto.RetypePasswod;
import com.wissen.dta.dto.UpdateDetails;
import com.wissen.dta.entity.AstoAudit;
import com.wissen.dta.entity.DtaRegistration;
import com.wissen.dta.repository.AstoAuditRepository;
import com.wissen.dta.repository.AstoRepository;
import com.wissen.dta.repository.DtaRepository;

@Service
public class ASTOService {

	@Autowired
	private AstoRepository repository;

	@Autowired
	private AstoAuditRepository astoAuditRepo;

	@Autowired
	private DtaRepository dtaRepo;

	@Value("${image.path}")
	String imagePath;

	public Map<String, Object> authSto(String code, String password) {

		return repository.authASto(code, password);
	}

	public String reTypePassword(RetypePasswod request) {
		Map<String, Object> code = repository.authASto(request.getCode(), request.getOldPassword());
		if (code != null) {
			repository.updatePassword(request.getCode(), request.getOldPassword(), request.getNewPassword());
			return code + "";
		}
		return null;
	}

	public List<PensinerDetails> getPensionerBySto(String astoCode) {
		return repository.getPensionersByAsto(astoCode);
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
		// logger.info("Attempting to retrieve image from path: " + path); // Log the
		// path being accessed

		File imageFile = new File(path);

		if (!imageFile.exists()) {
			String errorMessage = "Image file not found at path: " + path;
			// logger.error(errorMessage);
			throw new RuntimeException(errorMessage); // You can handle the exception as per your logic
		}

		byte[] imageBytes = null;
		try {
			imageBytes = Files.readAllBytes(imageFile.toPath());
			// logger.info("Successfully read the image file from path: " + path);
		} catch (IOException e) {
			String errorMessage = "Error reading image file from path: " + path;
			// logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}

		String encodedImage = null;
		try {
			encodedImage = Base64.getEncoder().encodeToString(imageBytes);
			// logger.info("Successfully encoded the image for Pensioner ID: " +
			// pensionerId);
		} catch (Exception e) {
			String errorMessage = "Error encoding image to Base64 for Pensioner ID: " + pensionerId;
			// logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}
		return encodedImage;
	}

	public String getTargetImageByUuid(String uuid, String pensionerId) {
		String path = imagePath + "/AadhaarImage/images/" + uuid.replace("-", "/") + "/" + pensionerId + ".png";
		// logger.info("Attempting to retrieve image from path: " + path); // Log the
		// path being accessed

		File imageFile = new File(path);

		if (!imageFile.exists()) {
			String errorMessage = "Image file not found at path: " + path;
			// logger.error(errorMessage);
			return null;
			// throw new RuntimeException(errorMessage); // You can handle the exception as
			// per your logic
		}

		byte[] imageBytes = null;
		try {
			imageBytes = Files.readAllBytes(imageFile.toPath());
			// logger.info("Successfully read the image file from path: " + path);
		} catch (IOException e) {
			String errorMessage = "Error reading image file from path: " + path;
			// logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}

		String encodedImage = null;
		try {
			encodedImage = Base64.getEncoder().encodeToString(imageBytes);
			// logger.info("Successfully encoded the image for Pensioner ID: " +
			// pensionerId);
		} catch (Exception e) {
			String errorMessage = "Error encoding image to Base64 for Pensioner ID: " + pensionerId;
			// logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}
		return encodedImage;
	}

	public String VerifyPensionerByASto(UpdateDetails details) {
		AstoAudit entity = new AstoAudit();
		try {
			BeanUtils.copyProperties(details, entity);
			DtaRegistration response = dtaRepo.findByPpoId(details.getPpoId());
			response.setAstoStatus(details.getStatus());
			response.setReMark(details.getReMark());
			dtaRepo.save(response);
			AstoAudit responseAudit = astoAuditRepo.save(entity);

		} catch (Exception e) {
			return "null";
		}
		return "Updated Successfully";
	}

	public List<PensinerDetails> VerifyPensionerByStoAndStatus(String status, String astoId) {
		return repository.getPensionersByAstoAndStatus(status, astoId);
	}

	public byte[] generatePdf(Object object) {

		return null;
	}
}
