package com.mystical.cloud.auth.utils;


import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;


/**
 * @Description:
 * @author: MysticalYcc
 * @Date: 2020/12/14
 */

public class EncryptUtil{

    /**
     * 加密算法AES
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * key的长度，Wrong key size: must be equal to 128, 192 or 256
     * 传入时需要16、24、36
     */
    private static final Integer KEY_LENGTH = 16 * 8;

    /**
     * 算法名称/加密模式/数据填充方式
     * 默认：AES/ECB/PKCS5Padding
     */
    private static final String ALGORITHMS = "AES/ECB/PKCS5Padding";

    /**
     * 后端AES的key，由静态代码块赋值
     */
    public static String key;

    static {
        key = getKey();
    }

    /**
     * 获取key
     */
    public static String getKey() {
        StringBuilder uid = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < KEY_LENGTH / 8; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type) {
                case 0:
                    //0-9的随机数
                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90之间为大写,获取大写随机
                    uid.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                    uid.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;
            }
        }
        return uid.toString();
    }

    /**
     * 加密
     *
     * @param content    加密的字符串
     * @param encryptKey key值
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        //设置Cipher对象
        Cipher cipher = Cipher.getInstance(ALGORITHMS,new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), KEY_ALGORITHM));

        //调用doFinal
        byte[] b = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));

        // 转base64
        return Base64.encodeBase64String(b);

    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        //base64格式的key字符串转byte
        byte[] decodeBase64 = Base64.decodeBase64(encryptStr);

        //设置Cipher对象
        Cipher cipher = Cipher.getInstance(ALGORITHMS,new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), KEY_ALGORITHM));

        //调用doFinal解密
        byte[] decryptBytes = cipher.doFinal(decodeBase64);
        return new String(decryptBytes);
    }



    public static void main(String[] args) throws Exception {
        com.cu.common.RSAEncrypt rsaEncrypt = new com.cu.common.RSAEncrypt();
       //RSAEncrypt.rsaPubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwesdNeHFSnUNteMPlAZwp5vo+3AOaEy3EjX+TVcjntc2Dk2yATFy49T654VksKjsu356LJv5Kjwx+deWUYlVoa7NliGGPEvao5zD3QWIC7OhLy47pw5XgcUw+3zJFl5xamE3aNcFns7UG4UMATL3XdjKrHB36THOe6ahaQjWqYQIDAQAB";
       // RSAEncrypt.rsaPriKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALB6x014cVKdQ214w+UBnCnm+j7cA5oTLcSNf5NVyOe1zYOTbIBMXLj1PrnhWSwqOy7fnosm/kqPDH515ZRiVWhrs2WIYY8S9qjnMPdBYgLs6EvLjunDleBxTD7fMkWXnFqYTdo1wWeztQbhQwBMvdd2MqscHfpMc57pqFpCNaphAgMBAAECgYEAnnFs9EHZRDnLKsLM5ZLfeMNWEw+G8FKduaTsUAQpvtZ7r9CxDjMA4FHWwh3u6Hb18ax09CvLDHeH2RXSS7lJQVrdtTHNjEjQ/i9XvOF3eE3Ba+IsNbc6HTJ6Ebe4Gz+ILQ/GAZ6QRo0//UiFf4lXg9nNk1Eox96EGiKtBMH4RV0CQQDme39nkBDbMG8HEmemoZbxjinfOgWrS1xbLBTjmX1tdyCeVrZThW9UZAQwet80ZUSmkl7zTV8wyS4qRBOiKMtXAkEAxASxhi1PC1eYy++j7klbdwsza3M9G23E481vnEduAxp0nPYRclkdt8ECBumI7u4SiItcVA/bxyRPNTU19iLdBwJBAKuLO5ZPKdyaPejbj/37wybiktOskc+edYz+UEXeVfaFdeejzmx3ohcDogKbUUnkHaaExtr7uV/MPOoEZUCKcF0CQHXlxMjJU7OkujhfKKBX6vWM/R5xr51hxnylwa2joN2o+d62egPm6eLt6cQyjrP26ZrLB3tgFnaOtj1whcZHbd0CQDQoDTeMPGdGttsZToR/B/Dv2woaO00k4h2IZi7fieQQzNWF7FaLKfJNtctoRXdZHCXgSFqUipuqtJHHL7zTf2k=";
        String request = "357915475975847";
        String pwd = "MIGfMA0GCSqGSIb3";
        String s = com.cu.common.RSAEncrypt.encryptByPublicKey(pwd.getBytes());
        System.out.println(URLEncoder.encode(s,"utf-8"));
        System.out.println(s);
        String s1 = encrypt(request, pwd);
        System.out.println(URLEncoder.encode(s1,"utf-8"));
        System.out.println(s1);
        byte[] bytes = Base64.decodeBase64(s);
        String s2 = com.cu.common.RSAEncrypt.decryptByPrivateKey(bytes);
        System.out.println(s2);
//        String result = "tiegzmx8plJY0PF/AEdkBP7ycOv2WYhSVQj4/K473ruB1InD4JzTmmBoh/eh9z69yRIXgnAGWzyT\r\nJHwiQWkYnnhd4yreQAffpP0LODdVrtKb/C4DSGxcxdjVEFePGhLtZLLGy5bWgS+HTGygoglO77QG\r\n0Qdwb6YYWmkUGjVLRY9GRN65+6foX86CJch69hUDWvAiVGr2yD40+hzzNBkOk1cEX2vGJhUASTMt\r\now+jPZVwWkSZw1dT1jJkjuN6Tqkd6ZtYEQ/a86tTw3LeKaF62fwtn01ize6wnf7AMQrbTjhQnx/j\r\nnMRPbx5TyoUAIIcFu/RgNz9I+cNb2p+k5F6gWUmc+iqP3r2aNrAn69VgDCE=";
//        System.out.println(decrypt(result,"MIGfMA0GCSqGSIb3"));

        System.out.println(decrypt("OpghRlOnfo9y912j1/vClFUrTCmR8l3w+cVsIGs9oHPAKVEasUAUhMYVa4W5UcBCX44zQrH131m4AP+WxS82TCZCaVfejqf17lyhi6PX7w48SGC3Mtp34/anQTFyp79htXCzirLaSlS7RIiCfwAOz68whU089C2Bo+f6eDMO1CaP9ZWX9OjjtMvsQbkJuZgtUOGCT8saIdLg3tEX5pEnQ790vHuQDdDRLLlJo64Pcw693VqIrI0NdXRbDmcF0qJ7o0WdTkHgpAFhdHtVWmi+E6LRpJ68ah+I9o84O5SVeBDZFLKSGInExlUzzMWAk39E/mtljISpVexA1upMikNp27PBz03JkmnureDJimtK2Is=",pwd));
        byte[] bytes1 = Base64.decodeBase64("WfR0yrkmOBZNJWGLXPZ3V/bmCfeVCoYYT3qIHNr74Km2BexRDwewsu4d//ufu6WVMKxBZc9+Ec7wIe+MRI+odFDGVScK3rsjO/TXDQx3zIA5RCo3bWeUT+8IeEMoY1mhMwJIYr4WWBX1MJ/+DSIpc1i5VIoq5Q78puBxkiFhe2Q=");
        System.out.println(com.cu.common.RSAEncrypt.decryptByPrivateKey(bytes1));

       // System.out.println(decryptHex("[YnTMF/zbUG2Bve0puoFvM9BchHm16UdzPcCC1e+WhaS9fZ3vxy89d5j6PvUEvi0V","123"));
    }
}