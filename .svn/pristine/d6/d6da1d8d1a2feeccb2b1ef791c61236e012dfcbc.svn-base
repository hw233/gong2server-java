package com.gamejelly.game.gong2.login.service.sdk.impl.haima;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HTTP CLIENT帮助类
 */
public class HttpClientUtil {

    private static final String POST_METHOD = "Post Method";
    private static final String UTF_8 = "UTF-8";
    private static final int TIME_OUT = 1000;
    private static final int SO_TIME_OUT = 1000;
    private static final int MAX_CONNECTIONS = 20;
    private static final int MAX_CONNECTIONS_PER_HOST = 10;
    private static HttpConnectionManager connectionManager;

    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
        connectionManagerParams.setConnectionTimeout(TIME_OUT);
        connectionManagerParams.setSoTimeout(SO_TIME_OUT);
        connectionManagerParams.setMaxTotalConnections(MAX_CONNECTIONS);
        connectionManagerParams.setDefaultMaxConnectionsPerHost(MAX_CONNECTIONS_PER_HOST);
        connectionManager.setParams(connectionManagerParams);
    }

    private HttpClientUtil() {
    }

    public static String doPostHttpClient(String url, Map<String, String> headerMap,
                                          Map<String, String> paramsMap) throws Exception {
        System.out.println("HttpClient请求开始 - " + POST_METHOD + " - " + url);
        long startTime = System.currentTimeMillis();
        String result = "";
        PostMethod method = null;
        try {
            method = new PostMethod(url);
            if (paramsMap != null && !paramsMap.isEmpty()) {
                System.out.println("POST 参数:" + paramsMap);
                Set<Map.Entry<String, String>> entries = paramsMap.entrySet();
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : entries) {
                    pairs.add(new NameValuePair(param.getKey(), param.getValue()));
                }
                method.setRequestBody(pairs.toArray(new NameValuePair[pairs.size()]));
            }
            if (headerMap != null && !headerMap.isEmpty()) {
                Set<Map.Entry<String, String>> heads = headerMap.entrySet();
                for (Map.Entry<String, String> head : heads) {
                    method.setRequestHeader(head.getKey(), head.getValue());
                }
            }
            method.getParams().setContentCharset(UTF_8);
            HttpClient httpClient = new HttpClient(connectionManager);
            httpClient.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                String charset = method.getResponseCharSet();
                InputStream ins = method.getResponseBodyAsStream();
                byte[] readBytes = new byte[1024];
                while (ins.read(readBytes) != -1) {
                    result += new String(readBytes, Charset.forName(charset));
                }
            }
        } catch (Exception e) {
            System.out.println("POST ERROR:" + e);
            throw e;
        } finally {
            if (method != null)
                method.releaseConnection();
            System.out.println("执行时间:" + (System.currentTimeMillis() - startTime));
        }
        return result;
    }

}
