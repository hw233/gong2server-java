package com.gamejelly.game.gong2.login.web.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gamejelly.game.gong2.login.meta.Notice;
import com.gamejelly.game.gong2.login.meta.Page;
import com.gamejelly.game.gong2.login.meta.ServerInfo;
import com.gamejelly.game.gong2.login.meta.UserPrincipal;
import com.gamejelly.game.gong2.login.net.GasAdminClientManager;
import com.gamejelly.game.gong2.login.service.AdminService;
import com.gamejelly.game.gong2.login.service.NoticeService;
import com.gamejelly.game.gong2.login.service.ServerInfoService;
import com.gamejelly.game.gong2.login.service.SystemDataService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.hadoit.game.common.framework.web.security.SecurityContextHolder;
import com.hadoit.game.common.framework.web.security.UserDetails;

@Controller
public class AdminController extends AbstractController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private ServerInfoService serverInfoService;

	@Autowired
	private GasAdminClientManager gasAdminClientManager;

	@Autowired
	private SystemDataService systemDataService;

	@Autowired
	private NoticeService noticeService;

	private static String REDIRECT_LIST_SRV_URI = "redirect:manager?module=server&action=listsrv";

	@RequestMapping(value = { "/", "/login" })
	public ModelAndView index(@RequestParam(value = "redirect", required = false) String redirect) {
		redirect = FsGameLoginUtils.decodeParameter(redirect);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("redirect", redirect);
		return getCommonModelAndView("/index", param);
	}

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public ModelAndView doLogin(@RequestParam("username") String username, @RequestParam("passwd") String passwd,
			@RequestParam(value = "redirect", required = false) String redirect, HttpServletRequest request) {
		LoggerUtils.info("doLogin username=" + username);
		if (!adminService.checkAdmin(username, passwd)) {
			throw new RuntimeException("账号或密码错误!");
		}
		String role = "admin";
		UserDetails userDetails = new UserDetails(username, passwd, new String[] { role }, new UserPrincipal(username));
		SecurityContextHolder.setSessionAuthInfo(userDetails);

		// 设置session过期时间
		HttpSession ses = request.getSession();
		ses.setMaxInactiveInterval(-1);

		if (StringUtils.isBlank(redirect)) {
			return new ModelAndView(REDIRECT_LIST_SRV_URI);
		} else {
			return new ModelAndView("redirect:" + redirect);
		}
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout() {
		SecurityContextHolder.clearSessionAuthInfo();

		return new ModelAndView("redirect:login");
	}

	@RequestMapping(value = "/manager", params = { "module=server", "action=listsrv" })
	public ModelAndView listSrv(@RequestParam(value = "curPage", defaultValue = "1") int curPage,
			@RequestParam(value = "limit", defaultValue = FsGameLoginConst.DEFAULT_SRV_LIST_SIZE + "") int limit) {
		LoggerUtils.info("listSrv limit=" + limit, "curPage=" + curPage);
		List<ServerInfo> all = serverInfoService.getAllServer(limit, getOffset(limit, curPage));
		int total = serverInfoService.getAllServerCount();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("srvs", all);
		param.put("page", new Page(total, limit, curPage));
		return getCommonModelAndView("/listsrv", param);
	}

	@RequestMapping(value = "/manager", params = { "module=server", "action=createsrv" })
	public ModelAndView createSrv(@RequestParam(value = "id", defaultValue = "0") long id) {
		ServerInfo si = null;
		if (id > 0) {
			si = serverInfoService.getServerInfo(id);
		} else {
			si = new ServerInfo();
			si.setPort(9101);
			si.setAdminPort(9102);
			si.setNewSrv(true);
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("si", si);
		return getCommonModelAndView("/createsrv", param);
	}

	@RequestMapping(value = "/manager", params = { "module=server", "action=docreatesrv" }, method = { RequestMethod.POST })
	public ModelAndView doCreateSrv(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		ServerInfo si = FsGameLoginUtils.parseRequestObject(request, ServerInfo.class);
		if (si.getId() <= 0) {
			si.setState(FsGameLoginConst.SERVER_STATE_CLOSE);
			si.setCreateTime(System.currentTimeMillis());
			boolean ret = serverInfoService.addServer(si);
			if (!ret) {
				throw new RuntimeException("区号和组重复了!");
			}
		} else {
			ServerInfo oldSi = serverInfoService.getServerInfoForUpdate(si.getId());
			si.setState(oldSi.getState());
			si.setCreateTime(oldSi.getCreateTime());
			serverInfoService.updateServer(si);
		}
		gasAdminClientManager.connectGasClient(si);
		return getCommonModelAndView(REDIRECT_LIST_SRV_URI, param);
	}

	@RequestMapping(value = "/manager", params = { "module=server", "action=delsrv" }, method = { RequestMethod.POST })
	public ModelAndView delSrv(@RequestParam(value = "id", defaultValue = "0") long id) {
		if (id > 0) {
			ServerInfo si = serverInfoService.deleteServer(id);
			gasAdminClientManager.removeConnect(si.getServerId(), si.getOprGroup());
		}
		return getCommonModelAndView(REDIRECT_LIST_SRV_URI, null);
	}

	@RequestMapping(value = "/manager", params = { "module=register", "action=registerstate" })
	public ModelAndView registerState(@RequestParam(value = "val", defaultValue = "", required = false) String value,
			HttpServletRequest request) {
		if (value != null && !value.isEmpty()) {
			if (value.equals("1")) {
				systemDataService.setOpenRegist(true);
			} else if (value.equals("0")) {
				systemDataService.setOpenRegist(false);
			}
		}

		if (systemDataService.isOpenRegist()) {
			value = "1";
		} else {
			value = "0";
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("val", value);
		return getCommonModelAndView("/registerstate", param);
	}

	class CompareNotice implements Comparator<Notice> {
		@Override
		public int compare(Notice o1, Notice o2) {
			if (o1.getCreateTime() < o2.getCreateTime()) {
				return 1;
			}
			return -1;
		}
	}

	@RequestMapping(value = "/manager", params = { "module=notice", "action=addnotice" })
	public ModelAndView addNotice(@RequestParam(value = "newcontent", defaultValue = "", required = true) String text,
			@RequestParam(value = "cntId", defaultValue = "", required = false) String cntId, HttpServletRequest request) {

		if (!text.equals("")) {
			if (cntId.isEmpty()) {
				noticeService.addNewNotice(text);
			} else {
				int id = Integer.valueOf(cntId);
				noticeService.updateNotice(id, text);
			}
		}
		List<Notice> listNotice = noticeService.getNoticeList();
		CompareNotice compareNotice = new CompareNotice();
		Collections.sort(listNotice, compareNotice);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("newcontent", text);
		param.put("listnotice", listNotice);
		return getCommonModelAndView("/addnotice", param);
	}

	@RequestMapping(value = "/manager", params = { "module=notice", "action=delnotice" })
	public ModelAndView delNotice(@RequestParam(value = "id", defaultValue = "", required = false) String id,
			HttpServletRequest request) {
		noticeService.delNotice(Integer.valueOf(id));
		List<Notice> listNotice = noticeService.getNoticeList();
		CompareNotice compareNotice = new CompareNotice();
		Collections.sort(listNotice, compareNotice);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("listnotice", listNotice);
		return getCommonModelAndView("/addnotice", param);
	}
}
