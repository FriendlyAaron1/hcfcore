package org.ayple.hcfcore.core.claims;

import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.HashSet;

public class ClaimsHandler {

    public static HashSet<String> chunksWithClaims = new HashSet<String>();


    // The idea that I came up with which I think is fairly efficient is that there's
    // this set which stores the IDs of the chunks that have A CLAIM in them.
    // I can then look up the claims within the area in the database and
    // compare the bounding boxes to the players position.
    // Only storing the IDs should save a massive amount of memory and be
    // very efficient.
    //
    //  TLDR:
    //  Chunk has claims? -> look up the claims on db -> calculate if players in bounding boxes of any
    //




    public static boolean chunkHasClaim(Chunk chunk) {
        String chunkID = ChunkHandler.getChunkID(chunk);
        return chunksWithClaims.contains(chunkID);
    }
}
