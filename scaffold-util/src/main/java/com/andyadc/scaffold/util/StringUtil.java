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
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str the CharSequence to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the CharSequence to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     */
    public static boolean isBlank(CharSequence str) {
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

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * Checks if some CharSequences has any blank string.<br>
     * If strings == null, return true
     *
     * @param strings the CharSequences
     * @return true if any CharSequence is blank, otherwise false.
     */
    public static boolean isAnyBlank(CharSequence... strings) {
        if (strings == null) {
            return true;
        }
        for (CharSequence s : strings) {
            if (isBlank(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all the CharSequences is not blank.
     *
     * @param strings the CharSequences
     * @return true if all CharSequence is not blank, otherwise false.
     */
    public static boolean isAllNotBlank(CharSequence... strings) {
        return !isAnyBlank(strings);
    }

    /**
     * <p>
     * Checks if some CharSequences has any empty string.
     * </p>
     * If strings == null, return true
     *
     * @param strings the CharSequences
     * @return true is any CharSequence is empty, otherwise false.
     */
    public static boolean isAnyEmpty(CharSequence... strings) {
        if (strings == null) {
            return true;
        }
        for (CharSequence s : strings) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all the CharSequences is not empty.
     *
     * @param strings the CharSequences
     * @return true if all CharSequence is not empty, otherwise false.
     */
    public static boolean isAllNotEmpty(CharSequence... strings) {
        return !isAnyEmpty(strings);
    }

    /**
     * <p>Reverses a String as per {@link StringBuilder#reverse()}.</p>
     * <p>A {@code null} String returns {@code null}.</p>
     *
     * <pre>
     * StringUtils.reverse(null)  = null
     * StringUtils.reverse("")    = ""
     * StringUtils.reverse("bat") = "tab"
     * </pre>
     *
     * @param str the String to reverse, may be null
     * @return the reversed String, {@code null} if null String input
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

}
