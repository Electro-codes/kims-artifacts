package net.keb4.kims_artifacts.client.event;

import net.keb4.kims_artifacts.client.keybind.Keybinds;
import net.keb4.kims_artifacts.client.util.ParticleHelper;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.c2s.SMRWeakExplosionPacket;
import net.keb4.kims_artifacts.util.RayUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onKey(InputEvent.Key key)
    {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null)
        {
            if (Keybinds.SMR_SMALL_EXPLOSION.consumeClick())
            {
                HitResult r = RayUtils.simpleBlockRay(Minecraft.getInstance().player, 20.0D, false);
                ParticleHelper.sphere(r.getLocation(), Minecraft.getInstance().level);
                PacketNetwork.sendToServer(new SMRWeakExplosionPacket());
                Minecraft.getInstance().player.sendSystemMessage(MutableComponent.create(ComponentContents.EMPTY).append("pew").withStyle(ChatFormatting.AQUA));
            }
        }
    }





}
