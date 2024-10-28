package me.syphix.thepit.core.annotation;

import me.syphix.thepit.core.action.Action;
import me.syphix.thepit.core.action.ActionManager;
import org.reflections.Reflections;

import java.util.List;

public class ActionHandlerProcessor {

    public static void process() {
        Reflections reflections = new Reflections("me.syphix.thepit");
        List<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(ActionHandler.class).stream().toList();

        for (Class<?> annotatedClass : annotatedClasses) {
            String id = annotatedClass.getAnnotation(ActionHandler.class).id();

            try {
                ActionManager.add(id, (Action) annotatedClass.newInstance());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
