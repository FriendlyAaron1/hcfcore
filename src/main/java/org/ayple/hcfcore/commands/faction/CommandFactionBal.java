package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class CommandFactionBal extends SubCommand {
    @Override
    public String getName() {
        return "bal";
    }

    @Override
    public String getDescription() {
        return "get the faction balance";
    }

    @Override
    public String getSyntax() {
        return "/f bal";
    }

    @Override
    public boolean perform(Player player, String[] args) {
        return false;
    }
}
