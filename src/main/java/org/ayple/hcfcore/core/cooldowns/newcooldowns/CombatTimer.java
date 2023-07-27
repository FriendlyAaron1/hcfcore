package org.ayple.hcfcore.core.cooldowns.newcooldowns;

import org.ayple.hcfcore.core.cooldowns.AbstractCooldown;
import org.ayple.hcfcore.core.cooldowns.oldcooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CombatTimer extends AbstractCooldown {

    public CombatTimer(Player owner) {
        super(owner, 3600, "combat_timer", ChatColor.DARK_RED + "Combat Timer: ");
    }

    @Override
    public void onTimerFinished() {
        CooldownManager.onFinishedPvpTimer(this.getOwnerID());
    }

    @Override
    public void onTimerChanged() {

    }
}
