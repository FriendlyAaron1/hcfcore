package org.ayple.hcfcore.core.claims;

import org.bukkit.Location;

import java.util.Hashtable;
import java.util.UUID;

public class Selection {
    private Location pos1;
    private Location pos2;

    public Selection(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public Location getPos1() { return this.pos1; }
    public Location getPos2() { return this.pos2; }

    public void setPos1(Location pos1) { this.pos1 = pos1; }
    public void setPos2(Location pos2) { this.pos2 = pos2; }

}
