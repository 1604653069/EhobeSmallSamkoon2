package com.su.ehobesmallsamkoon.util;

import android.content.Context;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.jessyan.autosize.utils.LogUtils;

public class TimeUtils {

    // 1，日期格式：String dateString = "2017-06-20 10:30:30" 对应的格式：String pattern = "yyyy-MM-dd HH:mm:ss";
    //2，日期格式：String dateString = "2017-06-20" 对应的格式：String pattern = "yyyy-MM-dd";
    //3，日期格式：String dateString = "2017年06月20日 10时30分30秒 对应的格式：String pattern = "yyyy年MM月dd日 HH时mm分ss秒";
    // 4，日期格式：String dateString = "2017年06月20日" 对应的格式：String pattern = "yyyy年MM月dd日";
    public static String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-M-d HH:mm:ss", "yyyy-M-d", "yyyy-MM-d HH:mm:ss", "yyyy-MM-d", "yyyy-M-dd HH:mm:ss", "yyyy-M-dd"};

    //将字符串转为时间戳
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    //将时间戳转为字符串
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    //获取当前时间
    public static String getCurDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date());
    }

    //获取当前时间
    public static String getCurDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sDateFormat.format(new Date());
    }

    //获取系统时间戳
    public static long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * 获取前一天时间
     */
    public static String getTimeOtherDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     * 两个时间比较
     */
    public static boolean getTimeCompareSize(String date1, String date2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() >= dt2.getTime())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean rangInChoose(String start, String end, String choose ) {
        long endMills = getStringToDate(end, "yyyy-MM-dd");
        long startMills = getStringToDate(start, "yyyy-MM-dd");
        long chooseMills = getStringToDate(choose, "yyyy-MM-dd");
        long a = chooseMills - startMills;
        long b = endMills - chooseMills;
        return a >= 0 && b >= 0;

    }
    public static String rangInOneMonth(Context context, String start, String end, boolean changStart) {
        String result = "";
        long endMills = getStringToDate(end, "yyyy-MM-dd");
        long startMills = getStringToDate(start, "yyyy-MM-dd");
        long monthMills = endMills - startMills;
        long oneMonthMills = 30L * 24L * 60L * 60L * 1000L;

        if (monthMills > oneMonthMills) {
            //  ToastUtil.showToast(context,"时间范围不超过一个月");
            if (changStart) {
                endMills = startMills + oneMonthMills;
                LogUtils.e(endMills + "");
                result = getDateToString(endMills, "yyyy-MM-dd");
            } else {
                startMills = endMills - oneMonthMills;
                LogUtils.e(startMills + "");
                result = getDateToString(startMills, "yyyy-MM-dd");
            }
        } else {
            if (monthMills < 0) {
                //    ToastUtil.showToast(context,"结束时间大于起始时间");
                if (changStart) {
                    result = start;
                } else {
                    result = end;
                }
            } else {
                LogUtils.e("正常");
                if (changStart) {
                    result = end;
                } else {
                    result = start;
                }
            }
        }
        return result;
    }

    public static String change(String str) {
        if (TextUtils.isEmpty(str))
            return "0000-00-00";
        for (String pattern : patterns) {
            if (change(str, pattern)) {
                return change(str, pattern, "yyyy-MM-dd");
            }
        }
        return "0000-00-00";
    }

    public static String changeMin(String str) {
        if (TextUtils.isEmpty(str))
            return "0000-00-00";
        for (String pattern : patterns) {
            if (change(str, pattern)) {
                return change(str, pattern, "yyyy-MM-dd HH:mm");
            }
        }
        return "0000-00-00";
    }

    public static String changeSecond(String str) {
        if (TextUtils.isEmpty(str))
            return "0000-00-00";
        for (String pattern : patterns) {
            if (change(str, pattern)) {
                return change(str, pattern, "yyyy-MM-dd HH:mm:ss");
            }
        }
        return "0000-00-00";
    }

    public static String changeWithoutDay(String str) {
        if (TextUtils.isEmpty(str))
            return "0000-00-00";
        for (String pattern : patterns) {
            if (change(str, pattern)) {
                return change(str, pattern, "HH:mm");
            }
        }
        return "0000-00-00";
    }

    public static boolean change(String str, String pattern) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static String change(String str, String patternCan, String patternWant) {
        String result = "0000-00-00";
        SimpleDateFormat format = new SimpleDateFormat(patternCan);
        try {
            Date date = format.parse(str);
            format = new SimpleDateFormat(patternWant);
            result = format.format(date);
        } catch (ParseException e) {

        }
        return result;
    }

    public static String getDateStr(String day, int Num) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //如果需要向后计算日期 -改为+
        Date newDate2 = new Date(nowDate.getTime() - (long) Num * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }

    public static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get((Calendar.DAY_OF_WEEK)) - 1;
        if (day == 0)
            day = 7;
        return day;
    }

    public static int getMonthDay(String sDate) {
        return Integer.parseInt(sDate.trim().substring(sDate.length() - 2));
    }

    public static String getQuarter(String sDate) {
        String year = sDate.substring(0, 4);
        int quarter = Integer.parseInt(sDate.substring(5, 7));
        String month = "";
        if (1 <= quarter && quarter <= 3) {
            month = "01";
        } else if (4 <= quarter && quarter <= 6) {
            month = "04";
        } else if (7 <= quarter && quarter <= 9) {
            month = "07";
        } else {
            month = "10";
        }
        return year + "-" + month + "-" + "01";

    }

    public static int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH);
    }

    public static int getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }
}
