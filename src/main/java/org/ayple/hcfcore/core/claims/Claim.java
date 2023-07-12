package org.ayple.hcfcore.core.claims;

import org.ayple.hcfcore.core.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class Claim {

    private final UUID owner_faction_id;
    public UUID getOwnerFactionID() { return  this.owner_faction_id; }

    private final String faction_name;
    public String getFactionName() { return this.faction_name; }




    private final Location corner_1;
    private final Location corner_2;
    private final Cuboid bounding_box;

    public Cuboid getCuboid() {
        return this.bounding_box;
    }

    public Claim(UUID owner_faction_id, String faction_name, int corner_1_x, int corner_1_z, int corner_2_x, int corner_2_z) {
        World world = Bukkit.getWorld("world");
        this.corner_1 = new Location(world, corner_1_x, 0, corner_1_z);
        this.corner_2 = new Location(world, corner_2_x, world.getMaxHeight(), corner_2_z);
        this.owner_faction_id = owner_faction_id;
        this.faction_name = faction_name;

        this.bounding_box = new Cuboid(this.corner_1, this.corner_2);
    }

    public Claim(UUID owner_faction_id, String faction_name, Location corner_1, Location corner_2) {
        this.corner_1 = corner_1;
        this.corner_2 = corner_2;
        this.owner_faction_id = owner_faction_id;
        this.faction_name = faction_name;

        this.bounding_box = new Cuboid(corner_1, corner_2);
    }
}
