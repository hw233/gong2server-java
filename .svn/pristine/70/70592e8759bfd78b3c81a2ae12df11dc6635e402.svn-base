package com.gamejelly.game.gong2.login.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;

public abstract class AbstractController {

	public ModelAndView getCommonModelAndView(String path, Map<String, Object> param) {
		if (param == null) {
			param = new HashMap<String, Object>();
		}

		FsGameLoginUtils.setStaticModel(FsGameLoginUtils.class, param);
		FsGameLoginUtils.setStaticModel(FsGameLoginConst.class, param);

		return new ModelAndView(path, param);
	}

	protected int getOffset(int limit, int curPage) {
		if (curPage < 1)
			curPage = 1;
		return (curPage - 1) * limit;
	}

}
