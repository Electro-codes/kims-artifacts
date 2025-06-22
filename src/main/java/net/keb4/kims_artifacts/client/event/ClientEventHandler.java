package net.keb4.kims_artifacts.client.event;

import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.client.keybind.Keybinds;
import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.item.artifacts.SMRItem;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.c2s.OpenPotionBagPacket;
import net.keb4.kims_artifacts.network.c2s.PotionMixPacket;
import net.keb4.kims_artifacts.network.c2s.SMRStrongExplosionPacket;
import net.keb4.kims_artifacts.network.c2s.SMRWeakExplosionPacket;
import net.keb4.kims_artifacts.sound.SoundRegistry;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onKey(InputEvent.Key key)
    {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null)
        {
            if (Keybinds.SMR_SWITCH.consumeClick() && CurioHelper.getArtifactCurio(Minecraft.getInstance().player).getItem() instanceof SMRItem)
            {
                Keybinds.SMR_strongSelected = !Keybinds.SMR_strongSelected;
                if (Keybinds.SMR_strongSelected)
                {
                    Minecraft.getInstance().player.playSound(SoundRegistry.SMR_SWITCHTO_WEAK.get());
                } else
                {
                    Minecraft.getInstance().player.playSound(SoundRegistry.SMR_SWITCHTO_STRONG.get());
                }
            }
            if (Keybinds.ARTIFACT_ACTIVATE.consumeClick())
            {

                ItemStack artifact = CurioHelper.getArtifactCurio(Minecraft.getInstance().player);

                Main.LOGGER.info("artifact: {}", artifact.getDisplayName().getString());

                if (artifact == null || artifact == ItemStack.EMPTY) return;
                Item type = artifact.getItem();

                if (type instanceof SMRItem)
                {
                    if (Keybinds.SMR_strongSelected)
                    {
                        PacketNetwork.sendToServer(new SMRStrongExplosionPacket());
                    } else
                    {
                        PacketNetwork.sendToServer(new SMRWeakExplosionPacket());
                    }
                } else if (type instanceof PotionBagItem)
                {
                    PacketNetwork.sendToServer(new OpenPotionBagPacket());
                }
            }
        }
    }








}
