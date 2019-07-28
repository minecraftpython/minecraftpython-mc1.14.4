package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.*;
import net.minecraft.entity.item.minecart.TNTMinecartEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.World;
import org.sapphon.minecraft.modding.minecraftpython.problemhandlers.JavaProblemHandler;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class EntityLookup {

	private static Map<String, Class> entityNameToClassMap = new HashMap<String, Class>() {
		{
			put("pig", PigEntity.class);
			put("skeleton", SkeletonEntity.class);
			put("horse", HorseEntity.class);
			put("creeper", CreeperEntity.class);
			put("cow", CowEntity.class);
			put("chicken", ChickenEntity.class);
			put("bat", BatEntity.class);
			put("arrow", ArrowEntity.class);
			put("boat", BoatEntity.class);
			put("endercrystal", EnderCrystalEntity.class);
			put("largefireball", FireballEntity.class);
			put("smallfireball", SmallFireballEntity.class);
			put("witherskull", WitherSkullEntity.class);
			put("fireworkrocket", FireworkRocketEntity.class);
			put("snowball", SnowballEntity.class);
			put("egg", EggEntity.class);
			put("xporb", ExperienceOrbEntity.class);
			put("minecart_tnt", TNTMinecartEntity.class);
			put("blaze", BlazeEntity.class);
			put("wolf", WolfEntity.class);
			put("ghast", GhastEntity.class);
			put("spider", SpiderEntity.class);
			put("witch", WitchEntity.class);
			put("iron_golem", IronGolemEntity.class);
			put("zombie", ZombieEntity.class);
			put("squid", SquidEntity.class);
			put("silverfish", SilverfishEntity.class);
			put("slime", SlimeEntity.class);
			put("witherboss", WitherEntity.class);
			put("enderdragon", EnderDragonEntity.class);
			put("ocelot", OcelotEntity.class);
			put("zombiepigman", ZombiePigmanEntity.class);
			put("enderman", EndermanEntity.class);
			put("magmacube", MagmaCubeEntity.class);
			put("sheep", SheepEntity.class);
			put("player", PlayerEntity.class);
		}
	};

	public static Class getPlayerClass() {
		return PlayerEntity.class;
	}

	private static Map<Class, String> classToEntityNameMap = new HashMap<Class, String>() {
		{
			for (String name : entityNameToClassMap.keySet()) {
				put(entityNameToClassMap.get(name), name);
			}
		}
	};

	public static Class getEntityClassByName(String name) {
		if (entityNameToClassMap.containsKey(name.toLowerCase())) {

			Class entityClass = entityNameToClassMap.get(name.toLowerCase());
			if (entityClass != null) {
				return entityClass;
			}
		}
		return TNTEntity.class;
	}

	public static Entity getEntityByName(String name, World worldserver) {
		// first, check our static list of entity names
		if (entityNameToClassMap.containsKey(name.toLowerCase())) {

			Class entityClass = entityNameToClassMap.get(name.toLowerCase());

			try {
				// TODO: THE BRITTLENESS IS UNBELIEVABLE
				Constructor constructor = entityClass
						.getConstructor(World.class);
				Object entityToSpawn = constructor.newInstance(worldserver);
				if (entityToSpawn instanceof ExperienceOrbEntity) {// TODO HAX: had to
															// add some xpValue
															// to an xpOrb or it
															// defaults to zero!
					ExperienceOrbEntity orb = (ExperienceOrbEntity) entityToSpawn;
					orb.xpValue = 5;
					entityToSpawn = orb;
				}

				return (Entity) entityToSpawn;

			} catch (Exception e) {
				JavaProblemHandler.printErrorMessageToDialogBox(e);
			}

		}
		return new TNTEntity(EntityType.TNT, worldserver);
	}

	public static String getNameByEntity(Entity entity) {
		Class<? extends Entity> classOfEntity = entity.getClass();
		if (classToEntityNameMap.containsKey(classOfEntity)) {
			return classToEntityNameMap.get(entity);
		}
		return "primed_tnt";

	}
}
