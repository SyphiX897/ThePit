package me.syphix.thepit.utils;

import com.cryptomorin.xseries.reflection.XReflection;
import org.bukkit.entity.Player;
import org.sayandev.stickynote.bukkit.nms.accessors.ServerPlayerAccessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NMSUtils {
    private static Class<?> CRAFT_PLAYER = XReflection.getCraftClass("entity.CraftPlayer");
    private static Method CRAFT_PLAYER_GET_HANDLE_METHOD;

    static {
        try {
            CRAFT_PLAYER_GET_HANDLE_METHOD = CRAFT_PLAYER.getMethod("getHandle");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getServerPlayer(Player player) {
        try {
            return CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(player);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
}
