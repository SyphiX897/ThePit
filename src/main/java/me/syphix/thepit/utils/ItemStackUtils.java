package me.syphix.thepit.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.sayandev.stickynote.bukkit.nms.NMSUtils;

public class ItemStackUtils {

    public static String toString(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return "{AIR}";
        } else {
            return NMSUtils.INSTANCE.getItemStackNBTJson(itemStack);
        }
    }

    public static ItemStack toItemStack(String itemStack) {
        return NMSUtils.INSTANCE.getItemStackFromNBTJson(itemStack);
    }
}
