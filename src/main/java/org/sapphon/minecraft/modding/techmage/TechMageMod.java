package org.sapphon.minecraft.modding.techmage;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.sapphon.minecraft.modding.base.ModConfigurationFlags;
import org.sapphon.minecraft.modding.minecraftpython.ScriptLoaderConstants;

public class TechMageMod {
	public static final String MODID = "techmage";
	public static final String VERSION = "1.14.4-0.5.0";

	public static TechMageMod instance;

	public TechMageMod(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doCommonInit);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientInit);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void doClientInit(FMLClientSetupEvent event){
		if (isEnabled()) {
			if (!ScriptLoaderConstants.resourcePathExists()) {
				ScriptLoaderConstants.setResourcePath(event);
			}
		}
	}

	public void doCommonInit(FMLCommonSetupEvent event) {
		if (isEnabled()) {
			registerWands();
		}
	}

	private void registerWands() {
		for (MagicWand wand : ArcaneArmory.SINGLETON().getWands()) {
			ForgeRegistries.ITEMS.register(wand);
			String wandDisplayName = wand.getSpell().getDisplayName();
			if (wandDisplayName != SpellMetadataConstants.NONE) {
				/*LanguageRegistry.instance().addStringLocalization(
						wand.getUnlocalizedName() + ".name", "en_US",
						wandDisplayName);*/
			}
		}
	}

	private boolean isEnabled() {
		return ModConfigurationFlags.SPELLCRAFTERS();
	}

}