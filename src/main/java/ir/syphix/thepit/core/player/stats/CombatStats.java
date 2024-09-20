package ir.syphix.thepit.core.player.stats;

public class CombatStats {

    double bounty;
    int kills;
    int deaths;
    int assists;
    int killStreak;
    int highestKillSteak;
    int swordHits;
    int arrowHits;
    int arrowShoots;
    double damageDealt;
    double meleeDamageDealt;
    double arrowDamageDealt;

    double damageTaken;
    double meleeDamageTaken;
    double arrowDamageTaken;
    int blockPlaced;
    int lavaBucketPlaced;


    public CombatStats(double bounty, int blockPlaced, int lavaBucketPlaced,
                       double arrowDamageTaken, double meleeDamageTaken, double damageTaken,
                       double arrowDamageDealt, double meleeDamageDealt, double damageDealt,
                       int arrowShoots, int arrowHits, int swordHits,
                       int highestKillSteak, int killStreak, int assists,
                       int deaths, int kills) {


        this.bounty = bounty;
        this.blockPlaced = blockPlaced;
        this.lavaBucketPlaced = lavaBucketPlaced;
        this.arrowDamageTaken = arrowDamageTaken;
        this.meleeDamageTaken = meleeDamageTaken;
        this.damageTaken = damageTaken;
        this.arrowDamageDealt = arrowDamageDealt;
        this.meleeDamageDealt = meleeDamageDealt;
        this.damageDealt = damageDealt;
        this.arrowShoots = arrowShoots;
        this.arrowHits = arrowHits;
        this.swordHits = swordHits;
        this.highestKillSteak = highestKillSteak;
        this.killStreak = killStreak;
        this.assists = assists;
        this.deaths = deaths;
        this.kills = kills;


    }
}
