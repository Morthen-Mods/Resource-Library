package net.xstopho.resourcelibrary.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TreeTrimmingUtil {

    private List<BlockPos> scanned = new ArrayList<>();
    private Level level;

    public BlockPos getLastBlock(Level level, BlockPos startPos) {
        this.level = level;
        scanned = new ArrayList<>();

        return scanTree(startPos);
    }

    BlockPos scanTree(BlockPos pos) {
        if (!notVisited(pos)) return pos;

        scanned.add(pos);

        // gets the highest possible block
        if (hasBlockAbove(pos)) {
            // checks if the Tree is a 2x2 or bigger
            if (hasNeighbour(pos) && getAllNeighbours(pos).size() >= 3) {
                scanned.addAll(getAllNeighbours(pos));
            }
            pos = scanTree(pos.above());
        }

        if (!hasBlockAbove(pos) && hasNeighbour(pos)) pos = scanTree(getFirstNeighbour(pos));

        if (!hasBlockAbove(pos) && hasDiagonalNeighbour(pos)) pos = scanTree(getFirstDiagonalNeighbour(pos));

        if (!hasBlockAbove(pos) && hasBlockBelow(pos)) pos = scanTree(pos.below());

        return pos;
    }

    boolean hasBlockAbove(BlockPos pos) {
        return isLog(pos.above()) && notVisited(pos.above());
    }

    boolean hasBlockBelow(BlockPos pos) {
        return isLog(pos.below()) && notVisited(pos.below());
    }

    boolean hasNeighbour(BlockPos pos) {
        return !pos.equals(getFirstNeighbour(pos));
    }

    BlockPos getFirstNeighbour(BlockPos pos) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos block = pos.offset(x, 0, z);
                if (isLog(block) && notVisited(block)) return block;
            }
        }
        return pos;
    }

    boolean hasDiagonalNeighbour(BlockPos pos) {
        return !pos.equals(getFirstDiagonalNeighbour(pos));
    }

    BlockPos getFirstDiagonalNeighbour(BlockPos pos) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos block = pos.offset(x, 1, z);
                if (isLog(block) && notVisited(block)) return block;
            }
        }
        return pos;
    }

    List<BlockPos> getAllNeighbours(BlockPos pos) {
        List<BlockPos> neighbours = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos block = pos.offset(x, 0, z);
                if (isLog(block)) neighbours.add(block);
            }
        }
        return neighbours;
    }

    boolean notVisited(BlockPos pos) {
        return !scanned.contains(pos);
    }

    boolean isLog(BlockPos pos) {
        return level.getBlockState(pos).is(BlockTags.LOGS);
    }
}
