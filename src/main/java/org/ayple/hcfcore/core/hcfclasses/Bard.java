package org.ayple.hcfcore.core.hcfclasses;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;

public class Bard extends HcfClass {


    public Bard() {
        classEffects.add(PotionEffectType.SPEED.createEffect(PotionEffect.INFINITE_DURATION, 2));
        classEffects.add(PotionEffectType.REGENERATION.createEffect(PotionEffect.INFINITE_DURATION, 1));
        classEffects.add(PotionEffectType.DAMAGE_RESISTANCE.createEffect(PotionEffect.INFINITE_DURATION, 2));
    }

}
