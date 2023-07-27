package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.core.cooldowns.AbstractCooldown;
import org.ayple.hcfcore.core.cooldowns.oldcooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EnderpearlCooldown extends AbstractCooldown {

    public EnderpearlCooldown(Player owner) {
        super(owner, 30, "epearlcooldown", ChatColor.LIGHT_PURPLE + "Endepearl Cooldown: ");
    }

    @Override
    public void onTimerFinished() {
        CooldownManager.onFinishedEnderpearlCooldown(this.getOwnerID());
    }

    @Override
    public void onTimerChanged() {

    }
}
