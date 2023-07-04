package org.ayple.hcfcore.core.claims;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.UUID;


public class Claim {
    public int CLAIM_LIMIT = 500;
    public String ownerID; // this will be a faction uuid not player

    // should be set by the children otherwise it's assumed a faction claim
    public ClaimType claimType;
    public Location startCorner;
    public Location endCorner;

    public Claim(String owner, int corner_1_x, int corner_1_z, int corner_2_x, int corner_2_z) {
        this.ownerID = owner;
        this.startCorner =  new Location(Bukkit.getWorld("world"), corner_1_x, 0, corner_1_z);
        this.endCorner =  new Location(Bukkit.getWorld("world"), corner_2_x, 0, corner_2_z);
    }



}
