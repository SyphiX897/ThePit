package ir.syphix.thepit.core.perk;

import ir.syphix.thepit.annotation.AutoConstruct;

import java.util.HashMap;

@AutoConstruct
public class PerkManager {

    private final static HashMap<String, Perk> perks = new HashMap<>();
    private static PerkManager instance;

    public static PerkManager getInstance() {
        return instance;
    }

    public PerkManager() {
        instance = this;
    }

    public static Perk perk(String id) {
        return perks.get(id);
    }

    public static boolean exist(String id) {
        return perks.get(id) != null;
    }


}
