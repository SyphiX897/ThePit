package me.syphix.thepit.core.action.actions;

import me.syphix.thepit.core.action.Action;
import me.syphix.thepit.core.action.TriggerData;
import me.syphix.thepit.core.annotation.ActionHandler;
import org.sayandev.stickynote.bukkit.StickyNote;

@ActionHandler(id = "held_item_slot")
public class ActionSetHeldItemSlot extends Action {
    @Override
    public void apply(TriggerData triggerData) {
        if (triggerData.player().isEmpty()) return;
        if (triggerData.number().isEmpty()) return;
        StickyNote.runSync(() -> triggerData.player().get().getInventory().setHeldItemSlot((int) ((double) triggerData.number().get())), 1);
    }
}
