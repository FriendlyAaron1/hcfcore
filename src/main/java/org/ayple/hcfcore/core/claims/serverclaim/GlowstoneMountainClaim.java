package org.ayple.hcfcore.core.claims.serverclaim;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.core.claims.Selection;
import org.ayple.hcfcore.core.claims.ServerClaim;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class GlowstoneMountainClaim extends ServerClaim {
    private Claim glowstoneClaim;
    private Selection glowstoneRespawnArea;

    private static GlowstoneMountainClaim instance;

    public GlowstoneMountainClaim() {
        // singletons woo
        if (instance != this && instance != null) {
            return;
        }

        instance = this;
        int claim_corner_1_x = ConfigHelper.getConfig().getInt("server_claims.glowstone_mountain.claim_corner_1_x");
        int claim_corner_1_z = ConfigHelper.getConfig().getInt("server_claims.glowstone_mountain.claim_corner_1_z");
        int claim_corner_2_x = ConfigHelper.getConfig().getInt("server_claims.glowstone_mountain.claim_corner_2_x");
        int claim_corner_2_z = ConfigHelper.getConfig().getInt("server_claims.glowstone_mountain.claim_corner_2_z");
        double respawn_corner_1_x = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_1_x");
        double respawn_corner_1_y = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_1_y");
        double respawn_corner_1_z = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_1_z");
        double respawn_corner_2_x = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_2_x");
        double respawn_corner_2_y = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_2_y");
        double respawn_corner_2_z = ConfigHelper.getConfig().getDouble("server_claims.glowstone_mountain.glowstone_respawn_area.corner_2_z");


        this.glowstoneClaim = new Claim(Bukkit.getWorld("nether"), "Glowstone Mountain", claim_corner_1_x, claim_corner_1_z, claim_corner_2_x, claim_corner_2_z);
        //this.glowstoneRespawnArea = new Selection(new Location(respawn_corner_1_x, respawn_corner_1_y, respawn_corner_1_z), new Location(respawn_corner_2_x, respawn_corner_2_y, respawn_corner_2_z));


    }
}
