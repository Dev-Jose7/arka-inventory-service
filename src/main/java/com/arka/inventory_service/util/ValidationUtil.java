package com.arka.inventory_service.util;

public class ValidationUtil {

    public static boolean isValid(Object value, Object valueCompare) {
        if (value == null) {
            return false;
        }

        if (value instanceof String) {
            return !value.toString().trim().isEmpty() && !value.equals(valueCompare);
        }

        return !value.equals(valueCompare);
    }

    public static boolean isGreaterThan(Object value, Number compareTo) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue() > compareTo.doubleValue();
        }
        return false;
    }
}
