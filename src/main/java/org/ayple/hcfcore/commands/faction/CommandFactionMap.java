package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.Cuboid;
import org.ayple.hcfcore.core.claims.ClaimPillarManager;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.claims.map.ClaimMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandFactionMap extends SubCommand {
    @Override
    public String getName() {
        return "map";
    }

    @Override
    public String getDescription() {
        return "display the claim corners";
    }

    @Override
    public String getSyntax() {
        return "/f map";
    }

    @Override
    public boolean perform(Player player, String[] args) {

        player.sendMessage("COMING SOON!");
        return true;

//        ArrayList<Cuboid> claims =  ClaimsManager.getAllClaimCuboids();
//
//
//
//        for (Cuboid claim : claims) {
//            Location lower_corner = claim.getLowerNE();
//            Location upper_corner = claim.getUpperSW();
//
//            // get each corner of the cuboid basically
//            ClaimMap corners = new ClaimMap(player.getWorld(),
//                        lower_corner.getX(), lower_corner.getZ(),
//                         lower_corner.getX(), upper_corner.getZ(),
//                         upper_corner.getX(), upper_corner.getZ(),
//                         upper_corner.getX(), lower_corner.getZ()
//            );
//
//
//
//
//
//        }

//        return true;
    }


}
