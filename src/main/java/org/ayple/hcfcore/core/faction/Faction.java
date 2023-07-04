package org.ayple.hcfcore.core.faction;

import org.ayple.hcfcore.core.claims.Claim;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Faction {
    private final UUID factionID;
    public UUID getFactionID() { return this.factionID; }

    private final String factionName;
    public String getFactionName() { return this.factionName; }

    private final Location factionHQ;
    public Location getFactionHQ() { return this.factionHQ; }

    private Claim claim;
    private final float dtr;


    private Set<Player> factionMembers;
    public Set<Player> getFactionMembers() { return this.factionMembers; }


    private HashSet<UUID> factionInvites;
    public HashSet<UUID> getFactionInvites() { return this.factionInvites; }


    public Faction(UUID faction_id, String faction_name, float dtr, Location faction_hq, Claim claim) {
        this.factionID = faction_id;
        this.factionName = faction_name;
        this.factionHQ = faction_hq;
        this.claim = claim;
        this.dtr = dtr;
    }

    public int getOnlineSize() {
        return getOnlinePlayers().size();
    }

    public Set<Player> getOnlinePlayers() {
        return factionMembers.stream()
                .filter(OfflinePlayer::isOnline)
                .map(p -> Bukkit.getPlayer(p.getUniqueId()))
                .collect(Collectors.toSet());
    }

}
