package me.syphix.thepit.core.action;

import org.bukkit.entity.Player;

import java.util.Optional;

public record TriggerData(
        Optional<Player> player,
        Optional<Double> number
)
{}
