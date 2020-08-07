package org.cncert.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class EncryptUtil {
    private static final String IV = "kaifkdu4";
    private static final String DEFKEY = "aiops.cncert.org";
    private static final Charset charset = Charset.forName("utf-8");

    public static String encrypt(String srcStr) {
        return encrypt(srcStr, DEFKEY);
    }

    public static String decrypt(String hexStr) throws Exception {
        return decrypt(hexStr, DEFKEY);
    }

    public static String encrypt(String srcStr, String sKey) {
        byte[] src = srcStr.getBytes(charset);
        byte[] buf = encrypt(src, sKey);
        return parseByte2HexStr(buf);
    }

    public static String decrypt(String hexStr, String sKey) throws Exception {
        byte[] src = parseHexStr2Byte(hexStr);
        byte[] buf = decrypt(src, sKey);
        return new String(buf, charset);
    }

    public static String md5(String str) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] s = m.digest();
            return parseByte2HexStr(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static byte[] encrypt(byte[] data, String sKey) {
        try {
            byte[] key = sKey.getBytes();
            // 初始化向量
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            DESKeySpec desKey = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成securekey
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decrypt(byte[] src, String sKey) throws Exception {
        byte[] key = sKey.getBytes();
        // 初始化向量
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key);
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

    private static String parseByte2HexStr(byte[] buf) {
        if (buf == null) return "";
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) hex = '0' + hex;
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] parseHexStr2Byte(String hexStr) {
        int len = hexStr.length();
        byte[] result = new byte[len/2];
        for (int i = 0; i < len/2; i++) {
            int p = i * 2;
            int high = Integer.parseInt(hexStr.substring(p, p+1), 16);
            int low = Integer.parseInt(hexStr.substring(p+1, p+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
