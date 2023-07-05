package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.core.faction.FactionMemberRank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class CommandFactionWho extends SubCommand {
    @Override
    public String getName() {
        return "who";
    }

    @Override
    public String getDescription() {
        return "show information about a faction";
    }

    @Override
    public String getSyntax() {
        return "/f who [faction name]";
    }


    // TODO: I hate this function. Need to do it better - Adam 05/07/23
    @Override
    public void perform(Player player, String[] args) {
        try {
            if (args.length > 2) {
                Faction target_faction = FactionManager.getFaction(args[2]);
                if (target_faction == null) {
                    player.sendMessage("Faction name wasn't fount.");
                    return;
                }

                player.sendMessage("Name: " + target_faction.getFactionName());
                player.sendMessage("HQ: " + target_faction.getFactionHQ().toString());
                player.sendMessage("Balance: " + target_faction.getFactionBal().toString());

                // usernames
                ArrayList<String> leaders = new ArrayList<String>();;
                ArrayList<String> coleaders = new ArrayList<String>();;
                ArrayList<String> officers = new ArrayList<String>();;
                ArrayList<String> members = new ArrayList<String>();
                //

                target_faction.getFactionMembers().forEach((id, rank) -> {
                    switch (rank) {
                        case 0:  members.add(Bukkit.getOfflinePlayer(id).getName());  break;
                        case 1:  officers.add(Bukkit.getOfflinePlayer(id).getName());  break;
                        case 2:  coleaders.add(Bukkit.getOfflinePlayer(id).getName());  break;
                        case 3:  leaders.add(Bukkit.getOfflinePlayer(id).getName());  break;
                    }
                });

                player.sendMessage("Leader: " + leaders.toString());
                player.sendMessage("Co-Leaders: " + coleaders.toString());
                player.sendMessage("officers: " + officers.toString());
                player.sendMessage("Members: " + members.toString());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
