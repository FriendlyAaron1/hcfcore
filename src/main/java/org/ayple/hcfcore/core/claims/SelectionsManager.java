package org.ayple.hcfcore.core.claims;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.UUID;

public class SelectionsManager {
    private static SelectionsManager INSTANCE;
    static Hashtable<UUID, Selection> selections = new Hashtable<UUID, Selection>();


    public SelectionsManager() {
        INSTANCE = this;
    }

    public void addPos1(Player player, Location location) {
        UUID id = player.getUniqueId();
        if (selections.containsKey(id)) {
            selections.get(id).setPos1(location);
        }


        selections.put(id, new Selection(location, null));
    }

    public void addPos2(Player player, Location location) {
        UUID id = player.getUniqueId();
        if (selections.containsKey(id)) {
            selections.get(id).setPos2(location);
        }


        selections.put(id, new Selection(null, location));
    }

    public static void clearAnySelectionPlayerHas(Player player) {
        UUID id = player.getUniqueId();

    }

}
