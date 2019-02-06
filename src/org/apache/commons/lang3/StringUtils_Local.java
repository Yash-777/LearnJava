package org.apache.commons.lang3;

public class StringUtils_Local {
    /**
     * Represents a failed index search.
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;
    
    /**
     * <p>Gets the String that is nested in between two Strings.
     * Only the first match is returned.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} open/close returns {@code null} (no match).
     * An empty ("") open and close returns an empty string.</p>
     *
     * <pre>
     * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringUtils.substringBetween(null, *, *)          = null
     * StringUtils.substringBetween(*, null, *)          = null
     * StringUtils.substringBetween(*, *, null)          = null
     * StringUtils.substringBetween("", "", "")          = ""
     * StringUtils.substringBetween("", "", "]")         = null
     * StringUtils.substringBetween("", "[", "]")        = null
     * StringUtils.substringBetween("yabcz", "", "")     = ""
     * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param str  the String containing the substring, may be null
     * @param open  the String before the substring, may be null
     * @param close  the String after the substring, may be null
     * @return the substring, {@code null} if no match
     * @since 2.0
     */
    public static String substringBetween(final String str, final String open, final String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        final int start = str.indexOf(open);
        if (start != INDEX_NOT_FOUND) {
            final int end = str.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
    
    public static String replaceSubstringInBetween2(final String str, final String replace, final String open, final String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        final int start = str.indexOf(open);
        if (start != INDEX_NOT_FOUND) {
            String preceding = "";
            if (start > 0) {
                preceding = str.substring(0, start);
            }
            final int end = str.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND) {
                String exceeding = "";
                if ((end < str.length() - 1) && (end + close.length() < str.length())) {
                    exceeding = str.substring(end + close.length(), str.length());
                }
                //String middleString = str.substring(start + open.length(), end);
                return preceding + open + replace + close + exceeding;
            }
        }
        return null;
    }
    
    /**
     * <p>Replaces the given String, with the String which is nested in between two Strings.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} open/close returns an input String (no match).</p>
     *
     * @param str  the String containing the substring, may be null
     * @param replace the Sting to be replaced, which is nested in between open and close substrings, may be null
     * @param open  the String before the substring, may be null
     * @param close  the String after the substring, may be null
     * @return the substring, {@code null} if no match
     */
    public static String replaceSubstringInBetween(final String str, final String replace, final String open, final String close) {
        if (str == null) {
            return null;
        }
        if (open == null || close == null || replace == null) {
            return str;
        }
        final int start = str.indexOf(open);
        if (start != INDEX_NOT_FOUND) {
            String preceding = str.substring(0, start);
            final int end = str.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND) {
                String exceeding = str.substring(end + close.length(), str.length());
                //String middleString = str.substring(start + open.length(), end);
                return preceding + open + replace + close + exceeding;
            }
        }
        return str;
    }
}
