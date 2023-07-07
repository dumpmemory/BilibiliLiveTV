package com.muedsa.bilibililiveapiclient.util;

import com.google.common.escape.Escaper;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.net.UrlEscapers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public class WbiUtil {
    private static final int[] MIXIN_KEY_ENC_TABLE = new int[]{
            46, 47, 18, 2, 53, 8, 23, 32, 15, 50, 10, 31, 58, 3, 45, 35, 27, 43, 5, 49,
            33, 9, 42, 19, 29, 28, 14, 39, 12, 38, 41, 13, 37, 48, 7, 16, 24, 55, 40,
            61, 26, 17, 0, 1, 60, 51, 30, 4, 22, 25, 54, 21, 56, 59, 6, 63, 57, 62, 11,
            36, 20, 34, 44, 52
    };

    public static final String QUERY_KEY_WTS = "wts";
    public static final String QUERY_KEY_W_RID = "w_rid";

    private static final HashFunction MD5 = Hashing.md5();

    public static String getMixinKey(String imgKey, String subKey) {
        String s = imgKey + subKey;
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            key.append(s.charAt(MIXIN_KEY_ENC_TABLE[i]));
        }
        return key.toString();
    }

    public static void fillWbiParams(Map<String, Object> params, String imgKey, String subKey) {
        String mixinKey = getMixinKey(imgKey, subKey);
        fillWbiParams(params, mixinKey);
    }

    public static void fillWbiParams(Map<String, Object> params, String mixinKey) {
        params.put(QUERY_KEY_WTS, System.currentTimeMillis() / 1000);
        HashCode hashCode = MD5.hashString(ApiUtil.stringSortedParams(params) + mixinKey,
                StandardCharsets.UTF_8);
        params.put(QUERY_KEY_W_RID, hashCode.toString());
    }
}
