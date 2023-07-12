package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionRename extends SubCommand {
    @Override
    public String getName() {
        return "rename";
    }

    @Override
    public String getDescription() {
        return "rename the faction";
    }

    @Override
    public String getSyntax() {
        return "/f rename [name]";
    }

    @Override
    public boolean perform(Player player, String[] args) {
        if (!(args.length > 1)) return false;
        if (args[1].length() > 20) {
            player.sendMessage("name is too long");
        }

        try {
            Faction faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (faction == null) {
                player.sendMessage("You're not in a faction!");
                return true;
            }

            if (FactionManager.isPlayerLeader(faction, player.getUniqueId()) || FactionManager.isPlayerCoLeader(faction, player.getUniqueId())) {
                FactionManager.renameFaction(faction.getFactionName(), args[1]);
                player.sendMessage("Renamed faction!");
                return true;
            }

            player.sendMessage("You must be leader or co-leader");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR! CONSULT DEVELOPER ASAP");
            return true;
        }

    }
}
