package com.xxy.yuanapi_client_sdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具包
 */
public class SignUtils {

    public static String doSign(String body,String secretKey) {
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        String data = body + secretKey;
        return sha256.digestHex(data);
    }
}
