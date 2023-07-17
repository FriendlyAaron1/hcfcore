package org.ayple.hcfcore.commands.staff;

import org.ayple.hcfcore.commands.SubCommand;
import org.bukkit.entity.Player;

public class CommandVanish extends SubCommand {
    @Override
    public String getName() {
        return "vanish";
    }

    @Override
    public String getDescription() {
        return "vanish";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {

    }
}
