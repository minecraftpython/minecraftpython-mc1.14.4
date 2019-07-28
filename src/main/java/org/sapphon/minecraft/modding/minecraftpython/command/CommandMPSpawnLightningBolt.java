package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import org.sapphon.minecraft.modding.base.ServerGetter;

public class CommandMPSpawnLightningBolt extends CommandMinecraftPythonServer {

	
	
	public CommandMPSpawnLightningBolt(String[] commandAndArgsToDeserialize) {
	}
	
	@Override
	public void doWork() {
		ServerWorld world = ServerGetter.getServer().getWorld(DimensionType.OVERWORLD);
			int x = -175;
			int z = 60;
			
			int y = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY() - 1;
			LightningBoltEntity entityLightningBolt = new LightningBoltEntity(world, x, y, z, false);
			world.addLightningBolt(entityLightningBolt);
	}
	@Override
	public String serialize() {
		return CommandMinecraftPythonServer.LIGHTNINGBOLT_NAME;
	}
}
