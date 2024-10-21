package ir.syphix.thepit.core.arena;

import java.util.Arrays;
import java.util.List;

public enum ArenaStatus {
    ENABLE,
    UPDATING,
    DISABLE;


    public static List<String> statusList() {
        return Arrays.stream(values()).map(String::valueOf).toList();
    }
}
