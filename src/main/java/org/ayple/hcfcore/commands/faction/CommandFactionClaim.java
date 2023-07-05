package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.claims.SelectionsManager;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.core.items.ClaimWand;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionClaim extends SubCommand {

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getDescription() {
        return "gives a claiming wand for claiming land";
    }

    @Override
    public String getSyntax() {
        return "/f claim";
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            if (FactionManager.playerInFaction(player.getUniqueId())) {
                if (FactionManager.isPlayerLeader(player.getUniqueId())) {
                    player.getInventory().addItem(ClaimWand.makeNewWand());
                    return;
                }

                player.sendMessage("You must be leader to be able to claim!");
                return;
            }

            player.sendMessage("You are not in a faction. Create one with /f create [name]");
        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR! CONTACT DEVELOPER ASAP.");
            return;
        }

//        if (player.getInventory().getItemInMainHand() == null) {
//            player.getInventory().addItem(ClaimWand.makeNewWand());
//            return;
//        }

    }
}
