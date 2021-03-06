package org.sapphon.minecraft.modding.minecraftpython.spells;

import org.sapphon.minecraft.modding.minecraftpython.command.SpellInterpreter;
import org.sapphon.minecraft.modding.minecraftpython.network.JavaGameLoopRunnable;


public class ThreadFactory {

    public static Thread makeSpellThread(ISpell spell,
                                         SpellInterpreter interpreter) {
        return new Thread(new SpellCastingRunnable(spell, interpreter));
    }

    public static Thread makeJavaGameLoopThread() {
        return new Thread(new JavaGameLoopRunnable(new SpellInterpreter()));
    }
}
