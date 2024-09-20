package ir.syphix.thepit.utils;

import org.bukkit.inventory.ItemStack;
import org.sayandev.stickynote.bukkit.nms.NMSUtils;

public class ItemStackUtils {

    public static String toString(ItemStack itemStack) {
        return NMSUtils.INSTANCE.getItemStackNBTJson(itemStack);
    }

    public static ItemStack toItemStack(String itemStack) {
        return NMSUtils.INSTANCE.getItemStackFromNBTJson(itemStack);
    }
}
