package org.ayple.hcfcore.commands;


import org.bukkit.entity.Player;

// https://www.youtube.com/watch?v=WyFN_jTS4nU
public abstract class SubCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract boolean perform(Player player, String[] args);
}
