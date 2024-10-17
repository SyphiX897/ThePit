package ir.syphix.thepit.annotation;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.List;

public class AutoConstructProcessor {

    public static void process() {
        Reflections reflections = new Reflections("ir.syphix.thepit");
        List<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(AutoConstruct.class).stream().toList();

        for (Class<?> annotatedClass : annotatedClasses) {
            createNewInstance(annotatedClass);
        }
    }

    private static void createNewInstance(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
