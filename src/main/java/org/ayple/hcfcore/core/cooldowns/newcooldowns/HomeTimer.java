package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.core.cooldowns.AbstractCooldown;
import org.ayple.hcfcore.core.cooldowns.oldcooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HomeTimer extends AbstractCooldown {

    public HomeTimer(Player owner) {
        super(owner, 3600, "home_timer", ChatColor.LIGHT_PURPLE + "Home timer: ");
    }

    @Override
    public void onTimerFinished() {
        CooldownManager.onFinishedEnderpearlCooldown(this.getOwnerID());
    }

    @Override
    public void onTimerChanged() {

    }
}
