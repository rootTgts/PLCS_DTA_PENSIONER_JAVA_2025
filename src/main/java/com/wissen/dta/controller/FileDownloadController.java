package com.wissen.dta.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.dta.service.DownloadService;

@RestController
@RequestMapping("/plcs/download")
public class FileDownloadController {
	@Autowired
	private DownloadService downloadService;

	@GetMapping("/DLC")
	public ResponseEntity<byte[]> downloadDLCByPpoId(@RequestParam("ppoId") String ppoId) {
		byte[] pdfBytes = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		Map<String, Object> response = downloadService.getDocumentDetails(ppoId);
		if (!response.isEmpty()) {
			pdfBytes = downloadService.generatePdf("DLC", response);
		} else {
			return new ResponseEntity<>(pdfBytes, headers, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<ByteArrayResource> downloadListByCodeAndStatus(@RequestParam("code") String code,
			@RequestParam("status") String status) {

		ResponseEntity<ByteArrayResource> file = downloadService.pensionerFileDownloadByCode(status, code);

		return file;
	}
}
