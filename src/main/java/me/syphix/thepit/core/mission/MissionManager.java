package me.syphix.thepit.core.mission;


import java.util.HashMap;

public class MissionManager {

    private final static HashMap<String, Mission> missions = new HashMap<>();
    private MissionManager() {
    }

    public static Mission mission(String id) {
        return missions.get(id);
    }

}
