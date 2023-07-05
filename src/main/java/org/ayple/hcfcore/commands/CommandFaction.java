package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.commands.faction.CommandFactionCreate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandFaction implements CommandExecutor {
    private ArrayList<SubCommand> subcommands = new ArrayList<SubCommand>();

    public CommandFaction() {
        subcommands.add(new CommandFactionCreate());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if (args.length > 0) {
            for (SubCommand subcommand : subcommands) {
                if (args[0].equalsIgnoreCase(subcommand.getName())) {
                    subcommand.perform(player, args);
                }
            }
        }
        return true;
    }
}
