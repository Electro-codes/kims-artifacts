package net.keb4.kims_artifacts.network.c2s;


import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.capability.CapRegistry;
import net.keb4.kims_artifacts.container.PotionBagMenu;
import net.keb4.kims_artifacts.entity.damage.DamageTypes;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.item.artifacts.SMRItem;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.s2c.ManualDeltaSyncPacket;
import net.keb4.kims_artifacts.network.s2c.PotionBagProgressSyncPacket;
import net.keb4.kims_artifacts.network.s2c.ScreenShakePacket;
import net.keb4.kims_artifacts.network.s2c.effects.SMRStrongExplosionCallbackPacket;
import net.keb4.kims_artifacts.network.s2c.effects.SMRWeakExplosionCallbackPacket;
import net.keb4.kims_artifacts.sound.SoundRegistry;
import net.keb4.kims_artifacts.system.artifact.potion.PotionSysUtil;
import net.keb4.kims_artifacts.system.artifact.potion.ServerPotionBagManager;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.keb4.kims_artifacts.util.ExplosionHelper;
import net.keb4.kims_artifacts.util.RayUtils;
import net.keb4.kims_artifacts.world.SMRExplosionDamageCalculator;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @apiNote All methods inside this class are assumed to be run inside {@link NetworkEvent.Context#enqueueWork(Runnable)}
 * **/
public class ServerPacketHandlers {
    /**
     * Dictates the range from the sender where other players can receive callback packets.
     * **/
    public static final int defaultResponseRange = 16*4; //~4 chunks


    public static void handleSMRStrongExplosionPacket(SMRStrongExplosionPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ServerPlayer player = player(ctx);
        if (!(CurioHelper.getArtifactCurio(player).getItem() instanceof SMRItem)) return;
        HitResult hit = RayUtils.simpleEntityBlockRay(player, SMRItem.RAYCAST_RANGE, true);
        


            Vec3 pos;
            if (hit instanceof EntityHitResult hitE)
            {
                pos = hitE.getEntity().getPosition(1.0f).add(hitE.getEntity().getForward().scale(0.5));
            } else
            {
                pos = hit.getLocation();
            }

            ServerLevel server = player.serverLevel();

                player.addDeltaMovement(player.getLookAngle().scale(4).reverse());
                PacketNetwork.sendToPlayer(new ManualDeltaSyncPacket(player.getDeltaMovement()), player);
                ExplosionHelper.createAoeDamage(player, pos, 20, 3, DamageTypes.ARTIFACT.getSource(server, player));
                server.explode(player, DamageTypes.ARTIFACT.getSource(server, player), new SMRExplosionDamageCalculator(), pos.x, pos.y, pos.z, 2, false, Level.ExplosionInteraction.BLOCK, false);
                server.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z), SoundRegistry.SMR_STRONG_SHOOT.get(), SoundSource.BLOCKS);
                for (Player p : player.level().players()) {
                if (p.position().distanceToSqr(player.position()) <= defaultResponseRange * defaultResponseRange) {
                    PacketNetwork.sendToPlayer(new SMRStrongExplosionCallbackPacket(player.getId()), (ServerPlayer) p);
                    //screen shake intensity diminishes based on player distance
                    float coeff = (float) (1/((0.01 * pos.distanceTo(p.position()))+1));
                    coeff = coeff < 0.2f ? 0 : coeff;
                    if (coeff > 0) {
                        PacketNetwork.sendToServer(new ScreenShakePacket(coeff*4, 2, "DEFAULT"));
                    }
                }
            }
    }

    public static void handleSMRWeakExplosionPacket(SMRWeakExplosionPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ServerPlayer player = player(ctx);
        if (!(CurioHelper.getArtifactCurio(player).getItem() instanceof SMRItem)) return;
        HitResult hit = RayUtils.simpleEntityBlockRay(player, SMRItem.RAYCAST_RANGE, true);
        ServerLevel server = player.serverLevel();
        
        Vec3 pos;
        if (hit instanceof EntityHitResult hitE)
        {
            pos = hitE.getEntity().getPosition(1.0f).add(hitE.getEntity().getForward().scale(0.5));
        } else
        {
            pos = hit.getLocation();
        }

        player.addDeltaMovement(player.getLookAngle().scale(1).reverse());
        PacketNetwork.sendToPlayer(new ManualDeltaSyncPacket(player.getDeltaMovement()), player);

        ExplosionHelper.createAoeDamage(player, pos, 8, 2, DamageTypes.ARTIFACT.getSource(server, player));
        server.playSound(null, new BlockPos((int) player.position().x, (int) player.position().y, (int) player.position().z), SoundRegistry.SMR_WEAK_SHOOT.get(), SoundSource.BLOCKS);


        for (Player p : player.level().players()) {
            if (p.position().distanceToSqr(player.position()) <= defaultResponseRange * defaultResponseRange) {
                PacketNetwork.sendToPlayer(new SMRWeakExplosionCallbackPacket(player.getId()), (ServerPlayer) p);
            }
        }

    }


    public static ServerPlayer player(Supplier<NetworkEvent.Context> ctx) {return ctx.get().getSender();}



    public static void handlePotionMixPacket(PotionMixPacket message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        ServerPlayer sender = contextSupplier.get().getSender();
        if (sender.containerMenu instanceof PotionBagMenu menu)
        {
            ItemStack stack = message.stack;

            if (!PotionSysUtil.Craft.isValid(menu)) return;

            if (!(stack.getItem() instanceof PotionBagItem)) {
                Main.LOGGER.error("Item is not a potionbagitem!");
                return;
            }
            ServerPotionBagManager.scheduleNewMix(sender, stack);
        }
    }

    public static void handleOpenPotionBagPacket(OpenPotionBagPacket message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        ServerPlayer sender = contextSupplier.get().getSender();
        if (!CurioHelper.wearingArtifactItem(sender, ItemRegistry.POTION_BAG_ITEM.get())) return;

        ItemStack bag = CurioHelper.getArtifactCurio(sender);
        // Get or create the ItemStackHandler for this item stack

        // Open the custom menu. The lambda provides the menu constructor with necessary data.
        NetworkHooks.openScreen((sender),new SimpleMenuProvider(
                (windowId, playerInventory, player) -> new PotionBagMenu(windowId, playerInventory, bag),
                Component.translatable("container." + Main.MODID + ".potion_bag") // Title for the screen
        ), (buf ->
        {
            buf.writeItem(bag);
        }));

    }
}
