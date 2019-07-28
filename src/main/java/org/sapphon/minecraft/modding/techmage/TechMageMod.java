package org.sapphon.minecraft.modding.techmage;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.sapphon.minecraft.modding.base.ModConfigurationFlags;
import org.sapphon.minecraft.modding.minecraftpython.ScriptLoaderConstants;

public class TechMageMod {
	public static final String MODID = "techmage";
	public static final String VERSION = "1.14.4-0.5.0";

	public static TechMageMod instance;

	public void preInit(FMLPreInitializationEvent event) {

		if (isEnabled()) {
			if(!ScriptLoaderConstants.resourcePathExists()){
				ScriptLoaderConstants.setResourcePath(event);
			}
				for (MagicWand wand : ArcaneArmory.SINGLETON().getWands()) {
					wand.setRegistryName(new ResourceLocation("techmage", wand.getUnlocalizedName()));
					ForgeRegistries.ITEMS.register(wand);
					String wandDisplayName = wand.getSpell().getDisplayName();
					if (wandDisplayName != SpellMetadataConstants.NONE) {
						/*LanguageRegistry.instance().addStringLocalization(
								wand.getUnlocalizedName() + ".name", "en_US",
								wandDisplayName);*/
					}
				}
		}
	}

	private boolean isEnabled() {
		return ModConfigurationFlags.SPELLCRAFTERS();
	}

}