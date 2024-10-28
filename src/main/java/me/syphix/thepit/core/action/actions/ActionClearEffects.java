package me.syphix.thepit.core.action.actions;

import me.syphix.thepit.core.action.Action;
import me.syphix.thepit.core.action.TriggerData;
import me.syphix.thepit.core.annotation.ActionHandler;
import org.bukkit.potion.PotionEffect;

@ActionHandler(id = "clear_effects")
public class ActionClearEffects extends Action {
    @Override
    public void apply(TriggerData triggerData) {
        if (triggerData.player().isEmpty()) return;

        for (PotionEffect potionEffect : triggerData.player().get().getActivePotionEffects()) {
            triggerData.player().get().removePotionEffect(potionEffect.getType());
        }
    }
}
