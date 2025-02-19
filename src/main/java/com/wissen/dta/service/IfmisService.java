package com.wissen.dta.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wissen.dta.dto.AuthPensionerReq;
import com.wissen.dta.dto.AuthPensionerRes;

@Service
public class IfmisService {

	private static final Log logger = LogFactory.getLog(IfmisService.class);
	@Autowired
	private RestTemplate restTemplate;
	@Value("${ifmis.url}")
	private String clientURL;

	@Value("${token}")
	private String token;

	public AuthPensionerRes authenticatePensioner(AuthPensionerReq authRequest) {
		Random random = new Random();
		String authDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		// Step 5: Generate acknowledgment number
		String applicationNumber = authRequest.getPpo_id() + "/" + String.format("%04d", random.nextInt(1000)) + "/"
				+ authDate.substring(authDate.length() - 2);

		logger.info("Generated acknowledgment number: {}" + applicationNumber);

		authRequest.setAcknowledgeNo(applicationNumber);
		authRequest.setAuthorizationDate(authDate);

		String finalUrl = clientURL + "authenticate_pensioner_details";
		logger.info("Final URL for authentication: {} " + finalUrl);

		// Set HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("JWT-AuthToken", token);

		// Prepare the request
		HttpEntity<AuthPensionerReq> requestEntity = new HttpEntity<>(authRequest, headers);
		logger.info("Request Payload: {} " + authRequest);

		// Initialize response
		AuthPensionerRes jsonResponse = null;
		// BeanUtils.copyProperties(authRequest, jsonResponse);

		try {
			// Send the POST request
			jsonResponse = restTemplate.postForObject(finalUrl, requestEntity, AuthPensionerRes.class);
			logger.info("Authentication response received: {}  " + jsonResponse);

		} catch (Exception e) {
			logger.error("Unexpected error occurred during authentication. Message: {} " + e.getMessage(), e);

		}
		jsonResponse.setAcknowledgeNo(authRequest.getAcknowledgeNo());
		jsonResponse.setAuthorizationDate(authRequest.getAuthorizationDate());
		jsonResponse.setPpo_id(authRequest.getPpo_id());
		return jsonResponse;
	}
}
