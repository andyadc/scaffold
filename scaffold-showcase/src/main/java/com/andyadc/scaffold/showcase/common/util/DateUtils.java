package com.andyadc.scaffold.showcase.common.util;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author andaicheng
 * @version 2017/1/2
 */
public class DateUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    public static final String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private DateUtils() {
    }

    /**
     * 获取当月第一天的日期
     *
     * @return <code>Date</code> yyyy-MM-dd 00:00:00
     */
    public static Date getFirstDayDateTimeOfMonth() {
        return new DateTime().dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toDate();
    }

    /**
     * 获取当月第一天的日期
     *
     * @return <code>String</code> yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayDateTimeStrOfMonth() {
        return new DateTime().dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toString(DEFAULT_TIME_FORMAT);
    }

    /**
     * 获取指定月第一天的日期
     *
     * @return <code>Date</code> yyyy-MM-dd 00:00:00
     */
    public static Date getFirstDayDateTimeOfMonth(Date date) {
        return new DateTime(date).dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toDate();
    }

    /**
     * 获取当月第一天的日期
     *
     * @return <code>String</code> yyyy-MM-dd 00:00:00
     */
    public static String getFirstDayDateTimeStrOfMonth(Date date) {
        return new DateTime(date).dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toString(DEFAULT_TIME_FORMAT);
    }

    /**
     * 获取当月最后一天的日期
     *
     * @return <code>Date</code> yyyy-MM-dd 23:59:59
     */
    public static Date getLastDayDateTimeOfMonth() {
        return new DateTime().dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate();
    }

    /**
     * 获取当月最后一天的日期
     *
     * @return <code>String</code> yyyy-MM-dd 23:59:59
     */
    public static String getLastDayDateTimeStrOfMonth() {
        return new DateTime().dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toString(DEFAULT_TIME_FORMAT);
    }

    /**
     * 获取当月最后一天的日期
     *
     * @return <code>Date</code> yyyy-MM-dd 23:59:59
     */
    public static Date getLastDayDateTimeOfMonth(Date date) {
        return new DateTime(date).dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate();
    }

    /**
     * 获取当月最后一天的日期
     *
     * @return <code>String</code> yyyy-MM-dd 23:59:59
     */
    public static String getLastDayDateTimeStrOfMonth(Date date) {
        return new DateTime(date).dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toString(DEFAULT_TIME_FORMAT);
    }

    /**
     * 构造具体日期时间
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   小时(24)
     * @param minute 分钟
     * @param second 秒
     */
    public static Date buildDateTime(int year, int month, int day, int hour, int minute, int second) {
        return new DateTime(year, month, day, hour, minute, second).toDate();
    }

    public static Date plusDays(Date date, int days) {
        return new DateTime(date).plusDays(days).toDate();
    }

    public static Date plusDays(int days) {
        return new DateTime().plusDays(days).toDate();
    }

    public static Date minusDays(Date date, int days) {
        return new DateTime(date).minusDays(days).toDate();
    }

    public static Date minusDays(int days) {
        return new DateTime().minusDays(days).toDate();
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(date);
    }

    public static Date parseDate(String date, String format) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            LOG.error("parseDate error", e);
            throw new RuntimeException(e);
        }
    }

    public static Date parseTime(String date, String format) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            LOG.error("parseTime error", e);
            throw new RuntimeException(e);
        }
    }

    public static Date parseDate(String date) {
        return parseDate(date, DEFAULT_DATE_FORMAT);
    }

    public static Date parseTime(String date) {
        return parseTime(date, DEFAULT_TIME_FORMAT);
    }

    /**
     * N天之后
     */
    public static Date nDaysAfter(Date date, Integer n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + n);
        return cal.getTime();
    }

    /**
     * N天之前
     */
    public static Date nDaysBefore(Date date, Integer n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - n);
        return cal.getTime();
    }
}
