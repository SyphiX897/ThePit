package me.syphix.thepit.core.action.actions;

import de.tr7zw.nbtapi.NBT;
import me.syphix.thepit.core.action.Action;
import me.syphix.thepit.core.action.TriggerData;
import me.syphix.thepit.core.annotation.ActionHandler;

import javax.swing.text.html.parser.Entity;

@ActionHandler(id = "food_level")
public class ActionSetFoodLevel extends Action {
    @Override
    public void apply(TriggerData triggerData) {
        if (triggerData.player().isEmpty()) return;
        if (triggerData.number().isEmpty()) return;

        triggerData.player().get().setFoodLevel((int) ((double) triggerData.number().get()));
    }
}
