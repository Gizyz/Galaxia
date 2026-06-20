package com.gtnewhorizons.galaxia.client;

import com.gtnewhorizons.galaxia.registry.rocketmodules.rocket.blueprint.RocketBlueprint;
import com.gtnewhorizons.galaxia.registry.rocketmodules.rocket.entities.EntityRocket;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import micdoodle8.mods.galacticraft.api.transmission.tile.INetworkProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.MutablePair;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class RocketCollider {

    enum PlayerType {
        NONE, CLIENT, REMOTE, SERVER
    }
    private static MutablePair<WeakReference<EntityRocket>, Double> safetyLock = new MutablePair<>();
    private static Map<EntityRocket, Map<EntityPlayer, Double>> remoteSafetyLocks = new WeakHashMap<>();

    static void collideEntities(EntityRocket rocket) {
        World world = rocket.worldObj;
        RocketBlueprint blueprint = rocket.getBlueprint();
        AxisAlignedBB bounds = rocket.getBoundingBox();

        if (rocket == null) return;
        if (bounds == null) return;

        Vec3 rocketPosition = Vec3.createVectorHelper(rocket.posX, rocket.posY, rocket.posZ);
        Vec3 rocketMotion = Vec3.createVectorHelper(rocket.motionX, rocket.motionY, rocket.motionZ);

        if (world.isRemote && safetyLock.left != null && safetyLock.left.get() == rocket) {
            saveClientPlayerFromClipping(rocket, rocketMotion);
        }

        boolean skipClientPlayer = false;
        CollisionList denseViableColliders = new CollsionList();
    }

    private static int packetCooldown = 0;
    @OnlyIn(Dist.CLIENT)
    private static void saveClientPlayerFromClipping(EntityRocket rocket, Vec3 rocketMotion) {
        EntityClientPlayerMP entity = Minecraft.getMinecraft().thePlayer;
        Vec3 entityDeltaMovement = Vec3.createVectorHelper(entity.motionX, entity.motionY, entity.motionZ);
        if (entity.noClip) return;

        double prevDiff = safetyLock.right;
        double currentDiff = entity.posY - rocket.posY;
        double motion = rocketMotion.subtract(entityDeltaMovement).yCoord;
        double trend = Math.signum(currentDiff - prevDiff);

        NetHandlerPlayClient handler = entity.sendQueue;
        if (handler.playerInfoList.size() > 1) {
            if (packetCooldown > 0)
                packetCooldown--;
            if (packetCooldown == 0) {
                cot
            }
        }


    }
}
