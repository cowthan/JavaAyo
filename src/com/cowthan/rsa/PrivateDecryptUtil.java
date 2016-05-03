package com.cowthan.rsa;


import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

import Decoder.BASE64Decoder;

/**
 * RSA解密
 */
public class PrivateDecryptUtil {

    // 私钥
//    private static String privatekeystr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOQXwTvB747/sprA"
//    		+"7VQUVcNstTj+MDWc77bdk122b7dYxQ+ah/HzpRQD9eS1Qsy0zn/VkAIXWlKbcyNf"
//    		+"2GvQT64j6IP/pzoQXSYnuJ2EIAXBKwEYTl0lQ20MWyxgM0pFvW4EH0CYlh+det0T"
//    		+"MNLCSLvYWS1COpSZXi6U1rfS+dEdAgMBAAECgYBCMW6fc/GckzH23SRCsUAOXZ7d"
//    		+"8k51UJz0easJmCclkzplA9iN68ItY9TZUY0YMSR4TrbjFHne3UP1sRvyddqZR3xd"
//    		+"9cj+pX3WmRzj8Ab0v2zwMbyjEYpdu9W+mzlaX35p+sQNyzdu6AFLZrzHZRpJIt1p"
//    		+"tRjuF2ramv/NjrM8wQJBAPjHlAcPcT8mlMtyf5xdn+Y1uWXxK7WP/GWJ51a7FdKJ"
//    		+"/uBKna+phLpTXG1zNdKXRvz+m/j9/7gl6ntV3dQ8oi0CQQDqtnl3F7tFC5oAqDNn"
//    		+"A4V+woNiPNLqj1zqjmuWZg1uTeP8WfBbObQbGxLB+lEdz+1dPNAOxTNGZqnA0mZI"
//    		+"wnCxAkEA2KdmYKewJiEBYBQ80f4vLJsI9KCNQjc8xWjNkRiorowC7W3N2Zc1wdar"
//    		+"HZ5B9z5LUjShvLvnIYXTqMlkwFp1mQJAbw4R4OCwshWwM3ligO5wnq6ofbXr0iIt"
//    		+"Q6pJi6sT1cozAncHvBJMfrrLoE6xTNrSI9ifVS3HehsfXOXwkaK2sQJBAJwG8LUu"
//    		+"XXswur1YQUvNlY0JAIxpdcMcKlU7+T6N7FD6SfObbG1r8JHpQYRGKKC/XXoUrXkT"
//    		+"gTaZvLoU5DFFSz0=";
    		private static String privatekeystr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOQXwTvB747/sprA"
+"7VQUVcNstTj+MDWc77bdk122b7dYxQ+ah/HzpRQD9eS1Qsy0zn/VkAIXWlKbcyNf"
+"2GvQT64j6IP/pzoQXSYnuJ2EIAXBKwEYTl0lQ20MWyxgM0pFvW4EH0CYlh+det0T"
+"MNLCSLvYWS1COpSZXi6U1rfS+dEdAgMBAAECgYBCMW6fc/GckzH23SRCsUAOXZ7d"
+"8k51UJz0easJmCclkzplA9iN68ItY9TZUY0YMSR4TrbjFHne3UP1sRvyddqZR3xd"
+"9cj+pX3WmRzj8Ab0v2zwMbyjEYpdu9W+mzlaX35p+sQNyzdu6AFLZrzHZRpJIt1p"
+"tRjuF2ramv/NjrM8wQJBAPjHlAcPcT8mlMtyf5xdn+Y1uWXxK7WP/GWJ51a7FdKJ"
+"/uBKna+phLpTXG1zNdKXRvz+m/j9/7gl6ntV3dQ8oi0CQQDqtnl3F7tFC5oAqDNn"
+"A4V+woNiPNLqj1zqjmuWZg1uTeP8WfBbObQbGxLB+lEdz+1dPNAOxTNGZqnA0mZI"
+"wnCxAkEA2KdmYKewJiEBYBQ80f4vLJsI9KCNQjc8xWjNkRiorowC7W3N2Zc1wdar"
+"HZ5B9z5LUjShvLvnIYXTqMlkwFp1mQJAbw4R4OCwshWwM3ligO5wnq6ofbXr0iIt"
+"Q6pJi6sT1cozAncHvBJMfrrLoE6xTNrSI9ifVS3HehsfXOXwkaK2sQJBAJwG8LUu"
+"XXswur1YQUvNlY0JAIxpdcMcKlU7+T6N7FD6SfObbG1r8JHpQYRGKKC/XXoUrXkT"
+"gTaZvLoU5DFFSz0=";

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data) throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(privatekeystr);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
        }
        PrivateKey key = null;
        try {
            key = keyFactory.generatePrivate(spec);
        } catch (InvalidKeySpecException e2) {
            e2.printStackTrace();
        }
        byte[] encryptedText = null;
        try {
            Cipher cipher = null;

            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.DECRYPT_MODE, key);

            //	return cipher.doFinal(data);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i += 128) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,
                        i + 128));
                sb.append(new String(doFinal));
            }
            String dataReturn = sb.toString();
            return dataReturn.getBytes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * 解密返回的数据
     *
     * @param srcContent
     * @return
     */
    public static String decryptByPrivateKey(String srcContent) {
        byte[] bb;
        byte[] array = null;
        try {
            bb = new BASE64Decoder().decodeBuffer(srcContent);
            array = PrivateDecryptUtil.decryptByPrivateKey(bb);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将解密信息进行编码转换
        String acceptContent = new String(array);
        try {
            acceptContent = java.net.URLDecoder.decode(acceptContent, "UTF-8");
            acceptContent = new String(acceptContent.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return acceptContent;
    }
    
}
