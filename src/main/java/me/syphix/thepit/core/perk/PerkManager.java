package me.syphix.thepit.core.perk;


import java.util.HashMap;

public class PerkManager {

    private final static HashMap<String, Perk> perks = new HashMap<>();

    private PerkManager() {
    }

    public static Perk perk(String id) {
        return perks.get(id);
    }

    public static boolean exist(String id) {
        return perks.get(id) != null;
    }


}
