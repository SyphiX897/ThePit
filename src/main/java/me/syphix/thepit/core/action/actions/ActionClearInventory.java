package me.syphix.thepit.core.action.actions;

import me.syphix.thepit.core.action.Action;
import me.syphix.thepit.core.action.TriggerData;
import me.syphix.thepit.core.annotation.ActionHandler;

@ActionHandler(id = "clear_inventory")
public class ActionClearInventory extends Action{
    @Override
    public void apply(TriggerData triggerData) {
        if (triggerData.player().isEmpty()) return;

        triggerData.player().get().getInventory().clear();
    }
}
