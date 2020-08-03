package proyectoanalisisii.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Returns the size in memory of multiple types.
 */
public class Sizeof {

    // By default in a 64 bit machine -XX:-UseCompressedOops is enabled, so
    // object reference have a size of 4 bytes
    public static int OBJECTREF_SIZE = 4;
    public static int ARRAY_HEADER_SIZE = 12;

    public static int sizeof(String s) {
        return 36 + (s.length() << Character.BYTES);
    }

    public static int sizeof(Set hs) {
        return 32 * hs.size() + 4 * hs.size();
    }
    
    public static int sizeof(Map hm) {
        return 32 * hm.size() + 4 * hm.size();
    }

    public static int sizeof(Object[] a) {
        return ARRAY_HEADER_SIZE + a.length * OBJECTREF_SIZE;
    }

    public static int sizeof(Object[][] m) {
        return ARRAY_HEADER_SIZE + OBJECTREF_SIZE * (m.length + m[0].length);
    }

    public static int sizeof(ArrayList a) {
        return 4 + ARRAY_HEADER_SIZE + OBJECTREF_SIZE * a.size();
    }

    public static int sizeof(LinkedList a) {
        return 12 + 3 * OBJECTREF_SIZE * a.size();
    }
}
