package com.wissen.dta.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Aspect
@Component

public class LoggingAspect {
	private static final Log logger = LogFactory.getLog(LoggingAspect.class);
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Around("execution(* com.wissen.dta.controller.*.*(..))") // Adjust package to match your controllers
	public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] signatureArgs = joinPoint.getArgs();

		ObjectMapper mapper = new ObjectMapper();

		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {

			if (signatureArgs.length > 0 && signatureArgs[0] != null) {
				logger.info(joinPoint.getSignature().getName() + "\nRequest from UI Dashboard :"
						+ mapper.writeValueAsString(signatureArgs[0]));
			}
		} catch (JsonProcessingException e) {
		}

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		String className = methodSignature.getDeclaringType().getSimpleName();

		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		stopWatch.stop();

		// Log method execution time
		logger.info(
				"Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");

		try {

			if (signatureArgs.length > 0 && signatureArgs[0] != null) {
				logger.info(joinPoint.getSignature().getName() + "\nResponse to UI Dashboard :"
						+ mapper.writeValueAsString(result));
			}
		} catch (JsonProcessingException e) {
		}

		return result;

	}
}
