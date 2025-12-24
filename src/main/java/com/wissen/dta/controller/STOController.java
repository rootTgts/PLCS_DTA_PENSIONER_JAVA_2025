package com.wissen.dta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.dta.dto.LoginRequest;
import com.wissen.dta.dto.LoginResponse;
import com.wissen.dta.dto.PensinerDetails;
import com.wissen.dta.dto.RetypePasswod;
import com.wissen.dta.dto.UpdateDetails;
import com.wissen.dta.security.JwtTokenProvider;
import com.wissen.dta.service.StoService;

@RestController
@RequestMapping("/api/sto")
public class STOController {

	private static final Log logger = LogFactory.getLog(STOController.class);
	@Autowired
	StoService service;
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@SuppressWarnings("unused")
//	@PostMapping("/signin")
//	public ResponseEntity<Map<String, String>> signIn(@RequestBody LoginRequest loginRequest) {
//		Map<String, String> map = new HashMap<>();
//		Map<String, Object> dept = service.authSto(loginRequest.getCode(), loginRequest.getPassword());
//		if (dept != null) {
//			map.put("code", (Integer) dept.get("sto_id") + "");
//			map.put("authenticate", "true");
//			map.put("passwordChanged", (String) dept.get("password_changed"));
//			return ResponseEntity.ok(map);
//		} else {
//			map.put("code", null);
//			map.put("authenticate", "false");
//
//			return ResponseEntity.ok(map);
//		}
//	}

	@PostMapping("/signin")
	public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest loginRequest) {

		Map<String, Object> dept = service.authSto(loginRequest.getCode(), loginRequest.getPassword());

		if (dept == null) {
			LoginResponse resp = new LoginResponse();
			resp.setCode(null);
			resp.setAuthenticate(false);
			resp.setPasswordChanged(null);
			resp.setToken(null);
			resp.setRole(null);
			return ResponseEntity.ok(resp);
		}

		LoginResponse resp = new LoginResponse();

		// Extract values safely
		resp.setCode(String.valueOf(dept.get("sto_id")));
		resp.setAuthenticate(true);
		resp.setPasswordChanged((String) dept.get("password_changed"));
		resp.setRole((String) dept.get("role"));

		// Generate JWT using LoginResponse
		String token = jwtTokenProvider.createToken(resp);
		resp.setToken(token);

		return ResponseEntity.ok(resp);
	}

	@PostMapping("/password-change")
	public ResponseEntity<Map<String, String>> changePassword(@RequestBody RetypePasswod request) {
		Map<String, String> responseMap = new HashMap<>();

		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			responseMap.put("error", "New password and confirm password do not match.");
			return ResponseEntity.badRequest().body(responseMap);
		}

		String response = service.reTypePassword(request);

		if (response != null) {
			responseMap.put("code", response);
			responseMap.put("passwordUpdated", "true");
			return ResponseEntity.ok(responseMap);
		}

		responseMap.put("error", "Failed to update the password.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
	}

	@GetMapping("/getPensioners/{stoCode}")
	public List<PensinerDetails> getPensionersBySto(@PathVariable String stoCode) {
		return service.getPensionerBySto(stoCode);
	}

	@GetMapping("/getDetails/{ppoId}")
	public ResponseEntity<PensinerDetails> getDetailsByPensionerId(@PathVariable String ppoId) {

		PensinerDetails response = service.findPensionerByPpoId(ppoId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/update")
	public String updateStatusBySto(@RequestBody UpdateDetails details) {
		return service.verifyPensionerBySto(details);
	}

	@PostMapping("/status")
	public List<PensinerDetails> VerifyPensionerByStoAndStatus(@RequestParam(value = "status") String status,
			@RequestParam(value = "stoid") String stoId) {
		logger.info("Request from UI is => " + stoId + " and " + status);
		return service.VerifyPensionerByStoAndStatus(status, stoId);
	}

}
