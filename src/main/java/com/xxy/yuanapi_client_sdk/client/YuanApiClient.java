package com.xxy.yuanapi_client_sdk.client;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

import static com.xxy.yuanapi_client_sdk.utils.SignUtils.doSign;

public class YuanApiClient {

    private static String accessKey;
    private static String secretKey;

    public YuanApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }
    public static Map<String,String> getHeaderMap(String body,String method) {
        Map<String, String> map = new HashMap<String,String>();
        map.put("accessKey",accessKey);
        map.put("nonce", RandomUtil.randomNumbers(10));
        map.put("timestamp", String.valueOf(DateUtil.now()));
        body = URLUtil.encode(body, CharsetUtil.CHARSET_UTF_8);
        map.put("body",body);
        map.put("method",method);
        map.put("signature",doSign(body,secretKey));
        return map;
    }
    //通过平台调用
    public String invokeInterface(String url, String params, String method) {
        if ("GET".equals(method)) {
            String getURLWithParams = url + (params.isEmpty() ? "" : "?" + params);
            HttpResponse responseGet = HttpRequest.get(getURLWithParams)
                    .addHeaders(getHeaderMap(params,method))
                    .contentType("application/json")
                    .execute();
            return JSONUtil.formatJsonStr(responseGet.body());
        }else {
            HttpResponse httpResponse = HttpRequest.post(url)
                    .addHeaders(getHeaderMap(params, method))
                    .contentType("application/json")
                    .body(params)
                    .execute();
            return JSONUtil.formatJsonStr(httpResponse.body());
        }
    }
    //引入sdk调用
    public String mobileQuery(String mobile) {
        String url = "http://127.0.0.1:8103/api/mobile/query";
        String params = "mobile=" + mobile;
        return invokeInterface(url, params, "GET");
    }
}
