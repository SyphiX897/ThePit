package me.syphix.thepit.core.action.actions;

import me.syphix.thepit.core.action.Action;
import me.syphix.thepit.core.action.TriggerData;
import me.syphix.thepit.core.annotation.ActionHandler;

@ActionHandler(id = "saturation")
public class ActionSetSaturation extends Action {
    @Override
    public void apply(TriggerData triggerData) {
        if (triggerData.player().isEmpty()) return;
        if (triggerData.number().isEmpty()) return;

        triggerData.player().get().getInventory().setHeldItemSlot((int) ((double) triggerData.number().get()));
    }
}
