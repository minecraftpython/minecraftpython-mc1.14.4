package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import org.sapphon.minecraft.modding.techmage.ArcaneArmory;

public class ItemLookup {
	public static Item getItemByName(String name, ServerWorld worldserver) {
		Item defaultResult = ForgeRegistries.ITEMS.getValue(new ResourceLocation(name.toLowerCase()));
		if(defaultResult != null){
			return defaultResult;
		}else if(ArcaneArmory.SINGLETON().hasWandWithSpellNamed(name)){
			return ArcaneArmory.SINGLETON().getWandBySpellName(name);
		}
		return Items.OAK_BOAT;
		
	}
}
