package com.gamejelly.game.gong2.login.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AdminControllerAdvice {

	@ExceptionHandler(Throwable.class)
	public ModelAndView handleException(HttpServletRequest request, Exception ex) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("exception", ex.getMessage());
		param.put("request", request.getRequestURI());
		return new ModelAndView("springError", param);
	}

}
