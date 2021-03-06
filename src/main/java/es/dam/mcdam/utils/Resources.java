/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.utils;

public class Resources {
    public static String get(Class<?> clazz, String resource) {
        return clazz.getResource(resource).toString();
    }

    public static String getPath(Class<?> clazz, String resource) {
        return clazz.getResource(resource).getPath();
    }
}
