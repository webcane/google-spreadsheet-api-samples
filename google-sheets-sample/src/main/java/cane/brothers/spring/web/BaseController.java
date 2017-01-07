package cane.brothers.spring.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cane.brothers.spring.exeption.DefaultExceptionAttributes;
import cane.brothers.spring.exeption.ExceptionAttributes;

public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest request) {
		logger.error("Exception", exception);

		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		Map<String, Object> responseBody = exceptionAttributes.getExceptionsAttributes(exception, request,
				HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
