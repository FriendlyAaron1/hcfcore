package org.ayple.hcfcore.core.claims;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;

public class ChunkHandler {

    public static String getChunkID(Chunk chunk) {
        ChunkSnapshot snapshot = chunk.getChunkSnapshot();
        String x = Integer.toString(snapshot.getX());
        String z = Integer.toString(snapshot.getZ());
        return x + "." + z;
    }
}
