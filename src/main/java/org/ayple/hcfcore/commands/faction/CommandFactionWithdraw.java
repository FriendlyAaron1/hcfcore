package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.BalanceHandler;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionWithdraw extends SubCommand {
    @Override
    public String getName() {
        return "withdraw";
    }

    @Override
    public String getDescription() {
        return "withdraw money from the faction";
    }

    @Override
    public String getSyntax() {
        return "/f withdraw [amount]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length >= 2) {
            Faction player_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (player_faction == null) {
                player.sendMessage("You are not in a faction!");
                return;
            }

            int faction_bal = player_faction.getFactionBal();
            int amount = Integer.parseInt(args[1]);
            //int player_bal = BalanceHandler.getPlayerBalance(player);

            if (faction_bal >= amount) {
                BalanceHandler.giveMoneyToPlayer(player, amount);
                BalanceHandler.takeMoneyFromFaction(player_faction.getFactionID(), amount);
                return;
            }

        }


    }
}
