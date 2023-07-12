package org.ayple.hcfcore.core.claims.map;

import org.ayple.hcfcore.core.claims.ClaimPillar;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MapPillarsHandler {
    private static final HashMap<UUID, List<ClaimMap>> claims_showing = new HashMap<UUID, List<ClaimMap>>();

    public static void showClaimCorners(Player player, ClaimMap claimmap) {
        for (Location corner : claimmap.getCorners()) {
            for (int y = 70; y < 100; y++) {
                Location pos = new Location(player.getWorld(), corner.getX(), y, corner.getZ());
                Block block = player.getWorld().getBlockAt(pos);

                if (block.getType() == Material.AIR) {
                    player.sendBlockChange(pos, Material.GLASS, (byte) 0);
                    claimmap.addAffectedBlockPosition(pos);
                }
            }
        }

    }

    public static void hideClaimCorners(Player player, ClaimMap claimmap) {
        for (Location block : claimmap.getAffectedBlocks()) {
            player.sendBlockChange(block, Material.GLASS, (byte) 0);
            claimmap.removeAffectedBlockPosition(block);
        }
    }

}