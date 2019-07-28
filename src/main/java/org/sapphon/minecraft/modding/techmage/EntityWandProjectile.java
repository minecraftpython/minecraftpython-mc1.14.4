package org.sapphon.minecraft.modding.techmage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.sapphon.minecraft.modding.minecraftpython.problemhandlers.JavaProblemHandler;

public class EntityWandProjectile extends EggEntity {

    private static double speed = 1.15;

    public EntityWandProjectile(World par1World, LivingEntity par2EntityLivingBase, MagicWand magicWand,
                                boolean isInaccurate) {
        super(par1World, par2EntityLivingBase);
        this.wand = magicWand;
        if (isInaccurate)
            randomizeVelocity();
        this.setMotion(par2EntityLivingBase.getLookVec().x * speed, par2EntityLivingBase.getLookVec().y * speed, par2EntityLivingBase.getLookVec().z * speed);
    }

    private MagicWand wand;

    @Override
    protected void onImpact(RayTraceResult raytraceHit) {
        Vec3d hitLocation = raytraceHit.getHitVec();
        if (this.wand == null) {
            JavaProblemHandler.printErrorMessageToDialogBox(new Exception(
                    "Problems with spell projectile not understanding which wand shot it."));
        } else if (hitLocation == null) {
            JavaProblemHandler.printErrorMessageToDialogBox(new Exception(
                    "Problems with the projectile knowing where it landed."));
        }
        if (this.world.isRemote) {
            wand.spellInterpreter.setupImpactVariablesInPython(hitLocation);
            wand.castStoredSpell();
            this.remove();
        }
    }

    private void randomizeVelocity() {
        double xVelRandom = Math.random() + 0.5f;
        double yVelRandom = Math.random() + 0.5f;
        double zVelRandom = Math.random() + 0.5f;
        Vec3d motion = this.getMotion();
        this.setVelocity(motion.x * xVelRandom, motion.y * yVelRandom, motion.z * zVelRandom);
    }

}
