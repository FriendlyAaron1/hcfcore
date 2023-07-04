package org.ayple.hcfcore.core.hcfclasses;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class HcfClass {
    List<PotionEffect> classEffects;

    void giveEffectsToPlayer(Player player) {
        for (PotionEffect effect : classEffects) {
            player.addPotionEffect(effect);
        }
    }


}
