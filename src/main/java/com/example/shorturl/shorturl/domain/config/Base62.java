package com.example.shorturl.shorturl.domain.config;

import java.math.BigInteger;

public class Base62 {
    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String encode(long value) {
        StringBuilder sb = new StringBuilder();
        do {
            int i = (int) (value % 62);
            sb.append(BASE62.charAt(i));
            value /= 62;
        } while (value > 0);
        return sb.toString();
    }

    public static String encode(BigInteger value) {
        StringBuilder sb = new StringBuilder();
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = value.divideAndRemainder(BigInteger.valueOf(62));
            sb.append(BASE62.charAt(divmod[1].intValue()));
            value = divmod[0];
        }
        return sb.toString();
    }

    public static long decode(String value) {
        long result = 0L;
        long power = 1L;
        for (int i = 0; i < value.length(); i++) {
            result += BASE62.indexOf(value.charAt(i)) * power;
            power *= 62;
        }
        return result;
    }
}
