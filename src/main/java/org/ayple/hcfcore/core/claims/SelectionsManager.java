package org.ayple.hcfcore.core.claims;

import org.ayple.hcfcore.events.PlayerUseEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.UUID;

public class SelectionsManager {
    private static Hashtable<UUID, Selection> selections = new Hashtable<UUID, Selection>();



    public static void addPos1(Player player, Location location) {
        UUID id = player.getUniqueId();
        if (selections.containsKey(id)) {
            selections.get(id).setPos1(location);
        }


        selections.put(id, new Selection(location, null));
    }

    public static void addPos2(Player player, Location location) {
        UUID id = player.getUniqueId();
        if (selections.containsKey(id)) {
            selections.get(id).setPos2(location);
        }


        selections.put(id, new Selection(null, location));
        PlayerUseEvent.placePillar(
                Bukkit.getWorld("world"),
                player,
                (int) location.getX(),
                (int) location.getZ()
        );
    }

    public static void clearAnySelectionPlayerHas(Player player) {
        UUID id = player.getUniqueId();
        selections.remove(id);

        // TODO: remove pillars
    }

    public static Selection getSelection(Player player) {
        return selections.get(player.getUniqueId());
    }

}
