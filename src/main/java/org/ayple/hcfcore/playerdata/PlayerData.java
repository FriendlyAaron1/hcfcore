package org.ayple.hcfcore.playerdata;

import java.util.UUID;

public class PlayerData {
    public final UUID player_id;
    int balance;
    int kills;

    public PlayerData(UUID player_id, int balance, int kills) {
        this.player_id = player_id;
        this.balance = balance;
        this.kills = kills;
    }

    public int getKills() { return this.kills; }
    public void incrementKills() { this.kills++; }
    public int getBalance() { return this.balance; }
    public void setPlayerBalance(int new_value) { this.balance = new_value; }

}
