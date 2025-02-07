package com.wissen.dta.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.wissen.dta.dto.PensinerDetails;
import com.wissen.dta.entity.DtaRegistration;
import com.wissen.dta.repository.DtaRepository;
import com.wissen.dta.util.Utils;

@Service
public class DownloadService {
	@Autowired
	private DtaRepository registrationRepo;
	@Autowired
	private Utils utils;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private StoService service;

	public Map<String, Object> getDocumentDetails(String ppoId) {

		DtaRegistration res = this.registrationRepo.findByPpoId(ppoId);
		Map<String, Object> data = new HashMap<>();
		if (res != null) {
			data.put("departmentPhotoBase64", this.utils.getImageByUuid(res.getInputPhotoUUID(), ppoId));
			data.put("pensionerName", res.getPpoName());
			data.put("ppoId", res.getPpoId());
			data.put("ppoNumber", res.getPpoNo());
			data.put("referenceNo", res.getApplicationNumber());
			data.put("SubmittedDate", Utils.localDateTimeToDateTimeWithSlash(res.getCreatedTime()));
			data.put("status", "A");

		}
		return data;
	}

	public byte[] generatePdf(String templateName, Map<String, Object> data) {
		Context context = new Context();
		context.setVariables(data); // Pass dynamic data to the template

		// Process the HTML template
		String htmlContent = templateEngine.process(templateName, context);

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.useFastMode();
			builder.withHtmlContent(htmlContent, null);
			builder.toStream(outputStream);
			builder.run();

			return outputStream.toByteArray(); // Return generated PDF bytes
		} catch (Exception e) {
			throw new RuntimeException("Error generating PDF", e);
		}
	}

	public ResponseEntity<ByteArrayResource> pensionerFileDownloadByCode(String status, String code) {

		List<PensinerDetails> response = service.VerifyPensionerByStoAndStatus(status, code);

		// Generate HTML content using Thymeleaf
		Context context = new Context();
		context.setVariable("pensioners", response);
		String htmlContent = templateEngine.process("pensioner-list", context);

		// Generate PDF
		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
		try {
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.useFastMode();
			builder.withHtmlContent(htmlContent, null);
			builder.toStream(pdfOutputStream);
			builder.run();
		} catch (Exception e) {
			throw new RuntimeException("Error while generating PDF", e);
		}

		// Convert to ByteArrayResource for multiple reads
		ByteArrayResource pdfResource = new ByteArrayResource(pdfOutputStream.toByteArray());

		// Return the PDF as a downloadable response
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=pensioner-list.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.contentLength(pdfResource.contentLength()).body(pdfResource);

	}

}
