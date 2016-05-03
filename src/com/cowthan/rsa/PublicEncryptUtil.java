package com.cowthan.rsa;


import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * RSA解密
 */
public class PublicEncryptUtil {

//    public static String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC51XaAwBaqDHNWubU3BU1OTJRq"
//								+"bFYIMCGHUCSMjt1pcxULEGpr3G262nfYI3iJoSzo7qXhiCulfmG88yBfbfMP4QFl"
//								+"2/P4Lr8Pdi36NvftdKMVIiRHy4Ho16gFsozm9LA+h3vnQMp2OAEQ0KmPPO3KZ5lf"
//								+"sJASiODaUXYac+G1nwIDAQAB";
    public static String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC51XaAwBaqDHNWubU3BU1OTJRq"
+"bFYIMCGHUCSMjt1pcxULEGpr3G262nfYI3iJoSzo7qXhiCulfmG88yBfbfMP4QFl"
+"2/P4Lr8Pdi36NvftdKMVIiRHy4Ho16gFsozm9LA+h3vnQMp2OAEQ0KmPPO3KZ5lf"
+"sJASiODaUXYac+G1nwIDAQAB";

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String content)
            throws Exception {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKeyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
        }

        PublicKey key = null;
        try {
            key = keyFactory.generatePublic(spec);
        } catch (InvalidKeySpecException e2) {
            e2.printStackTrace();
        }
        byte[] encryptedText = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key);

            //  encryptedText = cipher.doFinal(content.getBytes());
            byte[] data = content.getBytes();
            for (int i = 0; i < data.length; i += 99) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,
                        i + 99));
                encryptedText = ArrayUtils.addAll(encryptedText, doFinal);
            }
            return encryptedText;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey2(String content)
            throws Exception {
        return new BASE64Encoder().encode(encryptByPublicKey(URLEncoder.encode(content, "utf-8")));
    }
    

}
