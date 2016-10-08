package com.andyadc.scaffold.util;

/**
 * A string utility class that manipulates string.
 *
 * @author andaicheng
 * @version 1.0, 2016-10-07
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * <p>Checks if a String is empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Checks if some strings has any blank string.<br>
     * If strings == null, return true
     *
     * @param strings the strings
     * @return true if any string is blank, otherwise false.
     */
    public static boolean isAnyBlank(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String s : strings) {
            if (isBlank(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all the strings is not blank.
     *
     * @param strings the strings
     * @return true if all string is not blank, otherwise false.
     */
    public static boolean isAllNotBlank(String... strings) {
        return !isAnyBlank(strings);
    }

    /**
     * <p>
     * Checks if some strings has any empty string.
     * </p>
     * If strings == null, return true
     *
     * @param strings the strings
     * @return true is any string is empty, otherwise false.
     */
    public static boolean isAnyEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String s : strings) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all the strings is not empty.
     *
     * @param strings the strings
     * @return true if all string is not empty, otherwise false.
     */
    public static boolean isAllNotEmpty(String... strings) {
        return !isAnyEmpty(strings);
    }

}
