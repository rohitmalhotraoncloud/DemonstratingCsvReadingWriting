package com.app.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestErrorHandlerController {

	private static final Logger logger = LoggerFactory.getLogger(RestErrorHandlerController.class);

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<String> handleCoreTradesysException(RuntimeException ex, HttpServletRequest request) {
		logger.error("Error description : " + request.getRequestURI(), ex);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<String>(StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : ex.toString(),
				headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
