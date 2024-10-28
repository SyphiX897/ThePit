package me.syphix.thepit.core.action;

import org.bukkit.entity.Player;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.util.*;

public class ActionManager {

    private final static HashMap<String, Action> actions = new HashMap<>();

    public static <T extends Action> T Action(Class<?> clazz) {
        return (T) actions.values().stream().filter(action -> action.getClass() == clazz);
    }

    public static Action action(String id) {
        return actions.get(id);
    }

    public static void add(String id, Action action) {
        actions.putIfAbsent(id, action);
    }

    public static void handle(Player player, List<?> actionsList) {
        if (player == null) return;
        for (Object object : actionsList) {
            LinkedHashMap<String, ?> dataMap = (LinkedHashMap<String, ?>) object;
            String actionId = dataMap.get("id").toString();
            StickyNote.warn(actionId);
            TriggerData triggerData;

            if (dataMap.get("number") == null) {
                triggerData = new TriggerData(Optional.ofNullable(player), null);
            } else {
                int number = (int) dataMap.get("number");
                StickyNote.warn(String.valueOf(number));
                triggerData = new TriggerData(Optional.of(player), Optional.of((double) number));
            }

            actions.get(actionId).apply(triggerData);
        }
    }
}
