package ir.syphix.thepit.core.player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PitPlayerManager {

    private static final HashMap<UUID, PitPlayer> pitPlayers = new HashMap<>();

    private PitPlayerManager() {
    }

    public static PitPlayer pitPlayer(UUID uniqueId) {
        return pitPlayers.get(uniqueId);
    }

    public static List<PitPlayer> pitPlayers() {
        return pitPlayers.values().stream().toList();
    }

    public static void add(PitPlayer pitPlayer) {
        pitPlayers.put(pitPlayer.uniqueId(), pitPlayer);
    }

    public static void remove(UUID uniqueId) {
        pitPlayers.remove(uniqueId);
    }

    public static boolean exist(UUID uniqueId) {
        return pitPlayers.get(uniqueId) != null;
    }
}
