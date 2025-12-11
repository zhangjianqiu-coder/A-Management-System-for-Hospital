package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateUtil {
    // 用于Date类型的比较
    public static boolean compareDates(String inputDate, Date dbDate) {
        if ((inputDate == null || inputDate.trim().isEmpty()) && dbDate == null) {
            return true;
        }
        if (inputDate == null || dbDate == null) {
            return false;
        }
        // 使用DateUtil安全格式化
        String formattedDbDate = DateUtil.formatDateToString(dbDate);
        return inputDate.trim().equals(formattedDbDate);
    }

    private static boolean safeEquals(String str1, String str2) {
        // 处理null和空字符串都视为相等的情况
        if (str1 == null && str2 == null) return true;
        if (str1 == null) str1 = "";
        if (str2 == null) str2 = "";
        return str1.trim().equals(str2.trim());
    }


    // Date->String(yyyy-MM-dd)
    /**
     * 将Date对象格式化为yyyy-MM-dd字符串
     * @param date Date对象，可以为null
     * @return 格式化后的字符串，如果date为null则返回空字符串
     */
    public static String formatDateToString(Date date) {
        return formatDateToString(date, "yyyy-MM-dd");
    }

    /**
     * 将Date对象格式化为指定格式的字符串
     * @param date Date对象，可以为null
     * @param pattern 日期格式，如"yyyy-MM-dd"
     * @return 格式化后的字符串，如果date为null则返回空字符串
     */
    public static String formatDateToString(Date date, String pattern) {
        if (date == null) {
            return "";
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception e) {
            // 如果格式错误，返回空字符串
            return "";
        }
    }

    /**
     * 安全地将Object转换为yyyy-MM-dd格式字符串
     * @param value 可以是Date对象或字符串，可以为null
     * @return 格式化后的字符串，如果value为null则返回空字符串
     */
    public static String safeFormatDate(Object value) {
        return safeFormatDate(value, "yyyy-MM-dd");
    }

    /**
     * 安全地将Object转换为指定格式字符串
     * @param value 可以是Date对象或字符串，可以为null
     * @param pattern 日期格式
     * @return 格式化后的字符串
     */
    public static String safeFormatDate(Object value, String pattern) {
        if (value == null) {
            return "";
        }

        if (value instanceof Date) {
            return formatDateToString((Date) value, pattern);
        } else if (value instanceof String) {
            String str = ((String) value).trim();
            if (str.isEmpty()) {
                return "";
            }

            // 如果是日期字符串，尝试解析后重新格式化
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                // 先尝试按指定格式解析
                Date date = sdf.parse(str);
                return sdf.format(date);
            } catch (Exception e) {
                // 如果无法解析，返回原始字符串
                return str;
            }
        } else {
            // 其他类型直接转为字符串
            return value.toString().trim();
        }
    }


    // String(yyyy-MM-dd) -> Date
    public static java.sql.Date stringToSqlDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);

        try {
            java.util.Date utilDate = sdf.parse(dateStr.trim());
            return new java.sql.Date(utilDate.getTime());  // 返回java.sql.Date
        } catch (ParseException e) {
            return null;
        }
    }
}