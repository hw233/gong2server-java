package com.gamejelly.game.gong2.login.service.sdk;

import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.InMobiDataDao;
import com.gamejelly.game.gong2.login.meta.InMobiData;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.framework.utils.SimpleWebUtils;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@Transactional
@Component("inMobiService")
public class InMobiService {
	private static String INMOBI_KEY = "f9547762fd1ba245498da6602b367ffe";
	private static String propertyId = "0d346b902cb14995b5ae26632acb0bbc";

	private static String trackingPartner = "fsgame";

	private static String callbackUrl = "http://advertiser.inmobiapis.com/tpce/v1/events/download?trackingPartner={0}&propertyId={1}&impId={2}";

	@Autowired
	private InMobiDataDao inMobiDataDao;

	public void click(Map<String, Object> requestParams) {
		String clickId = FsGameLoginUtils.getMapString(requestParams, "clickId", "");
		HttpServletRequest request = (HttpServletRequest) requestParams.get("request");
		String ip = SimpleWebUtils.getRequestIp(request);
		String ua = SimpleWebUtils.getUserAgent(request);
		LoggerUtils.info("inmobi click", clickId, ip, ua);

		InMobiData inMobiData = inMobiDataDao.getNotActiveInMobiDataRecently(ip, ua);
		long curTime = System.currentTimeMillis();
		if (inMobiData == null) {
			inMobiData = new InMobiData();
			inMobiData.setClickId(clickId);
			inMobiData.setIp(ip);
			inMobiData.setUa(ua);
			inMobiData.setIdfa("");
			inMobiData.setClickTime(curTime);

			inMobiDataDao.addObject(inMobiData);
		}
	}

	@SuppressWarnings("unchecked")
	public void active(Map<String, Object> requestParams) {
		String idfa = FsGameLoginUtils.getMapString(requestParams, "idfa", "");
		String sign = FsGameLoginUtils.getMapString(requestParams, "sign", "");
		if (StringUtils.isBlank(idfa) || StringUtils.isBlank(sign)) {
			return;
		}
		HttpServletRequest request = (HttpServletRequest) requestParams.get("request");
		String ip = SimpleWebUtils.getRequestIp(request);
		String ua = SimpleWebUtils.getUserAgent(request);
		LoggerUtils.info("inmobi active", idfa, sign, ip, ua);

		String mySign = FsGameLoginUtils.md5low(idfa + INMOBI_KEY);
		if (!mySign.equalsIgnoreCase(sign)) {
			LoggerUtils.info("inmobi sign error", mySign, sign);
			return;
		}

		// 判断是否有idfa已经激活过了
		InMobiData oldInMobiData = inMobiDataDao.getInMobiDataByIdfa(idfa);
		if (oldInMobiData != null) {
			LoggerUtils.info("inmobi idfa is exists! idfa=", idfa);
			return;
		}

		InMobiData inMobiData = inMobiDataDao.getNotActiveInMobiDataRecently(ip, ua);
		if (inMobiData == null) {
			LoggerUtils.info("inmobi not found");
			return;
		}

		// 判断窗口期是否在7天内
		long now = System.currentTimeMillis();
		long last7Day = now - DateUtils.MILLIS_PER_DAY * 7;
		if (inMobiData.getClickTime() < last7Day) {
			LoggerUtils.info("inmobi expired! clickTime=",
					DataUtils.formatDate(inMobiData.getClickTime(), "yyyy-MM-dd HH:mm:ss"), ", now=",
					DataUtils.formatDate(now, "yyyy-MM-dd HH:mm:ss"));
			return;
		}

		String url = MessageFormat.format(callbackUrl, trackingPartner, propertyId, inMobiData.getClickId());
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
		String resultGet = shr.sendGetString();
		LoggerUtils.info("inmobi callback", url, resultGet);

		Map<String, Object> rm = GsonFactory.getDefault().fromJson(resultGet, Map.class);
		String status = FsGameLoginUtils.getMapString(rm, "status", "");
		if ("OK".equalsIgnoreCase(status)) {
			long curTime = System.currentTimeMillis();
			inMobiData.setIdfa(idfa);
			inMobiData.setActiveTime(curTime);
			inMobiDataDao.updateObject(inMobiData);
			LoggerUtils.info("inmobi active success");
		}
	}
}
