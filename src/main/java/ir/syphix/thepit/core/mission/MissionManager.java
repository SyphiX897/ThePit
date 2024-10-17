package ir.syphix.thepit.core.mission;

import ir.syphix.thepit.annotation.AutoConstruct;

import java.util.HashMap;

@AutoConstruct
public class MissionManager {

    private final static HashMap<String, Mission> missions = new HashMap<>();
    private static MissionManager instance;

    public static MissionManager getInstance() {
        return instance;
    }

    public MissionManager() {
        instance = this;
    }

    public static Mission mission(String id) {
        return missions.get(id);
    }

}
