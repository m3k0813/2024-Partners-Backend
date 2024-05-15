package com.example.shorturl.shorturl.domain.config;

public class Base62 {
    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String encode(Long value) {
        StringBuilder sb = new StringBuilder();
        do {
            int i = (int)(value % 62);
            sb.append(BASE62.charAt(i));
            value /= 62;
        } while (value > 0);
        return sb.toString();
    }

    public static Long decode(String value) {
            Long result = 0L;
            Long power = 1L;
            for (int i = 0; i < value.length(); i++) {
                result += BASE62.indexOf(value.charAt(i)) * power;
                power *= 62;
            }
            return result;
        }
}
