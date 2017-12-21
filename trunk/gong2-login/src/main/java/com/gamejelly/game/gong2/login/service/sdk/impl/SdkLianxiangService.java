package com.gamejelly.game.gong2.login.service.sdk.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gamejelly.game.gong2.login.service.sdk.PlatformRetData;
import com.gamejelly.game.gong2.login.service.sdk.SdkGroup;
import com.gamejelly.game.gong2.login.service.sdk.SdkService;
import com.gamejelly.game.gong2.login.service.sdk.impl.lianxiang.CpTransSyncSignValid;
import com.gamejelly.game.gong2.login.utils.FsGameLoginUtils;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.gamejelly.game.gong2.login.utils.SdkConst;
import com.hadoit.game.common.framework.http.SimpleHttpRequest;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;

@SdkGroup({ SdkConst.CC_SDK_ANDROID_LIANXIANG })
@Component("sdkLianxiangService")
public class SdkLianxiangService extends SdkBaseService implements SdkService {

	private String appKeyId = "1612290025818.app.ln";

	private String appKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIKLyggE4LYD9hwNa9z0swgSsdeWe1azQ2M+FPDQRobYNxjY3DWauLyrnSSMIrIP5r3m9DOe4xvPoj69Yq97q3mnPrbLAVJYvdAhXpZEGB0QeRrlxmqLZNtzMCKLrQlO+9s8IOLC1XxRV6ooXnU9832Vft7OYxSebF7MGDiA9QYZAgMBAAECgYBCHlz4DMhnR8BZ2u5bndAjcZyDbaMQeYDS/fmLMmHWICJfwk5eE/wnCuBvR063UthyLExY7MtoJZSuJCucZ42kMRm7mU9mSqZgdGbONWUuPfWUO75aYBUbBb3RAIdePa35UN1lHrKrwlnVShU5CttDdIGLYJiIbdwb8PgVNyjCkQJBAPftgkNpJac/O6CrlS8DD4Q8bsibvxSjUXqGakXjPHk+gSTS2QflDD3s/Gv4iRGWeEnyxrj3dqrZQh8RS76UM0UCQQCGy+XBEVknxIoB/TOBQxVvw5HTzRftv3yTch1pSTJMv8YPI7o+q3goiV6Neq79Wn8Nu1dwxng9/u8SSqWIY2rFAkBIOqypoPd0Syq++14CDT03BDguP5milJYHQSOy6qnSlNAg/0B1WViArgs4FXcxZYnz2UPl3Up4KcVmsa5Efn6VAkByoSp9CPe8bdK+5kfKgPXV2YZFi3/x2oliCgUg5/IyelM/xK45ZTAFqOwTfGUbZr3tO4eci+rtLcK412n1ljXRAkEA8uiXXC6VVVN+ZuWyf/j5KUagUZbMKRUm2FjdsC9+BE9cpSvmJHP6EnTCsPsaSUCq4ZGtAVmX0XhpOdsRcE3eig==";

	private String tokenCheckUrl = "http://passport.lenovo.com/interserver/authen/1.2/getaccountid";

	@SuppressWarnings("unchecked")
	@Override
	public Object payCallback(Map<String, Object> requestParams) {
		LoggerUtils.info("lianxiang payCallback", requestParams);
		String transdata = DataUtils.getMapString(requestParams, "transdata", "");
		String sign = DataUtils.getMapString(requestParams, "sign", "");
		String result = "FAILURE";
		try {
			boolean checkFlag = CpTransSyncSignValid.validSign(transdata, sign, appKey);
			if (checkFlag) {
				/* 4、业务处理 */
				LoggerUtils.info("liangxiang info:支付结果通知内容验签成功");
				Map<String, Object> tranMap = GsonFactory.getDefault().fromJson(transdata, Map.class);
				processOrder(DataUtils.getMapString(tranMap, "exorderno", ""),
						DataUtils.getMapString(tranMap, "money", ""), false);
				result = "SUCCESS";
			} else {
				LoggerUtils.info("liangxiang error:支付结果通知内容验签失败");
			}
		} catch (Exception e) {
			LoggerUtils.error(e);
			result = "FAILURE";
		}

		return result;
	}

	@Override
	public PlatformRetData loginAuth(Map<String, Object> requestParams) {

		String SessionId = FsGameLoginUtils.getMapString(requestParams, "SessionId", "");

		StringBuilder getUrl = new StringBuilder(tokenCheckUrl);
		getUrl.append("?lpsust=");
		getUrl.append(SessionId);
		getUrl.append("&realm=");
		getUrl.append(appKeyId);

		SimpleHttpRequest shr = SimpleHttpRequest.createGet(getUrl.toString());

		// <?xml version="1.0" encoding="UTF-8"?>
		// <IdentityInfo>
		// <AccountID>10038780956</AccountID>
		// <Username>18158183300</Username>
		// <DeviceID>357506051777220</DeviceID>
		// <verified>1</verified>
		// </IdentityInfo>

		// <?xml version="1.0" encoding="UTF-8"?>
		// <Error>
		// <Code>USS-0121</Code>
		// <Timestamp>2014-07-01T17:51:49+08:00</Timestamp> </Error>

		String resultGet = shr.sendGetString();
		boolean result = false;
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(resultGet.getBytes());
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName != null && rootName.equals("IdentityInfo")) {
				NodeList accountIDs = root.getElementsByTagName("AccountID");
				for (int i = 0; i < accountIDs.getLength(); i++) {
					Node childNode = accountIDs.item(i);
					if (childNode instanceof Element) {
						String text = childNode.getTextContent();
						requestParams.put("name", text);
						result = true;
						break;
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new PlatformRetData(result, null);
	}

}
