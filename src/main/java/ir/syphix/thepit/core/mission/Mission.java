package ir.syphix.thepit.core.mission;

import java.util.HashMap;

public interface Mission {

    public String id();
    public String displayName();
    public MissionType missionType();
    public int goal();
}
