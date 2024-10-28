package me.syphix.thepit.core.mission;

public interface Mission {

    public String id();
    public String displayName();
    public MissionType missionType();
    public int goal();
}
