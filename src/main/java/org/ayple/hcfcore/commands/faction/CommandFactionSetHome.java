package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionInviteManager;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.core.faction.FactionMemberRank;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionSetHome extends SubCommand {
    @Override
    public String getName() {
        return "sethome";
    }

    @Override
    public String getDescription() {
        return "sets the faction home";
    }

    @Override
    public String getSyntax() {
        return "/f sethome";
    }

    @Override
    public boolean perform(Player player, String[] args) {
        try {
            Faction faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (faction == null) {
                player.sendMessage("You are not in a faction!");
                return true;
            }

            if (!ClaimsManager.playerOwnsClaim(player)) {
                player.sendMessage("You are not in your claim!");
                return true;
            }

            int player_rank = faction.getFactionMembers().get(player.getUniqueId());

            // for the life of me, i tried to get enums to work
            // someone with more java experience can do it icba
            // 30 mins of my life gone. idek why it wasnt working
            if (!(player_rank == 3 || player_rank == 2)) {
                player.sendMessage("You are not the leader or co-leader!");
                return true;
            }

            FactionManager.setFactionHQ(faction.getFactionID(), player.getLocation());
            player.sendMessage("Set faction HQ!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR EXECUTING THIS COMMAND. CONTACT DEVELOPER ASAP!");
            return true;
        }
    }
}
