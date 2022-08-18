package adariel.mysql.models;

import java.util.Date;

public class PlayerStats {
    private String uuid;
    private int deaths;
    private int kills;
    private long blocksBroken;

    private double balance;
    private Date lastLogin;
    private Date lastLogout;

    public PlayerStats(String uuid, int deaths, int kills, long blocksBroken, double balance, Date lastLogin, Date lastLogout) {
        this.uuid = uuid;
        this.deaths = deaths;
        this.kills = kills;
        this.balance = balance;
        this.blocksBroken = blocksBroken;
        this.lastLogin = lastLogin;
        this.lastLogout = lastLogout;
    }

    //Getters

    public String getUuid() {
        return uuid;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getKills() {
        return kills;
    }

    public long getBlocksBroken() {
        return blocksBroken;
    }

    public double getBalance() {
        return balance;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public Date getLastLogout() {
        return lastLogout;
    }


    //Setters


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setBlocksBroken(long blocksBroken) {
        this.blocksBroken = blocksBroken;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLastLogout(Date lastLogout) {
        this.lastLogout = lastLogout;
    }
}
