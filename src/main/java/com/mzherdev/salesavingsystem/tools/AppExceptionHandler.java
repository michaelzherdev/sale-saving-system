package com.mzherdev.salesavingsystem.tools;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AppExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(AppExceptionHandler.class.getSimpleName());

	@ExceptionHandler(IllegalArgumentException.class)
	public String handle(IllegalArgumentException ex, Model model) {
		log.error("IllegalArgumentException:", ex);
		model.addAttribute("msg", ex.getMessage());
		return "/error";
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, Model model) {
		log.error(ex.getClass().getSimpleName(), ex);
		model.addAttribute("msg", ex.getMessage() == null ? "Internal Server Error" : ex.getMessage());
		return "/error";
	}

	public static String convertToString(List<ObjectError> list) {
		return list.stream().map(ObjectError::toString).reduce("", String::concat);
	}
}
