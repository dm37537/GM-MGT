package com.mokylin.gm.security;

import com.mokylin.gm.utils.Exceptions;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 支持SHA-1、MD5消息摘要的工具类
 *
 * 返回ByteSource，可进一步被编码为Hex, Base64等等
 *
 * Created by Administrator on 2014/6/18.
 */
public class Digests {

    public static final String SHA1 = "SHA-1";
    public static final String MD5 = "MD5";

    private static SecureRandom random = new SecureRandom();

    /**
     * 对输入字符串进行sha1散列
     */
    public static  byte[] sha1(byte[] input) {
        return digest(input, SHA1, null, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt) {
        return digest(input, SHA1, salt, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        return digest(input, SHA1, salt, iterations);
    }

    /**
     * 对输入字符串进行md5散列
     */
    public static byte[] md5(byte[] input) {
        return  digest(input, MD5, null, 1);
    }

    public static byte[] md5(byte[] input, byte[] salt) {
        return digest(input, MD5 , salt, 1);
    }

    public static byte[] md5(byte[] input, byte[] salt, int iterations) {
        return digest(input, MD5 , salt, iterations);
    }

    /**
     * 对文件进行md5散列
     */
    public static byte[] md5(InputStream input) throws IOException {
        return digest(input, MD5);
    }

    /**
     * 对文件进行sha1散列
     */
    public static byte[] sha1(InputStream input) throws IOException {
        return digest(input, SHA1);
    }

    /**
     * 对文件进行散列
     */
    private static byte[] digest(InputStream inputStream, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8*1024;
            byte[] buffer = new byte[bufferLength];
            int read = inputStream.read(buffer, 0, bufferLength);

            while(read > -1) {
                messageDigest.update(buffer, 0, read);
                read = inputStream.read(buffer, 0 , bufferLength);
            }
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw Exceptions.unchecked(e);
        }
    }


    /**
     * 对字符串进行散列，支持Md5与sha1算法
     */
    private static byte[] digest(byte[] input, String algorihm, byte[] salt, int interations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorihm);
            if(salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for(int i = 1; i < interations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 生产随机的Byte[]作为salt
     */
    public static byte[] generateSalt(int numBytes) {
        // 验证不成功会跑出异常
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or large)", numBytes);

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }


}
