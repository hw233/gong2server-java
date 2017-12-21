package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gamejelly.game.gong2.login.meta.PlatformPayRecord;
import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_GFAN })
@Component("sdkGfanService")
public class SdkGfanService extends SdkBaseService implements SdkService {

	private String mPayKey = "764077643";
	private String mAppSecret = "c40914e88f6fedc7";
	private String mUid = "28505466";

	private static final String AUTH_URL = "http://api.gfan.com/uc1/common/verify_token";

	private Map<String, String> parseResp(HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputStream is = request.getInputStream();
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName != null && rootName.equals("response")) {
				NodeList cls = root.getChildNodes();
				for (int i = 0; i < cls.getLength(); i++) {
					Node childNode = cls.item(i);
					if (childNode instanceof Element) {
						ret.put(childNode.getNodeName(), childNode.getTextContent());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	/**
	 * 平台回调
	 * 
	 * @param requestParams
	 * @return
	 */
	public Object payCallback(Map<String, Object> params) {
		String time = FsGameLoginUtils.getMapString(params, "time", "");
		String sign = FsGameLoginUtils.getMapString(params, "sign", "");
		HttpServletRequest request = (HttpServletRequest) params.get("request");
		Map<String, String> otherMap = parseResp(request);
		String order_id = FsGameLoginUtils.getMapString(otherMap, "order_id", ""); // 我们的订单号

		LoggerUtils.info("gfan payCallback", params);

		String mySign = FsGameLoginUtils.md5low(mUid + time);
		String resultTemp = "<response><ErrorCode>{0}</ErrorCode><ErrorDesc>pay</ErrorDesc></response>";
		int resultSt = 0;
		try {
			if (sign.equals(mySign)) { // 验签成功
				PlatformPayRecord ppr = platformPayRecordService.getByOrderSn(order_id);
				if (ppr != null) {
					if (ppr.getServerState() != FsGameLoginConst.AS_SERVER_STATE_GIVED_ITEM) {
						platformPayRecordService.updateServerState(ppr.getId(),
								FsGameLoginConst.AS_SERVER_STATE_VERIFIED);
						doGivePayItem(ppr);
					}
				}
				resultSt = 1;
			} else {
				LoggerUtils.info("gfan 验签失败", mySign, sign);
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			resultSt = 0;
		}
		return MessageFormat.format(resultTemp, resultSt);
	}

	/**
	 * 登录
	 * 
	 * @param requestParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {
		String ticket_id = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");
		String url = AUTH_URL + "?token=" + ticket_id;
		LoggerUtils.info("gfan login check id url =", url);
		SimpleHttpRequest shr = SimpleHttpRequest.createGet(url);
		String result = shr.sendGetString();
		Map<String, Object> rm = GsonFactory.getDefault().fromJson(result, Map.class);
		int code = FsGameLoginUtils.getMapInteger(rm, "resultCode", -1);
		if (code != 1) {
			return new PlatformRetData(false, rm);
		}
		return new PlatformRetData(true, rm);
	}

}
