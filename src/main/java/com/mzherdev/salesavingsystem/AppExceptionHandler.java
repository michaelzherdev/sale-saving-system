package com.mzherdev.salesavingsystem;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AppExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(AppExceptionHandler.class.getSimpleName());

	@ExceptionHandler(IllegalArgumentException.class)
	public String handle(IllegalArgumentException ex, Model model) {
		log.error("IllegalArgumentException:", ex.getMessage());
		model.addAttribute("msg", ex.getMessage());
		return "exception";
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, Model model) {
		log.error(ex.getClass().getSimpleName(), ex.getMessage());
		model.addAttribute("msg", ex.getMessage());
		return "exception";
	}
}
