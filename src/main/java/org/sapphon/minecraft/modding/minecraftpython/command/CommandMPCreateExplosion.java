package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.world.Explosion;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import org.sapphon.minecraft.modding.base.ServerGetter;

public class CommandMPCreateExplosion extends CommandMinecraftPythonServer {

    public int x;
    public int y;
    public int z;
    public int size;

    public CommandMPCreateExplosion(int x, int y, int z, int size) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
    }

    public CommandMPCreateExplosion(double x, double y, double z,
                                    double size) {
        this((int) x, (int) y, (int) z, (int) size);
    }

    public CommandMPCreateExplosion(String[] commandAndArgs) {
        this(Double.parseDouble(commandAndArgs[1]),
                Double.parseDouble(commandAndArgs[2]),
                Double.parseDouble(commandAndArgs[3]),
                Double.parseDouble(commandAndArgs[4]));
    }

    public void doWork() {
        ServerWorld worldserver = ServerGetter.getServer()
                .getWorld(DimensionType.OVERWORLD); // TODO ONLY WORKS IN OVERWORLD FOR
        // NOW
        worldserver.createExplosion(null, x, y, z, size, Explosion.Mode.DESTROY);
    }

    @Override
    public String serialize() {
        return CommandMinecraftPythonServer.CREATEEXPLOSION_NAME + CommandMinecraftPythonAbstract.SERIAL_DIV + x + CommandMinecraftPythonAbstract.SERIAL_DIV + y + CommandMinecraftPythonAbstract.SERIAL_DIV + z + CommandMinecraftPythonAbstract.SERIAL_DIV + size;
    }

}