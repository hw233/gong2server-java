package com.gamejelly.game.gong2.login.web.page;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gamejelly.game.gong2.login.meta.Notice;
import com.gamejelly.game.gong2.login.service.NoticeService;
import com.gamejelly.game.gong2.login.service.sdk.InMobiService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;

@Controller
public class PageController {
	@Autowired
	private NoticeService noticeService;

	@Autowired
	private InMobiService inMobiService;

	@RequestMapping(value = "/notice")
	public ModelAndView notice(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		inMobiService.active(params);
		Notice n = noticeService.getRecentlyNotice();
		if (n == null) {
			n = new Notice();
			n.setContent("");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("notice", n);
		return new ModelAndView("/notice", param);
	}

	@RequestMapping(value = "/clickInMobi")
	public ModelAndView clickInMobi(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		inMobiService.click(params);
		return new ModelAndView("redirect:https://itunes.apple.com/cn/app/feng-shen-lai-le/id981761694?clickInMobi");
	}

}
