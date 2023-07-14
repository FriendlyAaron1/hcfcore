package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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
        Faction target_faction = null;

        if (args.length == 1) {
            target_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (target_faction == null) {
                player.sendMessage("You are not in a faction.");
                return;
            }


        } else if (args.length > 1) {
            target_faction = NewFactionManager.getFaction(args[1]);

            if (target_faction == null) {
                player.sendMessage("Faction name wasn't fount.");
                return;
            }

        } else {
            player.sendMessage(ChatColor.RED + getSyntax());
            return;
        }

        String faction_name = target_faction.getFactionName();
        String members_size = Integer.toString(target_faction.getFactionMembersSize());
        String online_members = Integer.toString(target_faction.getOnlineMembersSize());
        Location hq = target_faction.getFactionHQ();
        String hq_x = null;
        String hq_z = null;
        if (hq != null) {
            hq_x = Double.toString(hq.getX());
            hq_z = Double.toString(hq.getZ());
        }

        String dtr = Faction.DTR_FORMAT.format(target_faction.getFactionDTR());
        String balance = target_faction.getFactionBal().toString();

        player.sendMessage("-----------------------------------------------------");

        if (hq != null) {
            player.sendMessage(ChatColor.GOLD + faction_name + "[" + online_members + "/" + members_size + "]" + ChatColor.YELLOW + " HQ:    " + hq_x + ", " + hq_z);
        } else {
            player.sendMessage(ChatColor.GOLD + faction_name + "[" + online_members + "/" + members_size + "]" + ChatColor.YELLOW + " HQ:    Not set");
        }


        // usernames
        ArrayList<String> leaders = new ArrayList<String>();;
        ArrayList<String> coleaders = new ArrayList<String>();;
        ArrayList<String> officers = new ArrayList<String>();;
        ArrayList<String> members = new ArrayList<String>();
        //

        target_faction.getFactionMembers().forEach((id, rank) -> {
            switch (rank) {
                case 0: members.add(getFactionMemberName(id)); break;
                case 1:  officers.add(getFactionMemberName(id));  break;
                case 2:  coleaders.add(getFactionMemberName(id));  break;
                case 3:  leaders.add(getFactionMemberName(id));  break;
            }
        });

        player.sendMessage(ChatColor.YELLOW + "Leader: " + ChatColor.WHITE + leaders.toString()
            .replace("[", "")
            .replace("]", "")
        );

        if (!coleaders.isEmpty()) player.sendMessage(ChatColor.YELLOW + "Co-Leaders: " + ChatColor.WHITE + coleaders.toString()
            .replace("[", "")
            .replace("]", "")
        );

        if (!officers.isEmpty()) player.sendMessage(ChatColor.YELLOW + "Officers: " + ChatColor.WHITE + officers.toString()
            .replace("[", "")
            .replace("]", "")
        );

        if (!members.isEmpty()) player.sendMessage(ChatColor.YELLOW + "Members: " + ChatColor.WHITE + members.toString()
            .replace("[", "")
            .replace("]", "")
        );

        player.sendMessage(ChatColor.YELLOW + "Balance: " + ChatColor.BLUE + "$" + balance);


        if (target_faction.getFactionDTR() > 0) {
            player.sendMessage(ChatColor.YELLOW + "dtr: " + ChatColor.GREEN + dtr);
        } else {
            player.sendMessage(ChatColor.YELLOW + "dtr: " + ChatColor.RED + dtr); // if raidable
        }

        player.sendMessage("-----------------------------------------------------");
    }

    private String getFactionMemberName(UUID id) {
        String name;
        OfflinePlayer target_player = Bukkit.getOfflinePlayer(id);
        if (target_player.isOnline()) {
            name = ChatColor.GREEN + target_player.getName();
        } else {
            name = ChatColor.GRAY + target_player.getName();
        }


        return name;
    }
}
