package org.sapphon.minecraft.modding.techmage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.sapphon.minecraft.modding.minecraftpython.IArcane;
import org.sapphon.minecraft.modding.minecraftpython.ScriptLoaderConstants;
import org.sapphon.minecraft.modding.minecraftpython.command.SpellInterpreter;
import org.sapphon.minecraft.modding.minecraftpython.spells.ISpell;
import org.sapphon.minecraft.modding.minecraftpython.spells.SpellCastingRunnable;
import org.sapphon.minecraft.modding.minecraftpython.spells.SpellThreadFactory;
import org.sapphon.minecraft.modding.techmage.wands.WandType;

import java.io.File;

public class MagicWand extends Item implements IArcane {
	private WandType wandType;
	private ISpell storedSpell;
	public SpellInterpreter spellInterpreter;
	private long lastCast = 0;
	private int experienceLevelRequirement;

	public MagicWand(ISpell spell, int experienceLevelRequirement,
			ItemGroup creativeModeInventoryTab) {
		super(new Item.Properties().group(creativeModeInventoryTab).maxStackSize(1));
		this.experienceLevelRequirement = experienceLevelRequirement;
		storedSpell = spell;
		setRegistryName(spell);
		this.wandType = spell.getWandType();
		this.spellInterpreter = new SpellInterpreter();
	}

	private void setRegistryName(ISpell spell) {
		this.setRegistryName(TechMageMod.MODID,
				spell.getSpellShortName()
						+ ScriptLoaderConstants.WAND_TEXTURE_NAME_SUFFIX);
		//This is how wands were textured prior to the 1.8 update
		/*
		if (wandHasHardcodedTexture()) {
			this.setUnlocalizedName(TechMageMod.MODID + ":" +
					spell.getSpellShortName()
					+ ScriptLoaderConstants.WAND_TEXTURE_NAME_SUFFIX);
		} else if (spell.hasCustomTexture()) {
			String customTextureName = spell.getCustomTextureName();
			if (customTextureName.contains(":")) {
				this.setUnlocalizedName(customTextureName);
			} else {
				this.setUnlocalizedName("minecraft:" + customTextureName);
			}
		} else {
			this.setUnlocalizedName(WandTextureRepository.SINGLETON()
					.getNextWandTextureName());
		}*/
	}

	private boolean wandHasHardcodedTexture() {// TODO this needs to look at the
												// textures wrapped up in the
												// jar with the mod
		return new File(ScriptLoaderConstants.WAND_TEXTURE_LOCATION
				+ this.storedSpell.getSpellShortName()
				+ ScriptLoaderConstants.WAND_TEXTURE_NAME_SUFFIX + ".png")
				.exists();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity techmage, Hand hand) {
		if (techmage.experienceLevel < this.experienceLevelRequirement) {
			if (world.isRemote) {
				techmage.sendMessage(new StringTextComponent(
						"Not enough experience!"));
			}
		} else if (timer() > this.getSpell().getCooldownInMilliseconds()) {
			this.castSpellFromWand(techmage, world);
		}
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, techmage.getHeldItem(hand));
	}

	private long timer() {
		return System.currentTimeMillis() - lastCast;
	}

	public void castSpellFromWand(LivingEntity magicCaster, World world) {
		if (this.wandType.equals(WandType.LOCAL) && world.isRemote) {
			castStoredSpell();
		} else if (this.wandType.equals(WandType.PROJECTILE)) {
			world.addEntity(new EntityWandProjectile(world,
					magicCaster, this, false));
		} else if (this.wandType.equals(WandType.RAY) && world.isRemote) {
			RayTraceResult rayTrace = Minecraft.getInstance().getRenderViewEntity().func_213324_a(300, 1.0f, false);
			spellInterpreter.setupRayVariablesInPython(rayTrace);
			castStoredSpell();
		}
		lastCast = System.currentTimeMillis();
	}

	protected synchronized void castStoredSpell() {
		SpellThreadFactory.makeSpellThread(
				new SpellCastingRunnable(this.storedSpell, spellInterpreter))
				.start();
	}

	public int getExperienceLevelRequiredForUse() {
		return this.experienceLevelRequirement;
	}

	public ISpell getSpell() {
		return this.storedSpell;
	}

	@Override
	public void doMagic() {
		castStoredSpell();
	}
}
