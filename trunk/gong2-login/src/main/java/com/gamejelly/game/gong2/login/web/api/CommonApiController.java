package com.gamejelly.game.gong2.login.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class CommonApiController {

	@RequestMapping(value = "/**", produces = "application/json; charset=UTF-8")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Map<String, Object> handle(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("error", "Request not found!");
		param.put("request", request.getRequestURI());
		return param;
	}

}
