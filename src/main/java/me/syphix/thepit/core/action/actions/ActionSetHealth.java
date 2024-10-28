package me.syphix.thepit.core.action.actions;

import me.syphix.thepit.core.action.Action;
import me.syphix.thepit.core.action.TriggerData;
import me.syphix.thepit.core.annotation.ActionHandler;

@ActionHandler(id = "health")
public class ActionSetHealth extends Action {
    @Override
    public void apply(TriggerData triggerData) {
        if (triggerData.player().isEmpty()) return;
        if (triggerData.number().isEmpty()) return;

        triggerData.player().get().setHealth(triggerData.number().get());
    }
}
