package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionInvite;
import org.ayple.hcfcore.core.faction.FactionInviteManager;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionJoin extends SubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "join a faction";
    }

    @Override
    public String getSyntax() {
        return "/f join [faction name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        try{
            if (args.length <= 2) return;
            if (FactionManager.getFactionFromPlayerID(player.getUniqueId()).equals(null)) return; // player not in faction

            Faction target_faction = FactionManager.getFaction(args[2]);
            if (target_faction == null) {
                player.sendMessage("That faction doesnt exist");
                return;
            }

            FactionInvite invite = new FactionInvite(target_faction.getFactionID(), player.getUniqueId());
            if (FactionInviteManager.lookForInvite(invite)) {
                FactionInviteManager.onPlayerJoinFaction(invite);
                player.sendMessage("Joined " + target_faction.getFactionName());
                return;
            }

            player.sendMessage("There is no faction invite");
        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR");
        }
    }
}
