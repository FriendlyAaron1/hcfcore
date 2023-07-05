package org.ayple.hcfcore.core.claims;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;

import java.util.UUID;

public class Claim {

    private UUID owner_faction_id;
    public UUID getOwnerFactionID() { return  this.owner_faction_id; }

    public void setOwnerFactionId(UUID id) { owner_faction_id = id; };

    private Location corner_1;
    private Location corner_2;
    private BoundingBox bounding_box;

    public BoundingBox getBoundingBox() {
        return this.bounding_box;
    }

    public Claim(int corner_1_x, int corner_1_z, int corner_2_x, int corner_2_z) {
        World world = Bukkit.getWorld("world");
        this.corner_1 = new Location(world, corner_1_x, 0, corner_1_z);
        this.corner_2 = new Location(world, corner_2_x, 0, corner_2_z);

        this.bounding_box = new BoundingBox(corner_1_x, world.getMinHeight(), corner_1_z, corner_2_x, world.getMaxHeight(), corner_2_z);
    }

    public Claim(Location corner_1, Location corner_2) {
        World world = Bukkit.getWorld("world");
        this.corner_1 = corner_1;
        this.corner_2 = corner_2;

        this.bounding_box = new BoundingBox(
                corner_1.getX(), world.getMinHeight(), corner_1.getZ(),
                corner_2.getX(), world.getMaxHeight(), corner_2.getZ()
        );
    }
}
