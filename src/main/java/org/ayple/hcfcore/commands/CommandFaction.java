package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.core.Faction;
import org.ayple.hcfcore.helpers.PlayerHelpers;
import org.ayple.hcfcore.helpers.SQLHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CommandFaction implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = PlayerHelpers.GetPlayerInstanceFromCommand(commandSender);
        Faction faction = null;
        if (player == null) return false;
        if (args.length == 1) {
            displayHelpInfo();
            return true;
        }

        try {
            switch (args[1]) {
                case "help":
                    break;

                case "create":
                    if (args.length >= 3) {
                        createFaction(player, args[2]);
                        return true;
                    }

                    return false;

                case "disband":
                    faction = Faction.getFactionFromPlayer(player);
                    if (faction != null) {
                        deleteFaction(faction.getFactionID());
                        return true;
                    }

                    return false;


                case "claim":
                    return true;

                case "who":
                    if (args.length == 2) {
                        faction = Faction.getFactionFromName(args[1]);
                        if (faction == null) return false; // unknown faction
                        displayFactionInfoToPlayer(player, faction);
                        return true;
                    }

                    faction = Faction.getFactionFromName(args[2]);
                    if (faction == null) return false;

                    displayFactionInfoToPlayer(player, faction);
                    return true;

                case "ally":
                    player.sendMessage("Allies coming soon!");
                    return true;
            }
        } catch (Exception e) {
            player.sendMessage(e.toString());
        }

        return true;
    }


    public void displayHelpInfo() {

    }

    public void deleteFaction(String factionID) {
        SQLHelper.querydb("DROP FROM factions WHERE id='" + factionID + "'");
    }

    protected void displayFactionInfoToPlayer(Player player, Faction faction) {
        player.sendMessage("Faction Name: " + faction.getFactionName());
        player.sendMessage("Faction HQ: " + faction.getFactionHQ().toString());
    }

    public void createFaction(Player player, String faction_name) throws Exception {
        // check if faction name is free
        ResultSet result = SQLHelper.querydb("SELECT id FROM factions WHERE faction_name='" + faction_name + "'");
        if (result.isBeforeFirst()) {
            throw new Exception("Faction name is already taken!");
        }

        // if free assign player to faction as leader
        String faction_id = UUID.randomUUID().toString();
        String player_id = player.getUniqueId().toString();
        SQLHelper.querydb("INSERT INTO factions (id, faction_name, dtr) VALUES ('" + faction_id + "'" + ",'" + faction_name + "'," + "1.01)");
        SQLHelper.querydb("INSERT INTO members VALUES (faction_id, player_uuid, rank) VALUES ('" + faction_id + "','" + player_id + "'," + "4)");
    }


}