/*----------------------------------------------------------------------------------------------------------------------------------
 * PACKAGE  : com.freeware.utils
 * FILE     : StringUtils.java
 * CREATED  : 15-Jun-2015 12:51:09 pm
 * AUTHOR   : Prasad P. Khandekar
 * COPYRIGHT: Copyright (c) 2008, Fundtech INDIA Ltd.
 *--------------------------------------------------------------------------------------------------------------------------------*/
package projO_Classi;

/**
 * <p>TODO - The description and purpose of this class goes here
 * <h3>Configuration</h3>
 * <pre style="padding:2px;margin:0px;border:1px dotted #0A246A;background-color:white;font-family:Consolas,monospace;">
 * TODO - Put bean configuration xml snippet here (if applicable)
 * </pre></p>
 * <p>
 * <h3>References</h3>
 * <table style="background-color:white;border:1px solid silver;border-collapse:collapse;" cellpadding="4">
 * 	<tr>
 * 		<td style="background-color: silver;border:1px dotted silver;">Module</td>
 *		<td style="border:1px dotted silver;">TODO - Module Name</td>
 *  </tr>
 * 	<tr>
 * 		<td style="background-color: silver;border:1px dotted silver;">Configuration File</td>
 *		<td style="border:1px dotted silver;"><code>TODO - XML Configuration file name</code></td>
 *  </tr>
 * </table></p>
 * @author Prasad P. Khandekar
 * @version 1.0
 * @since 15-Jun-2015 12:51:09 pm
 */
public final class StringUtils
{
	private StringUtils()
	{
		// Prevent instantiation via new
	}

    /**
     * <p>Checks if a String is empty ("") or null. Does not trims the string</p>
     * <h3>Usage</h3>
     * <pre style="padding:2px;margin:0px;border:1px dotted #0A246A;background-color:white;font-family:Consolas,monospace;">
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false</pre>
     * 
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str)
    {
        return str == null || str.length() == 0;
    }

    /**
     * <p>Checks if a String is not empty ("") and not null.</p>
     * <h3>Usage</h3>
     * <pre style="padding:2px;margin:0px;border:1px dotted #0A246A;background-color:white;font-family:Consolas,monospace;">
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true</pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmpty(String str)
    {
        return !StringUtils.isEmpty(str);
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null. Trims the string</p>
     * <h3>Usage</h3>
     * <pre style="padding:2px;margin:0px;border:1px dotted #0A246A;background-color:white;font-family:Consolas,monospace;">
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false</pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     */
    public static boolean isBlank(String str) {
        int strLen;

        if (str == null || (strLen = str.length()) == 0) return true;

        // Check whether string only contains whitespaces
        for (int i = 0; i < strLen; i++)
            if ((Character.isWhitespace(str.charAt(i)) == false)) return false;

        return true;
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     * <h3>Usage</h3>
     * <pre style="padding:2px;margin:0px;border:1px dotted #0A246A;background-color:white;font-family:Consolas,monospace;">
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true</pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null and not whitespace
     */
    public static boolean isNotBlank(String str)
    {
        return !StringUtils.isBlank(str);
    }

    /**
     * <p>Removes control characters (char &lt;= 32) from both ends of this String, handling <code>null</code> by returning
     * <code>null</code>.</p>
     *
     * <p>The String is trimmed using {@link String#trim()}. Trim removes start and end characters &lt;= 32. To strip whitespace 
     * use {@link #strip(String)}.</p>
     *
     * <p>To trim your choice of characters, use the {@link #strip(String, String)} methods.</p>
     *
     * <h3>Usage</h3>
     * <pre style="padding:2px;margin:0px;border:1px dotted #0A246A;background-color:white;font-family:Consolas,monospace;">
     * StringUtils.trim(null)          = null
     * StringUtils.trim("")            = ""
     * StringUtils.trim("     ")       = ""
     * StringUtils.trim("abc")         = "abc"
     * StringUtils.trim("    abc    ") = "abc"</pre>
     *
     * @param str  the String to be trimmed, may be null
     * @return the trimmed string, <code>null</code> if null String input
     */
    public static String trim(String str)
    {
        return str == null ? null : str.trim();
    }

    /**
     * <p>Compares two Strings, returning <code>true</code> if they are equal.</p>
     *
     * <p><code>null</code>s are handled without exceptions. Two <code>null</code> references are considered to be equal. The 
     * comparison is case sensitive.</p>
     *
     * <h3>Usage</h3>
     * <pre style="padding:2px;margin:0px;border:1px dotted #0A246A;background-color:white;font-family:Consolas,monospace;">
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false</pre>
     *
     * @see java.lang.String#equals(Object)
     * @param str1  the first String, may be null
     * @param str2  the second String, may be null
     * @return <code>true</code> if the Strings are equal, case sensitive, or both <code>null</code>
     */
    public static boolean equals(String str1, String str2)
    {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * <p>Compares two Strings, returning <code>true</code> if they are equal ignoring the case.</p>
     *
     * <p><code>null</code>s are handled without exceptions. Two <code>null</code> references are considered equal. Comparison is 
     * case insensitive.</p>
     *
     * <h3>Usage</h3>
     * <pre style="padding:2px;margin:0px;border:1px dotted #0A246A;background-color:white;font-family:Consolas,monospace;">
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true</pre>
     *
     * @see java.lang.String#equalsIgnoreCase(String)
     * @param str1  the first String, may be null
     * @param str2  the second String, may be null
     * @return <code>true</code> if the Strings are equal, case insensitive, or both <code>null</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2)
    {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * <p>Finds the first index within a String, handling <code>null</code>. This method uses {@link String#indexOf(int)}.</p>
     *
     * <p>A <code>null</code> or empty ("") String will return <code>-1</code>.</p>
     *
     * <h3>Usage</h3>
     * <pre style="padding:2px;margin:0px;border:1px dotted #0A246A;background-color:white;font-family:Consolas,monospace;">
     * StringUtils.indexOf(null, *)         = -1
     * StringUtils.indexOf("", *)           = -1
     * StringUtils.indexOf("aabaabaa", 'a') = 0
     * StringUtils.indexOf("aabaabaa", 'b') = 2</pre>
     *
     * @param str  the String to check, may be null
     * @param searchChar  the character to find
     * @return the first index of the search character, -1 if no match or <code>null</code> string input
     */
    public static int indexOf(String str, char searchChar)
    {
        if (isEmpty(str)) return -1;

        return str.indexOf(searchChar);
    }
}
