package org.sapphon.minecraft.modding.minecraftpython;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class MinecraftPythonCreativeTab extends ItemGroup {

	private Item itemIcon;

	public MinecraftPythonCreativeTab(String tabLabel, Item itemIcon) {
		super(tabLabel);
		this.itemIcon = itemIcon;
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(itemIcon);
	}
}