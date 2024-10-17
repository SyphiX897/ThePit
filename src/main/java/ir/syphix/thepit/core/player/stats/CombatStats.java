package ir.syphix.thepit.core.player.stats;

import org.bukkit.Material;

public class CombatStats {

    private double bounty;
    private long kills;
    private long deaths;
    private long assists;
    private long killStreak;
    private long highestKillSteak;
    private long swordHits;
    private long arrowHits;
    private long arrowShoots;
    private double damageDealt;
    private double meleeDamageDealt;
    private double arrowDamageDealt;
    private double damageTaken;
    private double meleeDamageTaken;
    private double arrowDamageTaken;
    private long blockPlaced;
    private long lavaBucketPlaced;


    public CombatStats(double bounty, long kills, long deaths, long assists, long killStreak, long highestKillSteak,
                       long swordHits, long arrowHits, long arrowShoots, double damageDealt, double meleeDamageDealt,
                       double arrowDamageDealt, double damageTaken, double meleeDamageTaken, double arrowDamageTaken,
                       long blockPlaced, long lavaBucketPlaced) {

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

    public double bounty() {
        return bounty;
    }

    public long kills() {
        return kills;
    }

    public long deaths() {
        return deaths;
    }

    public long assists() {
        return assists;
    }

    public long killStreak() {
        return killStreak;
    }

    public long highestKillSteak() {
        return highestKillSteak;
    }

    public long swordHits() {
        return swordHits;
    }

    public long arrowHits() {
        return arrowHits;
    }

    public long arrowShoots() {
        return arrowShoots;
    }

    public double damageDealt() {
        return damageDealt;
    }

    public double meleeDamageDealt() {
        return meleeDamageDealt;
    }

    public double arrowDamageDealt() {
        return arrowDamageDealt;
    }

    public double damageTaken() {
        return damageTaken;
    }

    public double meleeDamageTaken() {
        return meleeDamageTaken;
    }

    public double arrowDamageTaken() {
        return arrowDamageTaken;
    }

    public long blockPlaced() {
        return blockPlaced;
    }

    public long lavaBucketPlaced() {
        return lavaBucketPlaced;
    }

    public void setBounty(double bounty) {
        this.bounty = bounty;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public void setAssists(long assists) {
        this.assists = assists;
    }

    public void setKillStreak(long killStreak) {
        this.killStreak = killStreak;
    }

    public void setHighestKillSteak(long highestKillSteak) {
        this.highestKillSteak = highestKillSteak;
    }

    public void setSwordHits(long swordHits) {
        this.swordHits = swordHits;
    }

    public void setArrowHits(long arrowHits) {
        this.arrowHits = arrowHits;
    }

    public void setArrowShoots(long arrowShoots) {
        this.arrowShoots = arrowShoots;
    }

    public void setDamageDealt(double damageDealt) {
        this.damageDealt = damageDealt;
    }

    public void setMeleeDamageDealt(double meleeDamageDealt) {
        this.meleeDamageDealt = meleeDamageDealt;
    }

    public void setArrowDamageDealt(double arrowDamageDealt) {
        this.arrowDamageDealt = arrowDamageDealt;
    }

    public void setDamageTaken(double damageTaken) {
        this.damageTaken = damageTaken;
    }

    public void setMeleeDamageTaken(double meleeDamageTaken) {
        this.meleeDamageTaken = meleeDamageTaken;
    }

    public void setArrowDamageTaken(double arrowDamageTaken) {
        this.arrowDamageTaken = arrowDamageTaken;
    }

    public void setBlockPlaced(long blockPlaced) {
        this.blockPlaced = blockPlaced;
    }

    public void setLavaBucketPlaced(long lavaBucketPlaced) {
        this.lavaBucketPlaced = lavaBucketPlaced;
    }
}
