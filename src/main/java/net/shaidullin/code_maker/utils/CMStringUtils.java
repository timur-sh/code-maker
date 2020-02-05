//package net.shaidullin.code_maker.utils;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Iterator;
//
//public class CMStringUtils {
//    public static String getLastPartOfClassName(String className) {
//        String[] parts = className.split("\\.");
//        if (parts.length == 0) {
//            return null;
//        }
//
//        return parts[parts.length - 1];
//    }
//
//    public static boolean notNullAndNotEmpty(String value) {
//        return value != null && value.length() > 0;
//    }
//
//    /**
//     * Join collection of string by comma
//     * @param coll
//     * @return
//     */
//    public static String commaDelimitedString(Collection<?> coll) {
//        return commaDelimitedString(coll, ",");
//    }
//
//    /**
//     * Join collection of string by comma
//     * @param coll
//     * @param delim
//     * @return
//     */
//    public static String commaDelimitedString(Collection<?> coll, String delim) {
//        if (coll == null || coll.isEmpty()) {
//            return "";
//        }
//        StringBuilder sb = new StringBuilder();
//        Iterator<?> it = coll.iterator();
//        while (it.hasNext()) {
//            sb.append(it.next());
//            if (it.hasNext()) {
//                sb.append(delim);
//            }
//        }
//        return sb.toString();
//    }
//
//    /**
//     * Join array of string by comma
//     *
//     * @param array
//     * @return
//     */
//    public static String commaDelimitedString(String[] array) {
//        if (array == null || array.length == 0) {
//            return "";
//        }
//
//        return commaDelimitedString(Arrays.asList(array));
//    }
//
//    /**
//     * Capitalize first letter
//     *
//     * @param string
//     * @return
//     */
//    public static String capitalizeFirstLetter(String string) {
//        return string.substring(0, 1).toUpperCase() + string.substring(1);
//    }
//
//    public static boolean validatePackageName(String name) {
//        if (StringUtils.isEmpty(name)) {
//            return false;
//        }
//
//        if (name.contains(".") || name.contains(" ") || name.contains("-")) {
//            return false;
//        }
//
//        return true;
//    }
//
//    public static boolean validateFileName(String fileName) {
//        File f = new File(fileName);
//        try {
//            Paths.get(fileName);
//            f.getCanonicalPath();
//            return true;
//        }
//        catch (IOException e) {
//            return false;
//        }
//    }
//}
