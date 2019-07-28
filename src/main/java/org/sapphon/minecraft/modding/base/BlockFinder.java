package org.sapphon.minecraft.modding.base;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockFinder {
    public static Block getBlockWithName(String name) {
        Block registryResult = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name.toLowerCase()));
        return registryResult == null ? Blocks.DIRT : registryResult;
    }

}
