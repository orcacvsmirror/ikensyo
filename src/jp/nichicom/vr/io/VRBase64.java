/** TODO <HEAD> */
package jp.nichicom.vr.io;

import java.io.ByteArrayOutputStream;

/**
 * Base64エンコード/デコードを実行するクラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRBase64 {

    private static final String BASE64_ENCODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private static final int[] BASE64_DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, };

    /**
     * コンストラクタ
     */
    private VRBase64() {
    }

    /**
     * Base64エンコード処理を行います。
     * 
     * @param bytes エンコードデータ配列
     * @return エンコード済文字列
     */
    public static String encode(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        char[] e1 = new char[4];
        int i = 0;
        int l = bytes.length;

        while (i < l) {
            byte b0 = bytes[i];
            e1[0] = BASE64_ENCODE.charAt((b0 >> 2) & 0x3f);
            if (i + 1 >= l) {
                e1[1] = BASE64_ENCODE.charAt((b0 << 4) & 0x30);
                e1[2] = '=';
                e1[3] = '=';
            } else {
                byte b1 = bytes[i + 1];
                e1[1] = BASE64_ENCODE.charAt(((b0 << 4) & 0x30) | ((b1 >> 4) & 0x0f));
                if (i + 2 >= l) {
                    e1[2] = BASE64_ENCODE.charAt((b1 << 2) & 0x3c);
                    e1[3] = '=';
                } else {
                    byte b2 = bytes[i + 2];
                    e1[2] = BASE64_ENCODE.charAt(((b1 << 2) & 0x3c) | ((b2 >> 6) & 0x03));
                    e1[3] = BASE64_ENCODE.charAt(b2 & 0x3f);
                }
            }
            sb.append(e1);
            i += 3;
        }
        return sb.toString();
    }

    /**
     * Base64デコード処理を行います。
     * 
     * @param chars デコード文字列
     * @return デコード済データ配列
     */
    public static byte[] decode(String chars) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        int step = 0;
        int b0 = 0;
        int b1 = 0;

        int i = 0;
        int l = chars.length();
        while (i < l) {
            char c = chars.charAt(i);
            if (c < BASE64_DECODE_TABLE.length) {
                int ret = BASE64_DECODE_TABLE[c];

                if (ret >= 0) {
                    switch (step) {
                    case 0:
                        b0 = ret;
                        step = 1;
                        break;
                    case 1:
                        b1 = ret;
                        bytes.write(((b0 << 2) & 0xfc) | ((b1 >> 4) & 0x03));
                        step = 2;
                        break;
                    case 2:
                        b0 = ret;
                        bytes.write(((b1 << 4) & 0xf0) | ((b0 >> 2) & 0x0f));
                        step = 3;
                        break;
                    case 3:
                        b1 = ret;
                        bytes.write(((b0 << 6) & 0xc0) | ((b1 >> 0) & 0x3f));
                        step = 0;
                        break;
                    }
                }
            }
            i++;
        }
        byte[] rtn = bytes.toByteArray();

        bytes.reset();
        return rtn;

    }
}