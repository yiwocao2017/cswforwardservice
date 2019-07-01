package com.cdkj.service.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.cdkj.service.exception.BizException;

public class CalculationUtil {

    public static long mult(double source) {

        Double d = source * 1000;
        return d.longValue();
    }

    public static double divi(long source) {

        double l = (double) source / 1000;
        return l;

    }

    public static String mult(String number) {
        DecimalFormat df = new DecimalFormat(".00");
        df.setRoundingMode(RoundingMode.FLOOR);
        Double money;
        try {
            String m = df.format(Double.parseDouble(number));
            money = Double.parseDouble(m) * 1000;
        } catch (Exception e) {
            throw new BizException("zc000001", "金额必须是数字类型");
        }
        return String.valueOf(money.longValue());
    }

    public static String divi(String number) {
        Long money;
        try {
            money = Long.parseLong(number);
        } catch (Exception e) {
            throw new BizException("zc000001", "金额必须是数字类型");
        }
        return String.valueOf((Long) (money / 1000));
    }

    public static String multByTenThousand(String number) {
        DecimalFormat df = new DecimalFormat(".00");
        df.setRoundingMode(RoundingMode.FLOOR);
        Double money;
        try {
            String m = df.format(Double.parseDouble(number));
            money = Double.parseDouble(m) * 10000;
        } catch (Exception e) {
            throw new BizException("zc000001", "利率必须是数字类型");
        }
        return String.valueOf(money.longValue());
    }

    public static String diviByTenThousand(String number) {
        Long money;
        try {
            money = Long.parseLong(number);
        } catch (Exception e) {
            throw new BizException("zc000001", "利率必须是数字类型");
        }
        return String.valueOf((Long) (money / 10000));
    }

    public static String diviByHundred(String number) {
        double money;
        try {
            money = Double.parseDouble(number);
        } catch (Exception e) {
            throw new BizException("zc000001", "利率必须是数字类型");
        }
        return String.valueOf((double) (money / 100.0));
    }

    public static String multByHundred(String number) {
        double money;
        try {
            money = Double.parseDouble(number);
        } catch (Exception e) {
            throw new BizException("zc000001", "利率必须是数字类型");
        }
        return String.valueOf((double) (money * 100.0));
    }

    public static String diviFormate(Long number) {
        String result = null;
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        if (number != null) {
            result = df.format(number / 1000.0);
        }
        return result;
    }
}
