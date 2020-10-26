package com.shujia.hbase;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Demo8Hash {

    public static void main(String[] args) {

        /**
         * mdn5  恶意将任意长度的字符串转换成固定长度的字符串，不可逆  （加密）
         *
         */

        String java = "176001java95921";

        //3565e4bb6f1be2031b4a6f97e63bb460
        //17b60f0fcd789d406182b6e86e14a146
        //d8e4a3de0b931e2927d6cd60176e8b69

        System.out.println(stringToMD5(java));


    }

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

}
