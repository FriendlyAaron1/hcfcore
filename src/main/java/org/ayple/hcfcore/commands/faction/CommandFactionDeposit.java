package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.BalanceHandler;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionDeposit extends SubCommand {
    @Override
    public String getName() {
        return "deposit";
    }

    @Override
    public String getDescription() {
        return "deposit money into the faction balance";
    }

    @Override
    public String getSyntax() {
        return "/f deposit [amount]";
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            if (args.length > 2) {
                int amount = Integer.parseInt(args[2]);
                Faction player_faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
                if (player_faction == null) {
                    player.sendMessage("You are not in a faction!");
                    return;
                }

                if (BalanceHandler.getPlayerBalance(player) >= amount) {
                    BalanceHandler.takeMoneyFromPlayer(player, amount);
                    BalanceHandler.giveMoneyToFaction(player_faction.getFactionID(), amount);
                    player.sendMessage("Deposited " + Integer.toString(amount) + " into the faction balance!");
                    return;
                }

                player.sendMessage("Insufficient funds");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR");
        }

    }
}
