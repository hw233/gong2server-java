package com.gamejelly.game.gong2.login.web.page;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gamejelly.game.gong2.login.utils.BadRequestException;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;

@ControllerAdvice
public class PageControllerAdvice {

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Map<String, Object> handleException(HttpServletRequest request, Exception ex) {
		LoggerUtils.error(ex);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("error", ex.getMessage());
		param.put("request", request.getRequestURI());
		return param;
	}

	@ExceptionHandler({ BadRequestException.class, ServletRequestBindingException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, Object> handleBadRequestException(HttpServletRequest request, Exception ex) {
		LoggerUtils.error(ex);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("error", ex.getMessage());
		param.put("request", request.getRequestURI());
		return param;
	}

}
