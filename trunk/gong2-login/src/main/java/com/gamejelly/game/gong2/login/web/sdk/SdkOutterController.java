package com.gamejelly.game.gong2.login.web.sdk;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamejelly.game.gong2.login.meta.GameAccount;
import com.gamejelly.game.gong2.login.service.GameAccountService;
import com.gamejelly.game.gong2.login.service.SdkServiceGroups;
import com.gamejelly.game.gong2.login.service.UserCreditService;
import com.gamejelly.game.gong2.login.service.UserCreditService2;
import com.gamejelly.game.gong2.login.service.sdk.IdfaService;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.BadRequestException;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.hadoit.game.common.lang.Pair;

/**
 * 暴露给客户端和第三方sdk调用
 * 
 * @author apple
 * 
 */
@Controller
public class SdkOutterController {

	@Autowired
	private SdkServiceGroups sdkServiceGroups;

	@Autowired
	private IdfaService idfaService;

	@Autowired
	private UserCreditService userCreditService;
	@Autowired
	private UserCreditService2 userCreditService2;
	@Autowired
	private GameAccountService gameAccountService;

	@RequestMapping(value = "/outter/payCallback/{sdkId}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object payCallback(@PathVariable("sdkId") int sdkId, HttpServletRequest request,
			HttpServletResponse response) {
		// 不同渠道需要不同的url,或者带sdkId
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		SdkService service = sdkServiceGroups.getService(sdkId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		params.put("sdkId", sdkId);
		Object r = service.payCallback(params);
		LoggerUtils.logPayCallback("payCallback", r);

		return r;
	}

	@RequestMapping(value = "/outter/addItem/{sdkId}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object addItemCallback(@PathVariable("sdkId") int sdkId, HttpServletRequest request,
			HttpServletResponse response) {
		// 不同渠道需要不同的url,或者带sdkId
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		SdkService service = sdkServiceGroups.getService(sdkId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		params.put("sdkId", sdkId);
		Object r = service.addItemCallback(params);
		LoggerUtils.info("addItemCallback", r);

		return r;
	}

	@RequestMapping(value = "/outter/subItem/{sdkId}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object subItemCallback(@PathVariable("sdkId") int sdkId, HttpServletRequest request,
			HttpServletResponse response) {
		// 不同渠道需要不同的url,或者带sdkId
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		SdkService service = sdkServiceGroups.getService(sdkId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		params.put("sdkId", sdkId);
		Object r = service.subItemCallback(params);
		LoggerUtils.info("subItemCallback", r);

		return r;
	}

	@RequestMapping(value = "/outter/getUser/{sdkId}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getUserCallback(@PathVariable("sdkId") int sdkId, HttpServletRequest request,
			HttpServletResponse response) {
		// 不同渠道需要不同的url,或者带sdkId
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		SdkService service = sdkServiceGroups.getService(sdkId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		params.put("sdkId", sdkId);
		Object r = service.getUserCallback(params);
		LoggerUtils.info("getUserCallback", r);

		return r;
	}

	@RequestMapping(value = "/outter/getCcu/{sdkId}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getCcuCallback(@PathVariable("sdkId") int sdkId, HttpServletRequest request,
			HttpServletResponse response) {
		// 不同渠道需要不同的url,或者带sdkId
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		SdkService service = sdkServiceGroups.getService(sdkId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		params.put("sdkId", sdkId);
		Object r = service.getCcuCallback(params);
		LoggerUtils.info("getCcuCallback", r);

		return r;
	}

	@RequestMapping(value = "/outter/idfa-query", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object idfaQuery(@RequestParam("appid") String appid, @RequestParam("idfa") String idfa) {
		LoggerUtils.info("idfaQuery", appid, idfa);
		Object r = idfaService.idfaQueryForType(appid, idfa, IdfaService.IDFA_TYPE_QIANKA);
		LoggerUtils.info("idfaQuery result", r);
		return r;
	}

	@RequestMapping(value = "/outter/type-idfa-query/{idType}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object typeIdfaQuery(@PathVariable("idType") int idType, @RequestParam("appid") String appid,
			@RequestParam("idfa") String idfa) {
		LoggerUtils.info("type-idfa-query", appid, idfa, idType);
		Object r = idfaService.idfaQueryForType(appid, idfa, idType);
		LoggerUtils.info("type-idfa-query result", r);
		return r;
	}

	@RequestMapping(value = "/outter/getXmfbData/{sdkId}", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getXmfbData(@PathVariable("sdkId") int sdkId, HttpServletRequest request,
			HttpServletResponse response) {
		// 不同渠道需要不同的url,或者带sdkId
		Map<String, Object> params = FsGameLoginUtils.getRequestParameterMap(request, response);
		SdkService service = sdkServiceGroups.getService(sdkId);
		if (service == null) {
			throw new BadRequestException("Paramter(sdkId) is illegal");
		}
		Object r = service.getXmfbDataCallback(params);
		LoggerUtils.info("getXmfbDataCallback", r);

		return r;
	}

	@RequestMapping(value = "/outter/getShimingData", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getShimingData(@RequestParam("name") String name, @RequestParam("idCard") String idCard,
			@RequestParam("retType") int retType, @RequestParam(value = "callback", required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		name = FsGameLoginUtils.decodeParameter(name);
		Object r = userCreditService.getShimingData(name, idCard, retType, callback);
		LoggerUtils.info("getShimingData", r);
		return r;
	}

	@RequestMapping(value = "/outter/getShimingData2", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Object getShimingData2(@RequestParam("accountName") String accountName, @RequestParam("name") String name,
			@RequestParam("idCard") String idCard, HttpServletRequest request, HttpServletResponse response) {
		name = FsGameLoginUtils.decodeParameter(name);

		GameAccount account = gameAccountService.getGameAccountByAccount(accountName);
		if (account == null) {
			return Pair.newInstance(FsGameLoginConst.ERROR_REGIST_CAPTCHA_NOT_EXISTS, null);
		}

		GameAccount accountByIdCard = gameAccountService.getGameAccountByIdCardNo(idCard);
		if (accountByIdCard != null) {
			return Pair.newInstance(FsGameLoginConst.ERROR_BIND_ID_CARD_NO_BINDED, null);
		}

		Object r = userCreditService2.getShimingData2(name, idCard);
		if (r.equals("1")) {
			account.setName(name);
			account.setIdCardNo(idCard);
			gameAccountService.updateGameAccount(account);
		} else {
			return Collections.singletonMap("retCode", (Object) FsGameLoginConst.ERROR_BIND_ID_CARD_NAME_ERROR);// 验证错误
		}
		LoggerUtils.info("getShimingData", r);
		int retCode = FsGameLoginConst.CODE_OK;
		return Collections.singletonMap("retCode", retCode);//

	}

}
